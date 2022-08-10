<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8"/>
<%--header파일의 title을 변수(${param.title})로 지정해서 페이지마다 title속성 다르게 작성 가능 --%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="한글 스프링~~" />
</jsp:include>
		<img src="${pageContext.request.contextPath }/resources/images/logo-spring.png" id="center-image" alt="스프링로고" class="d-block mx-auto mt-5"/>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

