<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <% String findId = (String)request.getAttribute("findId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>

#popup {
  display: flex;
  justify-content: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, .7);
  z-index: 1;
}
#popup.hide {
  display: none;
}
.findContent {
  height: 450px; width: 500px;
  font-size:15px;
  padding: 20px;
  background: #fff;
  margin-top: 50px;
  border-radius: 5px;
}
.radioInput{width:20px; height:20px; margin-left: 10px; margin-right:10px; }
</style>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/emailjs-com@2/dist/email.min.js"></script>
<script type="text/javascript">
   (function(){emailjs.init("service_emuyg7f"); })
</script>
<title>펫시터</title>
    
</head>
<body>

<div id="popup" class="hide">
  <div class="findContent">
    <button type="button" class="btn btn-outline-primary btn-sm" onclick="closePopup()" style="margin-left: 90%; height:30px;"><b>&times;</b></button>
    <h3 align="center" id="findTitle"></h3><hr><br>

          <div class="find" id="findMain">   
                  <div style="height:230px;" align="center">
                    <h3>이용하실 서비스를 선택하세요</h3><br><br><br>
                    <button type="button" class="btn btn-primary" style="width:50%;" onclick="findId()">아이디 찾기</button><br><br><br>
                    <button type="button" class="btn btn-primary" style="width:50%;" onclick="findPwd()">패스워드 찾기</button><br><br><br>
                </div>             
         </div>


        <div class="find" id="findId1">
            <form action="findId.mem" method="post">     
                  <div style="height:230px;" align="center">       
                  <input type="radio" class="radioInput" id="findIdByEmail" name="findIdRadio" checked required><label for="findIdByEmail">이메일주소로 확인</label>
                  <input type="radio" class="radioInput" id="findIdByPhone" name="findIdRadio"><label for="findIdByPhone">휴대폰번호로 확인</label><br><br>
                  <input type="text" class="form-control" placeholder="이름을 입력하세요"  name="userName" required><br>
                  <input type="email" class="form-control" placeholder="가입메일주소 입력하세요" name="userEmail" id="userEmail"  required>
                  <input type="text" class="form-control" placeholder="휴대폰번호를 -를 포함하여 입력하세요" name="userPhone" id="userPhone" style="display: none;"><br>
                
                </div>             
                <button type="submit" class="btn btn-primary" style="width:100%;">다음</button><br><br>  
            </form>
        </div>

        <div class="find" id="findPwd1">
              <form action="" method="post">     
                  <div style="height:230px;" align="center">       
                    <br>
                    <input type="text" class="form-control" placeholder="아이디를 입력하세요"  name="userId" required><br>
                    <input type="text" class="form-control" placeholder="이름을 입력하세요" name="userName" required>
                    <br> <span>아이디를 모르시나요 ?&nbsp;&nbsp;</span>
                    <button type="button" class="btn btn-secondary btn-sm" onclick="closePopup(); findId();">아이디 찾기</button>
                  </div>             
                  <button type="button" class="btn btn-primary findBtn" style="width:100%;">다음</button><br><br>  <!--submit버튼으로 변경해야함-->
              </form>
       </div>

       <!-- 만약 앞에 클릭한 값에 값이 담겨있을 경우만 보여주기 시작 -->
        <div class="find">
              <form action="" method="post">     
                <div style="height:230px;" align="center">       
                  <br>
                  <br><br>
                  <input type="radio" class="radioInput" id="findPwdByEmail" checked><label for="findPwdByEmail" >이메일로 인증하기(ads***@naver.com)</label><br><br>
                  <input type="radio" class="radioInput" id="findPwdBySMS"><label for="findPwdBySMS">SMS로 인증하기(010-****-1111)</label><br>
                  <br><br>
                </div>             
                <button type="button" class="btn btn-primary" style="width:100%;" id="emailCheck">다음</button><br><br>
            </form> 
        </div>
       
        <div class="find">
            <form action="" method="post">     
              <div style="height:230px;" align="center">       
                <br>
                <br>
                이메일(SMS)를 확인 해 주세요<br><br>
                <input type="text" class="form-control" placeholder="인증번호를 입력하세요"  name="authNo" id="authNo"><br>
                <br><br><br>
                <br><br>
              </div>             
              <button type="button" class="btn btn-primary" style="width:100%;" id="emailConfirm">다음</button><br><br>
           </form> 
        </div>

        <div class="find">
          <form action="" method="post">     
            <div style="height:230px;" align="center">       
              <br>
                 새로운 비밀번호를 설정합니다<br><br>
                 <!-- <input type="hidden" autocomplete="username" ng-hide="true"> -->
                <input type="password" class="form-control" placeholder="영문, 특수문자, 숫자를 모두 포함하여 8~16자 사이"  name="userPwd" id="userPwd1" required autocomplete="new-password" ><br>
                <input type="password" class="form-control" placeholder="동일하게 입력하세요" id="userPwd2" required autocomplete="new-password" >
              <br><br>
            </div>             
            <button type="submit" class="btn btn-primary" style="width:100%;" onclick="return validate();">확인</button><br><br>
            <!-- 만약 앞에 클릭한 값에 값이 담겨있을 경우만 보여주기 끝 -->
         </form> 
       </div>
  </div>
</div>



<script>
        function findMain(){$("#popup").removeClass('hide'); $(".find").css("display","none"); $("#findMain").css("display","block")}
        function findId(){$("#popup").removeClass('hide'); $(".find").css("display","none"); $("#findId1").css("display","block");$("#findTitle").text("아이디 찾기")}
        function findPwd(){$("#popup").removeClass('hide'); $(".find").css("display","none"); $("#findPwd1").css("display","block"); $("#findTitle").text("비밀번호 찾기")}
        function closePopup(){$("#popup").addClass('hide'); $("#findMain,#findId1,#findPwd1").css("display","none"); $("#findTitle").text("")}

        $(function(){
          $("#findIdByEmail").click(function(){
              $("#userEmail").attr("required",true);
              $("#userPhone").attr("required",false);
              $("#userEmail").css("display","block"); $("#userPhone").css("display","none");});         
          $("#findIdByPhone").click(function(){
              $("#userEmail").attr("required",false);
              $("#userPhone").attr("required",true);
              $("#userEmail").css("display","none"); $("#userPhone").css("display","block");}); 

          $(".findBtn").click(function(){
               $(this).parent().parent().css("display","none");
              $(this).parent().parent().next().css("display","block");
          });
        })
</script>


<!-- 아이디찾기 마지막 부분 디스플레이 끝-->

<script type="text/javascript">
    var userName = "날드";  //고객님의 성함
    var userEmail = "daita0225@naver.com";  //고갱님의 이메일주소
    var userMessage = "1234";  //인증번호가 들어갈 자리
    $(document).ready(function() {
      emailjs.init("user_eo9lqugonxfzo2twbsdjP");	

    //이메일 인증번호 발송	
    $('#emailCheck').click(function(){       
      userMessage =Math.floor(Math.random()*10000000);
      console.log(userMessage);
      $(this).parent().parent().css("display","none");
      $(this).parent().parent().next().css("display","block");	 
      
    // var templateParams = {	
    //       name: userName, email : userEmail, message : userMessage
    //         };    
    //       emailjs.send('service_emuyg7f', 'template_x43f8ve', templateParams)
    //     .then(function(response) {
    //         console.log('SUCCESS!', response.status, response.text);
    //     }, function(error) {
    //         console.log('FAILED...', error);
    //    })

    }); 
    //이메일 인증번호 발송 끝
         
    // 발송된 이메일 검증
    $("#emailConfirm").click(function(){
              if($('#authNo').val()==userMessage){
                  $("#emailConfirm").parent().parent().css("display","none");
                  $("#emailConfirm").parent().parent().next().css("display","block");	 
              }else{ 
                  window.alert("인증번호가 일치하지 않습니다");
                  return false;
              }
          })
    });
    // 발송된 이메일 검증 끝


    function validate(){
          var userPwd1 = document.getElementById("userPwd1");
          var userPwd2 = document.getElementById("userPwd2");
          var  regExp = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,16}$/i;
          var hangulcheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;
          // // 비밀번호 검증
          if(hangulcheck.test(userPwd1.value)){
            alert("비밀번호에 한글을 사용 할 수 없습니다.");
            userPwd1.value = "";
             userPwd1.focus();
          }
          if(!regExp.test(userPwd1.value)){
          alert("유효한 비밀번호를 입력해주세요");
          userPwd1.value = "";
          userPwd1.focus();
          return false;
          }     
          //비밀번호 일치 검증
          if(userPwd1.value != userPwd2.value){
              alert("동일한 비밀번호를 입력해주세요");
              userPwd2.value = "";
              userPwd2.focus();
              return false;
          }
          alert("변경이 완료되었습니다");
}


    //정리를 하면 서블릿에서 값을 받아와야하는 경우애는...
    // js에서 display를 설정해줘야겠다...

</script>
</body>
</html>