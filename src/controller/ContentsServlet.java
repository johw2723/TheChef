package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardBean;
import dao.ContentsVO;
import dao.TheChefVO;
import model.ContentsService;


@WebServlet("/ContentsServlet.do")
public class ContentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContentsService cs;
	private ContentsVO vo;
       
  
    public ContentsServlet() {
        super();
    }
    
    public void init(ServletConfig config) throws ServletException {
    	cs = new ContentsService();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
        System.out.println("Servlet :: command = " + command);
        
        if(command.equals("ContentsSearch")) {
        	ContentsSearch(request, response);
		} else if(command.equals("favorite")) {
			favorite(request, response);
		} 
	}

	public void favorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("favorite 실행");
		
		int no = Integer.parseInt(request.getParameter("contents_num"));
		System.out.println("no : " + no);
		
		ContentsVO cvo = new ContentsVO();
		cvo.setContents_num(no);
		
		String id = request.getParameter("memid");
		System.out.println("id : " + id);
		
		TheChefVO tcvo = new TheChefVO(id);
		tcvo.setMemId(id);
		
		cs.insertOrDeleteFovorite(cvo, tcvo);
		
	}

	public void ContentsSearch(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//vo = new ContentsVO();
		request.setCharacterEncoding("UTF-8");
		
		// 검색 옵션과 내용을 가져온다.
		String opt = request.getParameter("opt");
		String condition = request.getParameter("condition");	
		System.out.println("검색 옵션 : " + opt + " / 검색 내용 : " + condition);
		
		// 검색조건과 내용을 Map에 담는다.
		HashMap<String, Object> listOpt = new HashMap<String, Object>();
		listOpt.put("opt", opt);
		listOpt.put("condition", condition);	
		
		ContentsService cs = new ContentsService();
		
		// 글목록을 가져온다.	
		ArrayList<ContentsVO> list =  cs.getContentsList(listOpt);
		
		// requestScope 호출을 위한 값 저장	
		request.setAttribute("list", list);	
		
		// ASCII 가 아닌 파라미터의 경우 (condition) percent encoding 을 해줘야한다.
		// URLEncoder를 사용하면 된다.
		String encodedParam = URLEncoder.encode(condition, "UTF-8");
		response.sendRedirect("../TheChef/view_contents/search.jsp?opt="+opt+"&condition="+encodedParam);
		
	}
}
