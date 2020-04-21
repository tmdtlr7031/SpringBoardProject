<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<%
	response.setHeader("Cache-Control","no-store");  
	response.setHeader("Pragma","no-cache");  
	response.setDateHeader("Expires",0);
	if (request.getProtocol().equals("HTTP/1.1")){
		
		response.setHeader("Cache-Control", "no-cache");
	}
%>
<jsp:include page="/WEB-INF/jsp/include/head.jsp" />
	<script type="text/javascript">
        function go_popup(index){
            $('#popup'+index).bPopup();
        }
    </script>

	<sitemesh:write property="head"/>
</head>
<body>
	<!-- skip navi -->
	<%--<div id="skipnavigation">--%>
		<%--<ul>--%>
			<%--<li><a href="#cont_wrp">본문 바로가기</a></li>--%>
			<%--<li><a href="#gnb">주 메뉴 바로가기</a></li>--%>
		<%--</ul>--%>
	<%--</div>--%>
	<!--// skip navi -->

	<div id="wrap" class="clfix">
		<!-- wrap (s)-->
		<%--<jsp:include page="/WEB-INF/jsp/mng/include/top.jsp" />--%>
		<header id="header"> <!-- header (s)-->
			<jsp:include page="/left.do" />
			<%--<jsp:include page="/WEB-INF/jsp/mng/include/left.jsp" />--%>
		</header>

			<%--<jsp:include page="/mng/menu/secondTop.do" />--%>

		<section id="cont_wrp" class="clfix"> <!-- main_wrp (s)-->

			<jsp:include page="/menuTopBar.do" />
			<!-- 			사이트매쉬 바디영역 -->
				<sitemesh:write	property="body" />
		</section>

<!-- 		<footer id="footer"> -->

<!-- 		</footer> -->

	</div>

</body>
