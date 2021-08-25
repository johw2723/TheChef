package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TheChefDAO {
	DataSource ds;
	private Connection con;
	private PreparedStatement pstmt;
	private String query;
	private ResultSet rs;
	private TheChefVO vo;
	
	// 싱글톤 패턴
	private static TheChefDAO instance;
	
	public TheChefDAO() {
        try {
	        Context ic=new InitialContext();
            ds=(DataSource)ic.lookup("java:comp/env/jdbc/Oracle11g");                
        } catch(NamingException e ) {
        	e.printStackTrace();
        }		
     }
	
	public static TheChefDAO getInstance() {
		if(instance==null)
			instance = new TheChefDAO();
		return instance;
	}
	
	public void insertMember(TheChefVO vo) {
		try {
			con = ds.getConnection(); 
			System.out.println("DAO :: insertMember() 실행");
			
			query = "insert into member(memId, pw, email, phone, address, payment, memberDate) values(?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getMemId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getPhone());
			pstmt.setString(5, vo.getAddress());
			pstmt.setString(6, vo.getPayment());
			pstmt.setDate(7, new Date(System.currentTimeMillis()));
			int i = pstmt.executeUpdate();	
			System.out.println(i+"행이 추가되었습니다.");			
		} catch(SQLException e) {		
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//목록을 가져오는 메소드
	public List<TheChefVO> MemberList() {
		List<TheChefVO> list = new ArrayList<TheChefVO>();
		//ArrayList list = new ArrayList();
		try {
			con = ds.getConnection(); 
			System.out.println("DAO :: MemberList() 실행");
			query = "SELECT * FROM member";
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String memId = rs.getString("memId").trim();
				String pw = rs.getString("pw").trim();
				String email = rs.getString("email").trim();
				String phone = rs.getString("phone").trim();
				String address = rs.getString("address");
				String payment =rs.getString("payment");	
				java.sql.Date memberDate = rs.getDate("memberDate");
				list.add(new TheChefVO(memId, pw, email, phone, address, payment, memberDate));
			}
			return list;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public TheChefVO MemberInfo(String memId) {
		try {
			con = ds.getConnection(); 
			System.out.println("DAO :: MemberInfo() 실행");
			query = "SELECT * FROM member WHERE memID=?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String pw = rs.getString("pw").trim();
				String email = rs.getString("email").trim();
				String phone = rs.getString("phone").trim();
				String address = rs.getString("address");
				String payment =rs.getString("payment");	
				java.sql.Date memberDate = rs.getDate("memberDate");
				vo = new TheChefVO(memId, pw, email, phone, address, payment, memberDate);
			}
			//return vo;
		} catch(SQLException e) {
			e.printStackTrace();
			//return null;
		} catch(Exception e) {
			e.printStackTrace();
			//return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return vo;
	}
	
	public void updateMember(TheChefVO vo) {	
		try {
			con = ds.getConnection(); 
			System.out.println("DAO :: updateMember() 실행");		
			
			String getSql = "SELECT * FROM member WHERE memID=?";
			PreparedStatement getPstmt = con.prepareStatement(getSql);
			getPstmt.setString(1, vo.getMemId());
			rs = getPstmt.executeQuery(); 
			
			query = "update member set pw=?, email=?, phone=?, address=?, payment=?, memberDate=? where memId=?";			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPhone());
			
			// 주소와 결제방법 모두 null 값인 경우
			if(("".equals(vo.getAddress()) || vo.getAddress() == null) && ("".equals(vo.getPayment()) || vo.getPayment() == null)) {			
				if(rs.next()) {
					vo.setAddress(rs.getString("address"));
					pstmt.setString(4, vo.getAddress());
					vo.setPayment(rs.getString("payment"));
					pstmt.setString(5, vo.getPayment());
				}	
			// 주소만 null값인 경우	
			} else if(("".equals(vo.getAddress()) || vo.getAddress() == null) && !("".equals(vo.getPayment()) || vo.getPayment() == null)) {
				if(rs.next()) {
					vo.setAddress(rs.getString("address"));
					pstmt.setString(4, vo.getAddress());
				}				
				pstmt.setString(5, vo.getPayment());
			// 결제방법만 null값인 경우
			} else if(("".equals(vo.getPayment()) || vo.getPayment() == null) && !("".equals(vo.getAddress()) || vo.getAddress() == null)) {			
				pstmt.setString(4, vo.getAddress());
				if(rs.next()) {
					vo.setPayment(rs.getString("payment"));
					pstmt.setString(5, vo.getPayment());						
				}
			} else {
				pstmt.setString(4, vo.getAddress());
				pstmt.setString(5, vo.getPayment());
			}	
			
			pstmt.setDate(6, new Date(System.currentTimeMillis()));
			pstmt.setString(7, vo.getMemId());
			
			int i = pstmt.executeUpdate();
			System.out.println(i+"행이 수정되었습니다.");
		
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();		
		} finally {
			try {
				rs.close();;
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteMember(String memId) {
		try {
			con = ds.getConnection(); 
			System.out.println("DAO :: deleteMember() 실행");
			query = "delete from member where memid=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, memId);
			int i = pstmt.executeUpdate();
			System.out.println(i+"행이 삭제되었습니다.");
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();		
		} finally {
			try {
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public int loginCheck(String memId, String pw) {
		String dbPW = ""; // db 에서 꺼낸 비밀번호를 담을 변수
		int x = -1;
		try {
			con = ds.getConnection();
			System.out.println("DAO :: loginCheck() 실행");	
			
			// 입력된 ID 값으로 DB에 저장된 비밀번호를 조회한다.
			query = "select pw from member where memId=?";		
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //입력된 아이디에 해당하는 비밀번호가 있을 경우
				dbPW = rs.getString("pw").trim(); //비밀번호를 변수에 담는다.
				
				if (dbPW.equals(pw)) {
					x = 1; // 넘겨받은 비밀번호와 꺼내온 비밀번호를 비교. 같으면 1 :: 인증 성공을 반환
				} else {
					x = 0; // DB의 비밀번호와 입력받은 비밀번호가 다를 경우 0 :: 인증 실패를 반환
				}		
			} else {
				x = -1; // 해당 아이디가 없을 경우  -1 :: 아이디가 없음을 알림
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return x;
	}
		
	public boolean duplicateIdCheck(String memId) {		
		boolean x= false;
		System.out.println("DAO :: duplicateIdCheck() 실행");
		try {
			// 쿼리			
			query = "SELECT memId FROM MEMBER WHERE memId=?";		
			con = ds.getConnection();			
			pstmt = con.prepareStatement(query);		
			pstmt.setString(1, memId);		
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				x= true; //해당 아이디 존재			
			}
		
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				con.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return x;
	} // end duplicateIdCheck()
}
