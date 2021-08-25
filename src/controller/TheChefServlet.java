package controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TheChefDAO;
import dao.TheChefVO;
import model.TheChefService;

@WebServlet("/TheChef.do")
public class TheChefServlet extends HttpServlet {
	private TheChefService tcs;
	private TheChefVO vo;
	//private ArrayList list;
	private List<TheChefVO> list;
	private HttpSession session;
	private static final long serialVersionUID = 1L;

    public TheChefServlet() {
        super();
        //tcs = new TheChefService();
        System.out.println("The Chef Servlet() :: 실행");
    }
    
    public void init(){
    	tcs = new TheChefService();
    	System.out.println("Servlet :: init() 실행");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//GET 방식 요청일 경우 호출
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//POST 방식 요청일 경우 호출
		processRequest(request, response);
	}
	
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");         
        
        String command = request.getParameter("command");
        
        System.out.println("Servlet :: command = " + command);
        	
    	if(command.equals("insertMember")){
    		insertMember(request, response);	
		} else if(command.equals("MemberList")){
			MemberList(request, response);	
		} else if(command.equals("MemberInfo")){
			MemberInfo(request, response);	
		} else if(command.equals("UpdateMember")){
			UpdateMember(request, response);	
		} else if(command.equals("DeleteMember")){
			DeleteMember(request, response);
		} else if(command.equals("idCheck")) {
			IdCheck(request,response);
		} else if(command.equals("MemberLogin")) {
			MemberLogin(request, response);			
		} else if(command.equals("MemberLogout")) {
			MemberLogout(request, response);
		} else if(command.equals("UserInfoUpdate")) {
			UserInfoUpdate(request, response);
		} else if(command.equals("UserDelete")) {
			UserDelete(request, response);
		}
    } 

	// login 요청일 경우 실행되는 메소드
	public void MemberLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberLogin() 실행");
		
		// 로그인 후 이전 페이지로 돌아가기 위한 referer 값을 받는다.
		String referer = request.getParameter("referer");
		System.out.println("referer : " + referer);
		
		// 로그인 화면에 입력된 아이디와 비밀번호를 가져온다.
		String memId = request.getParameter("memId");
		String pw = request.getParameter("pw");						
		
		System.out.println("memId : " + memId);
		System.out.println("pw : " + pw);
		
		// DB에서 아이디, 비밀번호 확인
		TheChefDAO dao = TheChefDAO.getInstance();
        int check = dao.loginCheck(memId, pw);
                       
        System.out.println("check : " + check);
        // URL 및 로그인 관련 전달 메시지
        String msg = "";
                    
        HttpSession session = request.getSession();                
        
        if(check == 1) { // 로그인 성공
        	// session에 현재 아이디 세팅
        	session.setAttribute("sessionID", memId);   
 	       	
        	// DB에서 회원정보를 가져옴 (회원 정보 수정시 활용할 session 값)
            vo = tcs.MemberInfo(memId);
            String phone = vo.getPhone();
            String email = vo.getEmail();
            String address = vo.getAddress();
            String payment = vo.getPayment();
            
            System.out.println("phone : " + phone);
            System.out.println("email : " + email);
            System.out.println("address : " + address);
            System.out.println("payment : " + payment);
            
            // 회원정보에서 수정하는 경우를 위한 값 세팅
         	session.setAttribute("sessionPW", pw);
         	session.setAttribute("sessionPHONE", phone);
         	session.setAttribute("sessionEMAIL", email);
         	session.setAttribute("sessionADDRESS", address);
         	session.setAttribute("sessionPAYMENT", payment);       	        	
         	
         	if(referer.equals("http://localhost:8081/TheChef/view_member/login.jsp")){
         		msg = "http://localhost:8081/TheChef/index.jsp";
         	} else {
         		msg = referer;
         	}
        } else if(check == 0) { //비밀번호가 틀릴 경우
        	msg = "view_member/login.jsp?msg=0";
        } else { //아이디가 틀렸을 경우
        	msg = "view_member/login.jsp?msg=-1";
        }       
        response.sendRedirect(msg);
	}
	
	public void MemberLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberLogout() 실행");
		String referer = (String)request.getHeader("REFERER");
		System.out.println("referer : " + referer);
		HttpSession session = request.getSession(false);
		
		if(session != null) {     
			System.out.println("logout called");
			// 모든 세션 정보 삭제
			session.invalidate();
			response.sendRedirect(referer);
		}
	}
	
	// insertMember 요청일 경우 실행되는 메소드
	public void insertMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: insertMember() 실행");
		String memId = request.getParameter("memId");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String payment = request.getParameter("payment");
		System.out.println("memId :: " + memId);
		System.out.println("pw :: " + pw);
		System.out.println("email :: " + email);
		System.out.println("phone :: " + phone);
		System.out.println("address :: " + address);
		System.out.println("payment :: " + payment);
		
		vo = new TheChefVO(memId, pw, email, phone, address, payment);
		
		// 로직 호출	
		try {
			tcs.insertMember(vo);
		} catch(Exception e) {
			// 로직 예외
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
			dispatcher.forward(request, response);
		}
		
		// 로직 응답
		RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/insertMember.jsp");
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}
	
	// MemberList 요청일 경우 실행되는 메소드
	public void MemberList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberList() 실행");
		//비즈니스 로직 호출
    	try {	
			list = tcs.MemberList();
    	} catch(NullPointerException e) {
    		e.printStackTrace();
		} catch(Exception e) {
			// 6. 예외처리
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
			dispatcher.forward(request, response);
		}			
		// 5. 응답
		RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/getMemberList.jsp");
		request.setAttribute("list", list);
		dispatcher.forward(request, response);
	}
	
	// MemberInfo 요청일 경우 실행되는 메소드
	public void MemberInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberInfo() 실행");
		//비즈니스 로직 호출				
    	try {	
    		String memId = request.getParameter("memId");
			vo = tcs.MemberInfo(memId);
    	} catch(NullPointerException e) {
    		e.printStackTrace();
		} catch(Exception e) {
			// 6. 예외처리
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
			dispatcher.forward(request, response);
		}			
		// 5. 응답
		RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/getMemberInfo.jsp");
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}	
	
	// UpdateMember 요청일 경우 실행되는 메소드
	public void UpdateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: UpdateMember() 실행");
		// 값 처리
		String memId = request.getParameter("memId");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String payment = request.getParameter("payment");			
		
		vo = new TheChefVO(memId, pw, email, phone, address, payment);
		
		// 아이디 체크 로직
		tcs.idCheck(memId);
        TheChefDAO dao = TheChefDAO.getInstance();
        boolean result = dao.duplicateIdCheck(memId);
		
		String msg = "";
		if("".equals(memId) || memId == null) {
			msg = "view_member/adminMenu.jsp?msg=0";	// 아이디가 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 0 : 아이디 미입력");
		} else if(result == false) {
			msg = "view_member/adminMenu.jsp?msg=-1";	// 존재하지 않는 회원 정보일 경우
			response.sendRedirect(msg);
			System.out.println("예외 -1 : 존재하지 않는 회원정보");
		} else if("".equals(pw) || pw == null) {
			msg = "view_member/adminMenu.jsp?msg=1";	// 패스워드가 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 1 : 비밀번호 미입력");
		} else if("".equals(email) || email == null) {
			msg = "view_member/adminMenu.jsp?msg=2";   // 이메일이 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 2 : 이메일 미입력");
		} else if("".equals(phone) || phone == null) {
			msg = "view_member/adminMenu.jsp?msg=3";   // 번호가 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 3 : 전화번호 미입력");
		} else {
			try {		
				tcs.updateMember(vo);				
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. 예외처리
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. 응답
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/updateMember.jsp");
			request.setAttribute("vo", vo);
			dispatcher.forward(request, response);			
		}
	}
	
	// UserInfoUpdate 요청일 경우 실행되는 메소드
	public void UserInfoUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: UserInfoUpdate() 실행");
		// 값 처리
		String memId = request.getParameter("memId");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String payment = request.getParameter("payment");			
				
		//비즈니스 로직
		String msg = "";
		if("".equals(pw) || pw == null) {
			msg = "view_member/userUpdate.jsp?msg=0";	// 패스워드가 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 0 : 비밀번호 미입력");
		} else if("".equals(email) || email == null) {
			msg = "view_member/userUpdate.jsp?msg=1";   // 이메일이 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 1 : 이메일 미입력");
		} else if("".equals(phone) || phone == null) {
			msg = "view_member/userUpdate.jsp?msg=2";   // 번호가 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 2 : 전화번호 미입력");
		} else {
			try {		
				vo = new TheChefVO(memId, pw, email, phone, address, payment);
				tcs.updateMember(vo);				
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. 예외처리
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. 응답
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/updateMember.jsp");
			request.setAttribute("vo", vo);
			dispatcher.forward(request, response);
			
			// 세션 갱신
			HttpSession session = request.getSession();
			session.setAttribute("sessionID", memId);
	     	session.setAttribute("sessionPW", pw);
	     	session.setAttribute("sessionPHONE", phone);
	     	session.setAttribute("sessionEMAIL", email);
	     	session.setAttribute("sessionADDRESS", address);
	     	session.setAttribute("sessionPAYMENT", payment);
		     	
	     	System.out.println("sessionID : " + memId);
	     	System.out.println("sessionPW : " + pw);
	     	System.out.println("sessionPHONE : " + phone);
	     	System.out.println("sessionEMAIL : " + email);
	     	System.out.println("sessionADDRESS : " + address);
	     	System.out.println("sessionPAYMENT : " + payment);
		}								
	}
	
	// DeleteMember 요청일 경우 실행되는 메소드
	public void DeleteMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: DeleteMember() 실행");
		
		String memId = request.getParameter("memId");	
		
		//비즈니스 로직 호출
		String msg = "";
		if("".equals(memId) || memId == null) {
			msg = "view_member/adminMenu.jsp?msg=-2";	// 아이디가 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 -1 : 아이디 미입력");
		} else {
			try {	
				tcs.deleteMember(memId);
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. 예외처리
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. 응답
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/deleteMember.jsp");
			request.setAttribute("member", memId);
			dispatcher.forward(request, response);
		}		
	}
	
	public void UserDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: UserDelete() 실행");
		
		String memId = request.getParameter("memId");	
		session = request.getSession();
		System.out.println("memId : " + memId);
		System.out.println("sessionId : " + session.getAttribute("sessionID"));

		String msg = "";
		if("".equals(memId) || memId == null) {
			msg = "view_member/userUpdate.jsp?msg=-1";	// 아이디가 입력되지 않았을 경우
			response.sendRedirect(msg);
			System.out.println("예외 -1 : 아이디 미입력");
		} else if(memId.equals(session.getAttribute("sessionID"))) {
			//비즈니스 로직 호출
			try {	
				tcs.deleteMember(memId);
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. 예외처리
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. 응답
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/deleteMember.jsp");
			request.setAttribute("member", memId);
			dispatcher.forward(request, response);
			
			HttpSession session = request.getSession(false);
			
			if(session != null) {     
				System.out.println("logout called");
				// 모든 세션 정보 삭제
				session.invalidate();
			}			
		} else {
			msg = "view_member/userUpdate.jsp?msg=-2";	// 아이디가 일치하지 않는경우
			response.sendRedirect(msg);
			System.out.println("예외 -2 : 아이디 불일치");
		}
	}
	
	protected void IdCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: IdCheck() 실행");
		
		String memId = request.getParameter("memId");
        System.out.println("Servlet :: memId = " + memId);    
        
        boolean result = tcs.idCheck(memId);
        //TheChefDAO dao = TheChefDAO.getInstance();
        //boolean result = dao.duplicateIdCheck(memId);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		if(result)	out.println("0"); // 아이디 중복
		else		out.println("1");
		
		out.close();
    } 
}
