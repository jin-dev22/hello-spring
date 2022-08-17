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

-- 게시판 | 첨부파일 테이블 생성

create table board (
    no number,
    title varchar2(1000) not null,
    member_id varchar2(15),
    content varchar2(4000),
    read_count number default 0,
    created_at date default sysdate,
    updated_at date,
    constraint pk_board_no primary key(no),
    constraint fk_board_member_id foreign key(member_id) references member(member_id) on delete set null
);

create sequence seq_board_no;

create table attachment (
    no number,
    board_no number not null,
    original_filename varchar2(256) not null,
    renamed_filename varchar2(256) not null,
    download_count number default 0,
    created_at date default sysdate,
    constraint pk_attachment_no primary key(no),
    constraint fk_attachment_board_no foreign key(board_no) references board(no) on delete cascade
);

create sequence seq_attachment_no;

Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 1','abcde','반갑습니다',to_date('18/02/10','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 2','qwerty','안녕하세요',to_date('18/02/12','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 3','admin','반갑습니다',to_date('18/02/13','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 4','abcde','안녕하세요',to_date('18/02/14','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 5','qwerty','반갑습니다',to_date('18/02/15','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 6','admin','안녕하세요',to_date('18/02/16','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 7','abcde','반갑습니다',to_date('18/02/17','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 8','qwerty','안녕하세요',to_date('18/02/18','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 9','admin','반갑습니다',to_date('18/02/19','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 10','abcde','안녕하세',to_date('18/02/20','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 11','qwerty','반갑습니다',to_date('18/03/11','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 12','admin','안녕하세',to_date('18/03/12','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 13','abcde','반갑습니다',to_date('18/03/13','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 14','qwerty','안녕하세',to_date('18/03/14','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 15','admin','반갑습니다',to_date('18/03/15','RR/MM/DD'),0);


Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 16','abcde','안녕하세',to_date('18/03/16','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 17','qwerty','반갑습니다',to_date('18/03/17','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 18','admin','안녕하세',to_date('18/03/18','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 19','abcde','반갑습니다',to_date('18/03/19','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 20','qwerty','안녕하세',to_date('18/03/20','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 21','admin','반갑습니다',to_date('18/04/01','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 22','abcde','안녕하세',to_date('18/04/02','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 23','qwerty','반갑습니다',to_date('18/04/03','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 24','admin','안녕하세',to_date('18/04/04','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 25','abcde','반갑습니다',to_date('18/04/05','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 26','qwerty','안녕하세',to_date('18/04/06','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 27','admin','반갑습니다',to_date('18/04/07','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 28','abcde','안녕하세',to_date('18/04/08','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 29','qwerty','반갑습니다',to_date('18/04/09','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 30','admin','안녕하세',to_date('18/04/10','RR/MM/DD'),0);

Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 31','abcde','반갑습니다',to_date('18/04/16','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 32','qwerty','안녕하세',to_date('18/04/17','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 33','admin','반갑습니다',to_date('18/04/18','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 34','abcde','안녕하세',to_date('18/04/19','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 35','qwerty','반갑습니다',to_date('18/04/20','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 36','admin','안녕하세',to_date('18/05/01','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 37','abcde','반갑습니다',to_date('18/05/02','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 38','qwerty','안녕하세',to_date('18/05/03','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 39','admin','반갑습니다',to_date('18/05/04','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 40','abcde','안녕하세',to_date('18/05/05','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 41','qwerty','반갑습니다',to_date('18/05/06','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 42','admin','안녕하세',to_date('18/05/07','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 43','abcde','반갑습니다',to_date('18/05/08','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 44','qwerty','안녕하세',to_date('18/05/09','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 45','admin','반갑습니다',to_date('18/05/10','RR/MM/DD'),0);

Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 46','abcde','안녕하세',to_date('18/05/16','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 47','qwerty','반갑습니다',to_date('18/05/17','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 48','admin','안녕하세',to_date('18/05/18','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 49','abcde','반갑습니다',to_date('18/05/19','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 50','qwerty','안녕하세',to_date('18/05/20','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 51','admin','반갑습니다',to_date('18/05/01','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 52','abcde','안녕하세',to_date('18/06/02','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 53','qwerty','반갑습니다',to_date('18/06/03','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 54','admin','안녕하세',to_date('18/06/04','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 55','abcde','반갑습니다',to_date('18/06/05','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 56','qwerty','안녕하세',to_date('18/06/06','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 57','admin','반갑습니다',to_date('18/06/07','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 58','abcde','안녕하세',to_date('18/06/08','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 59','qwerty','반갑습니다',to_date('18/06/09','RR/MM/DD'),0);
Insert into SPRING.BOARD (NO,TITLE,MEMBER_ID,CONTENT,CREATED_AT,READ_COUNT) values (SEQ_BOARD_NO.nextval,'안녕하세요, 게시판입니다 - 60','admin','안녕하세',to_date('18/06/10','RR/MM/DD'),0);


INSERT INTO ATTACHMENT VALUES(SEQ_ATTACHMENT_NO.NEXTVAL, 60, 'test.jpg', '20200525_090909_123.JPG', DEFAULT, DEFAULT);

INSERT INTO ATTACHMENT VALUES(SEQ_ATTACHMENT_NO.NEXTVAL, 60, 'moon.jpg', '20200525_090909_345.JPG', DEFAULT, DEFAULT);

INSERT INTO ATTACHMENT VALUES(SEQ_ATTACHMENT_NO.NEXTVAL, 59, 'sun.jpg', '20200525_020425_345.JPG', DEFAULT, DEFAULT);

COMMIT;

select * from board order by no desc;
select * from attachment;