
use master 
use pharmacy
 
create database pharmacy

/*
drop database pharmacy
*/

CREATE TABLE Categories(
	CatID int identity(1,1),
	CatName varchar(50) ,
	CONSTRAINT pk_CatID PRIMARY KEY (CatID),	
)

CREATE TABLE Product(
	PId bigint identity(1,1),
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
	CONSTRAINT pk_PId PRIMARY KEY (PId),
	CONSTRAINT fk_CatID FOREIGN KEY (CatID) REFERENCES Categories(CatID)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE stock(
	stockid int identity(1,1),
	PId bigint,
	Qty int,
	ExpiredTime date,
	DateIn date,
	CONSTRAINT pk_stockid PRIMARY KEY (stockid),
	CONSTRAINT fk_PId_stock FOREIGN KEY (PId) REFERENCES Product(PId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE Customer(
	CuId int identity(1,1),
	CuCode varchar(50),
	CuName varchar(50) ,
	CuAddrees varchar(max),
	CuPhone varchar(50),
	CuEmail varchar(50),
	CuLevel int,
	MoneySpend float DEFAULT 0,
	CONSTRAINT pk_CuId PRIMARY KEY (CuId),
)

CREATE TABLE DetailUser(
	DetailID int identity(1,1),
	Code varchar(50),
	Phone varchar(50),
	Email varchar(50),
	Addrees varchar(50),
	Sex bit,
	BirthDay date,
	Salary float,
	MoneySold float DEFAULT 0,
	/*Position varchar(50),*/
	Department varchar(50),
	ImageBlob varbinary(max),
	WorkDay date,
	Mission varchar(50) ,
	CONSTRAINT pk_DetailID PRIMARY KEY (DetailID),
)

CREATE TABLE Users(
	UsersID int identity(1,1),
	UsersName varchar(50) ,
	UsersPass varchar(50) ,
	UsersFullName varchar(50) ,
	DetailID int,
	CONSTRAINT pk_UsersID PRIMARY KEY (UsersID),
	CONSTRAINT fk_DetailID FOREIGN KEY (DetailID) REFERENCES DetailUser(DetailID)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE Orders(
	OrderID varchar(50) PRIMARY KEY,
	OrderDate date ,
	Total float,
	AmountTotal float DEFAULT 0 ,
	CuId int,
	UsersID int ,
	CONSTRAINT fk_users_Order FOREIGN KEY (UsersID) REFERENCES Users(UsersID)
	on delete cascade 
	on update cascade ,
	CONSTRAINT fk_CuId FOREIGN KEY (CuId) REFERENCES Customer(CuId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE OrderDetail(
	OrderDetailID int identity(1,1) PRIMARY KEY,
	OrderID varchar(50) ,
	PId bigint ,
	Qty int,
	SellPrice float,
	 Amount float DEFAULT 0,
	CONSTRAINT fk_Order_Detail FOREIGN KEY (OrderID) REFERENCES Orders(OrderID)
	on delete cascade 
	on update cascade ,
	CONSTRAINT fk_PId_Detail FOREIGN KEY (PId) REFERENCES Product(PId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE Supplier(
	SuID int identity(1,1),
	SuCode varchar(50),
	SuName varchar(50) ,
	SuAddrees varchar(50),
	SuPhone varchar(50),
	SuTax varchar(50),
	SuEmail varchar(50),
	SuWebsite varchar(50),
	SuNotice varchar(max),
	CONSTRAINT pk_SuID PRIMARY KEY (SuID),
)

CREATE TABLE Requests(
	ReqID bigint identity(1,1),
	ReqDate date ,
	Total float,
	SuId int,
	CONSTRAINT pk_ReqID PRIMARY KEY (ReqID),
	CONSTRAINT fk_SuId FOREIGN KEY (SuId) REFERENCES Supplier(SuId)
	on delete cascade 
	on update cascade ,
)

CREATE TABLE RequestsDetail(
	ReqIdDeTail bigint identity(1,1),
	ReqID bigint,
	PId bigint ,
	Qty int,
	CONSTRAINT ReqID PRIMARY KEY (ReqID),
	CONSTRAINT fk_Detail_Requests FOREIGN KEY (ReqID) REFERENCES Requests(ReqID)
	on delete cascade 
	on update cascade,
	CONSTRAINT fk_PIdReques FOREIGN KEY (PId) REFERENCES Product(PId)
	on delete cascade 
	on update cascade ,
)



CREATE procedure getAllEmployee
as
begin 
select dtu.Code as eplCode,us.UsersName as username,dtu.Phone as phone,dtu.Email as email
,dtu.Addrees as addrees ,dtu.Sex as gender,dtu.BirthDay as dateOfBirth,dtu.Salary as salary
,dtu.Department as department,dtu.ImageBlob as blogImage,dtu.WorkDay as dateWork ,us.UsersID as UserId,dtu.Mission as roles
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
@salary float,@department varchar(20),
@image varbinary(max),@workday date)   
AS 
INSERT INTO DetailUser ([Code],[Phone],[Email],[Addrees],[Sex],[BirthDay],[Salary],[Department],[ImageBlob],[WorkDay])
 VALUES (@eplCode,@phone,@email,@addrees,@gender,@birthday,@salary,@department,@image,@workday)  
GO 

CREATE PROCEDURE getUserMission
as
begin
select us.UsersName as username,us.UsersPass AS pass,us.UsersFullName as fullname,dtu.mission as mission,dtu.Department as department
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
PRD.Statuses AS Statuses,PRD.BuyPrice AS Buy,PRD.SellPrice AS Sell,PRD.Supplier AS Supplier,
PRD.PDescription AS Descriptions,CT.CatID AS CatID
FROM Categories CT JOIN Product PRD ON CT.CatID=PRD.CatID
END


SELECT *FROM Users

select *from DetailUser
/*
delete from Users*/


/*
alter table Orders
add UsersID int 

ALTER TABLE Orders
ADD CONSTRAINT fk_users FOREIGN KEY (UsersID) REFERENCES Users(UsersID) 
on delete cascade 
on update cascade
*/


/*
alter table Orders
add AmountTotal float DEFAULT 0 */

/*
alter table OrderDetail
add Amount float DEFAULT 0*/
/*
alter table DetailUser
add MoneySold float DEFAULT 0*/
/*
alter table Customer
add MoneySpend float DEFAULT 0*/
/*
alter table OrderDetail
drop column OrderDetailID 

alter table OrderDetail
add OrderDetailID int identity(1,1)(1,1) Primary Key
*/

