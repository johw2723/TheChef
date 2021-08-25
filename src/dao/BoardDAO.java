package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BoardDAO {
	DataSource ds;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private static BoardDAO instance;
	
	public BoardDAO(){
		try {
	        Context ic=new InitialContext();
            ds=(DataSource)ic.lookup("java:comp/env/jdbc/Oracle11g");                
        } catch(NamingException e ) {
        	e.printStackTrace();
        }	
	}
	
	public static BoardDAO getInstance(){
		if(instance==null)
			instance=new BoardDAO();
		return instance;
	}
	
	// 시퀀스를 가져온다.
	public int getSeq() {
		int result = 1;
		
		try {
			con = ds.getConnection();
			System.out.println("board DAO :: getSeq() 실행");
			
			// 시퀀스 값을 가져온다. (DUAL : 시퀀스 값을 가져오기위한 임시 테이블)
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT BOARD_NUM.NEXTVAL FROM DUAL");
			
			pstmt = con.prepareStatement(sql.toString());
			// 쿼리 실행
			rs = pstmt.executeQuery();
			
			if(rs.next())	result = rs.getInt(1);

		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return result;	
	} // end getSeq
	
	// 글 삽입
	public boolean boardInsert(BoardBean board){
		boolean result = false;
		
		try {
			System.out.println("DAO :: Insert");
            con = ds.getConnection();
            
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO REVIEW_BOARD");
            sql.append("(BOARD_NUM, BOARD_ID, BOARD_SUBJECT, BOARD_CONTENT, BOARD_FILE");
            sql.append(", BOARD_RE_REF, BOARD_COUNT, BOARD_DATE, BOARD_PARENT)");
            sql.append(" VALUES(?,?,?,?,?,?,?,sysdate,?)");
 
            int num = board.getBoard_num();            // 글번호(시퀀스 값)
            int ref = board.getBoard_re_ref();         // 그룹번호
            int parent = board.getBoard_parent();      // 부모글번호
            
            // 부모글일 경우 그룹번호와 글번호 동일
            if(parent == 0) ref = num;
 
            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, num);
            pstmt.setString(2, board.getBoard_id());
            pstmt.setString(3, board.getBoard_subject());
            pstmt.setString(4, board.getBoard_content());
            pstmt.setString(5, board.getBoard_file());
            pstmt.setInt(6, ref);
            pstmt.setInt(7, board.getBoard_count());
            pstmt.setInt(8, parent);
 
            int flag = pstmt.executeUpdate();
            if(flag > 0){
                result = true;
                con.commit(); // 완료시 커밋
            }
		
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			} 
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return result;	
	} // end boardInsert();
	
	
	// 글목록 가져오기
	public ArrayList<BoardBean> getBoardList(HashMap<String, Object> listOpt){
		ArrayList<BoardBean> list = new ArrayList<BoardBean>();
		
		System.out.println("DAO :: ArrayList");
		String opt = (String)listOpt.get("opt");             // 검색옵션 : 제목, 내용, 글쓴이 등 
		String condition = (String)listOpt.get("condition"); // 검색내용
		int start = (Integer)listOpt.get("start");           // 현재 페이지번호
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 글목록 전체를 보여줄 때
			if(opt == null){
				
				// BOARD_RE_REF (그룹번호)의 내림차순 정렬 후 동일한 그룹번호 일 때는
				// BOARD_RE_SEQ (답변글 
				
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT LEVEL, BOARD_NUM, BOARD_ID,	BOARD_SUBJECT,");
				sql.append("			BOARD_CONTENT, BOARD_FILE,BOARD_COUNT,");
				sql.append("			BOARD_RE_REF, BOARD_PARENT, BOARD_DATE");
				sql.append("	FROM REVIEW_BOARD");
				sql.append("	START WITH BOARD_PARENT = 0");
				sql.append("	CONNECT BY PRIOR BOARD_NUM = BOARD_PARENT");
				sql.append("	ORDER SIBLINGS BY BOARD_RE_REF desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum>=? and rnum<=?");

				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, start);
				pstmt.setInt(2, start+9); // 넘겨받은 페이지 번호에서 +9한 번호까지 글을 검색
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("0")) { // 제목으로 검색
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT LEVEL, BOARD_NUM, BOARD_ID,	BOARD_SUBJECT,");
				sql.append("			BOARD_CONTENT, BOARD_FILE,BOARD_COUNT,");
				sql.append("			BOARD_RE_REF, BOARD_PARENT, BOARD_DATE");
				sql.append("	FROM REVIEW_BOARD");
				sql.append(" 	WHERE BOARD_SUBJECT like ?");
				sql.append("	START WITH BOARD_PARENT = 0");
				sql.append("	CONNECT BY PRIOR BOARD_NUM = BOARD_PARENT");
				sql.append("	ORDER SIBLINGS BY BOARD_RE_REF desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum>=? and rnum<=?");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("1")) { // 내용으로 검색
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT LEVEL, BOARD_NUM, BOARD_ID,	BOARD_SUBJECT,");
				sql.append("			BOARD_CONTENT, BOARD_FILE,BOARD_COUNT,");
				sql.append("			BOARD_RE_REF, BOARD_PARENT, BOARD_DATE");
				sql.append("	FROM REVIEW_BOARD");
				sql.append(" 	WHERE BOARD_CONTENT like ?");
				sql.append("	START WITH BOARD_PARENT = 0");
				sql.append("	CONNECT BY PRIOR BOARD_NUM = BOARD_PARENT");
				sql.append("	ORDER SIBLINGS BY BOARD_RE_REF desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum>=? and rnum<=?");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setInt(2, start);
				pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("2")) { // 제목+내용으로 검색
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT LEVEL, BOARD_NUM, BOARD_ID,	BOARD_SUBJECT,");
				sql.append("			BOARD_CONTENT, BOARD_FILE,BOARD_COUNT,");
				sql.append("			BOARD_RE_REF, BOARD_PARENT, BOARD_DATE");
				sql.append("	FROM REVIEW_BOARD");
				sql.append(" 	WHERE BOARD_SUBJECT like ?");
				sql.append(" 	OR BOARD_CONTENT like ?");
				sql.append("	START WITH BOARD_PARENT = 0");
				sql.append("	CONNECT BY PRIOR BOARD_NUM = BOARD_PARENT");
				sql.append("	ORDER SIBLINGS BY BOARD_RE_REF desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum>=? and rnum<=?");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				pstmt.setString(2, "%"+condition+"%");
				pstmt.setInt(3, start);
				pstmt.setInt(4, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			else if(opt.equals("3")) { // 글쓴이로 검색	
				sql.append("SELECT * FROM");
				sql.append(" (SELECT  ROWNUM AS rnum, data.* FROM ");
				sql.append("	(SELECT LEVEL, BOARD_NUM, BOARD_ID,	BOARD_SUBJECT,");
				sql.append("			BOARD_CONTENT, BOARD_FILE,BOARD_COUNT,");
				sql.append("			BOARD_RE_REF, BOARD_PARENT, BOARD_DATE");
				sql.append("	FROM REVIEW_BOARD");
				sql.append(" 	WHERE BOARD_ID like ?");
				sql.append("	START WITH BOARD_PARENT = 0");
				sql.append("	CONNECT BY PRIOR BOARD_NUM = BOARD_PARENT");
				sql.append("	ORDER SIBLINGS BY BOARD_RE_REF desc)");              
				sql.append(" data) ");
				sql.append("WHERE rnum>=? and rnum<=?");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");  // % 검색내용 % :: 검색 내용을 제목에 포함한다면 모두 검색되도록 처리
				pstmt.setInt(2, start);
				pstmt.setInt(3, start+9);
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BoardBean vo = new BoardBean();
				vo.setBoard_re_lev(rs.getInt("LEVEL"));
				vo.setBoard_num(rs.getInt("BOARD_NUM"));
				vo.setBoard_id(rs.getString("BOARD_ID"));
				vo.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				vo.setBoard_content(rs.getString("BOARD_CONTENT"));
				vo.setBoard_file(rs.getString("BOARD_FILE"));
				vo.setBoard_count(rs.getInt("BOARD_COUNT"));
				vo.setBoard_re_ref(rs.getInt("BOARD_RE_REF"));
				vo.setBoard_parent(rs.getInt("BOARD_PARENT"));
				vo.setBoard_date(rs.getDate("BOARD_DATE"));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return list;
	} // end getBoardList
	
	
	// 글의 개수를 가져오는 메서드
	public int getBoardListCount(HashMap<String, Object> listOpt) {
		int result = 0;
		String opt = (String)listOpt.get("opt");             // 검색옵션 :: 제목, 내용, 글쓴이 등
		String condition = (String)listOpt.get("condition"); // 검색내용
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			if(opt == null)	{ // 전체글의 개수
				sql.append("select count(*) from REVIEW_BOARD");
				pstmt = con.prepareStatement(sql.toString());
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("0")) { // 제목으로 검색한 글의 개수 
				sql.append("select count(*) from REVIEW_BOARD where BOARD_SUBJECT like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("1")) { // 내용으로 검색한 글의 개수
				sql.append("select count(*) from REVIEW_BOARD where BOARD_CONTENT like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("2")) { // 제목+내용으로 검색한 글의 개수
				sql.append("select count(*) from REVIEW_BOARD ");
				sql.append("where BOARD_SUBJECT like ? or BOARD_CONTENT like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				pstmt.setString(2, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("3")) { // 글쓴이로 검색한 글의 개수
				sql.append("select count(*) from REVIEW_BOARD where BOARD_ID like ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, '%'+condition+'%');
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			if(rs.next())	result = rs.getInt(1);
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return result;
	} // end getBoardListCount
	
	
	// 상세보기
	public BoardBean getDetail(int boardNum) {	
		BoardBean vo = null;
		
		try {
			con = ds.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("select * from REVIEW_BOARD where BOARD_NUM = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			rs = pstmt.executeQuery();
			if(rs.next()){
				vo = new BoardBean();
				vo.setBoard_num(boardNum);
				vo.setBoard_id(rs.getString("BOARD_ID"));
				vo.setBoard_subject(rs.getString("BOARD_SUBJECT"));
				vo.setBoard_content(rs.getString("BOARD_CONTENT"));
				vo.setBoard_file(rs.getString("BOARD_FILE"));
				vo.setBoard_count(rs.getInt("BOARD_COUNT"));
				vo.setBoard_re_ref(rs.getInt("BOARD_RE_REF"));
				vo.setBoard_date(rs.getDate("BOARD_DATE"));
				vo.setBoard_parent(rs.getInt("BOARD_PARENT"));
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return vo;
	} // end getDetail()
	
	// 조회수 증가
	public boolean updateCount(int boardNum) {
		boolean result = false;
		
		try {
			con = ds.getConnection();
			
			// 자동 커밋을 false로 한다.
			con.setAutoCommit(false);
			
			StringBuffer sql = new StringBuffer();
			sql.append("update REVIEW_BOARD set BOARD_COUNT = BOARD_COUNT+1 ");
			sql.append("where BOARD_NUM = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				con.commit(); // 완료시 커밋
			}	
		} catch (Exception e) {
			try {
				con.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return result;
	} // end updateCount
	
	
	// 삭제할 파일명을 가져온다.
	public String getFileName(int boardNum) {
		String fileName = null;
		
		try {
			con = ds.getConnection();
			
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT BOARD_FILE from REVIEW_BOARD where BOARD_NUM=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) fileName = rs.getString("BOARD_FILE");
			
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		close();
		return fileName;
	} // end getFileName
		
	// 게시글 삭제
	public boolean deleteBoard(int boardNum) {
		boolean result = false;

		try {
			con = ds.getConnection();
			con.setAutoCommit(false); // 자동 커밋을 false로 한다.

			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM REVIEW_BOARD");
			sql.append(" WHERE BOARD_NUM IN");
			sql.append(" (SELECT BOARD_NUM");
			sql.append(" FROM REVIEW_BOARD");
			sql.append(" START WITH BOARD_NUM = ?");
			sql.append(" CONNECT BY PRIOR BOARD_NUM = BOARD_PARENT) ");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, boardNum);
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				con.commit(); // 완료시 커밋
			}	
			
		} catch (Exception e) {
			try {
				con.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}

		close();
		return result;
	} // end deleteBoard
	
	// 글 수정
	public boolean updateBoard(BoardBean border) {
		boolean result = false;
		
		try{
			con = ds.getConnection();
			con.setAutoCommit(false); // 자동 커밋을 false로 한다.
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE REVIEW_BOARD SET");
			sql.append(" BOARD_SUBJECT=?");
			sql.append(" ,BOARD_CONTENT=?");
			sql.append(" ,BOARD_FILE=?");
			sql.append(" ,BOARD_DATE=SYSDATE ");
			sql.append("WHERE BOARD_NUM=?");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, border.getBoard_subject());
			pstmt.setString(2, border.getBoard_content());
			pstmt.setString(3, border.getBoard_file());
			pstmt.setInt(4, border.getBoard_num());
			
			int flag = pstmt.executeUpdate();
			if(flag > 0){
				result = true;
				con.commit(); // 완료시 커밋
			}
			
		} catch (Exception e) {
			try {
				con.rollback(); // 오류시 롤백
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
			throw new RuntimeException(e.getMessage());
		}
	
		close();
		return result;
	} // end updateBoard
	
	
	// DB 자원해제
	private void close() {
		try {
			if ( pstmt != null ){ pstmt.close(); pstmt=null; }
			if ( con != null ){ con.close(); con=null;	}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	} // end close()	
}
