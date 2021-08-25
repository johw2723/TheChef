<%@ page import="dao.TheChefVO"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<% 
	TheChefVO vo=(TheChefVO)request.getAttribute("vo");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<style>
@import url(styles/member.css)
</style>
<script src="scripts/TheChef.js"></script>
</head>
<body>
	<header class="header">
		<div class="main">
			<input onclick="move_main()" class="main_page" type="button" value="TheChef">
		</div>
	</header>

	<div id="wrap">
	<br>
		<b><font size="4" color="gray"><%= vo.getMemId()%>님의 정보가 수정되었습니다.</font></b>
		<hr size="1" width="400">
	<br>
	<form name="formJoin">
		<div class="container">
			<fieldset style="width:250px" class="box">
			<legend>변경된 회원정보</legend>
			<table>                  
				<tr>
					<th>비밀번호</th>
					<td><%= vo.getPw() %></td>
				</tr>        
				<tr>
					<th>Email</th>
					<td><%= vo.getEmail()%></td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><%= vo.getPhone()%></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><%= vo.getAddress()%></td>
				</tr>            
				<tr>
					<th>결제수단</th>
					<td><%= vo.getPayment()%></td>
				</tr>         
			</table>
			<br/>       
			</fieldset>
		</div>
	</form>
</div>  
</body>
</html>