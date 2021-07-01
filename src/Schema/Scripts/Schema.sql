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
                                      user_name varchar(30) ,
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
                                     id_brand integer,
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
                                     id_model integer,
                                     id_measurement integer,
                                     serial_number varchar(50) not null,
    password varchar(30) not null,
    constraint pk_id_meter primary key (id_meter),
    constraint fk_id_measurement foreign key (id_measurement) references measurements (id_measurement),
    constraint fk_model foreign key (id_model) references models (id_model)
    );

create table if not exists residences(
                                         id_residence integer not null auto_increment,
                                         name varchar(30) not null ,
    id_client integer,
    id_address integer,
    id_tariff integer,
    id_meter integer,
    constraint fk_id_client foreign key (id_client) references clients(id_client),
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