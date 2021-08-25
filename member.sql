DROP TABLE Member;

create table member(
   memid      varchar(20) not null,
   pw         varchar(20) not null,
   email      varchar(30) not null,
   phone      varchar(13) not null,
   address    varchar(100),
   payment    varchar(50), 
   memberDate DATE
);

alter table member add primary key (memid);
insert into member values('admin','super','TheChef@TheChef.com','010-0000-0000','서울 강동구 천호동 하이미디어','한국카드', sysdate);
--alter table member MODIFY address varchar(100);
--alter table member MODIFY memid varchar(20);
--alter table member MODIFY pw varchar(20);


--alter table member rename column id to memid;

---------------------------------
select * from member;
---------------------------------


create table jjim(
   contents_num number REFERENCES contents_board(contents_num),
   rec_id varchar2(20) REFERENCES member(memid)
);

---------------------------------
select * from jjim;
---------------------------------

create table jjim_product(
   product_id VARCHAR2(10) REFERENCES Product(product_id),
   rec_id VARCHAR2(20) REFERENCES member(memid)
);

---------------------------------
select * from jjim_product;
---------------------------------
