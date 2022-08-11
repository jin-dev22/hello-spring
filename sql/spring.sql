--(관리자계정) spring일반계정 생성
alter session set "_oracle_script" = true;

create user spring
identified by spring
default tablespace users;

alter user spring quota unlimited on users;

grant connect, resource to spring;

-- spring 계정
--dev 테이블 생성
create table dev(
    no number,
    name varchar2(100) not null,
    career number not null,
    email varchar2(200) not null,
    gender char(1),
    lang varchar2(100) not null,
    created_at date default sysdate,
    constraint pk_dev_no primary key(no),
    constraint ck_dev_gender check(gender in ('M', 'F'))
);

create sequence seq_dev_no;

select * from dev;

--회원 테이블 생성 
create table member(
    member_id varchar2(50),
    password varchar2(300) not null,
    name varchar2(256) not null,
    gender char(1),
    birthday date,
    email varchar2(256),
    phone char(11) not null,
    address varchar2(512),
    hobby varchar2(256),
    created_at date,
    updated_at date,
    enabled number default 1,--나중에 배울 인증관련
    constraint pk_member_id primary key(member_id),
    constraint ck_member_gender check(gender in ('M', 'F')),
    constraint ck_member_enabled check(enabled in (1, 0))
);

insert into spring.member values ('abcde','1234','아무개','M',to_date('88-01-25','rr-mm-dd'),'abcde@naver.com','01012345678','서울시 강남구','운동,등산,독서',default, null, default);
insert into spring.member values ('qwerty','1234','김말년','F',to_date('78-02-25','rr-mm-dd'),'qwerty@naver.com','01098765432','서울시 관악구','운동,등산',default, null, default);
insert into spring.member values ('admin','1234','관리자','F',to_date('90-12-25','rr-mm-dd'),'admin@naver.com','01012345678','서울시 강남구','독서',default, null, default);
commit;
select * from member;
rollback;
--delete from member where member_id = 'admin';

--todo 테이블생성
create table todo (
    no number,
    todo varchar2(2000) not null,
    created_at date default sysdate,
    completed_at date,
    constraint pk_todo_no primary key(no)
);
create sequence seq_todo_no;

insert into todo values(seq_todo_no.nextval, '우산 고치기', default, null);
insert into todo values(seq_todo_no.nextval, '마당 쓸기', default, null);
insert into todo values(seq_todo_no.nextval, '장보기', default, null);
insert into todo values(seq_todo_no.nextval, '차 물 퍼내기', default, null);
commit;
select * from todo;
--update todo set completed_at = sysdate where no = 22;
insert into todo values(seq_todo_no.nextval, '실습문제 풀기', default, null);
insert into todo  values(seq_todo_no.nextval, '물 마시기', default, null);

--할일목록 no asc 나오고 완료 목록 completed_at desc 아래 4쿼리는 모두 같음
select * from todo order by completed_at desc nulls first, no;--nulls first 가 기본설정. null의 순서 설정가능

select * from (select * from todo where completed_at is null order by no)
union all
select * from (select * from todo where completed_at is not null order by completed_at desc);

select * from todo order by (case when completed_at is null then 1 else 2 end), completed_at desc;

select * from todo order by decode(completed_at, null, 1), completed_at desc;