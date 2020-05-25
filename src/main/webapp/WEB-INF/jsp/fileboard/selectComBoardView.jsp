<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%//<TODO> bpopup이나 form.js 등등 대체 할만한 것들 찾아서 다듬기, 스크립트 수정하기 %>

<style>
	#maininner {padding: 20px;/* min-width: 1630px; */position: relative;background: #fff;margin: -46px 20px 20px 252px;border-radius: 4px;box-shadow: 0 0 10px rgba(0,0,0,0.2);box-sizing: border-box;}
	#cont_wrp .usrposit {position: relative;margin: 0px 0 0 223px;/* border-bottom: 1px solid #d5dce0; */min-width: 1060px;/* background: #25476a; */padding: 31px 0 101px 30px;box-sizing: border-box;}
	@media all and (max-width:1600px){
  		#wrap,#cont_wrp .usrposit,.topbar{min-width: inherit;}
  	}
  	
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
<c:set var="img"><c:url value="/boardresources/image/Loading.gif"/></c:set> <!-- 로딩 이미지 -->

<!-- 제이쿼리 multifile 플러그인도 있음 -->
<!-- http://blog.naver.com/PostView.nhn?blogId=javaking75&logNo=220087280269&parentCategoryNo=&categoryNo=56&viewDate=&isShowPopularPosts=false&from=postView 참고 -->

<script>
	var excelExtList = ['pdf','jpg','png','gif','jpeg','hwp','ppt','xls','doc','txt','pdf','xlsx','pptx','docx']; // 업로드할 엑셀 확장자
	
	$(function(){
		var loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="${img}" />').appendTo(document.body).hide();
		$(window).ajaxStart(function(){loading.show(); }) // ajax 도는 중이면 로딩 show
				 .ajaxStop(function(){loading.hide();  });
		
		
	});

	/* 파일 선택 (Start) */ 
	function fileChange(validList) {
		$('#selectedFiles').text("");
		var fileList = document.getElementsByName("file")[0].files; // 객체 리스트로 담김 (ex.0번째 = {name: "엑셀업로드테스트용.xls", ~~~})
		
		// .size로 파일 용량체크도 할 수 있는데 이건 나중에..
		for (var i = 0; i < fileList.length; i++) {
			var fileFullName = fileList[i];
			var fileInfo = getFileInfo(fileFullName.name); 
			if (!validExt(fileInfo.ext, eval(validList))) {
				break;
			}
			$('#selectedFiles').append(fileFullName.name + '<br/>'); // FileList 객체는 읽기 전용이라 여러개 선택된 file중 특정한 file만 수정할 수 없음. 따라서 아예 다시 선택하거나 multiple대신 각각 type=file을 만들어야함
		}
	}
	
	// 파일명, 확장자 분리 (기존꺼랑 약간 달라짐)
	function getFileInfo(filePath){
		var fileInfo = new Object();
		var commaIndex = filePath.lastIndexOf('.');
		var name = filePath.substring(0, commaIndex);
		var ext = filePath.substring(commaIndex+1, filePath.length+1);

		fileInfo.name = name;
		fileInfo.ext = ext.toLowerCase();

		return fileInfo;
	}
	
	// 확장자 유효성 검사
	function validExt(ext, extList) {
		var returnValue = false;
		if ($.inArray(ext, extList) == -1 && ext != '') {
			$('input[name="file"]').val('');
			alert('첨부파일 확장자를 확인해주세요.');
			return returnValue;
		}
		returnValue = true;
		return returnValue;
	}
	/* 파일 선택 (End) */
	
	
	/* 해당파일 삭제하기 */
	function goDelete(obj) {
		$(obj).siblings("[name$=updateYn]").val("Y"); // 빽단에서 파일삭제 여부 플래그
		$(obj).siblings("a").remove();
		$(obj).remove();
	}
	
	/* 파일 다운로드 */
	function goFileDownload(boardCode, boardSeq, fileSeq) {
		$("body").find("#tempFrm").remove(); // 제거했다가 다시 생성안하면 (초기화 작업) 파라미터가 2개 넘어가게 됨.
		$("body").append('<form name="tempFrm" id="tempFrm" method="post" action="/fileboard/ComBoardFileDownload.do"></form>');
		$('#tempFrm').append('<input type="hidden" name="boardCode" value="'+ boardCode +'">');
		$('#tempFrm').append('<input type="hidden" name="boardSeq"  value="'+ boardSeq  +'">');
		$('#tempFrm').append('<input type="hidden" name="fileSeq"   value="'+ fileSeq   +'">');
		$('#tempFrm').submit();
	}
	
	/* 이전페이지(나중에 목록 검색조건유지도 추가하기) */
	function goBack() {
		// 일단은 간단하게 해놓기
		location.href = '<c:url value="/fileboard/admin/selectFileBoardList.do" />';
	}
	
	/* 수정 */
	function goUpdate() {
		if (confirm("수정하시겠습니까?")) {
			
			var form = $('#listFrm');
			var formData = new FormData(form.get(0));
			
			$.ajax({
				url : "<c:url value='/fileboard/updateComBoardViewAjax.do'/>",
	            type : 'POST',
	            enctype: 'multipart/form-data',
	            data : formData,
	            cache : false ,
	            processData: false, // multipart 시 필수
	            contentType: false, // multipart 시 필수
	            success : function(data){
	           		if(data.status = 'OK'){
						alert(data.msg);
						document.listFrm.action = "<c:url value='/fileboard/admin/selectFileBoardList.do'/>";
				        document.listFrm.submit();
		           	}else{
						alert(data.msg);
						return false;
		           	}
	            },
	            error: function (xhr, status, error) {
					alert("오류가 발생하였습니다.");
					return false;
		        }
			});
		}
	}
	
</script>

<div class="usrposit"></div>
<div id="maininner"><!-- maininner (s)-->
	<form name="listFrm" id="listFrm" method="post">
		<input type="hidden" name="comBoardCode" value='<c:out value="${result.comBoardCode}" />'>
		<input type="hidden" name="comBoardSeq" value='<c:out value="${result.comBoardSeq}" />'>
	
		<div>
			<table class="table table-striped">
	<%-- 			<caption>테스트 리스트</caption> --%>
				<colgroup>
					<!-- 필요에 따라 조절 / 합이 100이어야 함 -->
					<col style="width: 15%;">
					<col style="width: auto;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">제목</th>
						<td><input type="text" name="boardTitle" id="boardTitle" style="width: 85%" value='<c:out value="${result.boardTitle}" />'></td> 
					</tr>			
					<tr>
						<th scope="row">첨부파일</th>
						<td>
							<input type="file" name="file" multiple="multiple" onchange="fileChange('excelExtList');"> <br/>
							<span>*pdf, jpg, png, gif 및 오피스파일 형식의 10MB이하를 첨부하실 수 있습니다.</span>
							<p id="selectedFiles"></p>
							<c:if test="${not empty result.addFileList }">
								<hr>
								<c:forEach var="items" items="${result.addFileList}" varStatus="vs">
									<p>
										<a href="javascript:;" onclick="goFileDownload('<c:out value="${items.boardCode}"/>','<c:out value="${items.boardSeq}"/>','<c:out value="${items.fileSeq}"/>'); return false;"><c:out value="${items.orgFileNm}.${items.fileExt}"/></a>
										<button onclick="goDelete(this);">삭제하기</button>
										<input type="hidden" name="addFileList[${vs.index }].boardCode" value='<c:out value="${items.boardCode}"/>'>
										<input type="hidden" name="addFileList[${vs.index }].boardSeq" value='<c:out value="${items.boardSeq}"/>'>
										<input type="hidden" name="addFileList[${vs.index }].fileSeq" value='<c:out value="${items.fileSeq}"/>'>
										<input type="hidden" name="addFileList[${vs.index }].updateYn" value="N">
									</p>
								</c:forEach>
							</c:if>
						</td>
					</tr>		
					<tr>
						<th scope="row" colspan="2">내용</th>
					</tr>
					<tr>
						<td colspan="2">
							<textarea id="boardContent" rows="10" cols="164" name="boardContent" style="height: 300px; resize: none;"><c:out value="${result.boardTitle}" /></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>

<div style="text-align: center;">
	<span><a href="javascript:;" onclick="goBack(); return false;" style="padding: 0 25px 0 25px; display: inline-block; line-height: 36px; background-color: #5a5a5a; color: #fff">이전</a></span>
	<span><a href="#" onclick="goUpdate(); return false;" style="padding: 0 25px 0 25px; display: inline-block; line-height: 36px; background-color: #fd7e14; color: #fff">수정</a></span>
</div>
