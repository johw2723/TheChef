package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import org.json.simple.JSONObject;

import dao.BasketVO;
import dao.PaymentVO;
import dao.ProductVO;
import dao.TheChefVO;
import model.BasketService;


@WebServlet("/BasketServlet")
public class BasketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BasketService bs;   

    public BasketServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	bs = new BasketService();
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
		
		if(command.equals("AddBasket")) {
			AddBakset(request, response);
		} else if(command.equals("BasketDelete")) {
			BasketDelete(request, response);
		} else if(command.equals("BasketUpdate")) {
			BasketUpdate(request, response);
		} else if(command.equals("BasketOrder")) {
			BasketOrder(request, response);
		} else if(command.equals("BasketPayment")) {
			BasketPayment(request, response);
		} else if(command.equals("DeletePayInfo")) {
			DeletePayInfo(request, response);
		} else if(command.equals("quantityUpdate")) {
			quantityUpdate(request, response);
		}  
	}


	private void quantityUpdate(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			JSONObject obj = new JSONObject();
			
			// 파라미터 값 받기
			String memId = request.getParameter("memId");
			int quantity = Integer.parseInt(request.getParameter("quantity"));
			String product_id = request.getParameter("product_id");
			System.out.println(memId);
			System.out.println(quantity);
			System.out.println(product_id);
			
			obj.put("quantity", quantity);
			
			out.print(obj);   				
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}

	private void DeletePayInfo(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			JSONObject obj = new JSONObject();
			
			// 파라미터 값 받기
			int paymentNum = Integer.parseInt(request.getParameter("paymentNum"));
			System.out.println(paymentNum);
					
			bs.DeletePayInfo(paymentNum);
			
			obj.put("msg", paymentNum + "번 결제 정보가 삭제되었습니다.");
							
			System.out.println(obj.toString());	

			out.print(obj);   				
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}

	private void BasketPayment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String memId = (String) request.getSession().getAttribute("sessionID");		
		String[] product_id = request.getParameterValues("product_id");
		String[] product_name = request.getParameterValues("product_name");
		String[] product_price = request.getParameterValues("product_price");
		String[] quantity = request.getParameterValues("quantity");
		String[] money = request.getParameterValues("money");
		
		int[] product_price2 = Arrays.stream(product_price).mapToInt(Integer::parseInt).toArray();
		int[] quantity2 = Arrays.stream(quantity).mapToInt(Integer::parseInt).toArray();
		int[] money2 = Arrays.stream(money).mapToInt(Integer::parseInt).toArray();
		System.out.println(Arrays.toString(product_id));
		System.out.println(Arrays.toString(product_price2));
		System.out.println(Arrays.toString(quantity2));
		System.out.println(Arrays.toString(money2));
		
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String payment = request.getParameter("payment");
		int sumMoney = Integer.parseInt(request.getParameter("sumMoney"));
		int fee = Integer.parseInt(request.getParameter("fee"));
		int allSum = Integer.parseInt(request.getParameter("allSum"));
		
		PaymentVO vo = new PaymentVO();
		vo.setMemId(memId);
		vo.setProduct_id(product_id);
		vo.setProduct_name(product_name);
		vo.setProduct_price(product_price2);
		vo.setQuantity(quantity2);
		vo.setMoney(money2);
		vo.setPhone(phone);
		vo.setEmail(email);
		vo.setAddress(address);
		vo.setPayment(payment);
		vo.setSumMoney(sumMoney);
		vo.setFee(fee);
		vo.setAllSum(allSum);
		
		try {	
			 bs.orderPayment(vo);
    	} catch(NullPointerException e) {
    		e.printStackTrace();
		} catch(Exception e) {
			// 6. 예외처리
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
			dispatcher.forward(request, response);
		}			
		// 5. 응답
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/OrderComplete.jsp");
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
		
	}

	private void BasketOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//String memId = (String) request.getSession().getAttribute("sessionID");
		//String[] product_id = request.getParameterValues("product_id");
		//String[] quantity = request.getParameterValues("quantity");
		
		//System.out.println("memId : " + memId + " / product_id : " + product_id);
		//System.out.println("quantity : " + quantity);
		
		response.sendRedirect("product/BasketOrder.jsp");
	
	}

	private void BasketUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String memId = (String) request.getSession().getAttribute("sessionID");
		String[] product_id = request.getParameterValues("product_id");
		String[] quantity = request.getParameterValues("quantity");
		System.out.println("memId : " + memId + " / product_id : " + product_id);
		System.out.println("quantity : " + quantity);
		
		// 레코드의 갯수 만큼 반복문 실행
		
		for(int i=0; i<product_id.length; i++) {
			BasketVO bvo = new BasketVO();
			bvo.setMemId(memId);
			bvo.setQuantity(Integer.parseInt(quantity[i]));
			bvo.setProduct_id(product_id[i]);	
			//System.out.println(bvo.getQuantity());
			//System.out.println(bvo.getProduct_id());
			
			bs.modifyBasket(bvo);
		}			
		response.sendRedirect("product/BasketList.jsp");	
	}

	private void BasketDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int orderNum = Integer.parseInt(request.getParameter("orderNum"));
		
		System.out.println("orderNum : " + orderNum);
		bs.basketDelete(orderNum);
		
		response.sendRedirect("product/BasketList.jsp");
	}

	/* BasketList.JSP 에서 대체함 
	public void BasketList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String memId = (String) session.getAttribute("sessionID");
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<BasketVO> list = bs.listBasket(memId);
		//List<BasketVO> listProduct = bs.listProduct(memId);
		//List<ProductVO> listProduct = bs.listProduct(memId);
		int sumMoney = bs.sumMoney(memId);
		int fee = 3000;
		map.put("list", list);
		map.put("count", list.size());
		map.put("sumMoney", sumMoney);
		map.put("fee", fee);
		map.put("allSum", sumMoney + fee);
		request.setAttribute("map", map);
		
		//response.sendRedirect("product/BasketList.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("product/BasketList.jsp");
		request.setAttribute("list", list);
		//request.setAttribute("listProduct", listProduct);
		dispatcher.forward(request, response);
	}
	*/

	public void AddBakset(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		BasketVO bvo = new BasketVO();
		
		String product_id = request.getParameter("product_id");
		System.out.println("product_id : " + product_id);
		
		//ProductVO pvo = new ProductVO();
		bvo.setProduct_id(product_id);
		
		String memId = request.getParameter("memId");
		System.out.println("memId : " + memId);
		
		//TheChefVO tcvo = new TheChefVO(memId);
		bvo.setMemId(memId);
		
		// 장바구니에 기존 상품이 있는지 검사
		int count = bs.countBasket(bvo.getProduct_id(), memId);
		
		if(count == 0) {
			// 없으면 insert
			bs.insertBasket(bvo);
		} else {
			// 있으면 update
			bs.updateBasket(bvo);
		}
	}
}
