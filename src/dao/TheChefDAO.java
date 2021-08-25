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
	
	// �̱��� ����
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
			System.out.println("DAO :: insertMember() ����");
			
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
			System.out.println(i+"���� �߰��Ǿ����ϴ�.");			
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
	
	//����� �������� �޼ҵ�
	public List<TheChefVO> MemberList() {
		List<TheChefVO> list = new ArrayList<TheChefVO>();
		//ArrayList list = new ArrayList();
		try {
			con = ds.getConnection(); 
			System.out.println("DAO :: MemberList() ����");
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
			System.out.println("DAO :: MemberInfo() ����");
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
			System.out.println("DAO :: updateMember() ����");		
			
			String getSql = "SELECT * FROM member WHERE memID=?";
			PreparedStatement getPstmt = con.prepareStatement(getSql);
			getPstmt.setString(1, vo.getMemId());
			rs = getPstmt.executeQuery(); 
			
			query = "update member set pw=?, email=?, phone=?, address=?, payment=?, memberDate=? where memId=?";			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPhone());
			
			// �ּҿ� ������� ��� null ���� ���
			if(("".equals(vo.getAddress()) || vo.getAddress() == null) && ("".equals(vo.getPayment()) || vo.getPayment() == null)) {			
				if(rs.next()) {
					vo.setAddress(rs.getString("address"));
					pstmt.setString(4, vo.getAddress());
					vo.setPayment(rs.getString("payment"));
					pstmt.setString(5, vo.getPayment());
				}	
			// �ּҸ� null���� ���	
			} else if(("".equals(vo.getAddress()) || vo.getAddress() == null) && !("".equals(vo.getPayment()) || vo.getPayment() == null)) {
				if(rs.next()) {
					vo.setAddress(rs.getString("address"));
					pstmt.setString(4, vo.getAddress());
				}				
				pstmt.setString(5, vo.getPayment());
			// ��������� null���� ���
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
			System.out.println(i+"���� �����Ǿ����ϴ�.");
		
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
			System.out.println("DAO :: deleteMember() ����");
			query = "delete from member where memid=?";
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, memId);
			int i = pstmt.executeUpdate();
			System.out.println(i+"���� �����Ǿ����ϴ�.");
			
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
		String dbPW = ""; // db ���� ���� ��й�ȣ�� ���� ����
		int x = -1;
		try {
			con = ds.getConnection();
			System.out.println("DAO :: loginCheck() ����");	
			
			// �Էµ� ID ������ DB�� ����� ��й�ȣ�� ��ȸ�Ѵ�.
			query = "select pw from member where memId=?";		
			pstmt = con.prepareStatement(query.toString());
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //�Էµ� ���̵� �ش��ϴ� ��й�ȣ�� ���� ���
				dbPW = rs.getString("pw").trim(); //��й�ȣ�� ������ ��´�.
				
				if (dbPW.equals(pw)) {
					x = 1; // �Ѱܹ��� ��й�ȣ�� ������ ��й�ȣ�� ��. ������ 1 :: ���� ������ ��ȯ
				} else {
					x = 0; // DB�� ��й�ȣ�� �Է¹��� ��й�ȣ�� �ٸ� ��� 0 :: ���� ���и� ��ȯ
				}		
			} else {
				x = -1; // �ش� ���̵� ���� ���  -1 :: ���̵� ������ �˸�
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
		System.out.println("DAO :: duplicateIdCheck() ����");
		try {
			// ����			
			query = "SELECT memId FROM MEMBER WHERE memId=?";		
			con = ds.getConnection();			
			pstmt = con.prepareStatement(query);		
			pstmt.setString(1, memId);		
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				x= true; //�ش� ���̵� ����			
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
