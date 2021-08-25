<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "model.TheChefService" %>    
<%
	TheChefService tcs = new TheChefService();
	//boolean checkId = tcs.idCheck(memId);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join Member</title>
<style>
@import url(../styles/member.css)
</style>
<script type="text/javascript" src="../scripts/TheChef.js"></script>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<header class="header">
		<div class="main">
			<input onclick="move_main()" class="main_page" type="button" value="TheChef">
		</div>
	</header>

	<div class="container">
		<form name="formJoin" action="../TheChefProcessing?command=insertMember" method="post" onSubmit="return chkForm();">
			<div class="join">
				<table>
					<tr>
						<th>아이디</th>
						<td>
							<input type="text" name="memId" id="insertId" maxlength="20" size="30" onblur="checkId(this)"/>
							<input type="hidden" name="checkid" value="idUncheck">
							<span id="msgData"></span><br>
							<span id="alert" style="display:none" aria-live="assertive"></span>
		      			</td>
		      		</tr>
		      		<tr>
	            		<th>비밀번호</th>
	            		<td>
							<input type="password" name="pw" id="memPW" maxlength="20" size="30" onblur="checkPw(this)"/><br>     
							<span id="alertPW" style="display:none" aria-live="assertive"></span>
						</td>
					</tr>
					<tr>
	            		<th>비밀번호 확인</th>
	            		<td>      
							<input type="password" name="pwc" id="memPWC" maxlength="20" size="30" onblur="checkPwc(this)"/><br>
							<span id="alertPWC" style="display:none" aria-live="assertive"></span>
						</td>
					</tr>
					<tr>
	            		<th>이메일</th>
	            		<td> 
							<input type="text" name="email" maxlength="30" size="30" onblur="checkE(this)"/><br>
							<span id="alertE" style="display:none" aria-live="assertive"></span>
						</td>
					</tr>
					<tr>
	            		<th>연락처</th>
	            		<td>
							<input type="text" name="phone" maxlength="13" size="30" onblur="checkP(this)"/><br>
							<span id="alertP" style="display:none" aria-live="assertive"></span>
						</td>
					</tr>
					<tr>
	            		<th>주소</th>
	            		<td>		 
							<input type="text" name="address" maxlength="100" size="30"/><br>
						</td>
					</tr>
					<tr>
	            		<th>결제수단</th>
	            		<td> 
							<input type="text" name="payment" maxlength="50" size="30"/><br>    
						</td>
					</tr>			
				</table>
				<input type="hidden" name="command" value="join"/><br>
				<input type="submit" value="회원가입">
			</div>   
		</form>
	</div>

<script type="text/javascript">
	$("#insertId").focusout(function(){	
		$.ajax({
			url : "../TheChefProcessing?command=idCheck",	
			type : "post",
			data : {
				memId : $("input[name=memId]").val()			
			},
			success : function(data){
    	        if(data==0){
     		       	$("#msgData").html("ID 중복 체크 : 중복").css("color", "red"), 
        	    	$("input[name=checkid]").val("idUncheck");
				} else if(data==1){
					$("#msgData").html("ID 중복 체크 : 사용 가능").css("color", "blue"),
					$("input[name=checkid]").val("idCheck");
				}
			},
			error : function(){
				alert("통신실패");
			}  
		})
	});
</script>   
</body>
</html>