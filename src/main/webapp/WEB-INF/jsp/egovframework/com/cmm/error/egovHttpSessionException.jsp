<%
/************************************************************
*   @ 화면명		: 세션 만료
*   @ JSP NAME	: /jsp/egovframework/com/cmm/error/egovHttpSessionException.jsp
*   @ JSP작성자	: 
*   @ 소속		: 
*   @ 작성일		: 2019.07.08
*   @ 작업이력	: 
*************************************************************
1		2019.07.08 		 		신규작성
*************************************************************/
%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8"/>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<title>세션 만료 - 게시판 기능 넣기 프로젝트 v1.0</title>
</head>
<body>
<!-- 전체 레이아웃 -->
<div id="wrap">
	<div class="error_list wrapper_er">
		<div>
			<p class="big">죄송합니다.</p>
			<span class="bar"></span>
			<img src="<c:url value='/boardresources/image/bg_error01.png'/>" alt="시스템오류">
			<p class="small"><span>세션</span>이 만료되었습니다.<br/>다시 접속해주세요.</p>
			<%--<ul>--%>
				<%--<li>죄송합니다. 시스템 오류로 인하여 정상적인 서비스가 불가능합니다.</li>--%>
			<%--</ul>--%>

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
		.error_list p.big{font-size: 32px; font-weight:600; color: #333;}
		.error_list p.small{font-size: 22px; margin:20px 0; color:#0079c0; letter-spacing: -0.5px}
		.error_list p.small span{font-weight: 600; color: #0063a6}
		.error_list ul li {font-size: 15px; line-height:20px; font-weight:600; color:#656565}
	</style>
</div>
<!-- //전체 레이아웃 -->
</body>
</html>