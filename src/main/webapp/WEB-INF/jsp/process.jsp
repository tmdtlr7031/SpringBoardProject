<%
/************************************************************
*   @ 화면명		: ajax 처리페이지
*   @ JSP NAME	: /jsp/process.jsp
*   @ JSP작성자	: 
*   @ 소속		: 4DEPTH
*   @ 작성일		: 2019.07.08
*   @ 작업이력	: 
*************************************************************
1		2019.07.08 		 		신규작성
*************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
    <title>테스트 게시판</title>
    <script type="text/javascript" src="<c:url value='/boardresources/js/jquery-3.5.0.min.js'/>"></script>
    <script language='javascript'>
//<![CDATA[
        <c:if test="${!empty message}">
            alert("<c:out value="${message}" />");
        </c:if>
        <c:url value="${url}" />
        <c:out value="${function}" />
//]]>
    </script>
</head>
<body>
<form name="frm" action="<c:url value="${action}"/>" method="post">
    ${inputs}
</form>
</body>
</html>



