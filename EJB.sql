drop schema public cascade;

create schema public;

create table public.city
(
	ct_id serial,
	ct_name character varying(100),
	constraint pk_ct_id primary key (ct_id)
);

create table public.address
(
	addr_id serial,
	addr_building int not null,
	addr_road int not null,
	addr_block int not null,
	addr_city_id int,
	constraint pk_addr_id primary key (addr_id),
	constraint fk_city_id foreign key (addr_city_id)
	references public.city (ct_id)
);