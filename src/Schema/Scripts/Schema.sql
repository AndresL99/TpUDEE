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
dni_client integer,
id_address integer,
id_tariff integer,
id_meter integer not null,
constraint fk_dni_client foreign key (dni_client) references clients(dni_client),
constraint fk_id_address foreign key (id_address) references addresses(id_address),
constraint fk_id_tariff foreign key (id_tariff) references tariffs(id_tariff),
constraint fk_id_meter foreign key (id_meter) references meters(id_meter),
constraint pk_id_residence primary key (id_residence)
);

create table if not exists invoices(
id_invoice integer auto_increment,
id_residence integer not null,
is_paid boolean not null,
due_date datetime not null,
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

create user 'backOffice'@'%' identified by 'backofficeUee';
create user 'client'@'%' identified by 'clientUee';
create user 'meter'@'%' identified by 'meterUee';
create user 'billing'@'%' identified by 'billingUee';

select user from mysql.user;

grant select,insert,update,delete,create,drop on ueeapp.clients to 'backOffice';
grant select,insert,update,delete,create,drop on ueeapp.tariffs to 'backOffice';
grant select,insert,update,delete,create,drop on ueeapp.meters to 'backOffice';

grant select on ueeapp.measurements to 'client';



grant execute on procedure m_billing to 'billing';

drop procedure if exists m_billing;

create procedure m_billing()
begin

end




