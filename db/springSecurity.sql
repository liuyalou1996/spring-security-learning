create table users(
	username varchar(50) not null primary key,
	password varchar(100) not null,
	enabled boolean not null
);

create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);

create table persistent_logins (
	username varchar(64) not null,
	series varchar(64) primary key,
	token varchar(64) not null,
	last_used timestamp not null
);

insert into users(username,password,enabled) values('lq','123',1);
insert into authorities(username,authority) values('lq','ROLE_USER');
update users set password='{bcrypt}$2a$10$VmYUmu3CnAjm28gCmHCHAu3SMcEbeOIqXqfu7y3OUJT2VlVznXHPe';

select * from users;
select * from authorities;
select * from persistent_logins;

delete from users where username='zxq';
delete from authorities where username='zxq';
show tables;
describe users;