create database pharmacy2

use pharmacy2
 
use master 
drop database pharmacy

CREATE TABLE Categories(
	CatID int identity,
	CatName varchar(50) ,
	CONSTRAINT pk_CatID PRIMARY KEY (CatID),	
)
/*
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
*/
CREATE TABLE Product(
	PId bigint identity,
	PCode varchar(50) unique,
	PName varchar(50) ,
	PImage varbinary(max),
	Unit varchar(50),
	Statuses varchar(50),
	BuyPrice float,
	SellPrice float,
	Supplier varchar(50),
	PDescription varchar(max),
	CatID int,
	/*AmId int,
	SnId int,*/
	CONSTRAINT pk_PId PRIMARY KEY (PId),
	CONSTRAINT fk_CatID FOREIGN KEY (CatID) REFERENCES Categories(CatID)
	on delete cascade 
	on update cascade ,
	/*CONSTRAINT fk_AmId FOREIGN KEY (AmId) REFERENCES ActiveMaterial(AmId)
	on delete cascade 
	on update cascade ,
	CONSTRAINT fk_SnId FOREIGN KEY (SnId) REFERENCES ScienitificName(SnId)
	on delete cascade 
	on update cascade ,*/
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
/*
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
)*/

CREATE TABLE Customer(
	CuId int identity,
	CuCode varchar(50),
	CuName varchar(50) ,
	CuAddrees varchar(max),
	CuPhone varchar(50),
	CuEmail varchar(50),
	CuLevel int,
	/*CityId int,*/
	CONSTRAINT pk_CuId PRIMARY KEY (CuId),
	/*CONSTRAINT fk_CityId FOREIGN KEY (CityId) REFERENCES Cities(CityId)
	on delete cascade 
	on update cascade ,*/
)

CREATE TABLE Orders(
	OrderID bigint identity PRIMARY KEY,
	OrderDate date ,
	Total varchar(50),
	CuId int,

	CONSTRAINT fk_CuId FOREIGN KEY (CuId) REFERENCES Customer(CuId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE OrderDetail(
	OrderDetailID bigint identity PRIMARY KEY,
	PId bigint ,
	Qty varchar(50),
	CONSTRAINT fk_PId_Order FOREIGN KEY (PId) REFERENCES Product(PId)
	on delete cascade 
	on update cascade ,
)


alter table OrderDetail add
CONSTRAINT FK_OrderDetail_Orders FOREIGN KEY (OrderDetailID) REFERENCES Orders(OrderID)
	

CREATE TABLE Supplier(
	SuID int identity,
	SuCode varchar(50),
	SuName varchar(50) ,
	SuType varchar(50),
	SuAddrees varchar(50),
	SuPhone varchar(50),
	SuTax varchar(50),
	SuEmail varchar(50),
	SuWebsite varchar(50),
	SuNotice varchar(max),
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



CREATE TABLE DetailUser(
	DetailID int identity,
	Code varchar(50),
	Phone varchar(50),
	Email varchar(50),
	Addrees varchar(50),
	Sex bit,
	BirthDay date,
	Salary float,
	Position varchar(50),
	Department varchar(50),
	ImageBlob varbinary(max),
	WorkDay date,
	Mission varchar(50) ,
	CONSTRAINT pk_DetailID PRIMARY KEY (DetailID),
)

drop table DetailUser

CREATE TABLE Users(
	UsersID int identity,
	UsersName varchar(50) ,
	UsersPass varchar(50) ,
	UsersFullName varchar(50) ,
	DetailID int,
	CONSTRAINT pk_UsersID PRIMARY KEY (UsersID),
	CONSTRAINT fk_DetailID FOREIGN KEY (DetailID) REFERENCES DetailUser(DetailID)
	on delete cascade 
	on update cascade ,
)

CREATE procedure getAllEmployee
as
begin 
select dtu.Code as eplCode,us.UsersName as username,dtu.Phone as phone,dtu.Email as email
,dtu.Addrees as addrees ,dtu.Sex as gender,dtu.BirthDay as dateOfBirth,dtu.Salary as salary
,dtu.Position as position,dtu.Department as department,dtu.ImageBlob as blogImage,dtu.WorkDay as dateWork ,us.UsersID as UserId,dtu.Mission as roles
from Users AS us
 INNER JOIN DetailUser AS dtu ON us.DetailID=dtu.DetailID
end

CREATE PROCEDURE InsertUser 
(@UserName varchar(20),@Pass varchar(20),@DetailID int)   
AS 
INSERT INTO Users ([UsersName],[UsersPass],[DetailID]) VALUES (@UserName,@Pass,@DetailID)  
GO 


CREATE PROCEDURE ProceDetailUser
(@eplCode varchar(20),@phone varchar(20),@email varchar(20),
@addrees varchar(20),@gender bit,@birthday date,
@salary float,@position varchar(20),@department varchar(20),
@image varbinary(max),@workday date)   
AS 
INSERT INTO DetailUser ([Code],[Phone],[Email],[Addrees],[Sex],[BirthDay],[Salary],[Position],[Department],[ImageBlob],[WorkDay])
 VALUES (@eplCode,@phone,@email,@addrees,@gender,@birthday,@salary,@position,@department,@image,@workday)  
GO 

CREATE PROCEDURE getUserMission
as
begin
select us.UsersName as username,us.UsersPass AS pass,us.UsersFullName as fullname,dtu.mission as mission
from Users AS us
 INNER JOIN DetailUser AS dtu ON us.DetailID=dtu.DetailID
end

CREATE PROCEDURE updatePassCode
(@password varchar(50),@code varchar(50))
AS
UPDATE A SET A.UsersPass =@password
FROM Users A INNER JOIN DetailUser B ON  A.DetailID=B.DetailID
WHERE B.Code=@code 
GO

CREATE PROCEDURE getAllProduct
AS
BEGIN
SELECT PRD.PCode AS Code,PRD.PName AS Name,CT.CatName AS Categories,PRD.Unit AS Unit,PRD.PImage AS Images,
PRD.Statuses AS Statuses,PRD.BuyPrice AS Buy,PRD.SellPrice AS Sell,PRD.Supplier AS Supplier,EXT.ExDate AS ExTime,EXt.Qty AS Quantity
,PRD.PDescription AS Descriptions,CT.CatID AS CatID,EXT.ExId AS EID
FROM Categories CT JOIN Product PRD ON CT.CatID=PRD.CatID
JOIN ExpiredTime EXT ON PRD.PId=EXT.PId
END


SELECT *FROM Users

select *from DetailUser

use pharmacy2
