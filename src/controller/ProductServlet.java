package controller;

import java.awt.List;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProductVO;
import dao.TheChefVO;
import model.ProductService;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService ps;
	//private ProductVO vo;
       

    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	ps = new ProductService();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
		System.out.println("Servlet :: command : " + command);
		
		if(command.equals("ProductList")) {
			ProductList(request, response);
		} else if(command.equals("Jjim")) {
			Jjim(request, response);
		} 
	}

	

	public void Jjim(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ProductServlet :: jjim 실행");
		
		String product_id = request.getParameter("product_id");
		System.out.println("product_id : " + product_id);
		
		ProductVO pvo = new ProductVO();
		pvo.setProduct_id(product_id);
		
		String memId = request.getParameter("memId");
		System.out.println("memId : " + memId);
		
		TheChefVO tcvo = new TheChefVO(memId);
		tcvo.setMemId(memId);
		
		ps.insertOrDeleteJjim(pvo, tcvo);	
	}

	public void ProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ProductList.jsp 에서 대신함
		System.out.println("ProductServlet :: ProductList 실행");
		
		// 검색조건과 검색내용을 가져온다.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");
		
		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);	
		
		// 글목록을 가져온다.
		ArrayList<ProductVO> productList =  ps.getProductList(listOpt);
		request.setAttribute("productList", productList);
		
		// 로직 응답
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/productList.jsp");
		request.setAttribute("productList", productList);
		dispatcher.forward(request, response);	
	}
}
