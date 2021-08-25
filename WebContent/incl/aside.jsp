<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<nav class="aside_main">
   <div class="aside_menu">
   <% if(session.getAttribute("sessionID") == null) { // 로그인이 안되었을때%>
         <input onclick="move_main()" class="nav_button" type="button" value="홈"><br>          
         <input onclick="move_search()" class="nav_button" type="button" value="탐색"><br>
         <input onclick="move_product()" class="nav_button" type="button" value="상품"><br> 
   <%} else if(session.getAttribute("sessionID").equals("admin")) { // 관리자 계정 로그인인 경우 %> 	       
         <input onclick="move_main()" class="nav_button" type="button" value="홈"><br>          
         <input onclick="move_search()" class="nav_button" type="button" value="탐색"><br>
         <input onclick="move_product()" class="nav_button" type="button" value="상품"><br> 
         <input onclick="move_basket()" class="nav_button" type="button" value="장바구니"><br> 
         <input onclick="move_payment()" class="nav_button" type="button" value="결제내역"><br>      
         <input onclick="move_favorite()" class="nav_button" type="button" value="찜 목록"><br>
         <input onclick="move_admin()" class="nav_button" type="button" value="회원 관리"><br>
   <% } else { // 로그인이 되었을때     %> 	 
         <input onclick="move_main()" class="nav_button" type="button" value="홈"><br>          
         <input onclick="move_search()" class="nav_button" type="button" value="탐색"><br>
         <input onclick="move_product()" class="nav_button" type="button" value="상품"><br> 
         <input onclick="move_basket()" class="nav_button" type="button" value="장바구니"><br> 
         <input onclick="move_payment()" class="nav_button" type="button" value="결제내역"><br>     
         <input onclick="move_favorite()" class="nav_button" type="button" value="찜 목록"><br>    
         <input onclick="move_update()" class="nav_button" type="submit" value="정보 수정"><br>
   <% } %>
   </div>
</nav>