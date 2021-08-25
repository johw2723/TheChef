<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "dao.BoardBean" %>
<%@ page import = "dao.BoardDAO" %>
<%@ page import = "model.BoardService" %>
<%
    BoardService bs = new BoardService();
    int num = Integer.parseInt(request.getParameter("num"));

    // 답글 작성 후 원래 페이지로 돌아가기 위해 페이지 번호가 필요하다.
    String pageNum = request.getParameter("page");
    BoardBean vo = bs.getDetail(num);
    request.setAttribute("vo", vo);
    request.setAttribute("page", pageNum);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 - 답변글</title>
<style type="text/css">
   #title{
      height : 16;
      font-family :'돋움';
      font-size : 12;    
      text-align :center;
   }
</style>
</head>
<body>
   <br>
   <b><fort size="6" color="gray">답글 작성</fort></b>
   <br>
   
   <form method="post" action="../BoardProcessing?command=BoardReply&page=${pageNum}" name="boardForm">
   
   <!-- 부모글 정보를 넘긴다. -->
   <input type="hidden" name="board_id" value="${sessionScope.sessionID}">
   <input type="hidden" name="board_num" value="${vo.board_num}"/>
   <input type="hidden" name="board_re_ref" value="${vo.board_re_ref}"/>
   
   <table width="700" border="3" bordercolor="lightgray" align="center">
      <tr>
         <td id="title">작성자</td>
         <td>${sessionScope.sessionID}</td>
      </tr>
      <tr>
         <td id="title">제목</td>
         <td><input name="board_subject" type="text" size="70" maxlength="100" value=""/></td>        
      </tr>
      <tr>
         <td id="title">내 용</td>
         <td><textarea name="board_content" cols="72" rows="20"></textarea></td>        
      </tr>
      <tr align="center" valign="middle">
         <td colspan="5">
            <input type="reset" value="작성취소" >
            <input type="submit" value="등록" >
            <input type="button" value="뒤로" onclick="javascript:history.go(-1)">            
         </td>
      </tr>
   </table>
   </form>
</body>
</html>