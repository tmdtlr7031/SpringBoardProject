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
	
	$(function(){
		var loading = $('<div id="loading" class="loading"></div><img id="loading_img" alt="loading" src="${img}" />').appendTo(document.body).hide();
		$(window).ajaxStart(function(){loading.show(); }) // ajax 도는 중이면 로딩 show
				 .ajaxStop(function(){loading.hide();  });
	});

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
		location.href = '<c:url value="/fileboard/user/selectFileBoardList.do" />';
	}
	
	function goRegReply(obj, replyGroupOdr, replyDepth, reReplyYn) {
		if(confirm("댓글을 등록하시겠습니까?")) { 
			
			if(reReplyYn == 'N' ) {
				document.listFrm.replyGroupOdr.value= replyGroupOdr;
				document.listFrm.replyDepth.value= replyDepth;
				$('[name="replyContent"]').val($('#replyContent').val()); // 이상하게 textarea는 위의 형식처럼 접근하면 값을 못받음..
			}else{
				// 대댓글
				document.listFrm.replyGroupOdr.value= replyGroupOdr;
				document.listFrm.replyDepth.value= Number(replyDepth)+1;
				$('[name="replyContent"]').val($(obj).siblings("textarea").val());
			}
			
			$.ajax({
				type : "POST" ,
				dataType : "json" ,
				url : "${pageContext.request.contextPath }/fileboard/insertReplyAjax.do" ,
				cache : false ,
				data : $('#listFrm').serialize() ,
				success : function(data) {
					if( data.status == "OK"){
						alert("등록이 완료되었습니다.");
						location.reload();
// 						document.listFrm.action = "<c:url value='${pageContext.request.contextPath }/fileboard/selectComBoardViewForUser.do'/>";
// 						document.listFrm.submit();
					}else {
						alert("죄송합니다.알 수 없는 오류가 발생하였습니다.");
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
	
	/* 대댓글 입력창 열기*/
	function goOpen(obj) {
		$(obj).siblings("#reReply").toggle();
	}
	
	/* 본인이 남긴 댓글만 지우기 (해당댓글밑에 대댓글있으면 그것도 다 날림)*/
	function delReplyMy(replySeq, replyGroupOdr, replyDepth) {
		$('[name="replySeq"]').remove();
		$('#listFrm').append('<input type="hidden" name="replySeq" value="'+ replySeq +'" />')
		document.listFrm.replyGroupOdr.value= replyGroupOdr;
		document.listFrm.replyDepth.value= replyDepth;
		
		if (confirm("해당 댓글을 삭제하시겠습니까?")) {
			$.ajax({
				type : "POST" ,
				dataType : "json" ,
				url : "${pageContext.request.contextPath }/fileboard/deleteReplyAjax.do" ,
				cache : false ,
				data : $('#listFrm').serialize() ,
				success : function(data) {
					if( data.status == "OK"){
						alert("삭제가 완료되었습니다.");
						location.reload();
					}else {
						alert("죄송합니다.알 수 없는 오류가 발생하였습니다.");
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
					<td><c:out value="${result.boardTitle}" /></td> 
				</tr>
				<tr>
					<th scope="row">작성자</th>
					<td><c:out value="${result.regId}" /></td> 
				</tr>
				<c:if test="${not empty result.addFileList }">
					<tr>
						<th scope="row">첨부파일</th>
						<td>
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
						</td>
					</tr>		
				</c:if>
				<tr>
					<th scope="row" colspan="2">내용</th>
				</tr>
				<tr>
					<td colspan="2">
						<c:out value="${result.boardTitle}" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
<div style="border: double; margin: 100px 20px 20px 252px;">
	<form name="listFrm" id="listFrm" method="post">
		<input type="hidden" name="replyGroup" value='<c:out value="${result.comBoardCode}" />'>
		<input type="hidden" name="orgBoardSeq" value='<c:out value="${result.comBoardSeq}" />'>
		<input type="hidden" name="replyGroupOdr">
		<input type="hidden" name="replyDepth">
		<input type="hidden" name="replyDepthDepth">
		<input type="hidden" name="replyContent">
		
		
		<div>
			<table class="table table-striped">
				<colgroup>
					<!-- 필요에 따라 조절 / 합이 100이어야 함 -->
					<col style="width: 15%;">
					<col style="width: auto;">
				</colgroup>
				<tbody>
					<tr>
						<th scope="row">아이디</th>
						<th scope="row">댓글 내용</th>
					</tr>
					<c:forEach var="reply" items="${result.replyList}" varStatus="varStatus">
						<tr>
							<td><c:if test="${reply.replyDepth > 1}">ㄴ</c:if>
								<c:out value="${reply.regUser }" />
							</td> 
							<td>
								<c:out value="${reply.replyContent }" />
								<c:if test="${reply.replyDepth eq 1}"> <!-- 대댓글(2depth)까지만 -->
									<a href="javascript:;" onclick="goOpen(this); return false;">대댓글</a>
								</c:if>
								<c:if test="${reply.regUser eq tempLoginUser}">
									<a href="javascript:;" onclick="delReplyMy('${reply.replySeq}','${reply.replyGroupOdr}','${reply.replyDepth}'); return false;">삭제</a>
								</c:if>
								<div id="reReply" style="display: none; padding: inherit;">
									<c:out value="${tempLoginUser}"/>
									<textarea id="reReplyContent" cols="100" rows="3"style="resize: none;"></textarea>
									<a href="#" onclick="goRegReply(this,'${reply.replyGroupOdr}','${reply.replyDepth}','Y'); return false;" style="padding: 0 25px 0 25px; display: inline-table; line-height: 36px; background-color: #fd7e14; color: #fff">댓글등록</a>
								</div>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td><c:out value="${tempLoginUser}"/></td>
						<td>
							<textarea cols="100" rows="3" id="replyContent" style="resize: none;"></textarea>
							<a href="#" onclick="goRegReply(this,'0','1','N'); return false;" style="padding: 0 25px 0 25px; display: inline-block; line-height: 36px; background-color: #fd7e14; color: #fff">댓글등록</a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form>
</div>
<div style="text-align: center;">
	<span><a href="javascript:;" onclick="goBack(); return false;" style="padding: 0 25px 0 25px; display: inline-block; line-height: 36px; background-color: #5a5a5a; color: #fff">이전</a></span>
</div>
