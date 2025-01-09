drop database if exists rentcar;
create database rentcar;
use rentcar;
drop table if exists car;
drop table if exists brand;
# 테이블 생성
# 차 카테고리 테이블
create table car (
	cno int unsigned auto_increment,
    cname varchar(30) not null unique,
    constraint primary key(cno)
);

create table brand (
	bno int unsigned auto_increment,
    bname varchar(30) not null unique,
    cno int unsigned,
    constraint primary key(bno),
    constraint foreign key(cno) references car(cno) on update cascade on delete cascade
);

create table apply (
	ano int unsigned auto_increment,
    aname varchar(30) not null,
    aphone varchar(13) not null unique,
    atype int not null,
    deposit int not null,
    prepayments int not null,
    residual_value int not null,
    duration int not null,
    constraint primary key(ano)
);

# 데이터 입력
# car table
insert into car(cname) values ('국산차');
insert into car(cname) values ('수입차');

# brand table
insert into brand(bname, cno) values ('현대', 1);
insert into brand(bname, cno) values ('쉐보레', 1);
insert into brand(bname, cno) values ('기아', 1);
insert into brand(bname, cno) values ('BMW', 2);
insert into brand(bname, cno) values ('아우디', 2);
insert into brand(bname, cno) values ('포드', 2);

# apply table
insert into apply(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('유재석', '010-1111-1111', 1, 0, 0, 50, 60);
insert into apply(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('강호동', '010-2222-2222', 1, 30, 30, 40, 48);
insert into apply(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('신동엽', '010-3333-3333', 2, 50, 50, 30, 36);

# 테이블 확인
select * from car;
select * from brand;
select * from apply;