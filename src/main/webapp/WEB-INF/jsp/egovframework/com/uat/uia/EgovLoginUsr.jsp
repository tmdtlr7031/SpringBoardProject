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
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <meta name="keywords" content="테스트용" />
	<meta http-equiv="description" content="테스트 페이지 입니다." />
	<meta name="Publisher" content="nss">
	<meta name="Copyright" content="nss_test">

    <title>모바일 우편 통지서비스 v1.0</title>

	<!-- favicon -->
	<link rel="shortcut icon" type="image/x-icon" href="<c:url value='${pageContext.request.contextPath }/boardresources/image/favicon.ico'/>" />
	
	<!-- 부트스트랩4 필수 : 제이쿼리, popper.js -->
	<script type="text/javascript" src='/boardresources/js/jquery-3.5.0.min.js'></script>
	<script src="https://unpkg.com/@popperjs/core@2/dist/umd/popper.js"></script>
	
	<!-- 부트스트랩 -->
	<script type="text/javascript" language="javascript" src="<c:url value='/boardresources/bootstrap_4.4.1/js/bootstrap.js'/>"></script>
	<link rel="stylesheet" type="text/css" href='/boardresources/bootstrap_4.4.1/css/bootstrap.css' media="all">

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
            <img src="<c:url value='/boardresources/image/LoginCat.png'/>" alt="로그인하세요">
            <h1>로그인 폼</h1>
            <ul>
                <li><label for="uidinput">아이디</label><input type="text" name="usrId" title="아이디" id="uidinput" value=""
                                                            tabindex=1 placeholder="아이디"></li>
                <li><label for="upwinput">비밀번호</label><input type="password" name="usrPwd" title="비밀번호" id="upwinput"
                                                             maxlength="16" value="" tabindex="5"
                                                             onKeyDown="javascript:if (event.keyCode == 13) { actionLogin(); }"
                                                             tabindex=2 placeholder="비밀번호"></li>
                <li class="btn btn-block"><a href="javascript:;" onclick="actionLogin(); return false;">로그인</a></li>
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

