##建立用戶主表
create schema main_tab;
##建立用戶從表1
create schema fork_tab_1;
##建立用戶從表2
create schema fork_tab_2;

create table main_tab.users(id bigint, name varchar(8), comment varchar(16));
create table fork_tab_1.users(id bigint, name varchar(8), comment varchar(16));
create table fork_tab_2.users(id bigint, name varchar(8), comment varchar(16));

insert into demo_master.users values(1,'tian1','main'),(2,'tian2','main'),(3,'tian3','main');
insert into demo_slave_0.users values(1,'tian1','fork'),(2,'tian2','fork'),(3,'tian3','fork');
insert into demo_slave_1.users values(1,'tian1','fork'),(2,'tian2','fork'),(3,'tian3','fork');







## 分库分表

create schema demo_ds_0;
create schema demo_ds_1;

CREATE TABLE IF NOT EXISTS demo_ds_0.t_order_0 (
order_id BIGINT NOT NULL AUTO_INCREMENT,	
 user_id INT NOT NULL, 
status VARCHAR(50),
 PRIMARY KEY (order_id));

CREATE TABLE IF NOT EXISTS demo_ds_0.t_order_1 (
order_id BIGINT NOT NULL AUTO_INCREMENT, 	
user_id INT NOT NULL, 
status VARCHAR(50), 
PRIMARY KEY (order_id));

CREATE TABLE IF NOT EXISTS demo_ds_1.t_order_0 (
order_id BIGINT NOT NULL AUTO_INCREMENT, 
user_id INT NOT NULL, 
status VARCHAR(50), 
PRIMARY KEY (order_id));

CREATE TABLE IF NOT EXISTS demo_ds_1.t_order_1 (
order_id BIGINT NOT NULL AUTO_INCREMENT, 
user_id INT NOT NULL, 
status VARCHAR(50), 
PRIMARY KEY (order_id));

insert into demo_ds_1.t_order_0(1,1,true,),(2,2,true),(3,3,false);
insert into demo_ds_1.t_order_1(1,1,true,),(2,2,true),(3,3,false);


##item庫
CREATE TABLE IF NOT EXISTS demo_ds_0.t_order_item_0 (
order_item_id BIGINT NOT NULL AUTO_INCREMENT,	
 order_id BIGINT NOT NULL, 
user_id INT NOT NULL, 
status VARCHAR(50), 
PRIMARY KEY (order_item_id));

CREATE TABLE IF NOT EXISTS demo_ds_0.t_order_item_1 (
order_item_id BIGINT NOT NULL AUTO_INCREMENT, 	
order_id BIGINT NOT NULL, 
user_id INT NOT NULL, 
status VARCHAR(50), 
PRIMARY KEY (order_item_id));

CREATE TABLE IF NOT EXISTS demo_ds_1.t_order_item_0 (
order_item_id BIGINT NOT NULL AUTO_INCREMENT, 	
order_id BIGINT NOT NULL, 
user_id INT NOT NULL, 
status VARCHAR(50), 
PRIMARY KEY (order_item_id));

CREATE TABLE IF NOT EXISTS demo_ds_1.t_order_item_1 (
order_item_id BIGINT NOT NULL AUTO_INCREMENT, 	
order_id BIGINT NOT NULL,
 user_id INT NOT NULL, 
status VARCHAR(50), 
PRIMARY KEY (order_item_id));

insert into demo_ds_1.t_order_item_0(1,1,true,),(2,2,true),(3,3,false);
insert into demo_ds_1.t_order_item_1(1,1,true,),(2,2,true),(3,3,false);



##訂單庫  order
##建立訂單主表
create schema main_order;
##建立訂單從表
create schema fork_order;

create table main_order.order(id bigint, name varchar(8), momey varchar(16));
create table fork_order.order(id bigint, name varchar(8), momey varchar(16));


insert into demo_master.users values(1,'mooncake','main'),(2,'freach cook','main'),(3,'suger','main');
insert into demo_slave_0.users values(1,'mooncake','fork'),(2,'freach cook','fork'),(3,'suger','fork');




