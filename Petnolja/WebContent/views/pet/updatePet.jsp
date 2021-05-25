<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>반려견 추가</title>
<style>
	.outer{
		width:1000px;
		height:1400px;
		position:relative;
		margin:auto;
		margin-top:20px;
		padding:70px;
		border:1px solid lightgray;
	}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<div class="outer">	
		<div id="dog">
			<h2>반려동물 정보 수정</h2><br>
			<span><b>반려동물을 추가하지 않으면 예약이 진행되지 않습니다.</b></span>
			<br>
			<table>
				<td>	
				<img id="titleImg" width=250 height=170>
				</td>
			</table>
			<div id="file-area">
			<input type="file" id="file1" name="file1" value="" onchange="checkSize(this); loadImg(this, 1);" required> 
			</div>
			<br>

		<form action="" method="post">
			<!--이름-->
			<span>
				<b>이름</b> &nbsp;&nbsp;
				<input type="text" name="userId" id="userId" placeholder="예)댕댕이" required>
			</span>
			<br><br>

			<!--성별-->
			<span>
				<b>성별</b> &nbsp;&nbsp;
				<input type="radio" name="gender" id="male" checked> <label for="male"><b>남자아이</b></label> &nbsp;
				<input type="radio" name="gender" id="female"> <label for="female"><b>여자아이</b></label>
			</span>
			<br><br>

			<!--견종-->
			<span>
				<b>견종</b> &nbsp;&nbsp;
				<input type="text" name="dogBreed" placeholder="예)시베리안허스키" required>
			</span>
			<br><br>

			<!--생일-->
			<span>
				<b>생일</b> &nbsp;&nbsp;
				<input type="text" id="birth" placeholder="-포함해서 입력하시오." required>
			</span>
			<br><br>

			<!--몸무게-->
			<span>
				<b>몸무게</b> &nbsp;&nbsp;
				<input type="text" name="weight" size="3" required>&nbsp;<label for="">kg</label>
			</span>
			<br><br>

			<!--중성화-->
			<span>
				<b>중성화</b> &nbsp;&nbsp;
				<input type="radio" name="middle" id="neuDo" checked> <label for="neuDo"><b>했어요</b></label> &nbsp;
				<input type="radio" name="middle" id="neuNot"> <label for="neuNot"><b>안했어요</b></label> &nbsp;
			</span>
			<br><br>

			<!--반려견등록-->
			<span>
				<b>반려견등록</b> &nbsp;&nbsp;
				<input type="radio" name="dogAdd" id="builtIn" checked> <label for="builtIn"><b>내장형</b></label> &nbsp;
				<input type="radio" name="dogAdd" id="external"> <label for="external"><b>외장형</b></label> &nbsp;
				<input type="radio" name="dogAdd" id="registration"> <label for="registration"><b>등록인식표</b></label> &nbsp;
				<input type="radio" name="dogAdd" id="Never"> <label for="Never"><b>하지않음</b></label> &nbsp;
			</span>
			<br><br>

			<!--예방접종-->
			<div>
				<b>예방접종</b> &nbsp;
					<input type="checkbox" name="" id="dhppl" value="종합백신">
					<label for="dhppl"><b>종합백신(DHPPL)</b></label>

					<input type="checkbox" name="" id="colona" value="코로나장염">
					<label for="colona"><b>코로나장염(연1회)</b></label>

					<input type="checkbox" name="" id="dogFire" value="광견병">
					<label for="dogFire"><b>광견병</b></label>

					<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

					<input type="checkbox" name="" id="kennel" value="켄넬코프">
					<label for="kennel"><b>켄넬코프(연1회)</b></label> &nbsp;&nbsp;

					<input type="checkbox" name="" id="heart" value="심장사상충">
					<label for="heart"><b>심장사상충(월1회)</b></label>				
			</div>

			<hr>

			<!--주의사항-->
			<div>
				<b>주의사항</b> &nbsp;
					<input type="checkbox" name="" id="allergy" value="음식알러지">
					<label for="allergy"><b>음식알러지</b></label>

					<input type="checkbox" name="" id="colona" value="약물복용">
					<label for="colona"><b>약물복용</b></label>

					<input type="checkbox" name="" id="dogFire" value="분리불안">
					<label for="dogFire"><b>분리불안</b></label>

					<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

					<input type="checkbox" name="" id="kennel" value="지병">
					<label for="kennel"><b>지병</b></label> 
					
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

					<input type="checkbox" name="" id="heart" value="기타">
					<label for="heart"><b>기타</b></label>				
			</div>

			<br><br>

			<!--돌봄시 참고사항-->
			<span>
				<b>돌봄시 참고사항(주의사항에 체크하신 경우 해당 란에 상세하게 적어주세요)</b> <br>
				<textarea name="textarea" cols="100" rows="7" placeholder="예)우리집강아지는 실외배변만 해요."></textarea>
			</span>

			<br>
			<!--병원명,전화번호,사실기재-->
			<span>
				<b>우리아이가 자주 다니는 동물병원을 입력 해 주세요. 위급시에 사용 됩니다.</b> <br><br>
				<b>병원명</b> &nbsp;&nbsp;&nbsp;&nbsp; 
				<input type="text" placeholder="예)kh동물병원(논현동)" required> <br><br>
				<b>전화번호</b> &nbsp; 
				<input type="text" id="phone" placeholder="-포함하여 입력하시오."> <br><br>
				<input class="form-inputPop" type="checkbox" id="check" name="agree" value="1"><label for="check"><b>사실과 다른 기재로 문제가 발생한 경우 책임은 의뢰인에게 있습니다.</b></label>				
			</span>

			<br>

			<div align="center">
			<button type="submit" id="submit" class="btn btn-primary" onclick="return validate();">등록하기</button>
			</div>

		</div>
	</form>
	</div>
	
	<!--이름 글자수 제한 기능-->
	<script>
		$(function(){
			$("#userId").keyup(function(){
				$("#count").text($(this).val().length);
				if($(this).val().length > 15) {
					alert("글자수는 15자를 초과할 수 없습니다");
					$(this).val($(this).val().substring(0, 15));
				}})        
			})
	</script>

	<!--첨부파일 클릭시 사진 추가 기능(첨부파일버튼 숨기기)-->
	<script>
		$(function(){
			$("#file-area").hide();

			$("#titleImg").click(function(){
				$("#file1").click();
				})
		});
	</script>

	<!--해당 이미지 미리보기-->
	<script>
		function loadImg(inputFile, num){
                    
                	if(inputFile.files.length == 1){ 
                		
                	    // 파일을 읽어들일 FileReader 객체 생성
                	    var reader = new FileReader();
            			
                		// 파일을 읽어들이는 메소드 => 해당 파일을 읽어들이는 순간 해당 그 파일만의 고유한 url 부여됨
                		reader.readAsDataURL(inputFile.files[0]);
                		
                		// 파일 읽기가 다 완료되었을 때 실행할 함수를 정의
                		reader.onload = function(e){
                			// 각 영역에 맞춰서 이미지 미리보기
                			switch(num){
                			case 1: $("#titleImg").attr("src", e.target.result); break;              			
							}
                		}
                	}else{ // 선택된 파일이 사라졌을 경우 => 미리보기 사라지게
                		switch(num){
            			case 1: $("#titleImg").attr("src", null); break;
                		}
                	}
                }
	</script>

	<!--사진 용량 제한(첨부 파일)-->
	<script>
		function checkSize(input) {
			if (input.files && input.files[0].size > (2 * 1024 * 1024)) {
				alert("파일 사이즈가 2mb 를 넘습니다.");
				input.value = null;
			}
		}
	</script>
	
	<!--사진 미첨부시 알람, 생년 월일 제한 기능(년월까지), 전화번호 검증, 마지막 체크 미동의시 검증 기능-->
	<script>
		function validate(){
			
			if($("#file1").val()==""){
					window.alert("사진을 첨부해주세요.");
					return false;
				}

			var birth = document.getElementById("birth");

			// 생일 검증 (7자리 제한)
			var regExp = /^([0-9]{4})-(0[1-9]|1[0-2])$/;
			if(!regExp.test(birth.value)){
				alert("유효한 생년월일을 입력 해 주세요.")
				birth.select();
				return false;

			}

			// 전화번호 검증
			var regExp = /^\d{3}-\d{3,4}-\d{4}$/;
			if(!regExp.test(phone.value)){
				alert("유효한 전화번호를 입력 해 주세요.")
				phone.select();
				return false;

			}
			
			// 체크 여부 확인
			if($("input:checkbox[id='check']").is(":checked") != true){
				alert('약관동의에 체크해 주세요');
				check.select();
				return false;
				}

			


		}
	</script>

			


	
	<%@ include file = "../common/footer.jsp"%>
</body>
</html>