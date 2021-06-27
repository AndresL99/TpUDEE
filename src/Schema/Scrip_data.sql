create database UEEApp;
use UEEApp;

drop database UEEApp;
create table if not exists users(
user_name varchar(30) not null unique,
password varchar(30) not null,
first_name varchar(30) not null,
last_name varchar(30) not null,
is_admin bool default 0,
constraint pk_user_name primary key (user_name)
);

create table if not exists clients(
id_client integer not null auto_increment,
user_name varchar(30) not null ,
dni_client integer not null unique,
email_client varchar(50) not null unique,
first_name_client varchar(30) not null,
last_name_client varchar(30) not null,
constraint fk_user_name foreign key (user_name) references users (user_name),
constraint pk_id_client primary key (id_client)
);


create table if not exists tariffs(
id_tariff integer not null auto_increment,
name varchar(30) not null,
value float not null,
constraint pk_id_tariff primary key (id_tariff)
);

create table if not exists addresses(
id_address integer not null auto_increment,
name_address varchar(50) not null,
number_address integer not null,
constraint pk_id_address primary key (id_address)
);

create table if not exists brands(
id_brand integer not null auto_increment,
brand varchar(30) not null,
constraint pk_id_brand primary key (id_brand)
);

create table if not exists models(
id_model integer not null auto_increment,
id_brand integer not null,
model varchar(30) not null,
constraint pk_id_model primary key (id_model),
constraint fk_id_brand foreign key (id_brand) references brands (id_brand)
);

create table if not exists measurements(
id_measurement integer not null auto_increment,
measurement_date date not null,
kwh_measurement float not null,
value float not null,
constraint pk_id_measurement primary key (id_measurement)
);

create table if not exists meters(
id_meter integer not null auto_increment,
id_model integer not null,
id_measurement integer not null,
serial_number varchar(50) not null,
password varchar(30) not null,
constraint pk_id_meter primary key (id_meter),
constraint fk_id_measurement foreign key (id_measurement) references measurements (id_measurement),
constraint fk_model foreign key (id_model) references models (id_model)
);

create table if not exists residences(
id_residence integer not null auto_increment,
id_client integer,
id_address integer,
id_tariff integer,
id_meter integer not null,
constraint fk_id_client foreign key (id_client) references clients(dni_client),
constraint fk_id_address foreign key (id_address) references addresses(id_address),
constraint fk_id_tariff foreign key (id_tariff) references tariffs(id_tariff),
constraint fk_id_meter foreign key (id_meter) references meters(id_meter),
constraint pk_id_residence primary key (id_residence)
);

create table if not exists invoices(
id_invoice integer auto_increment,
id_residence integer not null,
is_paid boolean not null,
due_date date not null,
first_read float not null,
last_read float not null,
total_cons_kw float not null,
initial_date date not null,
last_date date not null,
total_amount float not null,
constraint fk_id_residence foreign key(id_residence) references residences(id_residence),
constraint pk_id_invoice primary key (id_invoice)
);

insert into tariffs(id_tariff,name,value) values (1,'R y Entidades de bien público',10000),(2,'Medianas demandas',8000),(3,'Grandes demandas',5000),(4,'Pequeñas demandas',1500);
insert into brands(id_brand, brand) values (1,'BAW'),(2,'Jieli'),(3,'AOPUTTRIVER'),(4,'Yasorn');
insert into models(id_model, id_brand, model) values (1,2,'DDS558'),(2,1,'MEKWH5-45'),(3,2,'150-12-00-00011'),(4,2,'150-12-00-00010'),(5,1,'FFKWH9-457'),(6,3,'AP-881E-UK'),(7,4,'YSI115D5-09'),(8,1,'LWEWH5-111'),(9,4,'YOSUOFP-122'),(10,4,'KLRIPX-20'),(11,3,'AC-995M-KL'),(12,3,'AL-661E-ML'),(13,4,'LOWACGK-0092');

insert into users(user_name,password,first_name,last_name,is_admin) values ('andreslerner','andres123','Andres','Lerner',true),('JuanOlachea','juan123','Juan','Olachea',true),('RamonMelgar','ramon123','Ramon','Melgar',false);
insert into clients(id_client,user_name,dni_client,email_client,first_name_client,last_name_client) value (1,'RamonMelgar',35444789,'Rmelgar@gmail.com','Ramon','Melgar');

insert into users(user_name,password,first_name,last_name,is_admin) value ('admin','1234','Admin','Admin',true);

create table if not exists liquidations
(
    id_liquidation integer not null auto_increment,
    id_residence integer not null,
    date_liquidation date not null,
    total float not null,
    quantity_invoices integer not null,
    constraint pk_id_liquidation primary key (id_liquidation)
);


alter table invoices add id_liquidation integer not null;
alter table invoices add foreign key(id_liquidation) references liquidations(id_liquidation);
alter table liquidations add foreign key (id_residence) references residences (id_residence);

select * from users;
select * from clients;
select * from tariffs;
select * from models;


#Punto 1.
#a) BACKOFFICE, que permitirá el manejo de clientes, medidores y tarifas.
#b) CLIENTES, que permitirá consultas de mediciones y facturación.
#c) MEDIDORES,, que será el sistema que enviará la información de
#mediciones a la base de datos.
#d) FACTURACIÓN , proceso automático de facturación
create user 'backOffice'@'%' identified by 'backofficeUee';
create user 'client'@'%' identified by 'clientUee';
create user 'meter'@'%' identified by 'meterUee';
create user 'billing'@'%' identified by 'billingUee';

select user from mysql.user;

grant select,insert,update,delete,create,drop on ueeapp.clients to 'backOffice';
grant select,insert,update,delete,create,drop on ueeapp.tariffs to 'backOffice';
grant select,insert,update,delete,create,drop on ueeapp.meters to 'backOffice';

grant select on ueeapp.measurements to 'client';
grant select on ueeapp.invoices to 'client';

grant insert on ueeapp.measurements to 'meter';

grant insert on measurements to 'billing';
grant execute on procedure m_billing to 'billing';


#Punto 2
#La facturación se realizará por un proceso automático en la base de datos. Se
#debe programar este proceso para el primer día de cada mes y debe generar una
#factura por medidor y debe tomar en cuenta todas las mediciones no facturadas
#para cada uno de los medidores, sin tener en cuenta su fecha. La fecha de vencimiento de
#esta factura será estipulado a 15 días.

drop procedure if exists m_billing;
delimiter $$
create procedure m_billing()
begin
    DECLARE p_id_meter VARCHAR(100);
    DECLARE p_id_client INT;
    DECLARE p_initial_date,p_last_date DATE;
    DECLARE p_id_residence,p_id_measurement INT;
    DECLARE p_is_paid BOOL;
    DECLARE p_initialConsumption,p_finalConsumption,p_totalConsumption FLOAT;
    DECLARE p_totalPrice,p_ratePrice FLOAT;

    SELECT serial_number INTO p_id_meter FROM meters  WHERE id_measurement = p_id_measurement;

    SELECT MIN(initial_date) INTO p_initial_date
    FROM invoices WHERE ISNULL(id_invoice) AND id_residence=p_id_residence LIMIT 1;

    SELECT MAX(last_date)INTO p_last_date FROM invoices
    WHERE ISNULL(id_invoice) AND id_residence = p_id_residence LIMIT 1;

    SELECT id_client INTO p_id_client FROM residences WHERE id_residence = p_id_residence;
    SELECT MAX(total_cons_kw) INTO p_initialConsumption FROM invoices WHERE initial_date = p_initial_date AND id_residence=p_id_residence;
    SELECT MAX(total_cons_kw) INTO p_finalConsumption FROM invoices WHERE last_date = p_last_date AND id_residence = p_id_residence;

    SET p_totalConsumption = p_finalConsumption-p_initialConsumption;
    SET p_totalPrice = p_ratePrice*p_totalConsumption;

    IF p_initial_date IS NOT NULL AND p_is_paid IS NOT FALSE THEN
        INSERT INTO invoices (id_residence,is_paid,due_date,first_read,last_read,total_cons_kw,initial_date,last_date,total_amount) VALUES
        (p_id_residence,p_is_paid,NOW(),p_initialConsumption,p_finalConsumption,p_totalConsumption,p_initial_date,p_last_date,p_totalPrice);

    END IF;
end;
$$

create event e_billing
on schedule every 3 minute starts now()
do call m_billing();

#Punto 3
#Generar las estructuras necesarias para el cálculo de precio de cada medición y las
#inserción de la misma. Se debe tener en cuenta que una modificación en la tarifa debe
#modificar el precio de cada una de estas mediciones en la base de datos y generar una
#factura de ajuste a la nueva medición de cada una de las mediciones involucradas con esta
#tarifa.

drop procedure liquidar_residencia ;
DELIMITER $$
CREATE PROCEDURE liquidar_residencia(pid_residence int)
BEGIN
    DECLARE vTotal float;
    DECLARE vIdLiquidacion int;
    DECLARE vIdFactura int;
    DECLARE vCant int default 0;
    DECLARE vFinished int DEFAULT 0;
    DECLARE vSuma float default 0 ;
    DECLARE vDummy int;
    DECLARE cur_liquidacion CURSOR FOR select inv.id_invoice, sum(m.kwh_measurement * m.value) as total from invoices inv inner join residences r on inv.id_residence = r.id_residence
    inner join meters me on r.id_meter = me.id_meter inner join measurements m on me.id_measurement = m.id_measurement where inv.id_liquidation is null and inv.id_residence = pid_residence group by inv.id_invoice;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = 1;
    insert into liquidations(id_residence, date_liquidation , total) values(pid_residence,now(),0);
    set vIdLiquidacion = last_insert_id();

    open cur_liquidacion;
    FETCH cur_liquidacion INTO   vIdFactura, vTotal;
    WHILE (vFinished=0) DO
        SET vSuma = vSuma + vTotal;
        SET vCant = vCant + 1;
        UPDATE invoices set id_liquidation = vIdLiquidacion where id_invoice = vIdFactura;
        FETCH cur_liquidacion INTO   vIdFactura, vTotal;
    END while;
    update liquidations set quantity_invoices = vCant, total = vSuma where id_liquidation = vIdLiquidacion;
    close cur_liquidacion;
END;
$$

call liquidar_residencia(1);

#Punto 4.
#Generar las estructuras necesarias para dar soporte a las consultas de mediciones
#por fecha y por usuario , debido a que tenemos restricción de que estas no pueden demorar
#más de dos segundos y tenemos previsto que tendremos 500.000.000 de mediciones en el
#sistema en el mediano plazo.
create index idx_clients_user_name on clients(user_name) using hash;
create index idx_invoice_date on invoices(due_date) using btree;
create index idx_meter_measurement on meters(id_measurement) using hash;

