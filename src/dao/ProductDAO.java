package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProductDAO {
	private DataSource ds;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public ProductDAO() {
		try {
			Context ic=new InitialContext();
			ds=(DataSource)ic.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e){
			e.printStackTrace();
		}
	}

	public ArrayList<ProductVO> getProductList(HashMap<String, Object> listOpt) {
		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		
		System.out.println("Contents DAO :: ArrayList");
		String opt = (String)listOpt.get("opt");             // 검색옵션 : 상품명, 키워드 등 
		String condition = (String)listOpt.get("condition"); // 검색내용
		
		System.out.println("opt = " + opt);
		System.out.println("condition = " + condition);
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			// 목록 전체
			if(opt == null) {
				sql.append("SELECT * FROM PRODUCT");
				sql.append(" ORDER BY PRODUCT_ID ASC");
				
				pstmt = con.prepareStatement(sql.toString());
				
				// StringBuffer를 비운다.
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("1")) { // 상품명으로 검색
				sql.append("SELECT * FROM PRODUCT");
				sql.append(" where PRODUCT_NAME like ? ");
				sql.append("ORDER BY PRODUCT_ID ASC");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%" + condition + "%"); // 검색내용을 포함하면 모두 검색되도록 처리
				
				sql.delete(0, sql.toString().length());
			} else if(opt.equals("0")) { // 키워드로 검색
				sql.append("SELECT * FROM PRODUCT");
				sql.append(" where PRODUCT_KEYWORD like ? ");
				sql.append("ORDER BY PRODUCT_ID ASC");
				
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setString(1, "%" + condition + "%"); // 검색내용을 포함하면 모두 검색되도록 처리
				
				sql.delete(0, sql.toString().length());
			}
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductVO vo = new ProductVO();
				vo.setProduct_id(rs.getString("PRODUCT_ID"));
				vo.setProduct_name(rs.getString("PRODUCT_NAME"));
				vo.setProduct_price(rs.getInt("PRODUCT_PRICE"));
				vo.setProduct_keyword(rs.getString("PRODUCT_KEYWORD"));
				vo.setProduct_detail(rs.getString("PRODUCT_DETAIL"));
				vo.setProduct_url(rs.getString("PRODUCT_URL"));
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (rs != null) {rs.close(); rs=null;}
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return list;
	}
	
	public ProductVO getDetail(String product_id) {
		ProductVO vo = null;
		
		try {
			con = ds.getConnection();
					
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM PRODUCT WHERE PRODUCT_ID = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, product_id);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				vo = new ProductVO();
				vo.setProduct_id(product_id);
				vo.setProduct_name(rs.getString("PRODUCT_NAME"));
				vo.setProduct_price(rs.getInt("PRODUCT_PRICE"));
				vo.setProduct_detail(rs.getString("PRODUCT_DETAIL"));
				vo.setProduct_keyword(rs.getString("PRODUCT_KEYWORD"));
				vo.setProduct_url(rs.getString("PRODUCT_URL"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());		
		} finally {
			try {
				if (rs != null) {rs.close(); rs=null;}
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}			
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return vo;
	}

	public String selectJjimCount(ProductVO pvo, TheChefVO vo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM JJIM_PRODUCT");
			sql.append(" WHERE PRODUCT_ID=? AND REC_ID=?");			
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pvo.getProduct_id());
			pstmt.setString(2, vo.getMemId());
			
			String jjim = null;
			rs = pstmt.executeQuery();
			while(rs.next()) {
				jjim = rs.getString(1);				
			}
			return jjim;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (rs != null) {rs.close(); rs=null;}
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}

	public void deleteJjim(ProductVO pvo, TheChefVO vo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM JJIM_PRODUCT");
			sql.append(" WHERE PRODUCT_ID=? AND REC_ID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pvo.getProduct_id());
			pstmt.setString(2, vo.getMemId());
			
			int i = pstmt.executeUpdate();
			System.out.println(i + "행이 삭제되었습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}		
	}

	public void insertJjim(ProductVO pvo, TheChefVO vo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO JJIM_PRODUCT");
			sql.append("(PRODUCT_ID, REC_ID)");
			sql.append(" VALUES(?, ?)");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, pvo.getProduct_id());
			pstmt.setString(2, vo.getMemId());
			
			int i = pstmt.executeUpdate();
			System.out.println(i + "1행이 추가되었습니다.");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		
	}

	public ArrayList<ProductVO> getJjimList(String memId) {
		ArrayList<ProductVO> jjimList = new ArrayList<ProductVO>();
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT * FROM JJIM_PRODUCT");
			sql.append(" WHERE REC_ID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, memId);
			
			sql.delete(0, sql.toString().length());
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductVO vo = new ProductVO();
				vo.setProduct_id(rs.getString("PRODUCT_ID"));
				jjimList.add(vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (rs != null) {rs.close(); rs=null;}
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return jjimList;
	}

	public ArrayList<ProductVO> getJjimListInfo(ArrayList<ProductVO> arrayList) {
		ArrayList<ProductVO> jjimList = new ArrayList<ProductVO>();
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT * FROM PRODUCT");
			sql.append(" WHERE PRODUCT_ID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			for(int i=0; i<arrayList.size(); i++) {
				arrayList.get(i).getProduct_id();
				pstmt.setString(1, arrayList.get(i).getProduct_id());
				
				sql.delete(0, sql.toString().length());
				
				rs = pstmt.executeQuery();
				while(rs.next()) {
					ProductVO vo = new ProductVO();
					vo.setProduct_id(rs.getString("PRODUCT_ID"));
					vo.setProduct_name(rs.getString("PRODUCT_NAME"));
					vo.setProduct_price(rs.getInt("PRODUCT_PRICE"));
					vo.setProduct_keyword(rs.getString("PRODUCT_KEYWORD"));
					vo.setProduct_detail(rs.getString("PRODUCT_DETAIL"));
					vo.setProduct_url(rs.getString("PRODUCT_URL"));
					jjimList.add(vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if (rs != null) {rs.close(); rs=null;}
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return jjimList;
	}
}
