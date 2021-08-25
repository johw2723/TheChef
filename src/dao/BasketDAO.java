package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BasketDAO {
	private DataSource ds;
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public BasketDAO() {
		try {
			Context ic=new InitialContext();
			ds=(DataSource)ic.lookup("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	public void insertBasket(BasketVO bvo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO BASKET");
			sql.append("(OrderNum, PRODUCTID, MEMID, Quantity)");
			sql.append(" VALUES(seq_Basket.NEXTVAL, ?, ? ,?)");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, bvo.getProduct_id());
			pstmt.setString(2, bvo.getMemId());
			pstmt.setInt(3, bvo.getQuantity() + 1);
			
			int i = pstmt.executeUpdate();
			System.out.println(i + "행이 추가되었습니다.");
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

	public List<BasketVO> listBasket(String memId) {
		List<BasketVO> list = new ArrayList<BasketVO>();
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT");
			sql.append("	b.OrderNum,");
			sql.append("	b.MemID,");
			sql.append("	m.address,");
			sql.append("	m.payment,");
			sql.append("	p.Product_ID,");
			sql.append("	p.Product_NAME,");
			sql.append("	p.Product_URL,");
			sql.append("	b.Quantity,");
			sql.append("	p.product_price,");
			sql.append("	(PRODUCT_PRICE * Quantity) money ");
			sql.append("FROM");
			sql.append("	member m, product p, basket b ");
			sql.append("WHERE m.memID = b.memID");
			sql.append("	AND p.product_id = b.ProductID");
			sql.append("	AND b.MemID = ? ");
			sql.append("ORDER BY");
			sql.append("	b.OrderNum");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, memId);
			
			// StringBuffer를 비운다.
			sql.delete(0, sql.toString().length());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {		
				BasketVO bvo = new BasketVO();
				bvo.setOrderNum(rs.getInt("OrderNum"));
				bvo.setProduct_id(rs.getString("PRODUCT_ID"));
				bvo.setProduct_name(rs.getString("PRODUCT_NAME"));
				bvo.setProduct_url(rs.getString("PRODUCT_URL"));
				bvo.setMemId(memId);
				bvo.setProduct_price(rs.getInt("PRODUCT_PRICE"));
				bvo.setQuantity(rs.getInt("Quantity"));
				bvo.setMoney(rs.getInt("money"));
				bvo.setAddress(rs.getString("address"));
				bvo.setPayment(rs.getString("payment"));
				list.add(bvo);
			}				
		} catch (Exception e) {
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
	
	public int countBasket(String product_id, String memId) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT * FROM");
			sql.append(" BASKET WHERE PRODUCTID=? and MEMID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, product_id);
			pstmt.setString(2, memId);
			
			int basketCount = 0;
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				basketCount = rs.getInt(1);
			}
			return basketCount;
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	public void updateBasket(BasketVO bvo) {
		try {
			con = ds.getConnection();
			StringBuffer getSql = new StringBuffer();
			getSql.append("SELECT * FROM");
			getSql.append(" BASKET WHERE PRODUCTID=? and MEMID=?");
			
			PreparedStatement getPstmt = con.prepareStatement(getSql.toString());
			getPstmt.setString(1, bvo.getProduct_id());
			getPstmt.setString(2, bvo.getMemId());
			rs = getPstmt.executeQuery();
			if(rs.next()) {
				bvo.setQuantity(rs.getInt("QUANTITY"));
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BASKET");
			sql.append(" SET Quantity = ?");
			sql.append(" WHERE PRODUCTID=? and MEMID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bvo.getQuantity() + 1);	
			pstmt.setString(2, bvo.getProduct_id());	
			pstmt.setString(3, bvo.getMemId());	
			
			pstmt.executeUpdate();
			System.out.println("상품 수량이 추가되었습니다.");
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	public int sumMoney(String memId) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT NVL(SUM(PRODUCT_PRICE * Quantity), 0) money ");
			sql.append("FROM PRODUCT p, BASKET b ");
			sql.append("WHERE b.PRODUCTID = p.PRODUCT_ID AND b.MEMID = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, memId);
			
			int sumMoney = 0;
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				sumMoney = rs.getInt(1);
			}
			return sumMoney;
			
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}

	public void basketDelete(int orderNum) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM BASKET");
			sql.append("	WHERE orderNum = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, orderNum);
			
			int i = pstmt.executeUpdate();
			System.out.println(i + "행이 삭제되었습니다.");
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}

	public void modifyBasket(BasketVO bvo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("UPDATE BASKET");
			sql.append("	SET QUANTITY = ?");
			sql.append("	WHERE MEMID = ?");
			sql.append("	AND PRODUCTID = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, bvo.getQuantity());
			pstmt.setString(2, bvo.getMemId());
			pstmt.setString(3, bvo.getProduct_id());
			
			pstmt.executeUpdate();
			
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
		
	}

	public void orderPayment(PaymentVO vo) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO PAYMENTINFO");
			sql.append("(paymentNum, memID, email, phone, address, payment");
			sql.append(", product_id, product_name, product_price, quantity, money, sumMoney, fee, allSum, paymentDate) ");
			sql.append("VALUES(seq_PaymentInfo.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate)");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, vo.getMemId());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPhone());
			pstmt.setString(4, vo.getAddress());
			pstmt.setString(5, vo.getPayment());
			pstmt.setString(6, Arrays.toString(vo.getProduct_id()));
			//pstmt.setString(6, vo.getProduct_id2());
			pstmt.setString(6, Arrays.toString(vo.getProduct_id()).substring(1, Arrays.toString(vo.getProduct_id()).length()-1));
			pstmt.setString(7, Arrays.toString(vo.getProduct_name()).substring(1, Arrays.toString(vo.getProduct_name()).length()-1));
			pstmt.setString(8, Arrays.toString(vo.getProduct_price()).substring(1, Arrays.toString(vo.getProduct_price()).length()-1));
			//pstmt.setString(9, new Date(System.currentTimeMillis()); // 회원가입에서 사용한 방법이지만 여기서는 sysdate 명령어를 사용했다.
			pstmt.setString(9, Arrays.toString(vo.getQuantity()).substring(1, Arrays.toString(vo.getQuantity()).length()-1));
			pstmt.setString(10, Arrays.toString(vo.getMoney()).substring(1, Arrays.toString(vo.getMoney()).length()-1));
			pstmt.setInt(11, vo.getSumMoney());
			pstmt.setInt(12, vo.getFee());
			pstmt.setInt(13, vo.getAllSum());
			
			int i = pstmt.executeUpdate();
			System.out.println("주문 정보" + i + "건이 추가되었습니다.");
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

	public ArrayList<PaymentVO> getPaymentList(String memId) {
		ArrayList<PaymentVO> payList = new ArrayList<PaymentVO>();
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT * FROM PAYMENTINFO");
			sql.append("	WHERE MEMID=?");
			sql.append("	ORDER BY PAYMENTDATE desc");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,  memId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PaymentVO vo = new PaymentVO();
				
				vo.setMemId(memId);
				vo.setPaymentNum(rs.getInt("paymentNum"));
				vo.setAddress(rs.getString("ADDRESS"));
				vo.setPhone(rs.getString("PHONE"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setPayment(rs.getString("PAYMENT"));		
				vo.setProduct_id(rs.getString("PRODUCT_ID").split(", "));
				vo.setProduct_name(rs.getString("PRODUCT_NAME").split(", "));			
				vo.setProduct_price(Arrays.stream(rs.getString("PRODUCT_PRICE").split(", ")).mapToInt(Integer::parseInt).toArray());			
				vo.setQuantity(Arrays.stream(rs.getString("Quantity").split(", ")).mapToInt(Integer::parseInt).toArray());
				vo.setMoney(Arrays.stream(rs.getString("Money").split(", ")).mapToInt(Integer::parseInt).toArray());
				vo.setSumMoney(rs.getInt("SUMMONEY"));
				vo.setFee(rs.getInt("FEE"));
				vo.setAllSum(rs.getInt("ALLSUM"));
				vo.setPaymentDate(rs.getString("PAYMENTDATE"));

				payList.add(vo);
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return payList;
	}
	
	public PaymentVO getPaymentVO(String memId) {
		PaymentVO vo = new PaymentVO();
		
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT * FROM PAYMENTINFO");
			sql.append("	WHERE MEMID=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1,  memId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {		
				vo.setMemId(memId);
				vo.setAddress(rs.getString("ADDRESS"));
				vo.setPhone(rs.getString("PHONE"));
				vo.setEmail(rs.getString("EMAIL"));
				vo.setPayment(rs.getString("PAYMENT"));			
				vo.setProduct_id(rs.getString("PRODUCT_ID").split(", "));
				vo.setProduct_name(rs.getString("PRODUCT_NAME").split(", "));			
				vo.setProduct_price(Arrays.stream(rs.getString("PRODUCT_PRICE").split(", ")).mapToInt(Integer::parseInt).toArray());
				vo.setQuantity(Arrays.stream(rs.getString("Quantity").split(", ")).mapToInt(Integer::parseInt).toArray());
				vo.setSumMoney(rs.getInt("SUMMONEY"));
				vo.setFee(rs.getInt("FEE"));
				vo.setAllSum(rs.getInt("ALLSUM"));
				vo.setPaymentDate(rs.getString("PAYMENTDATE"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if (pstmt != null) {pstmt.close(); pstmt=null;}
				if (con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return vo;
	}

	public void DeletePayInfo(int paymentNum) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM PAYMENTINFO");
			sql.append("	WHERE paymentNum = ?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, paymentNum);
			
			int i = pstmt.executeUpdate();
			System.out.println(i + "행이 삭제되었습니다.");
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}	
	}

	public void basketAllDelete(String memId) {
		try {
			con = ds.getConnection();
			StringBuffer sql = new StringBuffer();
			sql.append("DELETE FROM BASKET");
			sql.append("	WHERE memId=?");
			
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, memId);
			
			pstmt.executeUpdate();
		} catch(SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			try {
				if(rs != null) {rs.close(); rs=null;}
				if(pstmt != null) {pstmt.close(); pstmt=null;}
				if(con != null) {con.close(); con=null;}
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}		
	}
}
