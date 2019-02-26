create database quanlynhathuoc 
use quanlynhathuoc 

create table thuoc(
	id int identity,
	mathuoc varchar(50),
	tenthuoc nvarchar(50),
	gianhap float,
	giaban float,
	soluong int,
	ghichu ntext,
)
create table nhapkho(
	id int identity,
	manhap  nvarchar(50),
	mathuoc varchar(50),
	tenthuoc nvarchar(50),
	nhaphanphoi nvarchar(50),
	gianhap float,
	soluong int,
	ngaynhap date,
	ghichu ntext,
	datra float,
)
create table xuatkho(
	id int identity,
	mathuoc varchar(50),
	tenthuoc nvarchar(50),
	manoinhan nvarchar(50),
	giaxuat float,
	dathu float,
	soluong int,
	ngayxuat date,
	ghichu ntext,
)
create table noinhan(
	id int identity,
	manoinhan nvarchar(50),
	tennoinhan nvarchar(50),
)
create table tonkho(
	id int identity,
	mathuoc varchar(50),
	tenthuoc nvarchar(50),
	soluong int,
)
create table khachhang(
	id int identity,
	makhachhang varchar(50),
	tenkhachhang nvarchar(50),
	diachi nvarchar(50),
	sodienthoai int,
	sotiendamua float,
	email nvarchar(50),
	levels int,

)
create table nhaphanphoi(
	id int identity,
	manhaphanphoi varchar(50),
	tennhaphaphoi nvarchar(50),
	diachi nvarchar(50),
	sodienthoai int,
	sotiendamua float,

)
create table nhanvien(
	id int identity,
	manhanvien nvarchar(50),
	tennhanvien nvarchar(50),
	diachi nvarchar(50),
	sodienthoai int,
	gioitinh bit,
	chucvu nvarchar(50),
	luong float,
)
create table users(
	id int identity,
	manhanvien nvarchar(50),
	tendangnhap nvarchar(50),
	passwords nvarchar(50),
	maphanquyen tinyint,
)

create table congno_npp(
	id int identity,
	macongno varchar(50),
	manhaphanphoi nvarchar(50),
	sotienno money,
	manhap  nvarchar(50),
	
)
create table congno_kh(
	id int identity,
	macongno varchar(50),
	makh nvarchar(50),
	sotienno money,
	maxuat  nvarchar(50),
	
)











