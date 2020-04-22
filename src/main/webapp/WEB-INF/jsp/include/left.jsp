<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="validator" uri="http://www.springmodules.org/tags/commons-validator" %>

<script type="text/javascript">
    $(document).ready(function(){
       $('.gnbtab li').on('click',function(e){
            $(this).addClass('on');
//           $(this).siblings().removeClass('on');
       });
    });
   
    function go_url(url, menuOrder, prtMenuSeq){
    	document.menuForm.menuOrderChk.value = menuOrder;
    	document.menuForm.prtMenuSeqChk.value = prtMenuSeq;
		document.menuForm.action = "${pageContext.request.contextPath }"+url;
    	document.menuForm.submit();
    }
</script>
<%--<style>--%>
    <%--.gnbtab li .twodep{ display:block;}--%>

<%--</style>--%>
<%-- <form name="menuForm" action="" method="post"> --%>
<!-- <input type="hidden" name="menuOrderChk" id="menuOrderChk"/> -->
<!-- <input type="hidden" name="prtMenuSeqChk" id="prtMenuSeqChk"/> -->
<%-- </form> --%>

<h1><a href=";" title="홈으로">바디의 레프트</a></h1>
<div class="gnbwrp">
    <nav id="gnb">
        <ul class="gnbtab">
        	<li>레프트</li>
        </ul>
    </nav> 
</div>
