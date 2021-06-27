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
insert into meters(id_meter, id_model, id_measurement, serial_number, password) VALUES (1,1,666,'221j3hido4','andres123'),(2,1,233,'2424n4hina','root'),(3,2,213,'52j2jjo22j5','1234');

insert into addresses(id_address, name_address, number_address) VALUES (1,'Avenida Colon',3333),(2,'Belgrano',5555),(3,'Cordoba',1111);
insert into residences(id_client, id_address, id_tariff, id_meter) VALUES (1,2,3,1),(2,1,1,3),(3,3,1,2);
