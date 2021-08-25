CREATE TABLE review_board(
  board_num     NUMBER NOT NULL,
  board_id      VARCHAR2(50),
  board_subject VARCHAR2(100),
  board_content VARCHAR2(2000),
  board_file    VARCHAR2(100),
  board_re_ref  NUMBER,
  board_re_lev  NUMBER,
  board_re_seq  NUMBER,
  board_count   NUMBER,
  board_date    DATE,
  CONSTRAINT PK_review_Board PRIMARY KEY(board_num)
);

-- board_num : �Խù� �۹�ȣ
-- board_id : ���ۼ��� ID
-- board_subject : �� ����
-- board_content : �� ����
-- board_file : ÷������ �̸�
-- board_re_ref : �� �׷��ȣ
-- board_re_lev : �亯�� ����
-- board_re_seq : �亯�� ����
-- board_count : �� ��ȸ��
-- board_date �ۼ���¥


-- �Խ��� ������
create sequence BOARD_NUM; 
 
-- �������� �߰�
alter table review_board
add constraint pk_review_id foreign key(board_id)
REFERENCES member(memid);

---------------------------------
select * from review_board;
---------------------------------

-- board_parent Į�� :: ���� ������ ���� 

alter table review_board add (board_parent number(10));