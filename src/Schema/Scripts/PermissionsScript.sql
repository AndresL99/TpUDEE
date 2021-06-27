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