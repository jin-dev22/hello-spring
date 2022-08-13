<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="게시판" name="title"/>
</jsp:include>
<style>
/*글쓰기버튼*/
input#btn-add{float:right; margin: 0 0 15px;}
</style>
<section id="board-container" class="container">
	<input type="button" value="글쓰기" id="btn-add" class="btn btn-outline-success"/>
	<table id="tbl-board" class="table table-striped table-hover">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
			<th>첨부파일</th> <!-- 첨부파일 있을 경우, /resources/images/file.png 표시 width: 16px-->
			<th>조회수</th>
		</tr>
		<c:if test="${empty list}">
			<tr>
				<td colspan="6" class="text-center">조회결과가 없습니다.</td>
			</tr>
		</c:if>
		<c:if test="${not empty list}">
			<c:forEach items="${list}" var="board">
				<tr>
					<td>${board.no}</td>
					<td>${board.title}</td>
					<td>${board.memberId}</td>
					<td>
						<fmt:parseDate value="${board.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createdAt"/>
						<fmt:formatDate value="${createdAt}" pattern="MM-dd HH:mm"/>
					</td>
					<td>
						<c:if test="${board.attachCount gt 0}">
							<img src="${pageContext.request.contextPath}/resources/images/file.png" width="16px" />
						</c:if>
					</td>
					<td>${board.readCount}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>
</section> 

<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
