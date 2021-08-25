<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
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
	
	<div id="wrap">
	<br>
		<b><font size="4" color="gray">회원님의 최신정보가 아닌가요?</font></b>
		<hr size="1" width="400">
	<br>
	<form name="formJoin" action="../TheChefProcessing?command=UserInfoUpdate" method="post">
		<div class="container">
			<fieldset style="width:250px" class="box">
			<legend>회원정보 수정</legend>
			<table>
        		<tr>
					<th>회원ID</th>
					<td>
						<input type="text" name="memId"  size="20" value="<%= session.getAttribute("sessionID") %>" readonly/>
					</td>
				</tr>                  
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" name="pw"  size="20" />
					</td>
				</tr>        
				<tr>
					<th>Email</th>
					<td>
						<input type="text" name="email"  size="20" value="<%= session.getAttribute("sessionEMAIL") %>" />
					</td>
				</tr>
				<tr>
					<th>전화번호</th>
					<td>
						<input type="text" name="phone"  size="20" value="<%= session.getAttribute("sessionPHONE") %>" />
					</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>
						<input type="text" name="address"  size="20" value="<%= session.getAttribute("sessionADDRESS") %>" />
					</td>
				</tr>            
				<tr>
					<th>결제수단</th>
					<td>
						<input type="text" name="payment"  size="20" value="<%= session.getAttribute("sessionPAYMENT") %>" />
					</td>
				</tr>
				<%
				// 정보를 입력하지 않았을 경우 화면에 표시
				String msg=request.getParameter("msg");
   
				if(msg!=null && msg.equals("0")) {
					out.println("<br>");
					out.println("<font color='red' size='3'>비밀번호를 확인해주세요.</font>");
				} else if(msg!=null && msg.equals("1")){
					out.println("<br>");
					out.println("<font color='red' size='3'>이메일을 확인해주세요.</font>");
				} else if(msg!=null && msg.equals("2")){
					out.println("<br>");
					out.println("<font color='red' size='3'>전화번호를 확인해주세요.</font>");
				}
				%>         
			</table>
			<br/>
				<input type="hidden" name="command" value="UserInfoUpdate"/>
				<input type="submit" value="정보수정"/>
			</fieldset>
		</div>
	</form>
	<br>
	<form name="formJoin" action="../TheChefProcessing?command=UserDelete" method="post">
		<div class="container">
			<fieldset style="width:250px" class="adminP">
			<legend>회원탈퇴</legend>
			<table>
				<tr>            
					<td colspan="2">다시한번 ID를 입력해주세요.</td>
				</tr> 
				<tr>
					<th>ID</th>
					<td><input type="text" name="memId"  size="20" /></td>
				</tr>
				<%
				// 정보를 입력하지 않았을 경우 화면에 표시           
				if(msg!=null && msg.equals("-1")){
					out.println("<br>");
					out.println("<font color='red' size='3'>아이디를 입력해주세요.</font>");
				} else if(msg!=null && msg.equals("-2")){
					out.println("<br>");
					out.println("<font color='red' size='3'>아이디가 일치하지 않습니다.</font>");
				}
				%>                      
			</table>
			<br/>
				<input type="hidden" name="command" value="UserDelete"/>
				<input type="submit" value="회원탈퇴"/>
			</fieldset>
		</div>
	</form>
</div>  
</body>
</html>