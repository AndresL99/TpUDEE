create database UEEApp;
use UEEApp;

drop database UEEApp;
create table if not exists users(
user_name varchar(30) not null,
password varchar(30) not null,
constraint pk_user_name primary key (user_name)
);

create table if not exists admins(
user_name varchar(30),
dni_admin integer not null,
email_admin varchar(50) not null,
first_name_admin varchar(30) not null,
last_name_admin varchar(30) not null,
constraint fk_user_name_admin foreign key (user_name) references users (user_name),
constraint pk_dni_admin primary key (dni_admin)
);

create table if not exists clients(
user_name varchar(30),
dni_client integer not null,
email_client varchar(50) not null,
first_name_client varchar(30) not null,
last_name_client varchar(30) not null,
constraint fk_user_name_client foreign key (user_name) references users (user_name),
constraint pk_dni_client primary key (dni_client)
);

create table if not exists meters(
serial_number varchar(40) not null,
model varchar(30) not null,
brand varchar(30) not null,
constraint pk_serial_number primary key (serial_number)
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

create table if not exists residences(
id_residence integer not null auto_increment,
dni_client integer,
id_address integer,
serial_number varchar(40),
id_tariff integer,
constraint fk_dni_client foreign key (dni_client) references clients(dni_client),
constraint fk_id_address foreign key (id_address) references addresses(id_address),
constraint fk_serial_number foreign key (serial_number) references meters(serial_number),
constraint fk_id_tariff foreign key (id_tariff) references tariffs(id_tariff),
constraint pk_id_residence primary key (id_residence)
);

create table if not exists invoices(
id_residence integer,
id_invoice integer auto_increment,
is_paid boolean not null,
due_date datetime not null,
first_read float not null,
last_read float not null,
total_cons_kw float not null,
initial_date datetime not null,
last_date datetime not null,
total_amount float not null,
constraint fk_id_residence foreign key(id_residence) references residences(id_residence),
constraint pk_id_invoice primary key (id_invoice)
);