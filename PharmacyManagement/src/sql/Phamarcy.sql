create database pharmacy

use pharmacy
 
use master 
drop database pharmacy
	/*Loai thuoc*/
CREATE TABLE Categories(
	CatID int identity,
	CatName varchar(50) ,
	CONSTRAINT pk_CatID PRIMARY KEY (CatID),	
)
/*Hoạt chất */
CREATE TABLE ActiveMaterial(
	AmId int identity,
	AmName varchar(50) ,
	AmDescription varchar(max),
	CONSTRAINT pk_AmId PRIMARY KEY (AmId),
)

CREATE TABLE ScienitificName(
	SnId int identity,
	SnName varchar(50) ,
	CONSTRAINT pk_SnId PRIMARY KEY (SnId),
)

CREATE TABLE Product(
	PId bigint identity,
	PName varchar(50) ,
	PDescription varchar(max),
	PImage varbinary(max),
	buyPrice varchar(50),
	SellPrice varchar(50),
	CatID int,
	AmId int,
	SnId int,
	CONSTRAINT pk_PId PRIMARY KEY (PId),
	CONSTRAINT fk_CatID FOREIGN KEY (CatID) REFERENCES Categories(CatID)
	on delete cascade 
	on update cascade ,
	CONSTRAINT fk_AmId FOREIGN KEY (AmId) REFERENCES ActiveMaterial(AmId)
	on delete cascade 
	on update cascade ,
	CONSTRAINT fk_SnId FOREIGN KEY (SnId) REFERENCES ScienitificName(SnId)
	on delete cascade 
	on update cascade ,
	
)

CREATE TABLE ExpiredTime(
	ExId int identity,
	ExDate date ,
	PId bigint,
	Qty varchar(50),
	CONSTRAINT pk_ExId PRIMARY KEY (ExId),
	CONSTRAINT fk_PId FOREIGN KEY (PId) REFERENCES Product(PId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE Countries(
	CountryID int identity,
	CountryName varchar(50) ,
	CONSTRAINT pk_CountryID PRIMARY KEY (CountryID),
)


CREATE TABLE Cities(
	CityID int identity,
	CityName varchar(50) ,
	CountryID int,
	CONSTRAINT pk_CityID PRIMARY KEY (CityID),
	CONSTRAINT fk_CountryID FOREIGN KEY (CountryID) REFERENCES Countries(CountryID)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE Customer(
	CuId int identity,
	CuName varchar(50) ,
	CuAddrees varchar(max),
	CuPhone varchar(50),
	CuImage varbinary(max),
	CityId int,
	CONSTRAINT pk_CuId PRIMARY KEY (CuId),
	CONSTRAINT fk_CityId FOREIGN KEY (CityId) REFERENCES Cities(CityId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE Orders(
	OrderID bigint identity,
	OrderDate date ,
	Total varchar(50),
	CuId int,
	CONSTRAINT pk_OrderID PRIMARY KEY (OrderID),
	CONSTRAINT fk_CuId FOREIGN KEY (CuId) REFERENCES Customer(CuId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE OrderDetail(
	OrderID bigint identity,
	PId bigint ,
	Qty varchar(50),
	CONSTRAINT pk_OrderIDs PRIMARY KEY (OrderID),
	CONSTRAINT fk_PId_Order FOREIGN KEY (PId) REFERENCES Product(PId)
	on delete cascade 
	on update cascade ,
)
alter table OrderDetail add
CONSTRAINT FK_OrderDetail_Orders FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
	

CREATE TABLE Supplier(
	SuID int identity,
	SuName varchar(50) ,
	SuPhone varchar(50),
	CONSTRAINT pk_SuID PRIMARY KEY (SuID),
)

CREATE TABLE Requests(
	ReqID bigint identity,
	ReqDate date ,
	Total varchar(50),
	SuId int,
	CONSTRAINT pk_ReqID PRIMARY KEY (ReqID),
	CONSTRAINT fk_SuId FOREIGN KEY (SuId) REFERENCES Supplier(SuId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE RequestsDetail(
	ReqID bigint identity,
	PId bigint ,
	Qty varchar(50),
	CONSTRAINT ReqID PRIMARY KEY (ReqID),
	CONSTRAINT fk_Detail_Requests FOREIGN KEY (ReqID) REFERENCES Requests(ReqID),
	CONSTRAINT fk_PIdReques FOREIGN KEY (PId) REFERENCES Product(PId)
	on delete cascade 
	on update cascade ,
)


CREATE TABLE Permission(
	PerID int identity,
	PerName varchar(50) ,
	CONSTRAINT pk_PerID PRIMARY KEY (PerID),
)

CREATE TABLE Users(
	UsersID int identity,
	UsersName varchar(50) ,
	UsersPass varchar(50) ,
	UsersFullName varchar(50) ,
	PerID int,
	CONSTRAINT pk_UsersID PRIMARY KEY (UsersID),
	CONSTRAINT fk_PerID FOREIGN KEY (PerID) REFERENCES Permission(PerID)
	on delete cascade 
	on update cascade ,
)
