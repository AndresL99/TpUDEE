create database UEEApp;
use UEEApp;

drop database UEEApp;
create table if not exists users(
id_user integer not null auto_increment,
user_name varchar(30) not null unique,
password varchar(30) not null,
constraint pk_id_user primary key (id_user)
);

create table if not exists admins(
id_user integer not null,
dni_admin integer not null unique,
email_admin varchar(50) not null unique,
first_name_admin varchar(30) not null,
last_name_admin varchar(30) not null,
constraint fk_id_admin foreign key (id_user) references users (id_user),
constraint pk_dni_admin primary key (dni_admin)
);

create table if not exists clients(
id_user integer not null,
dni_client integer not null unique,
email_client varchar(50) not null unique,
first_name_client varchar(30) not null,
last_name_client varchar(30) not null,
constraint fk_id_client foreign key (id_user) references users (id_user),
constraint pk_dni_client primary key (dni_client)
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
password varchar(30) not null,
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

insert into users(id_user,user_name,password) values (1,'andreslerner','andres123'),(2,'JuanOlachea','juan123'),(3,'RamonMelgar','ramon123');
insert into admins(id_user,dni_admin,email_admin,first_name_admin,last_name_admin) values (1,41458332,'andreslerner99@gmail.com','Andres','Lerner'),(2,'41333000','juan@gmail.com','Juan','Olachea');
insert into clients(id_user,dni_client,email_client,first_name_client,last_name_client) value (3,35444789,'Rmelgar@gmail.com','Ramon','Melgar');


select * from users;
select * from admins;
select * from clients;
select * from tariffs;