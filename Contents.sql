drop table contents_board;

CREATE TABLE contents_board(
  contents_num     NUMBER NOT NULL,
  contents_subject VARCHAR2(100),  
  contents_creator VARCHAR2(100),
  contents_src     VARCHAR2(100),
  contents_file    VARCHAR2(100)
);

alter table contents_board add primary key (contents_num);
-- contensts_num : ������ ��ȣ
-- contensts_subject : ������ ����
-- contensts_creator : ������ ũ��������
-- contensts_src : ������ ������ �ּ�
-- contensts_file : ������ ���� (href�� �����)

-- ������ ������
drop sequence seq_contents;

Create Sequence seq_contents
start with 1
increment by 1
maxvalue 10000000;

-- ������ �߰�
insert into contents_board values(seq_contents.nextval, '������', '���ĸ�','https://www.youtube.com/embed/YkEt1bbVH8c','Contents1.jsp');
insert into contents_board values(seq_contents.nextval, '���� �������� ��ⱹ��', '���ĸ�','https://www.youtube.com/embed/ChFrn-gf3RM','Contents2.jsp');
insert into contents_board values(seq_contents.nextval, '��ī�� ���� ����', '���ĸ�','https://www.youtube.com/embed/T7z5_6WvGFs','Contents3.jsp');
insert into contents_board values(seq_contents.nextval, '�˸��� �ø���', '���̸�Ŵ','https://www.youtube.com/embed/rO9AJx_AiwY','Contents4.jsp');
insert into contents_board values(seq_contents.nextval, '���� ��ġ������', '���̸�Ŵ','https://www.youtube.com/embed/lLkF22Q7e0k','Contents5.jsp');
insert into contents_board values(seq_contents.nextval, '���� ġŲī��', '���̸�Ŵ','https://www.youtube.com/embed/NvnMG-XuBro','Contents6.jsp');
insert into contents_board values(seq_contents.nextval, 'Ȳ�ݺ�����', '�¿�ƺ�','https://www.youtube.com/embed/-QX4vd5xARQ','Contents7.jsp');
insert into contents_board values(seq_contents.nextval, '��ǳ����', '�¿�ƺ�','https://www.youtube.com/embed/24SO6nTTQsE','Contents8.jsp');
insert into contents_board values(seq_contents.nextval, '������', '�¿�ƺ�','https://www.youtube.com/embed/r2cY8GobZcU','Contents9.jsp');

-- ������ ����
delete from contents_board where contensts_num=0;

--------------------------------------------------------
select * from contents_board ORDER BY contents_num ASC;
--------------------------------------------------------