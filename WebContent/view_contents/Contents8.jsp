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
	int no = Integer.parseInt("8");
	ContentsVO cvo = new ContentsVO();
	cvo.setContents_num(no);

	String id = (String)session.getAttribute("sessionID");
	TheChefVO tcvo = new TheChefVO(id);
	tcvo.setMemId(id);
		
	boolean checkFavoriteData = cs.checkFavoriteData(cvo, tcvo);
	System.out.println("checkFavoriteData : " + checkFavoriteData);
	request.setAttribute("checkFavoriteData", checkFavoriteData);
	
	ProductService ps = new ProductService();

	//검색조건과 검색내용을 설정한다.
	String opt = "0";
	String condition = "깐풍만두";
	
	// 검색조건과 내용을 Map에 담는다.
	HashMap<String, Object> listOpt = new HashMap<String, Object>();
	listOpt.put("opt", opt);
	listOpt.put("condition", condition);	
		
	// 글목록을 가져온다.
	ArrayList<ProductVO> productList =  ps.getProductList(listOpt);
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
	<iframe width="100%" height="70%" src="https://www.youtube.com/embed/24SO6nTTQsE" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
	<p>
		깐풍만두 : 빠르게 튀김실력이 올라가는 레시피
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
			[깐풍만두]<br>물만두<br>고추<br>식초 1T<br>깨<br>
			간장 1T<br>설탕 1T<br>치킨스톡 or 굴소스 1t<br>물 3T<br>전분 1t<br>식용유<br><br>         
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
			contents_num : '8',
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