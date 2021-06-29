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
    declare p_id_client int;
    declare p_initial_date date;
    declare p_last_date date;
    declare p_totalPrice float;
    declare p_tariffPrice float;
    declare p_id_measurement int;
    declare p_totalCon float;
    declare p_is_paid bool;
    declare p_initCons float;
    declare p_lastCons float;
    declare id_tariff int;
    declare value_tariff float;



select serial_number into p_id_meter from meters  where id_measurement = p_id_measurement;

select min(initial_date) into p_initial_date
from invoices where isnull(id_invoice) and id_residence=p_id_residence limit 1;

select max(last_date) into p_last_date from invoices
where isnull(id_invoice) and id_residence = p_id_residence limit 1;

select id_client into p_id_client from residences where id_residence = p_id_residence;
select max(total_cons_kw) into p_initCons from invoices where initial_date = p_initial_date and id_residence=p_id_residence;
select max(total_cons_kw) into p_lastCons from invoices where last_date = p_last_date and id_residence = p_id_residence;
select id_tariff into id_tariff from residences where id_residence = p_id_residence;
select value into value_tariff from tariffs where tariffs.id_tariff = id_tariff;

set p_totalCon = p_lastCons-p_initCons;
    set p_totalPrice= p_tariffPrice * p_totalCon;

    if p_initial_date is not null then
        insert into invoices (id_residence,is_paid,due_date,first_read,last_read,total_cons_kw,initial_date,last_date,total_amount) values (p_id_residence,p_is_paid,NOW(),p_initCons,p_lastCons,p_totalCon,p_initial_date,p_last_date,p_totalPrice);
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
    update measurements m inner join residences r on r.id_meter = m.id_meter set m.value=(m.value/old.value)*new.value where r.id_tariff = old.id_tariff;
end;
$$



#Punto 4.
#Generar las estructuras necesarias para dar soporte a las consultas de mediciones
#por fecha y por usuario , debido a que tenemos restricción de que estas no pueden demorar
#más de dos segundos y tenemos previsto que tendremos 500.000.000 de mediciones en el
#sistema en el mediano plazo.

create index idx_clients_user_name on clients(user_name) using hash;
create index idx_invoice_date on invoices(due_date) using btree;
#create index idx_meter_measurement on meters(id_meter) using hash;

drop procedure measure_date_user;
delimiter $$
create procedure measure_date_user(p_id_client int, p_start date, p_end date)
begin
select c.id_client,c.email_client,c.dni_client,c.first_name_client,c.last_name_client, me.id_meter AS id_meter,me.serial_number,mo.id_model,mo.model,b.brand,
       m.id_measurement,m.measurement_date,m.kwh_measurement,m.value from measurements m inner join meters me on me.id_meter = m.id_meter
                                                                                         inner join models mo on mo.id_model= me.id_model inner join brands b on b.id_brand=mo.id_brand inner join residences r on r.id_meter = me.id_meter
                                                                                         inner join clients c on r.id_client = c.id_client where c.id_client = p_id_client and (m.measurement_date between p_start and p_end)
group by m.id_measurement;
end;
$$

call measure_date_user(2,'2021-01-01','2021-02-02');