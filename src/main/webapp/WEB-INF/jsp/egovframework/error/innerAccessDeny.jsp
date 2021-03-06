<%
/************************************************************
*   @ 화면명		: 접근권한
*   @ JSP NAME	: /jsp/egovframework/error/innerAccessDeny.jsp
*   @ JSP작성자	: 
*   @ 소속		: 
*   @ 작성일		: 2019.07.08
*   @ 작업이력	: 
*************************************************************
1		2019.07.08 	  		신규작성
*************************************************************/
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 전체 레이아웃 -->
<div id="wrap">
	<div class="error_list wrapper_er">
    	<div>
			<p class="big">죄송합니다.</p>
			<span class="bar"></span>
			<img src="<c:url value='/boardresources/image/bg_error01.png'/>" alt="접근 권한이 없는 페이지">
			<p class="small"><span>접근 권한이 없는 페이지</span>를 요청하셨습니다.</p>
			<ul>
				<li>먼저 운영자에게 문의하여 사용권한을 얻으신 후<br> 접속하시기 바랍니다.  </li>
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