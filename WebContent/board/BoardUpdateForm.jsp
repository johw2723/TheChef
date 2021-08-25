<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "dao.BoardBean" %>
<%@ page import = "dao.BoardDAO" %>
<%@ page import = "model.BoardService" %>
<%
    //페이지 번호와 글 번호를 가져온다.
    String pageNum = request.getParameter("page");
	String num = request.getParameter("num");
	int boardNum = Integer.parseInt(num);
	
	BoardService bs = new BoardService();
	BoardBean vo = bs.getDetail(boardNum);
	
	request.setAttribute("vo", vo);
	request.setAttribute("pageNum", pageNum);
%>
<html>
<head>
	<title>글 수정</title>
	
	<style type="text/css">
		#title{
			height : 16;
			font-family :'돋움';
			font-size : 12;
			text-align :center;
		}
	</style>	
	
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
<div id="wrap">
	<br>
	<b><font size="6" color="gray">글 수정</font></b>
	<hr size="1" width="460">
	<br>
</div>	

<form method="post" action="../BoardProcessing?command=BoardUpdate&page=${pageNum}" name="boardForm" enctype="multipart/form-data">
	<input type="hidden" name="board_num" value="${vo.board_num}"/>
	<input type="hidden" name="existing_file" value="${vo.board_file}"/>

	<table width="700" border="3" bordercolor="lightgray" align="center">
		<tr>
			<td id="title">작성자</td>
			<td>${vo.board_id}</td>
		</tr>
			<tr>
			<td id="title">제 목</td>
			<td>
				<input name="board_subject" type="text" size="70" maxlength="100" 
					value="${vo.board_subject}"/>
			</td>		
		</tr>
		<tr>
			<td id="title">내 용</td>
			<td>
				<textarea name="board_content" cols="72" rows="20">${vo.board_content}</textarea>			
			</td>		
		</tr>
		
		<!-- 답글이 아닐 경우에만 파일 첨부 가능하도록 처리 -->
		<c:if test="${vo.board_parent==0}">	
			<tr>
				<td id="title">기존 파일</td>
				<td>&nbsp;&nbsp; ${vo.board_file}</td>	
			</tr>
			<tr>
				<td id="title">첨부파일</td>
				<td><input type="file" name="board_file"/></td>	
			</tr>
		</c:if>		
		<tr align="center" valign="middle">
			<td colspan="5">
				<input type="reset" value="작성취소" >
				<input type="submit" value="수정" >
				<input type="button" value="목록" onclick="changeView()" >			
			</td>
		</tr>
	</table>	
</form>

<script type="text/javascript">
$("#wrap").css("width", "800px");
$("#wrap").css("text-align", "center");
$("#wrap").css("margin", "0 auto 0 auto");

function changeView(){
	location.href='BoardListForm.jsp?page=${pageNum}';
}
</script>
</body>
</html>