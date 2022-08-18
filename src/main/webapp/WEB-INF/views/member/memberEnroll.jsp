<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="회원등록" name="title"/>
</jsp:include>
<link rel="stylesheet" href="${ pageContext.request.contextPath }/resources/css/member.css" />

<div id="enroll-container" class="mx-auto text-center">
	<form name="memberEnrollFrm" action="" method="POST">
		<table class="mx-auto">
			<tr>
				<th>아이디</th>
				<td>
					<div id="memberId-container">
			            <input type="text" class="form-control" placeholder="아이디(4글자이상)" name="memberId" id="memberId" required>
			            <span class="guide ok">이 아이디는 사용가능합니다.</span>
			            <span class="guide error">이 아이디는 사용할 수 없습니다.</span>
			            <input type="hidden" id="idValid" value="0"/><!-- 아이디사용불가=0 사용가능=1 -->
			        </div>
				</td>
			</tr>
			<tr>
				<th>패스워드</th>
				<td>
					<input type="password" class="form-control" name="password" id="password" value="1234" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인</th>
				<td>	
					<input type="password" class="form-control" id="passwordCheck" value="1234" required>
				</td>
			</tr>  
			<tr>
				<th>이름</th>
				<td>	
					<input type="text" class="form-control" name="name" id="name" value="홍길동" required>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
					<input type="date" class="form-control" name="birthday" id="birthday" value="1999-09-09"/>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" class="form-control" placeholder="abc@xyz.com" name="email" id="email" value="honggd@naver.com">
				</td>
			</tr>
			<tr>
				<th>휴대폰</th>
				<td>	
					<input type="tel" class="form-control" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" 
					   value="01012341234"	required>
				</td>
			</tr>
			<tr>
				<th>주소</th>
				<td>	
					<input type="text" class="form-control" placeholder="" name="address" id="address" value="서울시 강남구 역삼동 123">
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
					<div class="form-check form-check-inline">
						<input type="radio" class="form-check-input" name="gender" id="gender0" value="M" checked>
						<label  class="form-check-label" for="gender0">남</label>&nbsp;
						<input type="radio" class="form-check-input" name="gender" id="gender1" value="F">
						<label  class="form-check-label" for="gender1">여</label>
					</div>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<div class="form-check form-check-inline">
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby0" value="운동" checked><label class="form-check-label" for="hobby0">운동</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby1" value="등산"><label class="form-check-label" for="hobby1">등산</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby2" value="독서"><label class="form-check-label" for="hobby2">독서</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby3" value="게임"><label class="form-check-label" for="hobby3">게임</label>&nbsp;
						<input type="checkbox" class="form-check-input" name="hobby" id="hobby4" value="여행" checked><label class="form-check-label" for="hobby4">여행</label>&nbsp;
					 </div>
				</td>
			</tr>
		</table>
		<input type="submit" value="가입" >
		<input type="reset" value="취소">
	</form>
</div>
<script>
document.memberEnrollFrm.addEventListener('submit', (e)=>{
	if(idValid.value === "0"){
		e.preventDefault();
		alert("유효한 아이디를 입력해주세요.");
		return;
	}
});

const ok = document.querySelector(".guide.ok");
const error = document.querySelector(".guide.error");
const idValid = document.querySelector("#idValid");

document.querySelector("#memberId").addEventListener('keyup', (e)=>{
	const {value : memberId} = e.target;
	console.log(memberId);
	
	if(memberId.length < 4){
		idValid.value = "0";
		error.style.display = "none";
		ok.style.display = "none";
		return;
	}
	
	$.ajax({
		url : "${pageContext.request.contextPath}/member/checkIdDuplicate.do",
		data : {memberId}, 
		success(response){
			console.log(response);// js object
			
			const {available} = response;
			if(available){
				error.style.display = "none";
				ok.style.display = "inline";
				idValid.value = "1";
			}else{
				error.style.display = "inline";
				ok.style.display = "none";
				idValid.value = "0";
			}
		},
		error(jqxhr, statusText, err){
			console.log(jqxhr, statusText, err);
		}
	});
});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"/>
