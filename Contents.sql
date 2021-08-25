drop table contents_board;

CREATE TABLE contents_board(
  contents_num     NUMBER NOT NULL,
  contents_subject VARCHAR2(100),  
  contents_creator VARCHAR2(100),
  contents_src     VARCHAR2(100),
  contents_file    VARCHAR2(100)
);

alter table contents_board add primary key (contents_num);
-- contensts_num : ÄÁÅÙÃ÷ ¹øÈ£
-- contensts_subject : ÄÁÅÙÃ÷ Á¦¸ñ
-- contensts_creator : ÄÁÅÙÃ÷ Å©¸®¿¡ÀÌÅÍ
-- contensts_src : ÄÁÅÙÃ÷ µ¿¿µ»ó ÁÖ¼Ò
-- contensts_file : ÄÁÅÙÃ÷ ÆÄÀÏ (href·Î »ç¿ëÇÒ)

-- ÄÁÅÙÃ÷ ½ÃÄö½º
drop sequence seq_contents;

Create Sequence seq_contents
start with 1
increment by 1
maxvalue 10000000;

-- ÄÁÅÙÃ÷ Ãß°¡
insert into contents_board values(seq_contents.nextval, '»ş½½¸¯', 'À°½Ä¸Ç','https://www.youtube.com/embed/YkEt1bbVH8c','Contents1.jsp');
insert into contents_board values(seq_contents.nextval, 'Á¦ÁÖ µ¼º£°í±â¿Í °í±â±¹¼ö', 'À°½Ä¸Ç','https://www.youtube.com/embed/ChFrn-gf3RM','Contents2.jsp');
insert into contents_board values(seq_contents.nextval, '½ÃÄ«°í µöµğ½¬ ÇÇÀÚ', 'À°½Ä¸Ç','https://www.youtube.com/embed/T7z5_6WvGFs','Contents3.jsp');
insert into contents_board values(seq_contents.nextval, '¾Ë¸®¿À ¿Ã¸®¿À', '·¹ÀÌ¸ÕÅ´','https://www.youtube.com/embed/rO9AJx_AiwY','Contents4.jsp');
insert into contents_board values(seq_contents.nextval, '½ºÆÔ ±èÄ¡ººÀ½¹ä', '·¹ÀÌ¸ÕÅ´','https://www.youtube.com/embed/lLkF22Q7e0k','Contents5.jsp');
insert into contents_board values(seq_contents.nextval, '¹öÅÍ Ä¡Å²Ä«·¹', '·¹ÀÌ¸ÕÅ´','https://www.youtube.com/embed/NvnMG-XuBro','Contents6.jsp');
insert into contents_board values(seq_contents.nextval, 'È²±İººÀ½¹ä', '½Â¿ì¾Æºü','https://www.youtube.com/embed/-QX4vd5xARQ','Contents7.jsp');
insert into contents_board values(seq_contents.nextval, '±ñÇ³¸¸µÎ', '½Â¿ì¾Æºü','https://www.youtube.com/embed/24SO6nTTQsE','Contents8.jsp');
insert into contents_board values(seq_contents.nextval, 'À¯¸°±â', '½Â¿ì¾Æºü','https://www.youtube.com/embed/r2cY8GobZcU','Contents9.jsp');

-- ÄÁÅÙÃ÷ »èÁ¦
delete from contents_board where contensts_num=0;

--------------------------------------------------------
select * from contents_board ORDER BY contents_num ASC;
--------------------------------------------------------