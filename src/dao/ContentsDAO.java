package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ContentsDAO {
	DataSource ds;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ContentsDAO() {
		try {
			Context ic=new InitialContext();
			ds=(DataSource)ic.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<ContentsVO> getContentsList(HashMap<String, Object> listOpt){
		ArrayList<ContentsVO> list = new ArrayList<ContentsVO>();
		
		System.out.println("Contents DAO :: ArrayList");
		String opt = (String)listOpt.get("opt");             // 검색옵션 : 제목, 크리에이터 등 
		String condition = (String)listOpt.get("condition"); // 검색내용
		
		System.out.println("opt = " + opt);
		System.out.println("condition = " + condition);
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 목록 전체
			if(opt == null) {
				sql.append("SELECT * FROM");
				sql.append(" (SELECT CONTENTS_NUM, CONTENTS_SUBJECT ");
				sql.append(", CONTENTS_CREATOR, CONTENTS_SRC, CONTENTS_FILE ");
				sql.append("FROM");
				sql.append(" (SELECT * FROM CONTENTS_BOARD order by CONTENTS_NUM DESC))");
				
				pstmt = con.prepareStatement(sql.toString());
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("0")) { // 제목으로 검색
				sql.append("SELECT * FROM");
				sql.append(" (SELECT CONTENTS_NUM, CONTENTS_SUBJECT, ");
				sql.append("  CONTENTS_CREATOR, CONTENTS_SRC, CONTENTS_FILE ");
				sql.append("FROM");
				sql.append(" (SELECT * FROM CONTENTS_BOARD where CONTENTS_SUBJECT like ? ");
				sql.append("order by CONTENTS_NUM DESC))");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("1")) { // 크리에이터 명으로 검색
				sql.append("SELECT * FROM");
				sql.append(" (SELECT CONTENTS_NUM, CONTENTS_SUBJECT, ");
				sql.append("  CONTENTS_CREATOR, CONTENTS_SRC, CONTENTS_FILE ");
				sql.append("FROM");
				sql.append(" (SELECT * FROM CONTENTS_BOARD where CONTENTS_CREATOR like ? ");
				sql.append("order by CONTENTS_NUM DESC))");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%"+condition+"%");
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ContentsVO vo = new ContentsVO();
				vo.setContents_num(rs.getInt("CONTENTS_NUM"));
				vo.setContents_subject(rs.getString("CONTENTS_SUBJECT"));
				vo.setContents_creator(rs.getString("CONTENTS_CREATOR"));
				vo.setContents_src(rs.getString("CONTENTS_SRC"));
				vo.setContents_file(rs.getString("CONTENTS_FILE"));
				list.add(vo);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if ( pstmt != null ){ pstmt.close(); pstmt=null; }
				if ( con != null ){ con.close(); con=null;	}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return list;
	}
	
	public int selectFavoriteCount(ContentsVO cvo, TheChefVO vo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("select * from jjim where Contents_num=? and rec_id=?");
			
			System.out.println("cvo.getContents_num() : " + cvo.getContents_num());
			System.out.println("vo.getMemId() : " + vo.getMemId());
		
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cvo.getContents_num());
			pstmt.setString(2, vo.getMemId());
			
			int favo = 0;
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				favo = rs.getInt(1);
			}
			
			return favo;
					
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if ( rs != null ){ rs.close(); rs=null; }
				if ( pstmt != null ){ pstmt.close(); pstmt=null; }
				if ( con != null ){ con.close(); con=null;	}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	public void insertFavorite(ContentsVO cvo, TheChefVO vo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("insert into jjim");	
			sql.append("(contents_num, rec_id)");
			sql.append(" values(?, ?)");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cvo.getContents_num());
			pstmt.setString(2, vo.getMemId());
			
			int i = pstmt.executeUpdate();
			System.out.println(i+"행이 추가되었습니다.");	
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if ( pstmt != null ){ pstmt.close(); pstmt=null; }
				if ( con != null ){ con.close(); con=null;	}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	public void deleteFavorite(ContentsVO cvo, TheChefVO vo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("delete from jjim");	
			sql.append(" where contents_num=? and rec_id=?");			
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, cvo.getContents_num());
			pstmt.setString(2, vo.getMemId());
			
			int i = pstmt.executeUpdate();
			System.out.println(i+"행이 삭제되었습니다.");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if ( pstmt != null ){ pstmt.close(); pstmt=null; }
				if ( con != null ){ con.close(); con=null;	}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}
	
	public ArrayList<ContentsVO> getFavoriteList(String memId){
		ArrayList<ContentsVO> favoList = new ArrayList<ContentsVO>();
		
		System.out.println("Contents DAO :: getFavoriteList");
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 커리문
			sql.append("SELECT * FROM JJIM ");
			sql.append("WHERE rec_id=?");
				
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, memId);
				
			// StringBuffer를 비운다.
			sql.delete(0, sql.toString().length());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ContentsVO vo = new ContentsVO();
				vo.setContents_num(rs.getInt("CONTENTS_NUM"));
				//vo.setContents_subject(rs.getString("CONTENTS_SUBJECT"));
				//vo.setContents_creator(rs.getString("CONTENTS_CREATOR"));
				//vo.setContents_src(rs.getString("CONTENTS_SRC"));
				//vo.setContents_file(rs.getString("CONTENTS_FILE"));
				favoList.add(vo);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if ( rs != null) { rs.close(); rs=null; }
				if ( pstmt != null ){ pstmt.close(); pstmt=null; }
				if ( con != null ){ con.close(); con=null;	}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return favoList;
	}
	
	public ArrayList<ContentsVO> getFavoriteListInfo(ArrayList<ContentsVO> arrayList){
		ArrayList<ContentsVO> favoList = new ArrayList<ContentsVO>();
		
		System.out.println("Contents DAO :: getFavoriteListInfo");
		//System.out.println("arrayList : " + arrayList);
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 커리문
			sql.append("SELECT * FROM CONTENTS_BOARD ");
			sql.append("WHERE CONTENTS_NUM=?");
				
			pstmt = con.prepareStatement(sql.toString());
			for(int i=0; i<arrayList.size(); i++) {
				arrayList.get(i).getContents_num();
				pstmt.setInt(1, arrayList.get(i).getContents_num());
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					ContentsVO vo = new ContentsVO();
					vo.setContents_num(rs.getInt("CONTENTS_NUM"));
					vo.setContents_subject(rs.getString("CONTENTS_SUBJECT"));
					vo.setContents_creator(rs.getString("CONTENTS_CREATOR"));
					vo.setContents_src(rs.getString("CONTENTS_SRC"));
					vo.setContents_file(rs.getString("CONTENTS_FILE"));
					favoList.add(vo);
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if ( rs != null) { rs.close(); rs=null; }
				if ( pstmt != null ){ pstmt.close(); pstmt=null; }
				if ( con != null ){ con.close(); con=null;	}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return favoList;
	}
}
