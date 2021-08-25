<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import = "model.BasketService" %>
<%@ page import = "model.TheChefService" %>
<%@ page import = "dao.BasketVO" %>
<%@ page import = "dao.TheChefVO" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.Map" %>
<%
	// 서비스 선언
	BasketService bs = new BasketService();
	TheChefService tcs = new TheChefService();

	// 파라미터 값 가져오기
	String memId = (String) session.getAttribute("sessionID");

	// 장바구니 정보가져오기
	Map<String, Object> map = new HashMap<String, Object>();
	List<BasketVO> list = bs.listBasket(memId);
	//List<BasketVO> listProduct = bs.listProduct(memId);
	
	// 회원정보 가져오기
	TheChefVO memberInfo = tcs.MemberInfo(memId);
	
	int sumMoney = bs.sumMoney(memId);
	int fee = 3000;
	map.put("list", list);         // 장바구니 정보를 map 에 저장
	map.put("count", list.size()); // 장바구니 상품의 유무
	map.put("sumMoney", sumMoney); // 장바구니 전체 금액
	map.put("fee", fee);           // 배송금액
	map.put("allSum", sumMoney + fee); // 주문 상품 전체 금액
	map.put("memberInfo", memberInfo);
	
	// 값 호출을 위한 저장
	request.setAttribute("map", map); 
	request.setAttribute("list", list);

%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheChef Basket Order</title>
<style>
@import url(../styles/product.css)
</style>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="../scripts/TheChef.js"></script>
</head>
<body style="overflow-x: hidden">
<%@ include file="../incl/banner.jsp" %>
<article class="BasketContainer">
	<div class="BasketBox">
	<!-- 주문정보 확인  -->
    	<c:choose>
    		<c:when test="${map.count == 0}">
    			<br>주문정보가 존재하지 읺습니다.
    		</c:when>
    	<c:otherwise>
		<!-- 주문정보  -->
		<h4>주문정보 확인/결제</h4>
			<form name="basketForm" id="baksetForm" method="post" action="../BasketProcessing?">
				<table border="1">
					<tr>
						<th colspan="2" width=225>상품정보</th>				
						<th width=125>단가</th>
						<th width=125>수량</th>
						<th width=150>총 금액</th>
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
							${bvo.quantity}개
							<input type="hidden" name="product_name" value="${bvo.product_name}">
							<input type="hidden" name="quantity" value="${bvo.quantity}">
							<input type="hidden" name="product_price" value="${bvo.product_price}">
							<input type="hidden" name="product_id" value="${bvo.product_id}">
							<input type="hidden" name="money" value="${bvo.money}">
						</td>
						<td><fmt:formatNumber value="${bvo.money}" pattern="###,###,###"/>원</td>
					</tr>								
    			</c:forEach>
    				<tr>    				
						<th colspan="3" align="left">주문자 정보</th>
						<th colspan="3" align="left">결제 상세</th>					
  					</tr>
    				<tr>
						<td colspan="3" align="left">				
							회원명 : ${map.memberInfo.memId}<br>
							연락처 : ${map.memberInfo.phone}<br>
							이메일 : ${map.memberInfo.email}<br>
							배송지 : ${map.memberInfo.address}<br>				
							결제수단 : ${map.memberInfo.payment}<br>		
						</td>			

    					<td colspan="3" align="right">
    					장바구니 금액 합계 : <fmt:formatNumber value="${map.sumMoney}" pattern="###,###,###"/>원<br>
    					배송료 : <fmt:formatNumber value="${map.fee}" pattern="###,###,###"/>원<br>
    					전체 주문금액 : <fmt:formatNumber value="${map.allSum}" pattern="###,###,###"/>원
    					</td>
    				</tr>	
    			</table>
    			<!-- 결제 내역 넘기기 -->
    			<input type="hidden" name="phone" value="${map.memberInfo.phone}">
    			<input type="hidden" name="email" value="${map.memberInfo.email}">
    			<input type="hidden" name="address" value="${map.memberInfo.address}">
    			<input type="hidden" name="payment" value="${map.memberInfo.payment}">
    			<input type="hidden" name="sumMoney" value="${map.sumMoney}">
    			<input type="hidden" name="fee" value="${map.fee}">
    			<input type="hidden" name="allSum" value="${map.allSum}">
    			<input type="hidden" name="count" value="${map.count}"><br>
    			<button type="submit" name="command" value="BasketPayment">결제하기</button>
    		</form>
		</c:otherwise>
    	</c:choose> 			
	</div>
</article>	
</body>
</html>