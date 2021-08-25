<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@ page import = "java.util.HashMap" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "model.ProductService" %>
<%@ page import = "dao.ProductVO" %>
<%
	ProductService ps = new ProductService();

	//검색조건과 검색내용을 가져온다.
	String opt = request.getParameter("opt");
	String condition = request.getParameter("condition");
	
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
<title>TheChef Product List</title>
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
		<!-- 상품 리스트 부분 -->
		<h4>상품 리스트</h4>
		<article class="container">
		<c:forEach var="product" items="${requestScope.productList}">			
			<div class="box">
        		<span>     			
        			<a href="javascript:void(window.open('productDetail.jsp?product_id=${product.product_id}','TheChef Product Detail','width=450, height=650'))">
          	  			<img src="${product.product_url}" width="125" height="100" alt="이미지">
          	  				${product.product_name}<br>
          	  				<fmt:formatNumber value="${product.product_price}" pattern="###,###,###"/>원     	  					
            		</a>
            	</span>       	
        	</div>       	     
    	</c:forEach>
    	</article>
    	<!-- 상품 검색 부분 -->   	
    	<div id="searchForm">
    		<form>
    			<select name="opt">
    				<option value="1">상품명</option>
    				<option value="0">키워드</option>
    			</select>
    			<input type="text" size="20" name="condition"/>&nbsp;
    			<input type="submit" value="검색"/>
    		</form>
    	</div>
    	<br>
    	</section>
    	<section class="in_section2">
    		<%@ include file="../incl/aside.jsp" %>
    	</section>
    </section>   
</body>
</html>