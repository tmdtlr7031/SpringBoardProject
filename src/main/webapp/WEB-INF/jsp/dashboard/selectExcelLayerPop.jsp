<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form name="detailFrm" id="detailFrm" method="post">
	<div class="pop-head">
		<h2>엑셀 업로드</h2>
		<p>
			<span class="b-close"><img src="${pageContext.request.contextPath}/boardresources/image/popupclose.png" alt="팝업 닫기"></span>
		</p>
	</div>
	<div class="pop-cont">
		<div>
			<table class="table table-striped">
				<colgroup>
					<!-- 필요에 따라 조절 / 합이 100이어야 함 -->
					<col style="width: 23%;">
					<col style="width: auto">
				</colgroup>
				<tbody>
					<tr>
						<th>엑셀파일</th>
						<td class="tl">
							<div class="filewrp">
								<label style="background-color: #8C8C8C; color: white; cursor: pointer; padding: 5px 12px 5px 12px;">업로드
									<input type="file" onchange="javascript:fileChange('excelExtList');" id="egovComfileUploader" name="file" style="position: absolute; overflow: hidden ;width: 0; height: 0;">
								</label> 
								<span id="fileName"></span>
							</div>
						</td>
					</tr>
					<tr>
						<th>양식 다운로드</th>
						<td class="tl">
							<span class="filewrp">
								<label onclick="downloadExcelForm();" style="background-color: #8C8C8C; color: white; cursor: pointer; padding: 5px 12px 5px 12px;">양식</label>
							</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="inq-btnb txtct" style="margin-top: 15px">
			<span class="b-close grybtn">취소</span> 
			<span class="indbtn" onclick="fileUpload();">등록</span>
		</div>
	</div>
</form>