<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 메뉴 기능</title>
<style>
@import url(../styles/member.css)
</style>
<script src="../scripts/TheChef.js"></script>
</head>
<body>
	<header class="header">
		<div class="main">
			<input onclick="move_main()" class="main_page" type="button" value="TheChef">
		</div>
	</header>

	<div class="container">
		<fieldset class="adminP" style="width:250px">
			<legend>회원조회</legend>
				<form name="formJoin" action="../TheChefProcessing?command=MemberInfo" method="post">
	     			<table>
	        			<tr>
							<th>회원ID</th>
							<td><input type="text" name="memId"  size="20" /></td>
						</tr>                      
					</table>	
					<br/>
					<input type="hidden" name="command" value="MemberInfo"/>
					<input type="submit" value="회원상세조회"/>     
				</form>
			<form name="formJoin" action="../TheChefProcessing?command=MemberList" method="post">
				<input type="hidden" name="command" value="MemberList"/><br>	
				<input type="submit" value="회원목록조회"/>
			</form>
		</fieldset>
		<br/>
		
		<form name="formJoin" action="../TheChefProcessing?command=UpdateMember" method="post">
			<fieldset style="width:250px" class="adminP">
			<legend>회원수정</legend>
			<table>
				<tr>
					<th>회원ID</th>
					<td><input type="text" name="memId"  size="20" /></td>
				</tr>                  
				<tr>
					<th>비밀번호</th>
					<td><input type="password" name="pw"  size="20" /></td>
				</tr>        
				<tr>
					<th>Email</th>
					<td><input type="text" name="email"  size="20" /></td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td><input type="text" name="phone"  size="20" /></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><input type="text" name="address"  size="20" /></td>
				</tr>            
				<tr>
					<th>결제수단</th>
					<td><input type="text" name="payment"  size="20" /></td>
				</tr>
				<%
				// 정보를 입력하지 않았을 경우 화면에 표시
				String msg=request.getParameter("msg");
	   
				if(msg!=null && msg.equals("0")) {
					out.println("<br>");
					out.println("<font color='red' size='3'>회원 아이디를 입력하세요.</font>");
				} else if(msg!=null && msg.equals("1")){
					out.println("<br>");
					out.println("<font color='red' size='3'>회원 비밀번호를 입력하세요.</font>");
				} else if(msg!=null && msg.equals("2")){
					out.println("<br>");
					out.println("<font color='red' size='3'>회원 이메일을 입력주세요.</font>");
				} else if(msg!=null && msg.equals("3")){
					out.println("<br>");
					out.println("<font color='red' size='3'>회원 전화번호를 입력주세요.</font>");
				} else if(msg!=null && msg.equals("-1")){
					out.println("<br>");
					out.println("<font color='red' size='3'>존재하지 않는 회원정보입니다.</font>");
				}
				%>          
			</table>
			<br/>
			<input type="hidden" name="command" value="UpdateMember"/>
			<input type="submit" value="회원수정"/>
			</fieldset>
		</form>
		<br>
		<form name="formJoin" action="../TheChefProcessing?command=DeleteMember" method="post">
			<fieldset style="width:250px" class="adminP">
			<legend>회원삭제</legend>
			<table>
				<tr>
					<th>회원ID</th>
					<td><input type="text" name="memId"  size="20" /></td>
				</tr>
				<%
				// 정보를 입력하지 않았을 경우 화면에 표시          
				if(msg!=null && msg.equals("-2")){
					out.println("<br>");
					out.println("<font color='red' size='3'>아이디를 입력해주세요.</font>");
				} 
				%>                        
			</table>
			<br/>
			<input type="hidden" name="command" value="DeleteMember"/>
			<input type="submit" value="회원삭제"/>
			</fieldset>
		</form>
	</div>
</body>
</html>