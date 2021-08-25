<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ page import="dao.TheChefVO"%>
<% TheChefVO vo=(TheChefVO)request.getAttribute("vo"); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료</title>
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
			<b><font size="4" color="gray"><%= vo.getMemId() %>님 회원가입이 완료되었습니다.</font></b>
			<hr size="1" width="400">
    		<b><font size="4" color="gray">더 많은 The Chef 서비스를 이용하실 수 있습니다.</font></b>
		<br>
	</div>

	<header class="header">
		<div class="join">
		<br><br>
			<input onclick="open_login()" class="login_page" type="button" value="로그인">
		</div>
	</header>

</body>
</html>