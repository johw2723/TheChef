/* 자바스크립트 파일 TheChef.js */

function index(){
	var link = "index.jsp";
	location.href=link;
}

function move_main(){
	var link = "http://localhost:8081/TheChef/index.jsp";
	location.href=link;
}

function move_favorite(){
	var link = "http://localhost:8081/TheChef/view_contents/List.jsp";
	location.href=link;
}

function open_login(){
	var link = "http://localhost:8081/TheChef/view_member/login.jsp";
	location.href=link;
}

function join_member(){
	var link = "http://localhost:8081/TheChef/view_member/member.jsp";
	location.href=link;
}

function move_search(){
	var link = "http://localhost:8081/TheChef/view_contents/search.jsp";
	location.href=link;
}

function move_admin(){
	var link = "http://localhost:8081/TheChef/view_member/adminMenu.jsp";
	location.href=link;
}

function move_update(){
	var link = "http://localhost:8081/TheChef/view_member/userUpdate.jsp";
	location.href=link;
}

function move_product(){
	location.href = "http://localhost:8081/TheChef/product/productList.jsp";
}

function move_basket(){
	location.href = "http://localhost:8081/TheChef/product/BasketList.jsp";
}

function move_payment(){
	location.href = "http://localhost:8081/TheChef/product/PaymentInfo.jsp";
}

function logout(){
	if(window.location.href == "http://localhost:8081/TheChef/index.jsp"){
		location.href="TheChefProcessing?command=MemberLogout";
	} else {
		location.href="../TheChefProcessing?command=MemberLogout";
	}
}

function changeMenu(value){	
	if(value == "0"){		
		menu_recipe.style.display='block';
		menu_board.style.display='none';
	} else if(value == "1"){
		menu_recipe.style.display='none';
		menu_board.style.display='block';
	}
}

function boardWrite(){	
	menu_recipe.style.display='none';
	menu_button.style.display='none';
	menu_board.style.display='none';
	menu_board2.style.display='block';

}

/* 회원가입 관련 */
function checkId(obj) { 
	var memId = document.all.memId.value;
    var alert = document.getElementById('alert');
    var isID = /^[a-z0-9][a-z0-9_\-]{4,19}$/;
    
	if (obj.value == "") {		
		alert.style.display='block';
		alert.innerHTML="필수 입력정보입니다.";
		obj.focus();
    } else if (!isID.test(memId)){
		alert.style.display='block';
		alert.innerHTML="5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.";
		//obj.focus();
    } else {
		alert.style.display='block';
		alert.innerHTML="완벽합니다!";
    }
}

function checkPw(obj) {
	var pw = document.all.pw.value;
    var alert = document.getElementById('alertPW');
    var isPW = /^[a-zA-Z0-9]{4,19}$/;
    //[a-zA-Z0-9_\-`~!@@#$%^&*|₩₩₩'₩";:₩/?]
    
    if (obj.value == "") {		
		alert.style.display='block';
		alert.innerHTML="필수 입력정보입니다.";
		//obj.focus();
    } else if (!isPW.test(pw)){
		alert.style.display='block';
		alert.innerHTML="5~20자 영문 대 소문자, 숫자를 사용하세요.";
		//obj.focus();
    } else {
		alert.style.display='block';
		alert.innerHTML="완벽합니다!";
    }
}

function checkPwc(obj) {
	var pw = document.all.pw.value;
	var pwc = document.all.pwc.value;
    var alert = document.getElementById('alertPWC');
    
    if (obj.value == "") {		
		alert.style.display='block';
		alert.innerHTML="설정하려는 비밀번호가 맞는지 확인하기 위해 다시 입력 해주세요.";
		//obj.focus();
    } else if (pw != pwc){
		alert.style.display='block';
		alert.innerHTML="비밀번호가 일치하지 않습니다.";
		//obj.focus();
    } else {
		alert.style.display='block';
		alert.innerHTML="일치합니다!";
    }
}

function checkE(obj) {
	var email = document.formJoin.email.value;
    var alert = document.getElementById('alertE');
    
    if (obj.value == "") {		
		alert.style.display='block';
		alert.innerHTML="필수 입력정보입니다.";
		//obj.focus();
    } else {
		alert.style.display='none';
    }
}

function checkP(obj) {
	var phone = document.formJoin.phone.value;
    var alert = document.getElementById('alertP');
    
    if (obj.value == "") {		
		alert.style.display='block';
		alert.innerHTML="필수 입력정보입니다.";
		//obj.focus();
    } else {
		alert.style.display='none';
    }
}

function chkForm() { 	
	var checkid = document.all.checkid.value;
	var memId = document.getElementById('alert').innerHTML;
	var pw = document.getElementById('alertPW').innerHTML;
	var pwc = document.getElementById('alertPWC').innerHTML;
	var memPW = document.getElementById('memPW').value;
	var memPWC = document.getElementById('memPWC').value;
	var phone = document.all.phone.value;
	var email = document.all.email.value;
	
	
	if(memId != "완벽합니다!") {
		alert("아이디를 확인해주세요!");
		return false;
	}
	
	if(pw != "완벽합니다!") {
		alert("비밀번호를 확인해주세요!");
		return false;
	}   
		
	if(memPWC != memPW) {
		alert("비밀번호가 일치하는지 확인해주세요!");
		return false;
	}
	
	if(phone == "") {
		alert("전화번호를 확인해주세요!");
		return false;
	}
	
	if(email == "") {
		alert("이메일을 확인해주세요!");
		return false;
	}

	if(checkid == "idUncheck") {
		alert("ID 중복체크를 확인해주세요!");
		return false;
    }
	return true;
	
}


function openCheckId() {
	var memId=document.all.memId.value;
	var pw=document.all.pw.value;
	var phone=document.all.phone.value;
	var email=document.all.email.value;
	var address=document.all.address.value;
	var payment=document.all.payment.value;
	if(memId){
		var title="chkid";
		var status="width=500, height=250, resizable=no, menubar=no, toolbar=no, scrollbars=no";
		window.open("IdCheckForm.jsp", title, status);

	}else{
		alert("ID를 입력하세요!");
	}   
}

/* 회원가입 관련 메소드 끝 */

/* 중복체크 메소드 */
var httpRequest = null;

// httpRequest 객체 생성
function getXMLHttpRequest(){
	var httpRequest = null;

	if(window.ActiveXObject){
		try{
			httpRequest = new ActiveXObject("Msxml2.XMLHTTP");	
		} catch(e) {
			try{
				httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e2) { httpRequest = null; }
		}
	}
	else if(window.XMLHttpRequest){
		httpRequest = new window.XMLHttpRequest();
	}
	return httpRequest;	
}


// 아이디 중복체크
function idCheck(){

	var memId = document.getElementById("userId").value;

	if (!memId) {
		alert("아이디를 입력하지 않았습니다.");
		return false;
	} else if((memId < "0" || memId > "9") && (memId < "A" || memId > "Z") && (memId < "a" || memId > "z")) { 
		alert("한글 및 특수문자는 아이디로 사용하실 수 없습니다.");
		return false;
	} else {
		var param="memId="+memId
		httpRequest = getXMLHttpRequest();
		httpRequest.onreadystatechange = callback;
		httpRequest.open("POST", "../TheChefProcessing?command=idCheck", true);	
		httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
		httpRequest.send(param);
	}
}

function callback(){
	if(httpRequest.readyState == 4){
		// 결과값을 가져온다.
		var resultText = httpRequest.responseText;
		if(resultText == 0){
			//alert("사용할 수 없는 아이디입니다.");
			document.getElementById("cancelBtn").style.visibility='visible';
			document.getElementById("useBtn").style.visibility='hidden';
			document.getElementById("msg").innerHTML ="사용할 수 없는 아이디입니다.";
		} 
		else if(resultText == 1){ 
			document.getElementById("cancelBtn").style.visibility='hidden';
			document.getElementById("useBtn").style.visibility='visible';
			document.getElementById("msg").innerHTML = "사용 가능한 아이디입니다.";
		}
	}
}

function idImport(){
	document.getElementById("userId").value = opener.document.formJoin.memId.value;
}

function idExport(){
    // 회원가입 화면의 ID입력란에 값을 전달
	opener.document.all.checkid.value="idCheck";
    opener.document.formJoin.memId.value = document.getElementById("userId").value;
    
    if (opener != null) {
        opener.chkForm = null;
        self.close();
    }    
}
/* 중복체크 메소드 끝 */

/* 로그인 메소드 */
function chkLogin() { 
	var memId = document.formlogin.memId.value;
	var pw = document.formlogin.pw.value;
    var alertID = document.getElementById('login_alertID');
    var alertPW = document.getElementById('login_alertPW');
    var fail_alert = document.getElementById('login_fail');
    
    if (!memId) {		
    	alertPW.style.display='none';
		alertID.style.display='block';
		alertID.innerHTML="아이디를 입력하세요";
		return false;
	} else if (!pw) {
		alertID.style.display='none';
		alertPW.style.display='block';
		alertPW.innerHTML="비밀번호를 입력하세요";
		return false;
	} else {
		alertID.style.display='none';
		alertPW.style.display='none';
		//fail_alert.style.display='block';
		//alertPW.innerHTML="가입하지 않은 아이디거나, 잘못된 비밀번호 입니다.";
	}
    return true;
}

/* 결제 취소 */
function cancelPayment(obj){
	$.ajax({
		url : "../BasketProcessing?command=DeletePayInfo",
		type : "post",
		dataType : "JSON",
		data : {
			paymentNum : obj
		},
		success : function(data){
			//alert("통신성공");
			cancelSuccess(data);
		},
		error : function(){
            alert("통신실패");
        }  
	})
}

function cancelSuccess(jsonData){
	$("#tbl_pay").remove();
	$("#cancelMsg").append(jsonData.msg +"<br><br>");
}
