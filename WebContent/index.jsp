<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheChef</title>
<style>
@import url(styles/Main.css)
</style>
<script src="scripts/TheChef.js"></script>
</head>
<body style="overflow-x: hidden">
	<%@ include file="incl/banner.jsp" %>
      
	<section class="section_main">      
		<section class="in_section1"> 
			<%@ include file="incl/creator1.jsp" %>
			<%@ include file="incl/creator2.jsp" %>
			<%@ include file="incl/creator3.jsp" %>
		</section>
	
		<section class="in_section2">     
			<%@ include file="incl/aside.jsp" %>
		</section>
	
		<%@ include file="incl/footer.jsp" %>
	</section>
</body>
</html>