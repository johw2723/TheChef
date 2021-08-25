<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="dao.TheChefVO,java.util.*" %>
<% String id = (String)request.getAttribute("member");%>

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
	<br>
	<div id="wrap">
    	<br>    
	 	<b><font size="4" color="gray"><%= id%>님의 정보가 삭제되었습니다.</font></b>
		<hr size="1" width="400">
    	<b><font size="4" color="gray">The Chef 서비스를 이용해주셔서 감사합니다. </font></b>
    	<br>
	</div>
</body>
</html>