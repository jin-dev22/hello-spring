<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시글 수정" name="title"/>
</jsp:include>
<style>
div#board-container{width:400px; margin:0 auto; text-align:center;}
div#board-container input{margin-bottom:15px;}
/* 부트스트랩 : 파일라벨명 정렬*/
div#board-container label.custom-file-label{text-align:left;}
</style>

<div id="board-container">
	<form 
		name="boardFrm" 
		action="${pageContext.request.contextPath}/board/boardUpdate.do" 
		method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="no" value="${board.no}" />
		<input type="text" class="form-control" placeholder="제목" name="title" id="title" value="${board.title}" required>
		<input type="text" class="form-control" name="memberId" value="${board.memberId}" readonly required>
		
		<c:forEach items="${board.attachments}" var="attach" varStatus="vs">
			<div class="btn-group-toggle p-0 mb-3" data-toggle="buttons">
				<label class="btn btn-outline-danger btn-block" style="overflow: hidden" title=""><!-- label안에 input형태로 작성해주기(버전문제) -->
					<input type="checkbox" id="delFile${vs.count}" name="delFile" value="${attach.no}">
					첨부파일삭제 - ${attach.originalFilename}
				</label>
			</div>
		</c:forEach>
		
		<div class="input-group mb-3" style="padding:0px;">
		  <div class="input-group-prepend" style="padding:0px;">
		    <span class="input-group-text">첨부파일1</span>
		  </div>
		  <div class="custom-file">
		    <input type="file" class="custom-file-input" name="upFile" id="upFile1" multiple>
		    <label class="custom-file-label" for="upFile1">파일을 선택하세요</label>
		  </div>
		</div>
		<div class="input-group mb-3" style="padding:0px;">
		  <div class="input-group-prepend" style="padding:0px;">
		    <span class="input-group-text">첨부파일2</span>
		  </div>
		  <div class="custom-file">
		    <input type="file" class="custom-file-input" name="upFile" id="upFile2" multiple>
		    <label class="custom-file-label" for="upFile2">파일을 선택하세요</label>
		  </div>
		</div>	
	    <textarea class="form-control" name="content" placeholder="내용" required>${board.content}</textarea>
		<br />
		<input type="submit" class="btn btn-outline-success" value="저장" >
	</form>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
