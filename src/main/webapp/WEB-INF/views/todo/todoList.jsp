<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param value="할일" name="title"/>
</jsp:include>

<style>
div#todo-container{width:60%; margin:0 auto;text-align:center;}
</style>
<div id="todo-container">
    <form action="${pageContext.request.contextPath}/todo/insertTodo.do" class="form-inline" method="post">
        <input type="text" class="form-control col-sm-10 ml-1" name="todo" placeholder="할일" required/>&nbsp;
        <button class="btn btn-outline-success" type="submit" >저장</button>
    </form>

    <br />
    <!-- 할일목록 -->
	<table class="table">
	    <tr>
	      <th>번호</th>
	      <th>완료여부</th>
	      <th>할일</th>
	      <th>생성일</th>
	      <th>완료일</th>
	      <th>삭제</th>
	    </tr>
	    <c:if test="${empty todoList}">
	    	<tr>
	    		<td colspan="6" class="text-center">작성한 할 일이 없습니다.</td>
	    	</tr>
	    </c:if>
	    <c:if test="${not empty todoList}">
			<c:forEach items="${todoList}" var="todo">
			    <tr>
			    	<td>${todo.no}</td>
			    	<td>
			    		<input type="checkbox" name="todoCompleted" value="${todo.no}" ${not empty todo.completedAt? 'checked' : ''}/>
			    	</td>
			    	<td>${todo.todo}</td>
			    	<td>
			    		<fmt:parseDate value="${todo.createdAt}" pattern="yyyy-MM-dd'T'HH:mm" var="createdAt"/>
						<fmt:formatDate value="${createdAt}" pattern="yy-MM-dd HH:mm"/>
					</td>
			    	<td>
			    		<%-- <c:if test="${not empty todo.completedAt}"> --%>
				    		<fmt:parseDate value="${todo.completedAt}" pattern="yyyy-MM-dd'T'HH:mm" var="completedAt"/>
							<fmt:formatDate value="${completedAt}" pattern="yy-MM-dd HH:mm"/>		    		
			    		<%-- </c:if> --%>
			    	</td>
			    	<td>
			    		<button class="btn btn-outline-danger" type="button" >삭제</button>
			    	</td>
			    </tr>
			</c:forEach>	    
	    </c:if>
	</table>
</div>
<form action="${pageContext.request.contextPath}/todo/updateTodo.do" name="todoUpdateFrm" method="post">
	<input type="hidden" name="no" />
	<input type="hidden" name="isCompleted" />
</form>
<script>
	document.querySelectorAll("[name=todoCompleted]").forEach((checkbox) => {
		checkbox.addEventListener('change', (e)=>{
			const {value, checked} = e.target;
			console.log(value, checked);
			
			const frm = document.todoUpdateFrm;
			frm.no.value = value;
			frm.isCompleted.value = checked;
			frm.submit();
		});
	});
</script>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
