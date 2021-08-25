<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import = "model.BasketService" %>
<%@ page import = "dao.BasketVO" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.Map" %>
<%
	// 서비스 선언
	BasketService bs = new BasketService();

	// 파라미터 값 가져오기
	String memId = (String) session.getAttribute("sessionID");

	// 장바구니 정보가져오기
	Map<String, Object> map = new HashMap<String, Object>();
	List<BasketVO> list = bs.listBasket(memId);
	//List<BasketVO> listProduct = bs.listProduct(memId);
	
	int sumMoney = bs.sumMoney(memId);
	int fee = 3000;
	map.put("list", list);         // 장바구니 정보를 map 에 저장
	map.put("count", list.size()); // 장바구니 상품의 유무
	map.put("sumMoney", sumMoney); // 장바구니 전체 금액
	map.put("fee", fee);           // 배송금액
	map.put("allSum", sumMoney + fee); // 주문 상품 전체 금액
	
	// 값 호출을 위한 저장
	request.setAttribute("map", map); 
	request.setAttribute("list", list);
	//request.setAttribute("listProduct", listProduct);

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheChef Basket List</title>
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
    	<!-- 장바구니 확인  -->
    	<c:choose>
    		<c:when test="${map.count == 0}">
    			<br>장바구니가 비어있습니다.
    		</c:when>
    	<c:otherwise>
		<!-- 장바구니  -->
		<h4>장바구니 목록</h4>
			<form name="basketForm" id="baksetForm" method="post" action="../BasketProcessing?">
				<table border="1">
					<tr>
						<th width=125>이미지</th>
						<th width=200>상품명</th>
						<th width=125>단가</th>
						<th width=125>수량</th>
						<th width=150>금액</th>
						<th width=100>취소</th>
					</tr>			
				<c:forEach var="bvo" items="${map.list}" varStatus="status">
					<tr>		
					    <td>
					    	<img src="${bvo.product_url}" name="product_url" width="125" height="100" alt="이미지">
					    </td>
						<td>
							<a href="javascript:void(window.open('productDetail.jsp?product_id=${bvo.product_id}','TheChef Product Detail','width=450, height=650'))">
							${bvo.product_name}</a>
						</td>
						<td><fmt:formatNumber value="${bvo.product_price}" pattern="###,###,###"/>원</td>
						<td>
							<input type="number" style="width: 40px" name="quantity" value="${bvo.quantity}" min="1" onchange="quantityUpdate()">개
							<input type="hidden" name="product_id" value="${bvo.product_id}">
							<input type="hidden" name="memId" value="${bvo.memId}">
						</td>
						<td><fmt:formatNumber value="${bvo.money}" pattern="###,###,###"/>원</td>
						<td><a href="../BasketProcessing?command=BasketDelete&orderNum=${bvo.orderNum}"><input type="button" value="삭제"></a></td>
					</tr>								
    			</c:forEach>
    				<tr>
    					<td colspan="6" align="right">
    					장바구니 금액 합계 : <fmt:formatNumber value="${map.sumMoney}" pattern="###,###,###"/>원<br>
    					배송료 : <fmt:formatNumber value="${map.fee}" pattern="###,###,###"/>원<br>
    					전체 주문금액 : <fmt:formatNumber value="${map.allSum}" pattern="###,###,###"/>원
    					</td>
    				</tr>	
    			</table>
    			<input type="hidden" name="count" value="${map.count}"><br>
    			<button type="submit" name="command" value="BasketUpdate">수정</button>
    			<button type="submit" name="command" value="BasketOrder">주문</button>
    		</form> 	
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