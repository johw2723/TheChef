package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardBean;
import model.BoardService;


@WebServlet(name = "BoardServlet1", urlPatterns = { "/BoardServlet1" })
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private HashMap<String, Action> commandMap;
	private BoardService bs;
	private BoardBean vo;
           
    public BoardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
    	bs = new BoardService();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doProcess(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			doProcess(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		
		String command = request.getParameter("command");
        System.out.println("Servlet :: command = " + command);        
        
        if(command.equals("BoardWrite")) {
			BoardWrite(request, response);
		} else if(command.equals("BoardDetail")) {
			BoardDetail(request, response);
		} else if(command.equals("FileDown")) {
			FileDown(request, response);
		} else if(command.equals("BoardReply")) {
			BoardReply(request, response);
		} else if(command.equals("BoardDelete")) {
			BoardDelete(request, response);
		} else if(command.equals("BoardUpdate")) {
			BoardUpdate(request, response);
		}
			
    } // end doProcess               
	
	public void BoardUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// ��� �ۼ� �� ���� �������� ���ư��� ���� ������ ��ȣ�� �ʿ���.
		String pageNum = request.getParameter("page");
		
		// ���ε� ���� ������
		int fileSize = 5*1024*1024;
		// ���ε�� ���� ������
		String uploadPath = request.getServletContext().getRealPath("/UploadFolder");
	
		try {
			MultipartRequest multi = new MultipartRequest
					(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());
			
			// �Ķ���� ���� �����´�.
			int num = Integer.parseInt(multi.getParameter("board_num"));
			String subject = multi.getParameter("board_subject");
			String content = multi.getParameter("board_content");
			String exisetFile = multi.getParameter("existing_file");
			
			// �Ķ���� ���� �ڹٺ� �����Ѵ�.
			vo = new BoardBean();
			vo.setBoard_num(num);
			vo.setBoard_subject(subject);
			vo.setBoard_content(content);
			
			// �� ���� �� ���ε�� ������ �����´�.
			Enumeration<String> fileNames = multi.getFileNames();
			if(fileNames.hasMoreElements()) {
				String fileName = fileNames.nextElement();
				String updateFile = multi.getFilesystemName(fileName);
				
				if(updateFile == null) { // ������ ���ο� ������ ÷�� ���ߴٸ� ���� ���ϸ��� �����Ѵ�.
					vo.setBoard_file(exisetFile);
				} else {                 // ���ο� ������ ÷������ ���
					vo.setBoard_file(updateFile);
				}
			}
			
			boolean result = bs.BoardUpdate(vo);
			
			if(result) {
				// �����ִ� �������� ���ư��� ���� ��������ȣ�� �����Ѵ�.
				response.sendRedirect("../TheChef/board/BoardListForm.jsp?page="+pageNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�� ���� ���� : " + e.getMessage());
		}					
	}

	public void BoardDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// �� ��ȣ�� �����´�.
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
		
		//BoardDAO dao = BoardDAO.getInstance();
		
		// ������ �ۿ� �ִ� ���� ������ �����´�.
		String fileName = bs.getFileName(boardNum);
		
		// �� ���� - ����� ���� ��� ��۵� ��� �����Ѵ�.
		boolean result = bs.deleteBoard(boardNum);
		
		// ���� ����
		if(fileName != null) {
			//������ �ִ� ������ ���� ��θ� �����´�.
			String folder = request.getServletContext().getRealPath("UploadFolder");
			// ������ ���� ��θ� �����.
			String filePath = folder + "/" + fileName;
			
			File file = new File(filePath);
			if(file.exists()) file.delete(); //������ 1���� ���ε� �ǹǷ� �ѹ��� ����.
		}
		
		if(result) {
			// �� ������ �Ϸ�Ǹ� ������� �̵��Ѵ�.
			response.sendRedirect("../TheChef/board/BoardListForm.jsp");
		} 
	}

	public void BoardReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		
		// BoardDAO dao = BoardDAO.getInstance();
		vo = new BoardBean();
		
		// ��� �ۼ� �� ���� �������� ���ư��� ���� ������ ��ȣ�� �ʿ��ϴ�.
        String pageNum = request.getParameter("page");
        
        // �ĸ����� ���� �����´�.
        int num = Integer.parseInt(request.getParameter("board_num"));
        String id = request.getParameter("board_id");
        String subject = request.getParameter("board_subject");
        String content = request.getParameter("board_content");
        int ref = Integer.parseInt(request.getParameter("board_re_ref"));
                
        // ��� ����
        vo.setBoard_num(bs.getSeq()); // ����� �۹�ȣ�� �������� ������ ����
        vo.setBoard_id(id);
        vo.setBoard_subject(subject);
        vo.setBoard_content(content);
        vo.setBoard_re_ref(ref);
        vo.setBoard_parent(num);    // �θ���� �� ��ȣ�� �����Ѵ�.
             
        boolean result = bs.boardInsert(vo);
        
        if(result){
        	// ��� ����� �Ϸ�Ǹ� ���� �������� �̵��ϱ� ���� ������ ��ȣ�� �����Ѵ�.
        	response.sendRedirect("../TheChef/board/BoardListForm.jsp?page="+pageNum);
        }						
	}

	public void FileDown(HttpServletRequest request, HttpServletResponse response) {
		// ���ϸ��� �����´�.
		String fileName = request.getParameter("file_name");

		// ������ �ִ� �����θ� �����´�.
		// ���� ���ε�� ������ UploadFolder ������ �ִ�.
		String folder = request.getServletContext().getRealPath("UploadFolder");
		// ������ �����θ� �����.
		String filePath = folder + "/" + fileName;

		try {
			File file = new File(filePath);
			byte b[] = new byte[(int) file.length()];
			
			// page�� ContentType���� �������� �ٲٱ� ���� �ʱ�ȭ��Ŵ
			response.reset();
			response.setContentType("application/octet-stream");
			
			// �ѱ� ���ڵ�
			String encoding = new String(fileName.getBytes("UTF-8"),"8859_1");
			
			// ���� ��ũ�� Ŭ������ �� �ٿ�ε� ���� ȭ���� ��µǰ� ó���ϴ� �κ�
			response.setHeader("Content-Disposition", "attachment;filename="+ encoding);
			response.setHeader("Content-Length", String.valueOf(file.length()));
			
			if (file.isFile()) // ������ �������
			{
				FileInputStream fileInputStream = new FileInputStream(file);
				ServletOutputStream servletOutputStream = response.getOutputStream();
				
				//  ������ �о Ŭ���̾�Ʈ������ �����Ѵ�.
				int readNum = 0;
				while ( (readNum = fileInputStream.read(b)) != -1) {
					servletOutputStream.write(b, 0, readNum);
				}
				
				servletOutputStream.close();
				fileInputStream.close();
			}
			
		} catch (Exception e) {
			System.out.println("Download Exception : " + e.getMessage());
		}				
	}

	public void BoardDetail(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		// BoardDetail.jsp ���� ��ü�� 
		System.out.println("Servlet :: BoardDetail ����");
		
		// �Ķ���ͷ� �Ѿ�� �۹�ȣ�� �����´�.
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
				
		System.out.println("boardNum : " + boardNum);
		
		String pageNum = request.getParameter("pageNum");
		System.out.println("pageNum : " + pageNum);
				
		//BoardDAO dao = BoardDAO.getInstance();
		vo = bs.getDetail(boardNum);
		boolean result = bs.updateCount(boardNum);				
				
		request.setAttribute("vo", vo);
		request.setAttribute("pageNum", pageNum);
		
		if(result){
			response.sendRedirect("../TheChef/board/BoardDetailForm.jsp");
		}		
	}

	public void BoardWrite(HttpServletRequest request, HttpServletResponse response) {		
		// ���ε� ���� ������
		int fileSize= 5*1024*1024;
		// ���ε�� ���� ������
		String uploadPath = request.getServletContext().getRealPath("/UploadFolder");

		try {	
			// ���Ͼ��ε� 
			MultipartRequest multi = new MultipartRequest
					(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

			// �����̸� ��������
			String fileName = "";
			Enumeration<String> names = multi.getFileNames();
			if(names.hasMoreElements()){
				String name = names.nextElement();
				fileName = multi.getFilesystemName(name);
			}
			
			//BoardDAO dao = BoardDAO.getInstance();
			vo = new BoardBean();
			
			vo.setBoard_num(bs.getSeq()); // �������� ������ ����
			vo.setBoard_id(multi.getParameter("board_id")); // ���簪
			vo.setBoard_subject(multi.getParameter("board_subject"));
			vo.setBoard_content(multi.getParameter("board_content"));
			vo.setBoard_file(fileName);
		
			boolean result = bs.boardInsert(vo);
			
			if(result){
				//String referer = (String)request.getHeader("REFERER");
				//System.out.println("referer : " + referer);			
				//response.sendRedirect(referer);
				
				response.sendRedirect("../TheChef/board/BoardListForm.jsp");			
			}		
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("�� �ۼ� ���� : " + e.getMessage());
		}				
	}
}
