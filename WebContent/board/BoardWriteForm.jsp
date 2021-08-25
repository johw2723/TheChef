<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>리뷰 작성</title>
<script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
  <div id="wrap">
    <br>
    <b><font size="6" color="gray">리뷰작성</font></b>
    <hr size="1" width="460">
    <br>
  </div>      
    <form method="post" action="../BoardProcessing?command=BoardWrite" name="boardForm" enctype="multipart/form-data">
    <input type="hidden" name="board_id" value="<%=session.getAttribute("sessionID") %>">
    <table width="700" border="3" bordercolor="lightgray" align="center">
       <tr>
          <td class="title">작성자</td>
          <td><%=session.getAttribute("sessionID") %></td>
       </tr>
       <tr>
          <td class="title">제 목</td>
          <td><input name="board_subject" type="text" size="70" maxlength="100" value=""/></td>        
       </tr>
       <tr>
          <td class="title">내 용</td>
          <td><textarea name="board_content" cols="72" rows="20"></textarea></td>        
       </tr>
       <tr>
          <td class="title">파일첨부</td>
          <td><input type="file" name="board_file" accept="image/jpeg, image/png"/></td>    
       </tr>
       <tr align="center" valign="middle">
          <td colspan="5">
             <input type="reset" id="reset" value="작성취소" >
             <input type="submit" onclick="referrer()" id="writeSubmit" value="등록" >
             <input type="button" id="list" value="목록" >            
          </td>
       </tr>
    </table>    
    </form>  
<script type="text/javascript">
$("#wrap").css("text-align", "center");
$(".title").css("height", "16");
$(".title").css("font-family", "돋움");
$(".title").css("font-size", "12");
$(".title").css("text-align", "center");

$("#list").click(function(){
	location.href="../board/BoardListForm.jsp";
});

$("#writeSubmit").click(function(){
	var form = document.forms[0];
	var board_subject = form.board_subject.value;
	var board_content = form.board_content.value;
	
	if(!board_subject){
		alert("제목을 입력해주세요.")
		return false;
	} else if(!board_content){
		alert("내용을 입력해주세요.")
		return false;
	}
})


</script>    
</body>
</html>