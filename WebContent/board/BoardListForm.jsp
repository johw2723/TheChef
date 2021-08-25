<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "dao.BoardBean" %>
<%@ page import = "dao.BoardDAO" %>
<%@ page import = "model.BoardService" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.ArrayList" %>
<%
	int spage = 1;
	String page1 = request.getParameter("page");
	
	if(page1 != null && !page1.equals(""))	
		spage = Integer.parseInt(page1);
	
	// 검색조건과 검색내용을 가져온다.
	String opt = request.getParameter("opt");
	String condition = request.getParameter("condition");
	
	// 검색조건과 내용을 Map에 담는다.
	HashMap<String, Object> listOpt = new HashMap<String, Object>();
	listOpt.put("opt", opt);
	listOpt.put("condition", condition);			
	
	BoardService bs = new BoardService();
	int listCount = bs.getBoardListCount(listOpt);
	
	// 한 화면에 10개의 게시글을 보여지게 함
	// 페이지 번호는 총 5개, 이후로는 [다음]으로 표시
	
	// 전체 페이지 수
	int maxPage = (int)(listCount/10.0 + 0.9);
	
	// 만약 사용자가 주소창에서 페이지 번호를 maxPage 보다 높은 값을 입력시
	// maxPage에 해당하는 목록을 보여준다.
	if(spage > maxPage) spage = maxPage;
	listOpt.put("start", spage*10-9);
	
	// 글목록을 가져온다.
	ArrayList<BoardBean> list =  bs.getBoardList(listOpt);
	
	// 시작 페이지 번호
	int startPage = (int)(spage/5.0 + 0.8) * 5 - 4;
	// 마지막 페이지 번호
	int endPage = startPage + 4;
	if(endPage > maxPage) endPage = maxPage;
		
	// 4개 페이지번호 저장
	request.setAttribute("spage", spage);
	request.setAttribute("amxPage", maxPage);
	request.setAttribute("startPage", startPage);
	request.setAttribute("endPage", endPage);
	
	// 글의 총 수와 글 목록 저장 
	//request.setAttribute("listCount", listCount);
	request.setAttribute("list", list);	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 목록</title>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div id="wrap">
    <br>
    <b><font size="6" color="gray">글 목록</font></b>
    <hr size="1" width="460">
    <br>  
    
    <!-- 게시글 목록 -->
    <div id="board">
        <table id="bList" width="800" border="3" bordercolor="lightgray">
            <tr height="30">
                <td>글번호</td>
                <td>제목</td>
                <td>작성자</td>
                <td>작성일</td>
                <td>조회수</td>
            </tr>   
            <c:forEach var="vo" items="${requestScope.list}">
            <tr>
                <td>${vo.board_num}</td>
                <td align="left">
                   <c:if test="${vo.board_re_lev > 1 }">
                      <c:forEach begin="1" end="${vo.board_re_lev}">
                         &nbsp;&nbsp; <!-- 답변글일경우 글 제목 앞에 공백을 준다. -->
                      </c:forEach>
                      RE :
                   </c:if>                             
                   <a href="BoardDetailForm.jsp?num=${vo.board_num}&pageNum=${spage}">
                   ${vo.board_subject}
                   </a>
                </td>
                <td>
                   <a href="#">
                    ${vo.board_id}
                   </a>
                </td>
                <td>${vo.board_date}</td>
                <td>${vo.board_count}</td>
            </tr>
            </c:forEach>
        </table>
    </div>
    
    <!-- 페이지 넘버 부분 -->
	<br>
	<div id="pageForm">
		<c:if test="${startPage != 1}">
			<a href='BoardListForm.jsp?page=${startPage-1}'>[ 이전 ]</a>
		</c:if>
		
		<c:forEach var="pageNum" begin="${startPage}" end="${endPage}">
			<c:if test="${pageNum == spage}">
				${pageNum}&nbsp;
			</c:if>
			<c:if test="${pageNum != spage}">
				<a href='BoardListForm.jsp?page=${pageNum}'>${pageNum}&nbsp;</a>
			</c:if>
		</c:forEach>
		
		<c:if test="${endPage != maxPage }">
			<a href='BoardListForm.jsp?page=${endPage+1 }'>[다음]</a>
		</c:if>
	</div>
	
	<!--  검색 부분 -->
	<br>
	<div id="searchForm">
		<form>
			<select name="opt">
				<option value="0">제목</option>
				<option value="1">내용</option>
				<option value="2">제목+내용</option>
				<option value="3">글쓴이</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
			<% if(session.getAttribute("sessionID") != null) { // 로그인이 되었을 때 %>
        	<input id="writeBtn" type="button" value="리뷰작성">
            <% } %>
		</form>			
	</div>
</div>    

<script type="text/javascript">
$("#wrap").css("width", "800px");
$("#wrap").css("text-align", "center");
$("#wrap").css("margin", "0 auto 0 auto");
$("#board").css("text-align", "center");
$("#pageForm").css("text-align", "center");
$("#searchForm").css("text-align", "center");
$("#bList").css("text-align", "center");

$("#writeBtn").click(function(){
	location.href="BoardWriteForm.jsp";
});

</script>
</body>
</html>