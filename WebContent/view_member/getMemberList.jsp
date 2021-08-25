<%@ page import="dao.TheChefVO,java.util.*"%>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
<h1><center>회원목록</center></h1>
<%
	List<TheChefVO> list = (List<TheChefVO>)request.getAttribute("list");
%>

<table align=center border="1">
	<tr align=center>
		<th>MemID</th>
		<th>Name</th>
		<th>Tel</th>
		<th>Address</th>
		<th>Payment</th>
	</tr>
<%
Iterator it = list.iterator();
while(it.hasNext()) {	
	TheChefVO vo = (TheChefVO)it.next(); 
%>   
	<tr align=center >
		<td><%=vo.getMemId()%></td>
		<td><%=vo.getEmail()%></td>
		<td><%=vo.getPhone()%></td>
		<td><%=vo.getAddress()%></td>
		<td><%=vo.getPayment()%></td>
	</tr>	
<%
}
%>
</table>
</body>
</html>