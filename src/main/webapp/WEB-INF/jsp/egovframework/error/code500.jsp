<%
/************************************************************
*   @ 화면명		: 500 에러페이지
*   @ JSP NAME	: /jsp/egovframework/error/code500.jsp
*   @ JSP작성자	: 
*   @ 소속		: 
*   @ 작성일		: 2019.07.08
*   @ 작업이력	: 
*************************************************************
1		2019.07.08 	  		신규작성
*************************************************************/
%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="keywords" content="관리자, 모바일 우편 통지서비스 v1.0" />
    <meta http-equiv="description" content="모바일 우편 통지서비스 v1.0 관리자 페이지 입니다." />
    <meta name="Copyright" content="모바일 우편 통지서비스 v1.0">

    <title>모바일 우편 통지서비스 v1.0</title>
</head>
<body>
 <!-- 전체 레이아웃 -->
        <div id="wrap">
                <div class="error_list wrapper_er">
                    <div>
                        <p class="big"><span>500 Error</span></p>
                        <span class="bar"></span>
                        <img src="<c:url value='/mobipost/images/bg_error01.png'/>" alt="접근 권한이 없는 페이지">
                        <ul>
                            <li>네트워크에 문제가 발생했습니다. </li>
                        </ul>

                    </div>
                </div>

                <style type="text/css">
                   /*오류 메시지*/
                    * {padding:0;margin:0;text-decoration: none;list-style: none;font-family: "맑은 고딕","돋움",Arial}
                    .error_list {width: 425px; border: 5px solid #dedede; padding:40px; margin: 20px auto; margin-top:10%;}
                    .error_list div {margin:0 auto;text-align: center;}
                    .error_list div img{margin:0 40px 0 0}
                    .error_list div span.bar{width: 80px; height: 2px; display: block; background: #555; margin: 15px auto 20px;}
                    .error_list p{color: #0079c0;margin-bottom: 5px}
                    .error_list p.big{font-size: 26px; font-weight:600; color: #333;}
                    .error_list p.small{font-size: 22px; margin:20px 0; color:#0079c0; letter-spacing: -0.5px}
                    .error_list p.small span{font-weight: 600; color: #0063a6}
                    .error_list ul li {font-size: 15px; line-height:20px; font-weight:600; color:#656565;margin-top: 15px}
                </style>
        </div>
        <!-- //전체 레이아웃 -->
</body>
</html>