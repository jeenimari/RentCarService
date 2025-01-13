drop database if exists leasedb;
create database leasedb;
use leasedb;

# Lease 관련 테이블
create table lease (
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
insert into lease(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('유재석', '010-1111-1111', 1, 0, 0, 50, 60);
insert into lease(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('강호동', '010-2222-2222', 1, 30, 30, 40, 48);
insert into lease(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('신동엽', '010-3333-3333', 2, 50, 50, 30, 36);

# 테이블 확인
select * from lease;

# 테이블 지우기
drop table lease;