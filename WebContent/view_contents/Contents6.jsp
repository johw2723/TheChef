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
	int no = Integer.parseInt("6");
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
	String condition = "버터치킨카레";
		
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
	<iframe width="100%" height="70%" src="https://www.youtube.com/embed/NvnMG-XuBro" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
	<p>
		버터치킨카레 : 숟가락 들 시간도 아까우니까 손으로 먹어요      
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
			[버터치킨카레]<br>닭가슴살 900g<br>빈달루커리 50g<br>토마토 두개<br>
			마늘 5~6쪽<br>생강 3~4쪽<br>레몬 한개<br>
			기버터오일<br>생크림 100ml<br>식용유 약간<br>
			플레인요거트 125ml<br>무염버터 200g<br>소금<br>
			후추<br>[생략가능]<br>페퍼론치노<br>고수<br>난<br><br>         
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
			contents_num : '6',
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