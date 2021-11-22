create schema master_bank_trancser;
create schema dataA;
create schema dataB;

create table bank_trancser.account_status(id bigint, name varchar(8), status varchar(16));
create table dataA.users(id bigint, money_rmb DOUBLE,money_doller DOUBLE);
create table dataB.users(id bigint, money_doller DOUBLE,money_rmb DOUBLE);

insert into demo_master.users values(1,'KK01','master'),(2,'KK02','master'),(3,'KK03','master');
insert into demo_slave_0.users values(1,'KK01','slave0'),(2,'KK02','slave0'),(3,'KK03','slave0');
insert into demo_slave_1.users values(1,'KK01','slave1'),(2,'KK02','slave1'),(3,'KK03','slave1');
