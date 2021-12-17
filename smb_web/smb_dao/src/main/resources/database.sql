    create table Admintor_0
    (
      id bigint not null auto_increment primary key,
      manageList varchar(100),
      admname varchar(100)
    );

    create table Admintor_1
    (
      id bigint not null auto_increment primary key,
      manageList varchar(100),
      admname varchar(100)
    );

    -- 这是广播表，新建在其中一个节点上就可以
    CREATE TABLE `t_config` (
      `id` int(11) NOT NULL AUTO_INCREMENT,
      `user_id` bigint(20) DEFAULT NULL,
      `config` varchar(100) DEFAULT NULL,
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB;

    CREATE TABLE user_0 (
      id bigint(20) NOT NULL,
      name varchar(100) DEFAULT NULL,
      age bigint(20) NOT NULL,
      PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    CREATE TABLE user_1 (
      id bigint(20) NOT NULL,
      name varchar(100) DEFAULT NULL,
      age bigint(20) NOT NULL,
      PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;