drop database if exists rentcar;
create database rentcar;
use rentcar;

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

create table model(
mno int unsigned auto_increment,
mname varchar(30) not null unique,
bno int unsigned,
constraint primary key(mno),
constraint foreign key(bno) references brand(bno) on update cascade on delete cascade
);


create table grade(
gno int unsigned auto_increment,
gname varchar(30) not null,
gprice int not null,
mno int unsigned,
constraint primary key (gno),
constraint foreign key (mno) references model(mno) on update cascade on delete cascade
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

#[차모델명 테이블]
insert into model(mname,bno)values('그랜저','1');
insert into model(mname,bno)values('소나타','1');
insert into model(mname,bno)values('타호','2');
insert into model(mname,bno)values('트래블레이저','2');
insert into model(mname,bno)values('K5','3');
insert into model(mname,bno)values('K8','3');
insert into model(mname,bno)values('3시리즈','4');
insert into model(mname,bno)values('5시리즈','4');
insert into model(mname,bno)values('A6','5');
insert into model(mname,bno)values('A7','5');
insert into model(mname,bno)values('머스탱','6');
insert into model(mname,bno)values('익스플로러','6');

#[차등급 테이블 DB]
insert into grade(gname,gprice,mno)values('프리미엄',38000000,1);
insert into grade(gname,gprice,mno)values('익스클루시브',45000000,1);
insert into grade(gname,gprice,mno)values('프리미엄',32000000,2);
insert into grade(gname,gprice,mno)values('익스클루시브',36000000,2);
insert into grade(gname,gprice,mno)values('하이컨트리',95000000,3);
insert into grade(gname,gprice,mno)values('다크나이트',100000000,3);
insert into grade(gname,gprice,mno)values('RS',29000000,4);
insert into grade(gname,gprice,mno)values('프리미어',32000000,4);
insert into grade(gname,gprice,mno)values('시그니처',29000000,5);
insert into grade(gname,gprice,mno)values('프리미엄',33000000,5);
insert into grade(gname,gprice,mno)values('시그니처',40000000,6);
insert into grade(gname,gprice,mno)values('프리미엄',44000000,6);
insert into grade(gname,gprice,mno)values('320i',60000000,7);
insert into grade(gname,gprice,mno)values('m340i',80000000,7);
insert into grade(gname,gprice,mno)values('520',70000000,8);
insert into grade(gname,gprice,mno)values('530',83000000,8);
insert into grade(gname,gprice,mno)values('35TDI',72000000,9);
insert into grade(gname,gprice,mno)values('45TDI',80000000,9);
insert into grade(gname,gprice,mno)values('45TDI',88000000,10);
insert into grade(gname,gprice,mno)values('55TDI',99857000,10);
insert into grade(gname,gprice,mno)values('쿠페',59900000,11);
insert into grade(gname,gprice,mno)values('컨버터블',67000000,11);
insert into grade(gname,gprice,mno)values('ST-Line',62500000,12);
insert into grade(gname,gprice,mno)values('PLATINUM',69000000,12);

# apply table
insert into apply(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('유재석', '010-1111-1111', 1, 0, 0, 50, 60);
insert into apply(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('강호동', '010-2222-2222', 1, 30, 30, 40, 48);
insert into apply(aname, aphone, atype, deposit, prepayments, residual_value, duration)
values ('신동엽', '010-3333-3333', 2, 50, 50, 30, 36);

# 조인 연습
select * from car, brand;
select * from car inner join brand on car.cno = brand.cno;
select * from car inner join brand on car.cno = brand.cno inner join model on brand.bno = model.bno;

select * from car inner join brand on car.cno = brand.cno 
inner join model on brand.bno = model.bno
inner join grade on model.mno = grade.mno;

select car.cno, car.cname, brand.bno, brand.bname, model.mno, model.mname, grade.gno, grade.gname, grade.gprice
from car 
inner join brand on car.cno = brand.cno 
inner join model on brand.bno = model.bno
inner join grade on model.mno = grade.mno;

select car.cno, car.cname, brand.bno, brand.bname, model.mno, model.mname, grade.gno, grade.gname, grade.gprice
from car 
inner join brand on car.cno = brand.cno 
inner join model on brand.bno = model.bno
inner join grade on model.mno = grade.mno where car.cno = 1;

select car.cno, car.cname, brand.bno, brand.bname, model.mno, model.mname, grade.gno, grade.gname, grade.gprice
from car 
inner join brand on car.cno = brand.cno 
inner join model on brand.bno = model.bno
inner join grade on model.mno = grade.mno where car.cname = "국산차";

select car.cno, car.cname, brand.bno, brand.bname, model.mno, model.mname, grade.gno, grade.gname, grade.gprice
from car 
inner join brand on car.cno = brand.cno 
inner join model on brand.bno = model.bno
inner join grade on model.mno = grade.mno where brand.bname = "기아";

select brand.bno, brand.bname, model.mno, model.mname, grade.gno, grade.gname, grade.gprice
from brand inner join model on brand.bno = model.bno inner join grade on model.mno = grade.mno
where brand.bname = "기아";

select * from grade where gname = "프리미엄" and gprice = 32000000 and mno = 2;

#select * from car where cname = "수입차";
#select * from brand where bname = "기아";
#insert into car(cname) values ("수입차");
#delete from car where cname = "전기차";
#delete from grade where gname = "노블레스" and gprice = 36000000 and mno = 5;

# 테이블 확인
select * from car;
select * from brand;
select * from model;
select * from grade;
select * from apply;

#테이블 지우기
#drop table car;
#drop table brand;
#drop table model;
#drop table grade;
#drop table apply;