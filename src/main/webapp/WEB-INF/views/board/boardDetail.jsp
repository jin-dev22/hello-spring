<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시판 상세보기" name="title"/>
</jsp:include>
<style>
div#board-container{width:400px;}
input, button, textarea {margin-bottom:15px;}
button { overflow: hidden; }
/* 부트스트랩 : 파일라벨명 정렬*/
div#board-container label.custom-file-label{text-align:left;}
</style>
<div id="board-container" class="mx-auto text-center">
	<input type="text" class="form-control" 
		   placeholder="제목" name="boardTitle" id="title" 
		   value="${board.title}" required>
	<input type="text" class="form-control" 
		   name="memberId" 
		   value="${board.member.name}" readonly required>

	<c:if test="${not empty board.attachments}">
		<c:forEach items="${board.attachments}" var="attach" varStatus="vs">
			<button type="button" 
					class="btn btn-outline-success btn-block"
					onclick="location.href='${pageContext.request.contextPath}/board/fileDownload.do?no=${attach.no}';">
					첨부파일${vs.count} - ${attach.originalFilename}	
			</button>
		</c:forEach>
	</c:if> 
	
    <textarea class="form-control" name="content" 
    		  placeholder="내용" required>${board.content}</textarea>
    <input type="number" class="form-control" name="readCount" title="조회수"
		   value="${board.readCount}" readonly>
	<input type="datetime-local" class="form-control" name="created_at" 
		   value='${board.createdAt}'>
	<c:if test="${loginMember.memberId eq board.memberId}">
	   	<button type="button" class="btn btn-outline-primary btn-block"
	   		onClick="location.href='${pageContext.request.contextPath}/board/boardUpdate.do?no=${board.no}';">수정</button>
	</c:if>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
