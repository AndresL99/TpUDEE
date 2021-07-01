insert into tariffs(id_tariff,name,value) values (1,'R y Entidades de bien público',10000),(2,'Medianas demandas',8000),(3,'Grandes demandas',5000),(4,'Pequeñas demandas',1500);
insert into brands(id_brand, brand) values (1,'BAW'),(2,'Jieli'),(3,'AOPUTTRIVER'),(4,'Yasorn');
insert into models(id_model, id_brand, model) values (1,2,'DDS558'),(2,1,'MEKWH5-45'),(3,2,'150-12-00-00011'),(4,2,'150-12-00-00010'),(5,1,'FFKWH9-457'),(6,3,'AP-881E-UK'),(7,4,'YSI115D5-09'),(8,1,'LWEWH5-111'),(9,4,'YOSUOFP-122'),(10,4,'KLRIPX-20'),(11,3,'AC-995M-KL'),(12,3,'AL-661E-ML'),(13,4,'LOWACGK-0092');

insert into users(user_name,password,first_name,last_name,is_admin) values ('andreslerner','andres123','Andres','Lerner',true),('JuanOlachea','juan123','Juan','Olachea',true),('RamonMelgar','ramon123','Ramon','Melgar',false);
insert into users(user_name, password, first_name, last_name,is_admin) VALUE ('JohnThompson','1234','John','Thompson',false);
insert into users (user_name, password, first_name, last_name,is_admin) value ('PabloFino','666','Pablo','Fino',false);
insert into clients(id_client,user_name,dni_client,email_client,first_name_client,last_name_client) value (1,'RamonMelgar',35444789,'Rmelgar@gmail.com','Ramon','Melgar');
insert into clients(id_client, user_name, dni_client, email_client, first_name_client, last_name_client) VALUE (2,'JohnThompson',34555666,'jthompson@gmail.com','John','Thompson');
insert into clients(id_client, user_name, dni_client, email_client, first_name_client, last_name_client) VALUE (3,'PabloFino',34666444,'pablofino@yahoo.com.ar','Pablo','Fino');

insert into users(user_name,password,first_name,last_name,is_admin) value ('admin','1234','Admin','Admin',true);

insert into measurements(id_measurement, measurement_date, kwh_measurement, value) VALUES (666,now(),45.00,100.00),(213,now(),120.00,40.00),(233,now(),50.00,200.00);
insert into measurements (id_measurement, measurement_date, kwh_measurement, value)
values (99,'2021-05-05',1000.0,2000.0),(13,'2021-06-06',500.0,750.0),(55,'2021-07-21',200.0,100.0);
insert into meters(id_meter, id_model, id_measurement, serial_number, password) VALUES (1,1,666,'221j3hido4','andres123'),(2,1,233,'2424n4hina','root'),(3,2,213,'52j2jjo22j5','1234');
insert into meters(id_model, id_measurement, serial_number, password) VALUES (1,99,'aaaa222','1234'),(1,99,'aaaa222','1234'),(1,55,'aaaa222','1234');

insert into addresses(id_address, name_address, number_address) VALUES (1,'Avenida Colon',3333),(2,'Belgrano',5555),(3,'Cordoba',1111);
insert into residences(name,id_client, id_address, id_tariff, id_meter) VALUES ('Departamento Ramon',1,2,3,1),('Casa de John',2,1,1,3),('Local de Pablo',3,3,1,2);

insert into invoices(id_invoice, id_residence, is_paid, due_date, first_read, last_read, total_cons_kw, initial_date, last_date, total_amount) VALUES
(1,1,true,'2021-09-20',1200.0,2000.0,3400.00,'2021-07-05','2021-08-21',3500.0),
(2,2,false,'2021-09-02',1000.0,1500.0,4000.0,'2021-07-10','2021-08-11',5000.0);

insert into residences (name,id_client, id_address, id_tariff, id_meter) VALUES ('Departamento de John',2,1,1,5),('Local de Ramon',1,2,2,4);

insert into invoices(id_invoice, id_residence, is_paid, due_date, first_read, last_read, total_cons_kw, initial_date, last_date, total_amount) VALUE(3,2,true,'2021-08-12',150.00,200.00,3500.00,'2021-07-29','2021-08-01',4000.0);

