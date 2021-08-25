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
	int no = Integer.parseInt("2");
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
	String condition = "돔베고기";
		
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
	<iframe width="100%" height="70%" src="https://www.youtube.com/embed/ChFrn-gf3RM" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
	<p>
		제주 돔베고기와 고기국수 : 삼겹살 1kg를 먹는 가장 완벽한 방법
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
			[돔베고기]<br>제주산 오겹살 수육용1kg<br> 물 1.5~1.8L<br>된장 1숟갈<br>
			간장 1숟갈<br>통후추 1/2숟갈<br>대파 1대<br>양파 1/2개<br>생강 2톨<br><br>
			[돔베고기 후작업]<br>고기가 잠길만한 양의 차가운 물<br>소금 1/2숟갈<br>식초 1숟갈<br><br>
			[제주 전통 고기국수]<br>위의 돔베고기 삶은 물 기준<br>중면 500g(5인분)까지 커버 가능<br>대파 1대<br>
			양파 1/2개<br>당근 1/2개<br>간장 1~2숟갈<br>마늘 2알<br>계란 1개<br><br>
			[콩나물 고명]<br>콩나물 한 주먹<br>간장 1/2숟갈<br>참기름 1/2숟갈<br>통깨 1/2숟갈<br><br>
			[올래 스타일 고기국수]<br>중면<br>돔베고기 삶은 물:시판 사골육수 = 1:3 ~ 1:4<br>쪽파 썬 것 약간<br>통 깨 약간<br><br>
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
			contents_num : '2',
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