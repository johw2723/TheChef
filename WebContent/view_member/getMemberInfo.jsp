<%@ page import="dao.TheChefVO,java.util.*"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<% TheChefVO vo=(TheChefVO)request.getAttribute("vo");%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 상세</title>
</head>
<body>
<h1><center>회원상세</center></h1>

<table align=center border="1">
	<tr align=center>
		<th>ID</th>
		<th>Email</th>
		<th>Tel</th>
		<th>Address</th>
		<th>Payment</th>
	</tr>
	<tr align=center >
		<td><%=vo.getMemId()%></td>
		<td><%=vo.getEmail()%></td>
		<td><%=vo.getPhone()%></td>
		<td><%=vo.getAddress()%></td>
		<td><%=vo.getPayment()%></td>
	</tr>	
</table>
</body>
</html>