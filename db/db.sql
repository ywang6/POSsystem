drop table Feeder;
drop table Customer;
drop table Stock;
drop table Product;
drop table Sell;
drop table OtherStorage;
drop table manager;

create table Feeder
(
  FeederID varchar(50) not null primary key,
  FeederName varchar(50) not null,
  Address varchar(50) null,
  LinkMan varchar(50) null,
  Tel varchar(50) null,
  FeedRemark varchar(50) null 
);
insert into Feeder values('10001','����','�ӱ�','����','1234567','��');
insert into Feeder values('10002','����','�ӱ�','����','1234567','��');
insert into Feeder values('10003','����','�ӱ�','����','1234567','��');


create table Customer
(
  CustomerID varchar(50) not null primary key,
  CustomerName varchar(50) null,
  Address varchar(50) null,
  LinkMan varchar(50) null,
  Tel varchar(50) null,
  CustomerRemark varchar(50) null
);

insert into Customer values('10001','��Сʫ','�ӱ�','��Сʫ','1234567','��');
insert into Customer values('10002','���','�ӱ�','���','1234567','��');
insert into Customer values('10003','������','�ӱ�','������','1234567','��');

create table Stock
(
  StockID varchar(50) not null primary key,
  FeederID varchar(50) not null,
  ProductID varchar(50) not null,
  ProductName varchar(50) not null,
  Spec varchar(50) null,
  Unit varchar(50) null,
  Quantity varchar(50) not null,
  UnitPrice varchar(50) not null,
  Payment varchar(50) null,
  Stackdate varchar(50) null
);
insert into Stock values('10001','10001','10001','����ʱ�','2310','̨','2','8500','17000','2000.1.1');
insert into Stock values('10002','10001','10002','�ֻ�','2310','̨','2','1500','3000','2000.1.1');
insert into Stock values('10003','10001','10003','��
��','2310','̨','0','1500','3000','2000.1.1');


create table Product
(
  ProductID varchar(50) not null primary key,
  ProductName varchar(50) not null,
  Spec varchar(50) null,
  Unit varchar(50) null,
  RFStockPrice varchar(50) null,
  RFSellPrice varchar(50) null,
  Min_sto varchar(50) not null,
  Max_sto varchar(50) not null
);
insert into Product values('10001','����ʼǱ�','2310','̨','8500','9000','0','2');
insert into Product values('10002','�ֻ�','2310','̨','500','600','0','2');
insert into Product values('10003','����','2310','̨','1500','2000','0','2');


create table Sell
(
  SellID varchar(50) not null primary key,
  CustomerName varchar(50) not null,
  ProductID varchar(50) not null,
  ProductName varchar(50) not null,
  Spec varchar(50) null,
  Unit varchar(50) null,
  Quantity varchar(50) null,
  UnitPrice varchar(50) null,
  Payment varchar(50) null,
  SellDate varchar(50) null
);


create table OtherStorage
(
  ObjectName varchar(50) not null primary key,
  StorageID varchar(50) not null,
  StorageName varchar(50) not null,
  StorageType varchar(50) not null,
  ProductID varchar(50) not null,
  ProductName varchar(50) not null,
  Spec varchar(50) null,
  Unit varchar(50) null,
  Quantity varchar(50) null,
  StorageDate varchar(50) not null 
);

create table manager
(
   mgNo varchar(50) primary key,
   permitted varchar(50),
   password varchar(50)
);
insert into manager values('wyf','1','hxl');
insert into manager values(1001,'0','1001');
















