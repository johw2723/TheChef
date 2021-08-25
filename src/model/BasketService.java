package model;

import java.util.ArrayList;
import java.util.List;

import dao.BasketDAO;
import dao.BasketVO;
import dao.PaymentVO;
import dao.ProductVO;

public class BasketService {
	private BasketDAO bdao;
	
	public BasketService() {
		bdao = new BasketDAO();
	}
	

	public int countBasket(String product_id, String memId) {
		return bdao.countBasket(product_id, memId);
	}

	// 장바구니 추가
	public void insertBasket(BasketVO bvo) {
		bdao.insertBasket(bvo);
		
	}

	public void updateBasket(BasketVO bvo) {
		bdao.updateBasket(bvo);
		
	}

	public List<BasketVO> listBasket(String memId) {
		return bdao.listBasket(memId);
	}
	
	/* listBasket 외래키 사용으로 미사용
	public List<BasketVO> listProduct(String memId){
		return bdao.listProduct(bdao.listBasket(memId));
	}
	*/


	public int sumMoney(String memId) {
		return bdao.sumMoney(memId);
	}


	public void basketDelete(int orderNum) {
		bdao.basketDelete(orderNum);		
	}


	public void modifyBasket(BasketVO bvo) {
		bdao.modifyBasket(bvo);	
	}


	public void orderPayment(PaymentVO vo) {
		bdao.orderPayment(vo);		
	}
	
	public void PaymentInfo(PaymentVO vo) {
		bdao.orderPayment(vo);		
	}
	
	public ArrayList<PaymentVO> getPaymentList(String memId){
		return bdao.getPaymentList(memId);
	}
	
	public PaymentVO getPaymentVO(String memId){
		return bdao.getPaymentVO(memId);
	}


	public void DeletePayInfo(int paymentNum) {
		bdao.DeletePayInfo(paymentNum);
		
	}

	public void basketAllDelete(String memId) {
		bdao.basketAllDelete(memId);	
	}
}
