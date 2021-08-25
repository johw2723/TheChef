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
		// 답글 작성 후 원래 페이지로 돌아가기 위해 페이지 번호가 필요함.
		String pageNum = request.getParameter("page");
		
		// 업로드 파일 사이즈
		int fileSize = 5*1024*1024;
		// 업로드될 폴더 절대경로
		String uploadPath = request.getServletContext().getRealPath("/UploadFolder");
	
		try {
			MultipartRequest multi = new MultipartRequest
					(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());
			
			// 파라미터 값을 가져온다.
			int num = Integer.parseInt(multi.getParameter("board_num"));
			String subject = multi.getParameter("board_subject");
			String content = multi.getParameter("board_content");
			String exisetFile = multi.getParameter("existing_file");
			
			// 파라미터 값을 자바빈에 세팅한다.
			vo = new BoardBean();
			vo.setBoard_num(num);
			vo.setBoard_subject(subject);
			vo.setBoard_content(content);
			
			// 글 수정 시 업로드된 파일을 가져온다.
			Enumeration<String> fileNames = multi.getFileNames();
			if(fileNames.hasMoreElements()) {
				String fileName = fileNames.nextElement();
				String updateFile = multi.getFilesystemName(fileName);
				
				if(updateFile == null) { // 수정시 새로운 파일을 첨부 안했다면 기존 파일명을 세팅한다.
					vo.setBoard_file(exisetFile);
				} else {                 // 새로운 파일을 첨부했을 경우
					vo.setBoard_file(updateFile);
				}
			}
			
			boolean result = bs.BoardUpdate(vo);
			
			if(result) {
				// 원래있던 페이지로 돌아가기 위해 페이지번호를 전달한다.
				response.sendRedirect("../TheChef/board/BoardListForm.jsp?page="+pageNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("글 수정 오류 : " + e.getMessage());
		}					
	}

	public void BoardDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 글 번호를 가져온다.
		String num = request.getParameter("num");
		int boardNum = Integer.parseInt(num);
		
		//BoardDAO dao = BoardDAO.getInstance();
		
		// 삭제할 글에 있는 파일 정보를 가져온다.
		String fileName = bs.getFileName(boardNum);
		
		// 글 삭제 - 답글이 있을 경우 답글도 모두 삭제한다.
		boolean result = bs.deleteBoard(boardNum);
		
		// 파일 삭제
		if(fileName != null) {
			//파일이 있는 폴더의 절대 경로를 가져온다.
			String folder = request.getServletContext().getRealPath("UploadFolder");
			// 파일의 절대 경로를 만든다.
			String filePath = folder + "/" + fileName;
			
			File file = new File(filePath);
			if(file.exists()) file.delete(); //파일은 1개만 업로드 되므로 한번만 삭제.
		}
		
		if(result) {
			// 글 삭제가 완료되면 목록으로 이동한다.
			response.sendRedirect("../TheChef/board/BoardListForm.jsp");
		} 
	}

	public void BoardReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		
		// BoardDAO dao = BoardDAO.getInstance();
		vo = new BoardBean();
		
		// 답글 작성 후 원래 페이지로 돌아가기 위해 페이지 번호가 필요하다.
        String pageNum = request.getParameter("page");
        
        // 파리미터 값을 가져온다.
        int num = Integer.parseInt(request.getParameter("board_num"));
        String id = request.getParameter("board_id");
        String subject = request.getParameter("board_subject");
        String content = request.getParameter("board_content");
        int ref = Integer.parseInt(request.getParameter("board_re_ref"));
                
        // 답글 저장
        vo.setBoard_num(bs.getSeq()); // 답글의 글번호는 시퀀스값 가져와 세팅
        vo.setBoard_id(id);
        vo.setBoard_subject(subject);
        vo.setBoard_content(content);
        vo.setBoard_re_ref(ref);
        vo.setBoard_parent(num);    // 부모글의 글 번호를 저장한다.
             
        boolean result = bs.boardInsert(vo);
        
        if(result){
        	// 답글 등록이 완료되면 원래 페이지로 이동하기 위해 페이지 번호를 전달한다.
        	response.sendRedirect("../TheChef/board/BoardListForm.jsp?page="+pageNum);
        }						
	}

	public void FileDown(HttpServletRequest request, HttpServletResponse response) {
		// 파일명을 가져온다.
		String fileName = request.getParameter("file_name");

		// 파일이 있는 절대경로를 가져온다.
		// 현재 업로드된 파일은 UploadFolder 폴더에 있다.
		String folder = request.getServletContext().getRealPath("UploadFolder");
		// 파일의 절대경로를 만든다.
		String filePath = folder + "/" + fileName;

		try {
			File file = new File(filePath);
			byte b[] = new byte[(int) file.length()];
			
			// page의 ContentType등을 동적으로 바꾸기 위해 초기화시킴
			response.reset();
			response.setContentType("application/octet-stream");
			
			// 한글 인코딩
			String encoding = new String(fileName.getBytes("UTF-8"),"8859_1");
			
			// 파일 링크를 클릭했을 때 다운로드 저장 화면이 출력되게 처리하는 부분
			response.setHeader("Content-Disposition", "attachment;filename="+ encoding);
			response.setHeader("Content-Length", String.valueOf(file.length()));
			
			if (file.isFile()) // 파일이 있을경우
			{
				FileInputStream fileInputStream = new FileInputStream(file);
				ServletOutputStream servletOutputStream = response.getOutputStream();
				
				//  파일을 읽어서 클라이언트쪽으로 저장한다.
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
		
		// BoardDetail.jsp 에서 대체함 
		System.out.println("Servlet :: BoardDetail 실행");
		
		// 파라미터로 넘어온 글번호를 가져온다.
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
		// 업로드 파일 사이즈
		int fileSize= 5*1024*1024;
		// 업로드될 폴더 절대경로
		String uploadPath = request.getServletContext().getRealPath("/UploadFolder");

		try {	
			// 파일업로드 
			MultipartRequest multi = new MultipartRequest
					(request, uploadPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

			// 파일이름 가져오기
			String fileName = "";
			Enumeration<String> names = multi.getFileNames();
			if(names.hasMoreElements()){
				String name = names.nextElement();
				fileName = multi.getFilesystemName(name);
			}
			
			//BoardDAO dao = BoardDAO.getInstance();
			vo = new BoardBean();
			
			vo.setBoard_num(bs.getSeq()); // 시퀀스값 가져와 세팅
			vo.setBoard_id(multi.getParameter("board_id")); // 히든값
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
			System.out.println("글 작성 오류 : " + e.getMessage());
		}				
	}
}
