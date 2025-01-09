drop database if exists rentcar;
create database rentcar;
use rentcar;

create table grade(
gno int unsigned auto_increment,
gname varchar(30) not null,
gprice int not null,
mno int,
constraint primary key (gno),
constraint foreign key (mno) references model(mno)
);

create table model(
mno int unsigned auto_increment,
mname varchar(30) not null unique,
bno int,
constraint primary key(mno),
constraint foreign key(bno) references brand(bno)
);
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
insert into grade(gname,gprice,mno)values('ST-Line',625000000,12);
insert into grade(gname,gprice,mno)values('PLATINUM',69000000,12);

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