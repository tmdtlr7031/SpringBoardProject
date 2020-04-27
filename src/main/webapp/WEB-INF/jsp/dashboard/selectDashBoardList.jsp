<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
	#maininner {padding: 20px;/* min-width: 1630px; */position: relative;background: #fff;margin: -46px 20px 20px 252px;border-radius: 4px;box-shadow: 0 0 10px rgba(0,0,0,0.2);box-sizing: border-box;}
	#cont_wrp .usrposit {position: relative;margin: 0px 0 0 223px;/* border-bottom: 1px solid #d5dce0; */min-width: 1060px;/* background: #25476a; */padding: 31px 0 101px 30px;box-sizing: border-box;}
	@media all and (max-width:1600px){
  		#wrap,#cont_wrp .usrposit,.topbar{min-width: inherit;}
  	}
  	
  	.page_num{padding: 20px 0 0 0;position:relative;overflow:hidden;clear: both;margin: 0 auto;width: 546px;text-align: center;}
	.page_num a{display:block;color:#666666;margin: 0px 1px 0 0;border:1px solid #d0d0d0;text-align:center;min-width: 30px;font-size: 13px;padding: 6px 0;display:inline-block;zoom:1;*display:inline;_display:inline;vertical-align: middle;background-color: #fff}
	.page_num a.on{background: #8C8C8C;color:#FFF;font-weight: bold;}
	.page_num a:hover{border: 1px solid #8C8C8C;color:#8C8C8C;font-weight: bold;}
	.page_num a.on:hover{color: #fff}
	.page_num a.pn {padding:0;border:0}
	.page_num a.pn:hover {border:0}
</style>

<script>
	/* 페이징 */
	function fnLinkPage(pageIndex){
		document.listFrm.pageIndex.value = pageIndex;
		document.listFrm.action = "${pageContext.request.contextPath }/dashboard/selectDashBoardList.do";
		document.listFrm.submit();
	}
	
	/* 엑셀 업로드 */ 
	var excelExtList = [ 'xls']; // 업로드할 엑셀 확장자
	
// 	function fileChange(fileInputNameNum, validList) {
// 		var routeId = 'file_' + fileInputNameNum + '_route';
// 		var filePath = $('input[name=file_' + fileInputNameNum + ']').val();
// 		var fileInfo = getFileInfo(filePath);
// 		if (!validExt(fileInfo.ext, eval(validList), fileInputNameNum)) {
// 			return;
// 		}
// 		$('#' + routeId).val(fileInfo.name);
// 	}
	
	/* 엑셀 다운로드 */
	function fnExcelDownload() {
		document.listFrm.action = "${pageContext.request.contextPath }/dashboard/selectDashBoardListDownloadExcel.do";
		document.listFrm.submit();
	}
	
</script>

<div class="usrposit"></div>
<div id="maininner"><!-- maininner (s)-->
	<form name="listFrm" id="listFrm">
		<input type="hidden" name="pageIndex" value='<c:out value="${comonVO.pageIndex}" />'>
<!-- 		<input type="hidden" name="pageUnit"> -->

		<div style="float: left;">
			전체 | <strong><fmt:formatNumber value="${paginationInfo.totalRecordCount}" /></strong>
		</div>
		
		<div style="float: right;">
			엑셀 업로드 : <input type="file" onchange="fileChange('excelExtList');">
			<button onclick="fnExcelDownload();">엑셀다운로드</button>
			<select><option>페이지수</option></select>
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
					<col style="width: auto;">
					<col style="width: auto;">
				</colgroup>
				<thead>
					<tr>
						<th scope="col">No</th>
						<th scope="col">주문번호</th>
						<th scope="col">고객명</th>
						<th scope="col">주문상품</th>
						<th scope="col">주문수량</th>
						<th scope="col">주문상태</th>
						<th scope="col">등록일시</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty resultList }">
							<c:forEach var="result" items="${resultList}" varStatus="varStatus">
								<tr style="cursor: pointer;" onclick="javascript:goView('${result.orderCode}')">
									<!-- No -->
									<td><c:out value="${paginationInfo.totalRecordCount - (comonVO.pageUnit * (paginationInfo.currentPageNo-1 )) - (varStatus.count-1) }"/></td>
									<td><c:out value="${result.orderCode}"/></td>
									<td><c:out value="${result.userName}"/></td>
									<td><c:out value="${result.orderProduct}"/></td>
									<td><c:out value="${result.orderCnt}"/></td>
									<td><c:out value="${result.orderStatus}"/></td>
									<td><c:out value="${result.regDt}"/></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<td colspan="7">목록이 없습니다.</td>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>
	</form>
	<div class="page_num">
		<span class="num">
			<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fnLinkPage"/>
		</span>
	</div>
</div>
