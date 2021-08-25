<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%
   String referer = (String)request.getHeader("REFERER");
   request.setAttribute("referer", referer);
%>
<html>
<head>
<meta charset="UTF-8">
<title>The Chef Login</title>
<style>
@import url(../styles/join.css)
</style>
<script src="../scripts/TheChef.js"></script>
</head>
<body>
	<header class="header">
		<div class="main">
			<input onclick="move_main()" class="main_page" type="button" value="TheChef">
		</div>
	</header>

	<br>

	<div class="container">
		<form name="formlogin" ACTION="../TheChefProcessing?command=MemberLogin&referer=${referer}" METHOD="POST" onSubmit="return chkLogin();">
			<div class="login">
				<table>
					<tr>
						<th>아이디</th>
						<td>
							<input type="text" name="memId"/><br>
							<span id="login_alertID" style="display:none" aria-live="assertive"></span>
						</td>
					</tr>
					<tr>
						<th>비밀번호</th>
						<td> 
							<input type="password" name="pw"/><br>
							<span id="login_alertPW" style="display:none" aria-live="assertive"></span>
							<span id="login_fail" style="display:none" aria-live="assertive"></span>          
						</td>	
					</tr>	
				</table>	
			
				<%
				// 아이디, 비밀번호가 틀렸을 경우 화면에 메시지 표시
				String msg=request.getParameter("msg");
	   
				if(msg!=null && msg.equals("0")) {
					out.println("<br>");
					out.println("<font color='red' size='3'>비밀번호를 확인해주세요.</font>");
				} else if(msg!=null && msg.equals("-1")){
					out.println("<br>");
					out.println("<font color='red' size='3'>아이디를 확인해주세요.</font>");
				}
				%>
				<br>
				<input type="hidden" name="command" value="login"/>
				<input type="submit" value="로그인">
				<input onclick="join_member()" class="join_page" type="button" value="회원가입">
			</div>
		</form>
	</div>
</body>
</html>               