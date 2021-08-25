package model;

import java.util.ArrayList;
import java.util.HashMap;

import dao.ProductDAO;
import dao.ProductVO;
import dao.TheChefVO;

public class ProductService {
	private ProductDAO dao;
	
	public ProductService() {
		dao = new ProductDAO();
	}

	public ArrayList<ProductVO> getProductList(HashMap<String, Object> listOpt) {
		return dao.getProductList(listOpt);
	}

	public ProductVO getDetail(String product_id) {
		return dao.getDetail(product_id);
	}
	
	public boolean checkJjimData(ProductVO pvo, TheChefVO vo) {
		return dao.selectJjimCount(pvo, vo) != null;
	}
	
	public void insertOrDeleteJjim(ProductVO pvo, TheChefVO vo) {
		if(checkJjimData(pvo, vo)) {
			dao.deleteJjim(pvo, vo);
		} else {
			dao.insertJjim(pvo, vo);
		}
	}
	
	public ArrayList<ProductVO> getJjimList(String memId){
		return dao.getJjimList(memId);
	}
	
	public ArrayList<ProductVO> getJjimListInfo(String memId){
		return dao.getJjimListInfo(dao.getJjimList(memId));
	}
}
