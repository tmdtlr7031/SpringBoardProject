<%
/************************************************************
*   @ 화면명		: header
*   @ JSP NAME	: /jsp/include/head.jsp
*   @ JSP작성자	: 
*   @ 소속		: 4DEPTH
*   @ 작성일		: 2019.07.08
*   @ 작업이력	: 
*************************************************************
1		2019.07.08 	  		신규작성
*************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta charset="utf-8" />
<!-- 브라우저별 최상의 렌더링 방식 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge">

<meta name="keywords" content="관리자, 모바일 우편 통지서비스 v1.0" />
<meta http-equiv="description" content="모바일 우편 통지서비스 v1.0 관리자 페이지 입니다." />
<meta name="Publisher" content="(주)포뎁스">
<meta name="Copyright" content="모바일 우편 통지서비스 v1.0">

<title>모바일 우편 통지서비스 v1.0</title>

<script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/jquery-1.12.4.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/jquery.bpopup.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/jquery.dotdotdot.min.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/common.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/date.js'/>"></script>
<script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/jquery.countdown.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/mobipost/css/front-base.css?ver=20190214-1'/>" media="all">
<link rel="stylesheet" type="text/css" href="<c:url value='/mobipost/css/sub.css'/>" media="all"/>
<link rel="shortcut icon" type="image/x-icon" href="<c:url value='${pageContext.request.contextPath }/mobipost/images/common/favicon.ico'/>" />
<!-- 부트스트랩 -->
<script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/bootstrap/bootstrap.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/mobipost/css/bootstrap/bootstrap.css'/>" media="all">
