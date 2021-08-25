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

-- board_num : 게시물 글번호
-- board_id : 글작성자 ID
-- board_subject : 글 제목
-- board_content : 글 내용
-- board_file : 첨부파일 이름
-- board_re_ref : 글 그룹번호
-- board_re_lev : 답변글 깊이
-- board_re_seq : 답변글 순서
-- board_count : 글 조회수
-- board_date 작성날짜


-- 게시판 시퀀스
create sequence BOARD_NUM; 
 
-- 제약조건 추가
alter table review_board
add constraint pk_review_id foreign key(board_id)
REFERENCES member(memid);

---------------------------------
select * from review_board;
---------------------------------

-- board_parent 칼럼 :: 계층 쿼리를 적용 

alter table review_board add (board_parent number(10));