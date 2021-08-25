<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import = "model.BasketService" %>
<%@ page import = "dao.PaymentVO" %>
<%@ page import = "dao.ProductVO" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.util.HashMap" %>

<%
	// 서비스 선언
	BasketService bs = new BasketService();
		
	// 파라미터 값 가져오기
	String memId = (String) session.getAttribute("sessionID");	
	
	// 목록 가져오기
	ArrayList<PaymentVO> payList = bs.getPaymentList(memId);
	
	// 값 저장
	request.setAttribute("count", payList.size());
	request.setAttribute("payList", payList);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheChef Payment Info</title>
<style>
@import url(../styles/product.css)
</style>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="../scripts/TheChef.js"></script>
</head>
<body style="overflow-x: hidden">
<%@ include file="../incl/banner.jsp" %>

<section class="section_main">      
    <section class="in_section1">
    	<article class="BasketContainer">
		<div class="BasketBox">
		<!-- 결제 확인  -->
    	<c:choose>
    		<c:when test="${requestScope.count == 0}">
    			<br>결제내역이 없습니다.
    		</c:when>
    	<c:otherwise>
		<!-- 결제 내역  -->
		<h4>결제 내역</h4>
		<c:forEach var="pay" items="${requestScope.payList}">
			<table border="1" id="tbl_pay">
				<tr>
					<th width=200>상품명</th>
					<th width=125>단가</th>
					<th width=125>수량</th>
					<th width=150>금액</th>
				</tr>				
					<c:forEach var="product_name" items="${pay.product_name}" varStatus="status">
						<tr>
							<td>										
								${product_name}							
							</td>
							<td>										
								<fmt:formatNumber value="${pay.product_price[status.index]}" pattern="###,###,###"/>원<br>						
							</td>
							<td>										
								${pay.quantity[status.index]}						
							</td>	
							<td>										
								<fmt:formatNumber value="${pay.money[status.index]}" pattern="###,###,###"/>원<br>							
							</td>
						</tr>	
					</c:forEach>
				<tr>
					<td colspan="2" align="left">				
						회원명 : ${pay.memId}<br>
						연락처 : ${pay.phone}<br>
						이메일 : ${pay.email}<br>
						배송지 : ${pay.address}<br>								
						결제수단 : ${pay.payment}<br>		
					</td>
    				<td colspan="2" align="right">					
    					결제 번호 : ${pay.paymentNum} <input type="button" value="결제 취소" onclick="cancelPayment(${pay.paymentNum})"><br>
    					결제 날짜 : ${pay.paymentDate}<br>
    					금액 합계 : <fmt:formatNumber value="${pay.sumMoney }" pattern="###,###,###"/>원<br>
    					배송료 : <fmt:formatNumber value="${pay.fee}" pattern="###,###,###"/>원<br>
    					전체 주문금액 : <fmt:formatNumber value="${pay.allSum}" pattern="###,###,###"/>원
    				</td>
    			</tr>
																								
    		</table><br>
    		<div id="cancelMsg"></div>
    	</c:forEach>
    	</c:otherwise>
    	</c:choose> 
    	</div>
    	</article>	
    </section>
    
    <section class="in_section2">
    	<%@ include file="../incl/aside.jsp" %>
    </section>
</section>  
</body>
</html>