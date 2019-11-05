SELECT * FROM clients 
insert into clients (id, address, first_name, last_name, patronymic)
select
		i,
		left(md5(i::text), 25),
		left(md5(i::text), 7),
		left(md5(i::text), 10),
		left(md5(random()::text), 10)
from generate_series(1, 1000000) s(i);
insert into accounts(id, balance, client_id)
select
	i,
	1000000,
	i
from generate_series(3, 1000000) s(i);