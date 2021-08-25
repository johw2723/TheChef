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
        System.out.println("The Chef Servlet() :: ����");
    }
    
    public void init(){
    	tcs = new TheChefService();
    	System.out.println("Servlet :: init() ����");
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//GET ��� ��û�� ��� ȣ��
		processRequest(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//POST ��� ��û�� ��� ȣ��
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

	// login ��û�� ��� ����Ǵ� �޼ҵ�
	public void MemberLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberLogin() ����");
		
		// �α��� �� ���� �������� ���ư��� ���� referer ���� �޴´�.
		String referer = request.getParameter("referer");
		System.out.println("referer : " + referer);
		
		// �α��� ȭ�鿡 �Էµ� ���̵�� ��й�ȣ�� �����´�.
		String memId = request.getParameter("memId");
		String pw = request.getParameter("pw");						
		
		System.out.println("memId : " + memId);
		System.out.println("pw : " + pw);
		
		// DB���� ���̵�, ��й�ȣ Ȯ��
		TheChefDAO dao = TheChefDAO.getInstance();
        int check = dao.loginCheck(memId, pw);
                       
        System.out.println("check : " + check);
        // URL �� �α��� ���� ���� �޽���
        String msg = "";
                    
        HttpSession session = request.getSession();                
        
        if(check == 1) { // �α��� ����
        	// session�� ���� ���̵� ����
        	session.setAttribute("sessionID", memId);   
 	       	
        	// DB���� ȸ�������� ������ (ȸ�� ���� ������ Ȱ���� session ��)
            vo = tcs.MemberInfo(memId);
            String phone = vo.getPhone();
            String email = vo.getEmail();
            String address = vo.getAddress();
            String payment = vo.getPayment();
            
            System.out.println("phone : " + phone);
            System.out.println("email : " + email);
            System.out.println("address : " + address);
            System.out.println("payment : " + payment);
            
            // ȸ���������� �����ϴ� ��츦 ���� �� ����
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
        } else if(check == 0) { //��й�ȣ�� Ʋ�� ���
        	msg = "view_member/login.jsp?msg=0";
        } else { //���̵� Ʋ���� ���
        	msg = "view_member/login.jsp?msg=-1";
        }       
        response.sendRedirect(msg);
	}
	
	public void MemberLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberLogout() ����");
		String referer = (String)request.getHeader("REFERER");
		System.out.println("referer : " + referer);
		HttpSession session = request.getSession(false);
		
		if(session != null) {     
			System.out.println("logout called");
			// ��� ���� ���� ����
			session.invalidate();
			response.sendRedirect(referer);
		}
	}
	
	// insertMember ��û�� ��� ����Ǵ� �޼ҵ�
	public void insertMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: insertMember() ����");
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
		
		// ���� ȣ��	
		try {
			tcs.insertMember(vo);
		} catch(Exception e) {
			// ���� ����
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
			dispatcher.forward(request, response);
		}
		
		// ���� ����
		RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/insertMember.jsp");
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}
	
	// MemberList ��û�� ��� ����Ǵ� �޼ҵ�
	public void MemberList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberList() ����");
		//����Ͻ� ���� ȣ��
    	try {	
			list = tcs.MemberList();
    	} catch(NullPointerException e) {
    		e.printStackTrace();
		} catch(Exception e) {
			// 6. ����ó��
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
			dispatcher.forward(request, response);
		}			
		// 5. ����
		RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/getMemberList.jsp");
		request.setAttribute("list", list);
		dispatcher.forward(request, response);
	}
	
	// MemberInfo ��û�� ��� ����Ǵ� �޼ҵ�
	public void MemberInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: MemberInfo() ����");
		//����Ͻ� ���� ȣ��				
    	try {	
    		String memId = request.getParameter("memId");
			vo = tcs.MemberInfo(memId);
    	} catch(NullPointerException e) {
    		e.printStackTrace();
		} catch(Exception e) {
			// 6. ����ó��
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
			dispatcher.forward(request, response);
		}			
		// 5. ����
		RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/getMemberInfo.jsp");
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}	
	
	// UpdateMember ��û�� ��� ����Ǵ� �޼ҵ�
	public void UpdateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: UpdateMember() ����");
		// �� ó��
		String memId = request.getParameter("memId");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String payment = request.getParameter("payment");			
		
		vo = new TheChefVO(memId, pw, email, phone, address, payment);
		
		// ���̵� üũ ����
		tcs.idCheck(memId);
        TheChefDAO dao = TheChefDAO.getInstance();
        boolean result = dao.duplicateIdCheck(memId);
		
		String msg = "";
		if("".equals(memId) || memId == null) {
			msg = "view_member/adminMenu.jsp?msg=0";	// ���̵� �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� 0 : ���̵� ���Է�");
		} else if(result == false) {
			msg = "view_member/adminMenu.jsp?msg=-1";	// �������� �ʴ� ȸ�� ������ ���
			response.sendRedirect(msg);
			System.out.println("���� -1 : �������� �ʴ� ȸ������");
		} else if("".equals(pw) || pw == null) {
			msg = "view_member/adminMenu.jsp?msg=1";	// �н����尡 �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� 1 : ��й�ȣ ���Է�");
		} else if("".equals(email) || email == null) {
			msg = "view_member/adminMenu.jsp?msg=2";   // �̸����� �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� 2 : �̸��� ���Է�");
		} else if("".equals(phone) || phone == null) {
			msg = "view_member/adminMenu.jsp?msg=3";   // ��ȣ�� �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� 3 : ��ȭ��ȣ ���Է�");
		} else {
			try {		
				tcs.updateMember(vo);				
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. ����ó��
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. ����
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/updateMember.jsp");
			request.setAttribute("vo", vo);
			dispatcher.forward(request, response);			
		}
	}
	
	// UserInfoUpdate ��û�� ��� ����Ǵ� �޼ҵ�
	public void UserInfoUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: UserInfoUpdate() ����");
		// �� ó��
		String memId = request.getParameter("memId");
		String pw = request.getParameter("pw");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String payment = request.getParameter("payment");			
				
		//����Ͻ� ����
		String msg = "";
		if("".equals(pw) || pw == null) {
			msg = "view_member/userUpdate.jsp?msg=0";	// �н����尡 �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� 0 : ��й�ȣ ���Է�");
		} else if("".equals(email) || email == null) {
			msg = "view_member/userUpdate.jsp?msg=1";   // �̸����� �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� 1 : �̸��� ���Է�");
		} else if("".equals(phone) || phone == null) {
			msg = "view_member/userUpdate.jsp?msg=2";   // ��ȣ�� �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� 2 : ��ȭ��ȣ ���Է�");
		} else {
			try {		
				vo = new TheChefVO(memId, pw, email, phone, address, payment);
				tcs.updateMember(vo);				
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. ����ó��
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. ����
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/updateMember.jsp");
			request.setAttribute("vo", vo);
			dispatcher.forward(request, response);
			
			// ���� ����
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
	
	// DeleteMember ��û�� ��� ����Ǵ� �޼ҵ�
	public void DeleteMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: DeleteMember() ����");
		
		String memId = request.getParameter("memId");	
		
		//����Ͻ� ���� ȣ��
		String msg = "";
		if("".equals(memId) || memId == null) {
			msg = "view_member/adminMenu.jsp?msg=-2";	// ���̵� �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� -1 : ���̵� ���Է�");
		} else {
			try {	
				tcs.deleteMember(memId);
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. ����ó��
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. ����
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/deleteMember.jsp");
			request.setAttribute("member", memId);
			dispatcher.forward(request, response);
		}		
	}
	
	public void UserDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: UserDelete() ����");
		
		String memId = request.getParameter("memId");	
		session = request.getSession();
		System.out.println("memId : " + memId);
		System.out.println("sessionId : " + session.getAttribute("sessionID"));

		String msg = "";
		if("".equals(memId) || memId == null) {
			msg = "view_member/userUpdate.jsp?msg=-1";	// ���̵� �Էµ��� �ʾ��� ���
			response.sendRedirect(msg);
			System.out.println("���� -1 : ���̵� ���Է�");
		} else if(memId.equals(session.getAttribute("sessionID"))) {
			//����Ͻ� ���� ȣ��
			try {	
				tcs.deleteMember(memId);
	    	} catch(NullPointerException e) {
	    		e.printStackTrace();
			} catch(Exception e) {
				// 6. ����ó��
				RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/error.jsp");
				dispatcher.forward(request, response);
			}			
			// 5. ����
			RequestDispatcher dispatcher = request.getRequestDispatcher("view_member/deleteMember.jsp");
			request.setAttribute("member", memId);
			dispatcher.forward(request, response);
			
			HttpSession session = request.getSession(false);
			
			if(session != null) {     
				System.out.println("logout called");
				// ��� ���� ���� ����
				session.invalidate();
			}			
		} else {
			msg = "view_member/userUpdate.jsp?msg=-2";	// ���̵� ��ġ���� �ʴ°��
			response.sendRedirect(msg);
			System.out.println("���� -2 : ���̵� ����ġ");
		}
	}
	
	protected void IdCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet :: IdCheck() ����");
		
		String memId = request.getParameter("memId");
        System.out.println("Servlet :: memId = " + memId);    
        
        boolean result = tcs.idCheck(memId);
        //TheChefDAO dao = TheChefDAO.getInstance();
        //boolean result = dao.duplicateIdCheck(memId);
		
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

		if(result)	out.println("0"); // ���̵� �ߺ�
		else		out.println("1");
		
		out.close();
    } 
}
