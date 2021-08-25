<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import = "model.ContentsService" %>
<%@ page import = "dao.ContentsVO" %>
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "model.ProductService" %>
<%@ page import = "dao.ProductVO" %>

<%	
	// 검색조건과 검색내용을 가져온다.
	String opt = request.getParameter("opt");
	String condition = request.getParameter("condition");
	
	// 검색조건과 내용을 Map에 담는다.
	HashMap<String, Object> listOpt = new HashMap<String, Object>();
	listOpt.put("opt", opt);
	listOpt.put("condition", condition);			
	
	// 컨텐츠 목록을 가져온다.	
	ContentsService cs = new ContentsService();
	ArrayList<ContentsVO> list =  cs.getContentsList(listOpt);	
	
	// 상품 목록을 가져온다.
	ProductService ps = new ProductService();
	ArrayList<ProductVO> productList =  ps.getProductList(listOpt);
	
	// requestScope 호출을 위한 값 저장	
	request.setAttribute("list", list);
	request.setAttribute("productList", productList);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TheChef Search</title>
<style>
@import url(../styles/Sub.css)
</style>
<script src="../scripts/TheChef.js"></script>
</head>
<body style="overflow-x: hidden">
<%@ include file="../incl/banner.jsp" %>
   
<section class="section_main">      
	<section class="in_section1">
	<h4>검색 컨텐츠</h4>
	
	<!-- 컨텐츠 목록 -->
	<article class="container">
	<c:forEach var="vo" items="${requestScope.list}">           
		<div class="box">
			<iframe src="${vo.contents_src}" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
			<span><a href="${vo.contents_file}" target="_self">${vo.contents_subject}</a></span>                
		</div>            
	</c:forEach>
	</article>    
	
	<!-- 상품 목록 -->  
	<h4>검색 상품</h4>
	<article class="container2">
	<c:forEach var="product" items="${requestScope.productList}">			
		<div class="box2">
			<span>
				<a href="javascript:void(window.open('../product/productDetail.jsp?product_id=${product.product_id}','TheChef Product Detail','width=450, height=650'))">
					<img src="${product.product_url}" width="125" height="100" alt="이미지">
					${product.product_name}<br>
					<fmt:formatNumber value="${product.product_price}" pattern="###,###,###"/>원     	  					
				</a>
			</span>       	
		</div>       	     
	</c:forEach>
	</article>
</section>

<section class="in_section2">
<%@ include file="../incl/aside.jsp" %>
</section>

</section>
</form>
</body>
</html>