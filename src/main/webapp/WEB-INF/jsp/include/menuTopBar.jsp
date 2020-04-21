<%
/************************************************************
*   @ 화면명		: menuTopBar
*   @ JSP NAME	: /jsp/include/menuTopBar.jsp
*   @ JSP작성자	: 
*   @ 소속		: 4DEPTH
*   @ 작성일		: 2019.07.08
*   @ 작업이력	: 
*************************************************************
1		2019.07.08 	  		신규작성
*************************************************************/
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript">

</script>
<div class="topbar">
    <span class="txtle flLeft"><a href="${pageContext.request.contextPath }/myPage.do"><img src="${pageContext.request.contextPath }/mobipost/images/common/admin.png" alt="관리자님 환영합니다.">${LoginVO.usrName }님 환영합니다.</a></span>
    <span class="intimed flLeft">마지막 접속시간&nbsp;  ${LoginVO.logDt }&nbsp;<em id="logTimer"></em></span>
    <span class="exten flLeft"><a href="javascript:loginExtension();"><img src="${pageContext.request.contextPath }/mobipost/images/common/login_exten.png" alt="로그인연장">로그인연장</a></span>
	<span class="last"><a href="${pageContext.request.contextPath }/actionLogout.do"><img src="${pageContext.request.contextPath }/mobipost/images/common/logout.png" alt="로그아웃">로그아웃</a></span>
	<!-- 190419 (s) -->
	<span class="last" style="background-color: #252525;"><a href="${pageContext.request.contextPath }/mobipost/Mobi Post v1.0.pptx" title="매뉴얼 다운로드"><img src="${pageContext.request.contextPath }/mobipost/images/common/btn_dw.png" alt="다운로드">매뉴얼 다운로드</a></span>
	<!-- //190419 (e) -->
</div>

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