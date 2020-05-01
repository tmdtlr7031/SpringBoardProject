<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%//<TODO> bpopup이나 form.js 등등 대체 할만한 것들 찾아서 다듬기 %>

<style>
	#maininner {padding: 20px;/* min-width: 1630px; */position: relative;background: #fff;margin: -46px 20px 20px 252px;border-radius: 4px;box-shadow: 0 0 10px rgba(0,0,0,0.2);box-sizing: border-box;}
	#cont_wrp .usrposit {position: relative;margin: 0px 0 0 223px;/* border-bottom: 1px solid #d5dce0; */min-width: 1060px;/* background: #25476a; */padding: 31px 0 101px 30px;box-sizing: border-box;}
	@media all and (max-width:1600px){
  		#wrap,#cont_wrp .usrposit,.topbar{min-width: inherit;}
  	}
  	
  	/* 페이징 스타일 */
  	.page_num{padding: 20px 0 0 0;position:relative;overflow:hidden;clear: both;margin: 0 auto;width: 546px;text-align: center;}
	.page_num a{display:block;color:#666666;margin: 0px 1px 0 0;border:1px solid #d0d0d0;text-align:center;min-width: 30px;font-size: 13px;padding: 6px 0;display:inline-block;zoom:1;*display:inline;_display:inline;vertical-align: middle;background-color: #fff}
	.page_num a.on{background: #8C8C8C;color:#FFF;font-weight: bold;}
	.page_num a:hover{border: 1px solid #8C8C8C;color:#8C8C8C;font-weight: bold;}
	.page_num a.on:hover{color: #fff}
	.page_num a.pn {padding:0;border:0}
	.page_num a.pn:hover {border:0}
	
	
	/* 로딩*/
	#loading {
		height: 100%;
		left: 0px;
		position: fixed;
		_position:absolute;
		top: 0px;
		width: 100%;
		filter:alpha(opacity=50);
		-moz-opacity:0.5;
		opacity : 0.5;
	}
	.loading {
		background-color: white;
		z-index: 199;
	}

	#loading_img{
		position:absolute;
		top:50%;
		left:50%;
		height:75px;
		margin-top:-75px
		margin-left:-75px;
		z-index: 200;
	}
	
</style>

<script type="text/javascript" src="<c:url value="/boardresources/js/jquery.form.min.js"/>"></script> <!-- ajaxStart 해당 함수 들어있음 -->
<script type="text/javascript" src="<c:url value="/boardresources/js/jquery.bpopup.min.js"/>"></script>
<c:set var="img"><c:url value="/boardresources/image/Loading.gif"/></c:set> <!-- 로딩 이미지 -->

<script>

	$(function(){
		var loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="${img}" />').appendTo(document.body).hide();
		$(window).ajaxStart(function(){loading.show(); }) // ajax 도는 중이면 로딩 show
				 .ajaxStop(function(){loading.hide();  });
		
		/* 페이지유닛  이벤트 */
		$('select[name="pageUnit"]').on('change', function(){
			document.listFrm.pageIndex.value = 1;
			document.listFrm.action = "${pageContext.request.contextPath }/dashboard/selectDashBoardList.do";
			document.listFrm.submit();
		});
	});


	/* 페이징 */
	function fnLinkPage(pageIndex){
		document.listFrm.pageIndex.value = pageIndex;
		document.listFrm.action = "${pageContext.request.contextPath }/dashboard/selectDashBoardList.do";
		document.listFrm.submit();
	}
	
	/* 등록 */
	function goReg() {
		document.listFrm.action = "${pageContext.request.contextPath }/fileboard/selectComBoardForm.do";
		document.listFrm.submit();
	}	
	
</script>

<div class="usrposit"></div>
<div id="maininner"><!-- maininner (s)-->
	<form name="listFrm" id="listFrm" method="post">
		<input type="hidden" name="pageIndex" value='<c:out value="${comonVO.pageIndex}" />'>

		<div style="float: left;">
			전체 | <strong><fmt:formatNumber value="${paginationInfo.totalRecordCount}" /></strong>
		</div>
		
		<div>
			<table class="table table-striped">
	<%-- 			<caption>테스트 리스트</caption> --%>
				<colgroup>
					<!-- 필요에 따라 조절 / 합이 100이어야 함 -->
					<col style="width: 5%;">
					<col style="width: auto;">
					<col style="width: auto;">
					<col style="width: auto;">
					<col style="width: auto;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">게시판구분코드</th>
						<th scope="col">게시판일련번호</th>
						<th scope="col">제목</th>
						<th scope="col">등록일시</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty resultList }">
							<c:forEach var="result" items="${resultList}" varStatus="varStatus">
								<tr style="cursor: pointer;">
									<!-- No -->
									<td><c:out value="${paginationInfo.totalRecordCount - (comonVO.pageUnit * (paginationInfo.currentPageNo-1 )) - (varStatus.count-1) }"/></td>
									<td><c:out value="${result.comBoardCode}"/></td>
									<td><c:out value="${result.comBoardSeq}"/></td>
									<td><c:out value="${result.boardTitle}"/></td>
									<td><c:out value="${result.regDt}"/></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<td colspan="5" style="text-align: center;">목록이 없습니다.</td>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</form>
	
	<div>
		<button style="text-align: right;" onclick="goReg();">등록</button>
	</div>
	
	<div class="page_num">
		<span class="num">
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fnLinkPage"/>
		</span>
	</div>
	
</div>
