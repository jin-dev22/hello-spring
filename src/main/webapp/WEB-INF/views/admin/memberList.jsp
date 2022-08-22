<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<fmt:requestEncoding value="utf-8"/>
<%--header파일의 title을 변수(${param.title})로 지정해서 페이지마다 title속성 다르게 작성 가능 --%>
<jsp:include page="/WEB-INF/views/common/header.jsp">
	<jsp:param name="title" value="관리자 회원관리" />
</jsp:include>
		<p>관리자 전용 회원관리 페이지 입니다. 이 페이지가 보인다면 관리자입니다.</p>
<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>

