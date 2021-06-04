<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="targetTemp">
	<table class="table table-striped"> 
		<colgroup> 
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
						<tr style="cursor: pointer;"> 
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
<div id="paging">
	<span class="num">
		<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fnLinkPage"/>
	</span>
</div>
