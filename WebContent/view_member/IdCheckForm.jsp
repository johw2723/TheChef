<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 체크</title>
<style>
@import url(../styles/member.css)
</style>
<script src="../scripts/TheChef.js"></script>
</head>
<body onload="idImport()">
<!-- 아이디 중복체크를 Ajax 사용 후 해당 jsp는 사용하지 않음 -->
	<div id="wrap">
    	<br>
    		<b><font size="4" color="gray">아이디 중복체크</font></b>
    		<hr size="1" width="460">
    	<br>
		<div id="chk">
			<form id="checkForm">
				<input type="text" name="idinput" id="userId">
				<input type="button" value="중복확인" onclick="idCheck()">
			</form>
				<div id="msg"></div>
	        <br>
	        <input id="cancelBtn" type="button" value="취소" onclick="window.close()"><br>
	        <input id="useBtn" type="button" value="사용하기" onclick="idExport()">
    	</div>
	</div>    
</body>
</html>