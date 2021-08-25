<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
   String url = request.getRequestURL().toString();
   //System.out.println("url : " + url);
%>
<header class="header">
   <% if(session.getAttribute("sessionID") == null) { // 로그인이 안되었을 때  %>
         <input onclick="move_main()" class="main_page" type="button" value="TheChef">
         <input onclick="open_login()" class="login_page" type="button" value="로그인">
         <!--  검색 부분 -->
         <div id="search">  
         <% if(url.equals("http://localhost:8081/TheChef/index.jsp")){ %>
         <form method="post" action="ContentsProcessing?command=ContentsSearch">
         <select name="opt">
				<option value="0">제목</option>
				<option value="1">크리에이터</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
		 </form>         
         <%} else { %>     
         <form method="post" action="../ContentsProcessing?command=ContentsSearch">
         <select name="opt">
				<option value="0">제목</option>
				<option value="1">크리에이터</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
		</form>
         <%} %>	 						
	    </div>
   <% } else if(session.getAttribute("sessionID").equals("admin")) { //관리자 로그인  %> 	         
         <input onclick="move_main()" class="main_page" type="button" value="TheChef">         
         <span class="login_page">
         <font color="red"><%=session.getAttribute("sessionID") %></font> 님           
         <input onclick="logout()" type="button" value="로그아웃">  
         </span>             
         <!--  검색 부분 -->
         <div id="search">  
         <% if(url.equals("http://localhost:8081/TheChef/index.jsp")){ %>
         <form method="post" action="ContentsProcessing?command=ContentsSearch">
         <select name="opt">
				<option value="0">제목</option>
				<option value="1">크리에이터</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
		 </form>         
         <%} else { %>     
         <form method="post" action="../ContentsProcessing?command=ContentsSearch">
         <select name="opt">
				<option value="0">제목</option>
				<option value="1">크리에이터</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
		</form>
         <%} %>	 						
	    </div>
   <% } else { // 로그인 했을 경우     %> 	 
         <input onclick="move_main()" class="main_page" type="button" value="TheChef">         
         <span class="login_page">
         <font color="blue"><%=session.getAttribute("sessionID") %></font> 님           
         <input onclick="logout()" type="button" value="로그아웃">  
         </span>    
         <!--  검색 부분 -->
         <div id="search">  
         <% if(url.equals("http://localhost:8081/TheChef/index.jsp")){ %>
         <form method="post" action="ContentsProcessing?command=ContentsSearch">
         <select name="opt">
				<option value="0">제목</option>
				<option value="1">크리에이터</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
		 </form>         
         <%} else { %>     
         <form method="post" action="../ContentsProcessing?command=ContentsSearch">
         <select name="opt">
				<option value="0">제목</option>
				<option value="1">크리에이터</option>
			</select>
			<input type="text" size="20" name="condition"/>&nbsp;
			<input type="submit" value="검색"/>
		</form>
         <%} %>	 						
	    </div>
   <% } %>
</header>