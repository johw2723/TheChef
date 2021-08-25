<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import = "dao.ContentsVO" %>
<%@ page import = "dao.TheChefVO" %>
<%@ page import = "model.ContentsService" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "model.ProductService" %>
<%@ page import = "dao.ProductVO" %>

<%
	ContentsService cs = new ContentsService();
	int no = Integer.parseInt("3");
	ContentsVO cvo = new ContentsVO();
	cvo.setContents_num(no);

	String id = (String)session.getAttribute("sessionID");
	TheChefVO tcvo = new TheChefVO(id);
	tcvo.setMemId(id);
		
	boolean checkFavoriteData = cs.checkFavoriteData(cvo, tcvo);
	System.out.println("checkFavoriteData : " + checkFavoriteData);
	request.setAttribute("checkFavoriteData", checkFavoriteData);  
	
	// 상품
	String opt = "0";
	String condition = "시카고 딥디쉬 피자";
		
	// 검색조건과 내용을 Map에 담는다.
	HashMap<String, Object> listOpt = new HashMap<String, Object>();
	listOpt.put("opt", opt);
	listOpt.put("condition", condition);
		
	// 상품 목록을 가져온다.
	ProductService ps = new ProductService();
	ArrayList<ProductVO> productList =  ps.getProductList(listOpt);
		
	// requestScope 호출을 위한 값 저장	
	request.setAttribute("productList", productList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>The Chef</title>
<style>
@import url(../styles/Contents.css)
</style>
<script src="../scripts/TheChef.js"></script>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body style="overflow-x: hidden">
<%@ include file="../incl/banner.jsp" %>
   
<section class="section_contents">   
	<iframe width="100%" height="70%" src="https://www.youtube.com/embed/T7z5_6WvGFs" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
	<p>
		시카고 딥디쉬 피자 : 고기는 빼곡히, 치즈는 가득히
		<!-- 찜 확인 -->                     
		<c:if test="${ checkFavoriteData }">
			<span id="favoriteCheck" style="color: red;">♥</span>
		</c:if>
		<c:if test="${ !checkFavoriteData }">
			<span id="favoriteCheck" style="color: red;"></span>
		</c:if>
		<!-- 찜 기능 -->
		<c:if test="${ sessionScope.sessionID != null }">
			<input class="favorite" id="favorite_login" type="button" value="찜">	
		</c:if>
		<c:if test="${ sessionScope.sessionID == null }">
			<input class="favorite" id="favorite_notLogin" type="button" value="찜">
		</c:if>
	</p>
	<hr>
	<div id=menu_button>
		<p>
			<button class="menu" onclick="changeMenu(0)">레시피</button>
			<button class="menu" onclick="changeMenu(1)">리뷰</button>
		</p>
	</div>
	<div id="menu_recipe" style="display:block"> 
		<p>
			[반죽 재료 : 2판 용]<br>밀가루(중력분, 다목적용) 430g<br> 옥수수가루 82g<br>가는 소금 12g<br>
			 백설탕 14g<br>이스트 8g<br>35℃ 온도 물 300ml<br>무염버터 56 g<br><br>
			[소스 재료 : 2판 용]<br>베이컨 약 6줄<br>양파 1개<br>마늘 5개<br>
			칠리 플레이크(레드페퍼 플레이크) 2티스푼<br>이탈리아 토마토 통조림 800ml<br>소금 1티스푼<br>설탕 1티스푼<br>
			오레가노 1테이블스푼<br>바질 1테이블스푼<br><br>
			[피자 재료 : 2판 용]<br>모짜렐라 치즈 1kg~1.2kg<br>파르미지아노 레지아노 갈아서 종이컵 2개 분량<br>살라미 300g<br><br>
		</p>
	</div>
	<div id="menu_board" style="display:none">
		<iframe src="../board/BoardListForm.jsp" name="BoardList" width="100%" height="800px" class="iframeBoard" frameborder="0">오류 : iframe 미지원 브라우저</iframe>
	</div>  
</section>

<aside class="aside_contents">
	<form class="aside_form">
		<h3>관련 상품</h3>
		<c:forEach var="product" items="${requestScope.productList}">
		<div class="aside_item">     
			<a href="javascript:void(window.open('../product/productDetail.jsp?product_id=${product.product_id}','TheChef Product Detail','width=450, height=650'))">
				<img src="${product.product_url}" width="125" height="100" alt="이미지"><br>
				<br>${product.product_name}
				<br><fmt:formatNumber value="${product.product_price}" pattern="###,###,###"/>원
			</a>      
		</div>
		</c:forEach>                     
	</form>
</aside>
   
<script type="text/javascript">
$("#favorite_notLogin").click(function(){
    alert('찜 기능은 로그인한 회원만 이용할 수 있습니다.') 
});

$("#favorite_login").click(function(){	
    $.ajax({
	    url : "../ContentsProcessing?command=favorite",	
		type : "post",
		data : {
			contents_num : '3',
			memid : '${ sessionScope.sessionID }'			
		},
		success : function(){
            alert("통신성공");
            if($("#favoriteCheck").html() == "♥"){
				$("#favoriteCheck").html("");
			} else if($("#favoriteCheck").html() == ""){
				$("#favoriteCheck").text("♥");
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