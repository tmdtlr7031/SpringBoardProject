<%
/************************************************************
*   @ 화면명		: left
*   @ JSP NAME	: /jsp/include/left.jsp
*   @ JSP작성자	: 
*   @ 소속		: 4DEPTH
*   @ 작성일		: 2019.07.08
*   @ 작업이력	: 
*************************************************************
1		2019.07.08 	  		신규작성
*************************************************************/
%>
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
    
    /* function go_url(url_path, Order, prtMenuSeq, seltYn){
    	if("N" == seltYn){
    		alert("권한이 없습니다. 관리자에게 문의해 주세요.");
    		return false;
    	} else {
	   		if(url_path.indexOf("?")>0){
	   			$(location).attr("href", url_path+"&menuOrder="+Order+"&prtMenuSeq="+prtMenuSeq);
	   		}else{
	   			$(location).attr("href", url_path+"?menuOrder="+Order+"&prtMenuSeq="+prtMenuSeq);
	   		}
   		}
    	
    }  */
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
<form name="menuForm" action="" method="post">
<input type="hidden" name="menuOrderChk" id="menuOrderChk"/>
<input type="hidden" name="prtMenuSeqChk" id="prtMenuSeqChk"/>
</form>
<h1><a href="${pageContext.request.contextPath }/main.do" title="홈으로"><img src="${pageContext.request.contextPath }/mobipost/images/common/logo.png" alt="모바일 공인전자 시스템 관리자"></a></h1>
<div class="gnbwrp"> <!-- gnbwrp (s)-->
    <nav id="gnb"> <!-- gnb (s)-->
        <ul class="gnbtab">
            <c:forEach items="${menues }" var="result">
            	<c:if test="${result.menuLevel eq 1 }">
	            	<li class="ord d0${fn:substring(result.menuOrder, 0, 1) }">
						<a href="#" class="gnbbt <c:if test="${fn:substring(result.menuOrder, 0, 2) eq fn:substring(currentMenuOdr, 0, 2)}">on</c:if>" title="${result.menuName }">${result.menuName }</a>
						<ul class="twodep"  <c:choose>
								            	<c:when test = "${fn:substring(result.menuOrder, 0, 2) eq fn:substring(currentMenuOdr, 0, 2)}">
								            		style="display:block;"
								            	</c:when>
								            	<c:otherwise>
								            		style="display:none;"
								            	</c:otherwise>
								            </c:choose> >
							<c:forEach items="${menues }" var="result2">
								<c:if test="${result2.menuLevel eq 2 && result.menuSeq eq result2.prtMenuSeq }">
									<li><a href="javascript:go_url('${result2.menuUrl }', '${result2.menuOrder }','${result2.prtMenuSeq }');" title="${result2.menuName }" <c:if test="${result2.menuOrder eq currentMenuOdr}">class="on"</c:if>>${result2.menuName }</a></li>
								</c:if>
							</c:forEach>
						</ul>
	            	</li>
				</c:if>	
            </c:forEach> 
        </ul>
    </nav> <!-- gnb (e)-->
</div> <!-- gnbwrp (e)-->
