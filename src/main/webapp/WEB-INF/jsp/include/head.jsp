<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- <%@ taglib prefix="codeUtil" uri="tld/codeUtil.tld"%> --%>


<meta charset="utf-8" />
<!-- 브라우저별 최상의 렌더링 방식 -->
<meta http-equiv="X-UA-Compatible" content="IE=Edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<meta name="keywords" content="테스트용" />
<meta http-equiv="description" content="테스트 페이지 입니다." />
<meta name="Publisher" content="nss">
<meta name="Copyright" content="nss_test">

<title>테스트 헤더</title>
<!-- favicon -->
<link rel="shortcut icon" type="image/x-icon" href="<c:url value='${pageContext.request.contextPath }/boardresources/image/favicon.ico'/>" />

<!-- 부트스트랩4 필수 : 제이쿼리, popper.js -->
<script type="text/javascript" src="<c:url value='/boardresources/js/jquery-3.5.0.min.js'/>"></script>
<script src="https://unpkg.com/@popperjs/core@2/dist/umd/popper.js"></script>

<script type="text/javascript" src="<c:url value='/boardresources/js/jquery.countdown.js'/>"></script>

<!-- 부트스트랩 -->
<script type="text/javascript" language="javascript" src="<c:url value='/boardresources/bootstrap_4.4.1/js/bootstrap.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/boardresources/bootstrap_4.4.1/css/bootstrap.css'/>" media="all">
