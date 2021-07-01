#Punto 2
#La facturación se realizará por un proceso automático en la base de datos. Se
#debe programar este proceso para el primer día de cada mes y debe generar una
#factura por medidor y debe tomar en cuenta todas las mediciones no facturadas
#para cada uno de los medidores, sin tener en cuenta su fecha. La fecha de vencimiento de
#esta factura será estipulado a 15 días.

drop procedure if exists m_billing;
delimiter $$
create procedure m_billing(IN p_id_residence INT)
begin
    declare p_id_meter int;
    declare p_id_measurement int;
    declare id_tariff int;
    declare valueM float;
    declare vIdMeasurement_initial float;
    declare vIdMeasurement_final float;



select serial_number into p_id_meter from meters  where id_measurement = p_id_measurement;

select m.id_measurement into vIdMeasurement_initial
from invoices i inner join residences r2 on i.id_residence = r2.id_residence
                inner join meters me on r2.id_meter = me.id_meter inner join measurements m on me.id_measurement = m.id_measurement
where i.id_residence = p_id_residence order by m.measurement_date limit 1;

select id_measurement into vIdMeasurement_final from measurements m inner join meters me on m.id_measurement = me.id_measurement
where me.id_meter = p_id_meter;

if(vIdMeasurement_final is not null) then
	    if (vIdMeasurement_initial is not null) then

		    if (vIdMeasurement_final != vIdMeasurement_initial) then
		    set valueM = (select kwh_measurement from measurements where id_measurement = vIdMeasurement_final) -
			      (select kwh_measurement from measurements where id_measurement = vIdMeasurement_initial);
end if;

else
		set valueM = (select kwh_measurement from measurements where id_measurement =vIdMeasurement_final);
end if;

insert into invoices(id_residence, is_paid, due_date, first_read, last_read, total_cons_kw,initial_date,last_date,total_amount)
values (p_id_residence,true,'2021-08-10', vIdMeasurement_initial, vIdMeasurement_final, valueM,CURDATE(),'2022-02-02',
        (select value from tariffs where tariffs.id_tariff = id_tariff) * valueM);
end if;
end;
$$

drop procedure if exists m_getAll_Invoices;
delimiter $$
create procedure m_getAll_Invoices()
begin
        declare endLoop int default 0;
        declare p_residence_id int;
        declare invoiceCursor cursor for select id_residence from residences;
declare continue handler for not found set endLoop = 1;
        set autocommit = 0;
set transaction isolation level repeatable read ;
open invoiceCursor;
foreach: loop
                fetch invoiceCursor into p_residence_id;
                     if endLoop = 1 then
                     leave foreach;
end if;
call m_billing(p_residence_id);
end loop foreach;
close invoiceCursor;
commit ;
set autocommit = 1;
end
$$

create event e_billing
on schedule every 1 minute starts now()
do call m_getAll_Invoices();

drop event e_billing;

#Punto 3
#Generar las estructuras necesarias para el cálculo de precio de cada medición y las
#inserción de la misma. Se debe tener en cuenta que una modificación en la tarifa debe
#modificar el precio de cada una de estas mediciones en la base de datos y generar una
#factura de ajuste a la nueva medición de cada una de las mediciones involucradas con esta
#tarifa.

create trigger tbi_measurement before insert on measurements for each row
begin
        declare vLastDate date default null;
        declare vlastMeasurement integer default 0;
select max(measurement_date) into vLastDate from measurements where id_measurement = new.id_measurement and measurement_date > new.measurement_date;
if(vLastDate is not null) then
select kwh_measurement into vlastMeasurement from measurements where id_measurement = new.id_measurement and measurement_date = vLastDate;
set new.value = (new.kwh_measurement - vlastMeasurement)* 5.0;
end if;
end;
$$

drop trigger tau_tariff;
delimiter $$
create trigger tau_tariff after update on tariffs for each row
begin
    update measurements m inner join meters me on m.id_measurement = me.id_measurement inner join residences r on me.id_meter = r.id_meter set m.value = (m.value/old.value)*new.value
    where r.id_tariff = old.id_tariff;
    call adjustment_invoice (old.value,new.value,old.id_tariff);
end;
$$

DELIMITER $$
CREATE PROCEDURE adjustment_invoice(oldPrice FLOAT,newPrice FLOAT, id_tariff INT)
BEGIN
    declare newTotal float;
    declare vIdMeter int;
    declare vId_residence int;
    declare vFinished integer default 0;
    declare vTotal_cons_kw float;
    declare cur_residences cursor for select residences.id_meter, residences.id_client from residences;
declare continue handler for not found set vFinished = 1;
open cur_residences;
invoices_r:
    loop
        fetch cur_residences into vIdMeter,vId_residence;
        if vFinished = 1 then
            LEAVE invoices_r;
end if;
        set newTotal = null;
        set vTotal_cons_kw = null;
SELECT SUM(total_cons_kw)
FROM invoices i
         inner join residences r on i.id_residence = r.id_residence
         inner join meters m on r.id_meter = m.id_meter
where r.id_meter = vIdMeter;
SELECT (vTotal_cons_kw*newPrice)-(vTotal_cons_kw*oldPrice) INTO newTotal;
IF (newTotal IS NOT NULL) THEN
            INSERT INTO invoices (id_residence,is_paid,last_date,total_amount)
             VALUES (vId_residence, true ,CURDATE(),newTotal);
END IF;
END LOOP invoices_r;
CLOSE cur_residences;
END
$$

#Punto 4.
#Generar las estructuras necesarias para dar soporte a las consultas de mediciones
#por fecha y por usuario , debido a que tenemos restricción de que estas no pueden demorar
#más de dos segundos y tenemos previsto que tendremos 500.000.000 de mediciones en el
#sistema en el mediano plazo.

create index idx_clients_user_name on clients(user_name) using hash;
create index idx_invoice_date on invoices(due_date) using btree;
create index idx_meter_measurement on meters(id_meter) using hash;
create index idx_client on clients(id_client) using hash;
create index idx_measurement_date on measurements(measurement_date) using hash ;



drop procedure measure_date_user;
delimiter $$
create procedure measure_date_user(p_id_client int, p_start date, p_end date)
begin
select c.id_client,c.email_client,c.dni_client,c.first_name_client,c.last_name_client, me.id_meter,me.serial_number,
       m.id_measurement,m.measurement_date,m.kwh_measurement,m.value from measurements m inner join meters me on me.id_measurement = m.id_measurement
                                                                                         inner join residences r on r.id_meter = me.id_meter
                                                                                         inner join clients c on r.id_client = c.id_client where c.id_client = p_id_client and (m.measurement_date between p_start and p_end)
group by m.id_measurement;
end;
$$

call measure_date_user(1,'2021-07-10','2021-08-11');