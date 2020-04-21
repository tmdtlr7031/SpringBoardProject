<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
    /**
     * @Class Name : EgovLoginUsr.jsp
     * @Description : Login 인증 화면
     * @Modification Information
     * @
     * @  수정일         수정자                   수정내용
     * @ -------    --------    ---------------------------
     * @ 2009.03.03    박지욱          최초 생성
     *   2011.09.25    서준식          사용자 관리 패키지가 미포함 되었을때에 회원가입 오류 메시지 표시
     *   2011.10.27    서준식          사용자 입력 탭 순서 변경
     *  @author 공통서비스 개발팀 박지욱
     *  @since 2009.03.03
     *  @version 1.0
     *  @see
     *
     *  Copyright (C) 2009 by MOPAS  All right reserved.
     */
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
    <!-- 브라우저별 최상의 렌더링 방식 -->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">

    <meta name="keywords" content="관리자, 모바일 우편 통지서비스 v1.0" />
    <meta http-equiv="description" content="모바일 우편 통지서비스 v1.0 관리자 페이지 입니다." />
    <meta name="Publisher" content="(주)포뎁스">
    <meta name="Copyright" content="모바일 우편 통지서비스 v1.0">

    <title>모바일 우편 통지서비스 v1.0</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/mobipost/css/front-base.css'/>" media="all">
    	<link rel="shortcut icon" type="image/x-icon" href="https://notice.kiha21.or.kr/mobipost/images/common/favicon.ico" />
<%--     <link rel="shortcut icon" type="image/x-icon" href="<c:url value='${pageContext.request.contextPath }/mobipost/images/common/favicon.ico'/>" /> --%>
    <script type="text/javascript" language="javascript" src="<c:url value='/mobipost/js/jquery-1.12.4.min.js'/>"></script>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="<c:url value='/mobipost/js/html5shiv.min.js'/>"></script>
    <![endif]-->

    <script type="text/javaScript" language="javascript">
        function actionLogin() {

            if (document.loginForm.usrId.value == "") {
                alert("아이디를 입력하세요.");
                document.loginForm.usrId.focus();
            } else if (document.loginForm.usrPwd.value == "") {
                alert("비밀번호를 입력하세요.");
                document.loginForm.usrPwd.focus();
            } else {
                document.loginForm.action = "<c:url value='/actionLogin.do'/>";
                document.loginForm.submit();
            }
        }
    </script>
</head>
<body>
<form name="loginForm" action="" method="post">
<div><!-- wrap (s) -->
    <div id="loginwrap"> <!-- loginwrap (s)-->
        <div class="lgninner">
            <h1><img src="<c:url value='/mobipost/images/common/logo2.png'/>" alt="대한산업보건협회"></h1>
            <ul>
                <li><label for="uidinput">아이디</label><input type="text" name="usrId" title="아이디" id="uidinput" value=""
                                                            tabindex=1 placeholder="아이디"></li>
                <li><label for="upwinput">비밀번호</label><input type="password" name="usrPwd" title="비밀번호" id="upwinput"
                                                             maxlength="16" value="" tabindex="5"
                                                             onKeyDown="javascript:if (event.keyCode == 13) { actionLogin(); }"
                                                             tabindex=2 placeholder="비밀번호"></li>
                <li class="btn"><a href="javascript:actionLogin();">로그인</a></li>
            </ul>
        </div>

    </div>  <!-- loginwrap (e)-->
</div>    
</form>

<style type="text/css">
	* {padding:0;margin:0;text-decoration: none;font-family: "맑은 고딕","돋움",Arial}
	input{font-family:inherit;font-size:inherit;color:#000;display:inline-block;zoom:1;*display:inline;_display:inline;}
	input[type="checkbox"],input[type="radio"]{margin: 0 3px 0 0 !important;width: 14px;height: 14px;vertical-align: middle;}
	ul,ol,li,dl,dt,dd{list-style:none;vertical-align: top;}
	                                
	#loginwrap{position:relative; margin-top:10%}
	.lgninner{width: 400px;margin: 0 auto;padding: 40px 30px 25px; border: 5px solid #dedede;position: relative;}
	.lgninner h1{text-align: center;margin-bottom: 14px;}
	.lgninner h1 img{margin-right: 10px}
	.lgninner h1 span {vertical-align: top;font-size: 25px;}
	.lgninner ul{margin-top: 3px;padding: 0 0 0 0}
	.lgninner ul li{margin-bottom: 10px;}
	.lgninner ul li input {width: 98%;height: 40px; line-height: 40px; padding: 0 0 0 5px; }
	.lgninner ul li label {position:absolute;left:-10000px;top:auto;height:1px;overflow:hidden}
	.lgninner ul .btn{margin-top:15px;margin-bottom: 0}
	.lgninner ul .btn a{padding: 11px 20px 15px 20px;background-color: #555;display: block;border-radius: 5px;text-align: center;color: #fff;font-size: 18px;font-weight: 600;}
</style>

</body>
</html>

