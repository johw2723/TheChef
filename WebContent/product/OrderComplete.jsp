<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import = "model.BasketService" %>  

<%
	// 서비스 선언
	BasketService bs = new BasketService();
		
	// 파라미터 값 가져오기
	String memId = (String) session.getAttribute("sessionID");	
	
	// 기존 장바구니 비우기
	bs.basketAllDelete(memId);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문 결제 완료</title>
<style>
@import url(styles/product.css)
</style>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="scripts/TheChef.js"></script>
</head>
<body style="overflow-x: hidden" onload="basketAllDelete()">
<%@ include file="../incl/banner.jsp" %>
<article class="BasketContainer">
	<div class="BasketBox">
		<div id="wrap">
   			<br><br>
    		<b><font size="4" color="gray">${vo.memId }님의 주문이 정상적으로 결제되었습니다.</font></b>
    		<hr size="1" width="400">
    			<h4>결제 정보</h4>
    			<table border="1">
    				<tr>
						<th width=200>상품명</th>
						<th width=125>단가</th>
						<th width=125>수량</th>
						<th width=150>금액</th>
					</tr>
    				<c:forEach var="product_name" items="${vo.product_name}" varStatus="status">
    				<tr>
						<td>										
							${product_name}							
						</td>
						<td>										
							<fmt:formatNumber value="${vo.product_price[status.index]}" pattern="###,###,###"/>원<br>					
						</td>
						<td>										
							${vo.quantity[status.index]}				
						</td>	
						<td>										
							<fmt:formatNumber value="${vo.money[status.index]}" pattern="###,###,###"/>원<br>					
						</td>
					</tr>
					</c:forEach>
					<tr>
						<td colspan="2" align="left">				
						연락처 : ${vo.phone}<br>
						이메일 : ${vo.email}<br>
						배송지 : ${vo.address}<br>								
						결제수단 : ${vo.payment}<br>		
						</td>
						<td colspan="2" align="right">
    					금액 합계 : <fmt:formatNumber value="${vo.sumMoney }" pattern="###,###,###"/>원<br>
    					배송료 : <fmt:formatNumber value="${vo.fee}" pattern="###,###,###"/>원<br>
    					전체 주문금액 : <fmt:formatNumber value="${vo.allSum}" pattern="###,###,###"/>원
    					</td>
					</tr>
    			</table>
    		<br>
		</div>
		<br>
		<input type="button" id="PaymentList" value="결제 내역" >
	</div>
</article>
<script type="text/javascript">
	$("#PaymentList").click(function(){
		location.href="../TheChef/product/PaymentInfo.jsp";
	});
</script> 
</body>
</html>