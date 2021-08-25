<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@ page import = "model.ProductService" %>
<%@ page import = "dao.ProductVO" %>
<%@ page import = "dao.TheChefVO" %>

<%
    // 파라미터로 넘어온 값
	String product_id = request.getParameter("product_id");
	System.out.println("product_id : " + product_id);

	// Product 호출
	ProductService ps = new ProductService();
	ProductVO vo = ps.getDetail(product_id);
	
	ProductVO pvo = new ProductVO();
	pvo.setProduct_id(product_id);
	
	// TheChef 호출
	String memId = (String)session.getAttribute("sessionID");
	TheChefVO tcvo = new TheChefVO(memId);
	tcvo.setMemId(memId);
	
	// jjim 로직 
	boolean checkJjimData = ps.checkJjimData(pvo, tcvo);
	System.out.println("checkJjimData : " + checkJjimData);
	
	// 값 저장
	request.setAttribute("vo", vo);
	request.setAttribute("checkJjimData", checkJjimData);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheChef Product Detail</title>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div id="wrap">
	<h4>상품 상세정보</h4>
	<!-- 상품 정보 -->
	<table id="detailproduct" width="400" border="3" bordercolor="lightgray">
		<tr>
			<td colspan="2">
				<img src="${vo.product_url}" width="400" height=auto alt="이미지">
			</td>
		</tr>
		<tr>
			<td>상품명</td>
			<td>${vo.product_name}
			<!-- 찜 확인 -->                   
      		<c:if test="${ checkJjimData }">
      			<span id="JjimCheck" style="color: red;">♥</span>
      		</c:if>
      		<c:if test="${ !checkJjimData }">
      			<span id="JjimCheck" style="color: red;"></span>
      		</c:if>
			</td>
		</tr>
		<tr>
			<td>상품가격</td>
			<td><fmt:formatNumber value="${vo.product_price}" pattern="###,###,###"/>원</td>
		</tr>
		<tr>
			<td>상품설명</td>
			<td>${vo.product_detail}</td>
		</tr>
	</table>
	<br>
	<!-- 찜 기능 -->
    <c:if test="${ sessionScope.sessionID != null }">
    	<input class="Jjim" id="Jjim_login" type="button" value="찜">
    </c:if>
    <c:if test="${ sessionScope.sessionID == null }">
    	<input class="Jjim" id="Jjim_notLogin" type="button" value="찜">
    </c:if>
	<!-- 장바구니 -->	
	<c:if test="${sessionScope.sessionID != null}">	
		<input type="button" value="담기" id="basket_login">		
		<input class="cancelBtn" type="button" value="닫기"><br>
	</c:if>
	<c:if test="${sessionScope.sessionID == null}">
		<input type="button" value="담기" id="basket_notLogin">
		<input class="cancelBtn" type="button" value="닫기"><br>
	</c:if>	
</div>

<script type="text/javascript">
$("#basket_notLogin").click(function(){
    alert('장바구니 이용은 로그인한 회원만 이용할 수 있습니다.') 
});

$("#basket_login").click(function(){
	$.ajax({
		url : "../BasketProcessing?command=AddBasket",
		type : "post",
		data : {
			product_id : '${vo.product_id}',
			memId : '${sessionScope.sessionID}'
		},
		success : function(){
			var con_val = confirm("장바구니에 등록했습니다. \n장바구니를 확인하시겠습니까?");
			if(con_val == true){	
				window.close();
				window.opener.location.href="http://localhost:8081/TheChef/product/BasketList.jsp";		
			} else if(con_val == false){
				window.close();
			}
			
		},
		error : function(){
			alert("장바구니에 등록실패했습니다.");
		}
	})
});

$("#Jjim_notLogin").click(function(){
    alert('찜 기능은 로그인한 회원만 이용할 수 있습니다.') 
});

$("#Jjim_login").click(function(){
	$.ajax({
		url : "../ProductProcessing?command=Jjim",
		type : "post",
		data : {
			product_id : '${vo.product_id}',
			memId : '${sessionScope.sessionID}'
		},
		success : function(){
			alert("찜 등록을 했습니다.");
			if($("#JjimCheck").html() == "♥"){
				$("#JjimCheck").html("");
			} else if($("#JjimCheck").html() == ""){
				$("#JjimCheck").html("♥");
			}
		},
		error : function(){
			alert("찜 등록에 실패했습니다.");
		}
	})
});

$(".cancelBtn").click(function(){
	window.close();
});

$("#wrap").css("text-align", "center");
$("#detailproduct").css("margin", "0 auto 0 auto");
</script> 
</body>
</html>