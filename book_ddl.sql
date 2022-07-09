#mysql不区分大小写
#和hibernate一起会有问题
#后端出错时看一下表里有没有多余的列

use bookstore;
show tables;
drop table if exists cart_item ;
drop table if exists order_item;
drop table if exists book;
drop table if exists bookorder;
drop table if exists user;
create table user(
	userID int not null auto_increment, # uuid16bytes int4bytes
    username varchar(20) not null,# varchar不定长
    password char(64) not null, # char定长 效率高 sha256
	email varchar(50) not null, # 长度都在前端先做了限制
    `admin` boolean default false,
    ban boolean default false,
    primary key(userID),
    unique key(username),
    unique key(email)
)ENGINE=InnoDB;
CREATE TABLE `book` (
  bookID int not null auto_increment,
  `isbn` varchar(13) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `author` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `inventory` int DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (bookID)
) ENGINE=InnoDB;

create table bookorder(
	orderID char(20) not null,
    userID int not null,
    time timestamp not null default current_timestamp,
    price decimal(12,2) not null check(price>=0),
    address varchar(50) not null,
    phone char(11) not null,
    comment text default null,
    phase int default 1 check(phase>=1 and phase <=5),
    primary key(orderID),
    foreign key (userID) references user (userID)
    )engine=InnoDB;
    
create table order_item(
    orderID char(20) not null,
    bookID int not null,
    amount int not null default 1 check(amount>0),
    primary key(orderID,bookID),
    foreign key (orderID) references bookorder (orderID),
    foreign key (bookID) references book (bookID)
)engine=InnoDB;
    
create table cart_item(
		userID int not null,
        bookID int not null,
        amount int not null default 1 check(amount>0),
        primary key(userID,bookID),
        foreign key (userID) references user (userID),
		foreign key (bookID) references book (bookID)
	)engine=InnoDB;