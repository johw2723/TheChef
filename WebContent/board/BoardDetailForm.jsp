<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ page import = "dao.BoardBean" %>
<%@ page import = "dao.BoardDAO" %>
<%@ page import = "model.BoardService" %>

<%
    //파라미터로 넘어온 글번호를 가져온다.
	String num = request.getParameter("num");
	int boardNum = Integer.parseInt(num);			
	//System.out.println("boardNum : " + boardNum);
		
	String pageNum = request.getParameter("pageNum");
	//System.out.println("pageNum : " + pageNum);
				
	BoardService bs = new BoardService();
	BoardBean vo = bs.getDetail(boardNum);
	boolean result = bs.updateCount(boardNum);
				
	request.setAttribute("vo", vo);
	request.setAttribute("pageNum", pageNum);
		
	String getId = vo.getBoard_id();
	System.out.println("getId : " + getId);
%>
<html>
<head>
	<title>글 상세보기</title>
	
	<style type="text/css">		
		#title{
			height : 16;
			font-family :'돋움';
			font-size : 12;
			text-align :center;
			background-color: #F7F7F7;
		}
		
		#btn{
			font-family :'돋움';
			font-size : 14;
			text-align :center;
		}

	</style>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<div id="wrap">
    <br>
	<b><font size="6" color="gray">상세 보기</font></b>
	<hr size="1" width="460">
	<br>
	<div id="board">
		<table id="detailBoard" width="800" border="3" bordercolor="lightgray">
		
			<tr>
				<td id="title">작성일</td>
				<td>${vo.board_date}</td>
			</tr>
			<tr>
				<td id="title">작성자</td>
				<td>${vo.board_id}</td>
			</tr>
			<tr>
				<td id="title">제 목</td>
				<td>${vo.board_subject}</td>		
			</tr>			
			<tr>
				<td id="title">내 용</td>
				<td>
				    <!-- 첨부된 이미지가 있을 경우 내용칸에 이미지 추가 -->
				    <% if(vo.getBoard_file() != null){ %>
					    <img src="../UploadFolder/${vo.board_file}" width="500"><br>
				    <%} %>			    
					${vo.board_content}
				</td>		
			</tr>
			<tr>
				<td id="title">첨부파일</td>
				<td>				
					<a href='../BoardProcessing?command=FileDown&file_name=${vo.board_file}'>${vo.board_file}</a>
				</td>	
			</tr>			
			
			<!-- 답글 부분 : admin 계정만 답글 작성 가능 -->
			<tr align="center" valign="middle">
				<td colspan="5">
				<% if(session.getAttribute("sessionID") == null){ %>
				<% } else if(session.getAttribute("sessionID").equals(vo.getBoard_id())) { %>
        	            <input type="button" value="수정" onclick="doAction(0)">
						<input type="button" value="삭제" onclick="doAction(1)">			
				<% } else if(session.getAttribute("sessionID").equals("admin")) {  %>       	      
        	            <input type="button" value="수정" onclick="doAction(0)">
						<input type="button" value="삭제" onclick="doAction(1)">   
        	            <input type="button" value="답글" onclick="changeView(1)" >
                <% } %>					
					<input type="button" value="목록" onclick="changeView(0)">			
				</td>
			</tr>
		</table>
	</div>
	<br><br>
</div>	
<script type="text/javascript">
$("#wrap").css("width", "800px");
$("#wrap").css("text-align", "center");
$("#wrap").css("margin", "0 auto 0 auto");
$("#detailBoard").css("text-align", "center");

function changeView(value){
	if(value == 0)	
		location.href='BoardListForm.jsp?page=${pageNum}';
	else if(value == 1)
	    location.href='BoardReplyForm.jsp?num=${vo.board_num}&page=${pageNum}';
}

function doAction(value){
	if(value == 0) // 수정
		location.href="BoardUpdateForm.jsp?num=${vo.board_num}&page=${pageNum}";
	else if(value == 1) // 삭제
	    location.href="../BoardProcessing?command=BoardDelete&num=${vo.board_num}";
}					
</script>
</body>
</html>