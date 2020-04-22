<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link rel="stylesheet" type="text/css" href="<c:url value='/boardresources/css/dashboard.css'/>" media="all">

<!--     <nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow"> -->
<!-- 		<a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Company name</a> -->
<!-- 		<ul class="navbar-nav px-3"> -->
<!-- 			<li class="nav-item text-nowrap"> -->
<%-- 			  <input class="form-control form-control-dark w-100" value='<c:out value="(수정해야함)관리자님 환영합니다." />'> --%>
<%-- 			  <span class="navbar-brand col-sm-3 col-md-2 mr-0">마지막 접속시간&nbsp;<c:out value="(수정해야함)2020-04-22 16:29:30" />&nbsp;<em id="logTimer"></em></span> --%>
<!-- 			  <span class="navbar-brand col-sm-3 col-md-2 mr-0"><a href="javascript:;" onclick="loginExtension(); return false;">로그인연장</a></span> -->
<%-- 		      <a class="nav-link" href="${pageContext.request.contextPath }/actionLogout.do">Sign out</a> --%>
<!-- 		    </li> -->
<!-- 	  	</ul> -->
<!-- 	</nav> -->
		<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
		  <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Company name</a>
		  <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">
		  <ul class="navbar-nav px-3">
		    <li class="nav-item text-nowrap">
		      <a class="nav-link" href="#">Sign out</a>
		    </li>
		  </ul>
		</nav>

<!-- 일단 어디에 합칠지 고민.. -->
<script type="text/javascript">

    // 로그인
    <c:if test="${!empty LoginVO}">

    $(document).ready(function(){
        doMainTimer();
    });
    </c:if>

    function doMainTimer(){

    //메인바 타이머
        // 10000 10초
        // 600000 1분
        // 1800000 30분
    var lapSeconds = new Date().getTime() + 1800000;
    $('#logTimer').countdown(lapSeconds, {elapse: false})
        .on('update.countdown', function(event) {
            var $this = $(this);
                $this.html(event.strftime('<span>%M:%S</span>'));
        })
        .on('finish.countdown', function(event){
            logout();
        });

    }

    // 로그인 연장
    function loginExtension() {
        doRefresh();
        doMainTimer();
    }

    // 로그아웃
    function logout() {
        $("body").append($("<form id=\"logout\" method=\"post\" action=\"${pageContext.request.contextPath }/actionLogout.do\"><input type=\"hidden\" name=\"logout\" value=\"Y\" /></form>"));
        $("#logout").submit();
    }

    function doRefresh() {
        $.ajax({
            type : "POST",
            url : "/keepSession.do",
            success : function(data) {
            },
            error : function() {
            }
        });
    }


</script>