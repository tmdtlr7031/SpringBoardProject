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
	
	/* 레이어 팝업 */
	.inquiryBox{width:600px;top: 10% !important;overflow: hidden;display:none;border: 2px solid #292929;position: fixed !important;background:#FFFFFF;z-index:999}
	.inquiryBox .pop-head {position: relative;overflow: hidden;background-color: #292929;height: 40px;margin: 0 0 15px 0;}
	.inquiryBox .pop-head h2 {position: absolute;top: 0;left: 0;padding: 10px 15px;font-size: 16px;font-weight:bold;color: #fff;margin: 0;}
	.inquiryBox .pop-head p {position: absolute;top: 0;right: 0;padding: 6px 16px;margin: 0;}
	.inquiryBox .pop-cont {overflow: hidden;padding: 14px 6px 10px;padding-top: 0;line-height: 18px;max-height: 800px;overflow-y: auto;margin: 0 0 0px 0;}
	.inquiryBox .pop-cont h3{font-size: 14px;font-weight:bold;margin: 0 0 6px 0}
	span.b-close{cursor: pointer}
	.inq-btnb {text-align: right;margin: 0 0 15px 0;padding: 0 14px 0 14px}
	.inq-btnb span{display:inline-block;zoom:1;*display:inline;_display:inline;font-weight:bold;font-size:13px;color:#585858;padding: 5px 20px 7px;border:1px solid #585858;border-radius:3px;-moz-border-radius:3px;-ms-border-radius:3px;-o-border-radius:3px;-webkit-border-radius:3px;font-size: 13px;cursor: pointer}
	.inq-btnb .grybtn{color: #555!important;background-color: #e8e8e8 !important;border: 1px solid #b9b9b9 !important}
	.inq-btnb .grybtn2{color: #ffffff !important;background-color: #959595 !important;border: 1px solid #7e7e7e !important}
	
</style>

<script type="text/javascript" src="<c:url value="/boardresources/js/jquery.form.min.js"/>"></script> <!-- ajaxStart 해당 함수 들어있음 -->
<script type="text/javascript" src="<c:url value="/boardresources/js/jquery.bpopup.min.js"/>"></script>
<c:set var="img"><c:url value="/boardresources/image/Loading.gif"/></c:set> <!-- 로딩 이미지 -->

<script>

	$(function(){
		var loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="${img}" />').appendTo(document.body).hide();
		$(window).ajaxStart(function(){loading.show(); }) // ajax 도는 중이면 로딩 show
				 .ajaxStop(function(){loading.hide();  });
	});


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
	
	/* 레이어 팝업 호출 */
	function goPop() {
		$.ajax({
			type : "POST" ,
			dataType : "html" ,
			url : '<c:url value="/dashboard/selectExcelLayerPop.do"/>',
			cache : false ,
//	 		data : $('#listFrm').serialize() ,
			success : function(data) { // 팝업 jsp가 data로 들어옴
				$('#ExcelPop').html(data);
				$('#ExcelPop').bPopup(); // popper.js로 어케 못하나..?
			},
			error: function (xhr, status, error) {
				alert("오류가 발생하였습니다.");
				return false;
	        }
		});
    }
	
	
	//윈도우 팝업
// 	function goDetailPop(searchDate, type) {
		
// 		// 팝업관련 변수
// 		var winHeight = document.body.clientHeight;	// 현재창의 높이
// 		var winWidth = document.body.clientWidth;	// 현재창의 너비
// 		var winX = window.screenLeft;	// 현재창의 x좌표
// 		var winY = window.screenTop;	// 현재창의 y좌표
// 		var popX = winX + (winWidth - 650)/2;
// 		var popY = winY + (winHeight - 400)/2;
		
// 		// 스케쥴이 없는 날짜는 팝업을 띄우지 않는다.
// 		if(scdlChkArr.indexOf(searchDate) == -1) {
// 			return;
// 		}
		
// 		$("#searchDate").val(searchDate);
    	
//     	popup = window.open("", "pop","width=1000,height=400,top="+popY+",left="+popX+", scrollbars=yes, resizable=yes");

//     	$("form[name=sendForm]").attr("action", "<c:url value='/send/selectSendSchListPop.do'/>");
//     	$("form[name=sendForm]").attr("target", "pop");
//     	$("form[name=sendForm]").submit();
    	
//     	$(".fc-day").css("background-color","");
// 	    $(".fc-bg [data-date='"+searchDate+"']").css('background-color', '#7970ff');
		
//     	popup.focus();
//     }
	
</script>

<div class="usrposit"></div>
<div id="maininner"><!-- maininner (s)-->
	<form name="listFrm" id="listFrm" method="post">
		<input type="hidden" name="pageIndex" value='<c:out value="${comonVO.pageIndex}" />'>
<!-- 		<input type="hidden" name="pageUnit"> -->

		<div style="float: left;">
			전체 | <strong><fmt:formatNumber value="${paginationInfo.totalRecordCount}" /></strong>
		</div>
		
		<div style="float: right;">
			<label style="background-color: #8C8C8C; color: white; cursor: pointer;" onclick="goPop();">엑셀 업로드</label> 
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
	
	<div class="inquiryBox" id="ExcelPop" style="width: 530px"></div> <!-- 레이어 팝업 생길 곳 -->
</div>
