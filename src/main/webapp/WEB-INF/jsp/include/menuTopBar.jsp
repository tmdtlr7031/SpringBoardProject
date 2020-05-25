<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
	.topbar {background-color: #8C8C8C;text-align: right;padding: 0px 25px 0 241px;height: 60px;line-height: 35px;font-size: 0;min-width: 1060px;/* border-bottom: 1px solid #1b3958; */}
	.topbar span{cursor: pointer;color: #fff;font-weight: normal;display:inline-block;zoom:1;*display:inline;_display:inline;padding: 0 0 0 13px;font-size: 13px;line-height: 60px;height: 60px;}
	.topbar span.intimed{border: 0;}
	.topbar span a{color: #ffffff;padding: 7px 13px 7px 7px;border-radius: 3px;/* background: #ffffff; *//* border: 1px solid #48a76e; */border: 1px solid rgba(255,255,255,0.7);}
	.topbar img{margin-right: 9px;margin-top: -3px;}
	.topbar span.last {border-left: 0;}
	.topbar span.last:last-child{background: none !important;}
	.topbar span.txtle {border:0;padding: 0;}
</style>
		
	<div class="topbar">
	    <span class="txtle flLeft"><a href="/myPage.do">관리자님 환영합니다.</a></span>
	    <span class="intimed flLeft">마지막 접속시간&nbsp;  2020-04-23 10:03:03&nbsp;<em id="logTimer"></em></span>
	    <span class="exten flLeft"><a href="javascript:;" onclick="loginExtension(); return false;">로그인연장</a></span>
		<span class="last"><a href="/actionLogout.do">로그아웃</a></span>
		<!-- 190419 (s) -->
		<span class="last" style="background-color: #252525;"><a>임시용</a></span>
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