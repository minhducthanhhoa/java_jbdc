create database java_jdbc;
use java_jdbc;

create table account (
    username varchar(50) primary key,
    password varchar(100),
    status boolean,
    role varchar(10) check (role in ('admin', 'hr'))
);

create table department (
    id int auto_increment primary key,
    name varchar(100) unique not null,
    description varchar(255),
    status boolean
);

create table employee (
    id varchar(5) primary key,
    name varchar(150) not null,
    email varchar(100) unique not null,
    phone varchar(15),
    gender enum('male', 'female', 'other'),
    level int not null,
    salary double not null,
    dob date,
    address varchar(255),
    status enum('active', 'inactive', 'onleave', 'policyleave'),
    department_id int,
    foreign key (department_id) references department(id)
);