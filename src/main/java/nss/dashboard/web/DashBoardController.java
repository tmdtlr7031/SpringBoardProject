package nss.dashboard.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovFormBasedFileUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import nss.common.util.SXSSFxlsxUtil;
import nss.common.util.XlsxBindingUtil;
import nss.common.util.XlsxUtil;
import nss.dashboard.service.DashBoardService;
import nss.dashboard.service.DashBoardVO;


@Controller
public class DashBoardController {
	
	@Resource(name="dashboardService")
	private DashBoardService dashboardService;
	
	/**
	 * 게시판 목록 조회
	 *
	 */	
	@RequestMapping(value="/dashboard/selectDashBoardList.do")
	public String selectDashBoardList(@ModelAttribute("comonVO")DashBoardVO dashBoardVO, Model model) {
//		LoginVO loginVO = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		/* 페이징 */
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(dashBoardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(dashBoardVO.getPageUnit());
		paginationInfo.setPageSize(dashBoardVO.getPageSize());

		dashBoardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dashBoardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		dashBoardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		/* 조회 */
		List<DashBoardVO> resultList = dashboardService.selectTestList(dashBoardVO);
		paginationInfo.setTotalRecordCount(dashboardService.selectTestListCnt(dashBoardVO));
		
		model.addAttribute("paginationInfo",paginationInfo);
		model.addAttribute("resultList",resultList);
		
		return "/dashboard/selectDashBoardList";
	}
	
	
	/**
	 * 게시판 목록 엑셀 다운로드
	 *
	 */
	@RequestMapping(value="/dashboard/selectDashBoardListDownloadExcel.do")
	public void selectDashBoardListDownloadExcel(@ModelAttribute("comonVO")DashBoardVO dashBoardVO, HttpServletResponse res) {
		
		/*엑셀 다운로드 flag on*/
		dashBoardVO.setExcelYn("Y");
		
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(dashBoardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(dashBoardVO.getPageUnit());
		paginationInfo.setPageSize(dashBoardVO.getPageSize());

		dashBoardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		dashBoardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		dashBoardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		/* 조회 */
		List<DashBoardVO> resultList = dashboardService.selectTestList(dashBoardVO);
		paginationInfo.setTotalRecordCount(dashboardService.selectTestListCnt(dashBoardVO));
		
		/* 리스트 번호 붙여주기 (쿼리에서 rno 뽑아온다면 해줄필요 없음) */
		for (int i = 0; i < resultList.size(); i++) {
			resultList.get(i).setRno((paginationInfo.getTotalRecordCount() - (dashBoardVO.getPageUnit() * (paginationInfo.getCurrentPageNo()-1 )) - ((i+1)-1)));
			// resultList.get(i).setTgtCntStr(StringUtil.getThousandCommaSeperator(resultList.get(i).getTgtCnt())); 천단위 구분기호 쓴다면..
		}
		
		// ======================================== SXSSF를 이용한 Poi(템플릿있는경우) ============================
//		String title = "테스트 목록";
//		final String[] keyArray = {"rno", "order_code", "user_name", "order_product", "order_cnt", "order_status","reg_dt"}; // VO에 있는 결과값 맵핑 (VO에 있는 필드값을 Underscore 형태로 작성)
//		XlsxBindingUtil xlsxBindingUtil = new XlsxBindingUtil();
//		try {
//			xlsxBindingUtil.readFile(resultList, keyArray, "", res, "", title);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		// ======================================== SXSSF를 이용한 Poi(템플릿있는경우) ============================
		
		// ======================================== SXSSF를 이용한 Poi =======================================
		List selectResultList = resultList;	// 결과리스트 selectResultList에 설정
		boolean titleYn = true;	// 엑셀파일 타이틀 존재여부
		boolean combineYn = false; // 셀 병합 여부
		String sheetName = "sheet";	// 엑셀파일 시트명
		String title = "테스트 목록";
		String saveFileName = "testBoardList.xlsx";	// 사용자에게 저장시킬 엑셀파일명
		String filePath = EgovProperties.getProperty("Globals.filePath") + "excel/";	// 엑셀파일을 저장할 서버 경로
		final String[] titleArray = {"No", "주문번호", "고객명", "주문상품", "주문수량", "주문상태", "등록일시"};	// 타이틀명 설정
		final String[] keyArray = {"rno", "order_code", "user_name", "order_product", "order_cnt", "order_status","reg_dt"}; // VO에 있는 결과값 맵핑 (VO에 있는 필드값을 Underscore 형태로 작성)
		
		SXSSFxlsxUtil xlsxUtil = new SXSSFxlsxUtil();
		try {
			xlsxUtil.init(sheetName, selectResultList, titleArray, keyArray, filePath, saveFileName, titleYn, title, res, combineYn);
			xlsxUtil.make();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ======================================== SXSSF를 이용한 Poi =======================================
		
		
//		// ======================================== XSSF를 이용한 Poi ========================================
//		//		엑셀생성 영역(s)
//		List selectResultList = resultList;	// 결과리스트 selectResultList에 설정
//		boolean titleYn = true;	// 엑셀파일 타이틀 존재여부
//		boolean combineYn = false; // 셀 병합 여부
//		String sheetName = "sheet";	// 엑셀파일 시트명
//		String title = "테스트 목록";
//		String saveFileName = "testBoardList.xlsx";	// 사용자에게 저장시킬 엑셀파일명
//		String filePath = EgovProperties.getProperty("Globals.filePath") + "excel/";	// 엑셀파일을 저장할 서버 경로
//		final String[] titleArray = {"No", "주문번호", "고객명", "주문상품", "주문수량", "주문상태", "등록일시"};	// 타이틀명 설정
//		final String[] keyArray = {"rno", "order_code", "user_name", "order_product", "order_cnt", "order_status","reg_dt"}; // VO에 있는 결과값 맵핑 (VO에 있는 필드값을 Underscore 형태로 작성)
//		
//		XlsxUtil xlsxUtil = new XlsxUtil();
//		try {
//			xlsxUtil.init(sheetName, selectResultList, titleArray, keyArray, filePath, saveFileName, titleYn, title, res, combineYn);
//			xlsxUtil.make();
//		} catch (InvalidFormatException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//		엑셀생성 영역(e)
//		// ======================================== XSSF를 이용한 Poi ========================================
	}
	
	/**
	 * 엑셀 업로드 팝업 호출
	 *
	 */
	@RequestMapping(value="/dashboard/selectExcelLayerPop.do")
	public String selectExcelLayerPop() {
		return "/dashboard/selectExcelLayerPop";
	}
	
	/**
	 * 엑셀 업로드 처리 Ajax
	 *
	 */
	@ResponseBody
	@RequestMapping(value="/dashboard/insertDashBoardExcelFile.do", produces="application/json; charset=utf-8;")
	public String insertDashBoardExcelFile(final MultipartHttpServletRequest multiReq) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		final Map<String,MultipartFile> files = multiReq.getFileMap();
		
		// 파일 정보가 있는 경우
		if(!files.isEmpty()) {
			// db 트렌젝션 + 오류 시 파일삭제를 위해 Service로 감싸기
			resultMap = dashboardService.insertDashBoardExcelUpload(files);
		}else {
			resultMap.put("status", "EMPTY");
			resultMap.put("msg", "업로드 하려는 파일이 비었습니다. 확인해주세요.");
		}
		
		return new Gson().toJson(resultMap);
	}
	
	/**
	 * 엑셀 업로드 양식 다운로드
	 * @throws Exception 
	 *
	 */
	@RequestMapping(value="/dashboard/DashBoardExcelFormDownload.do")
	public void DashBoardExcelFormDownload(HttpServletResponse res) throws Exception {
//		String uploadPath = resultList.getFilePath() + resultList.getFileNm();
		String uploadPath = "C://Users//겨ㅓㅇ로//Downloads/엑셀업로드테스트용.xls"; // 위에 주석된것처럼 풀패스가 필요
		File file = new File(uploadPath);
		
		// 기존버전
//		byte fileByte[] = FileUtils.readFileToByteArray(file);
//		
//		response.setContentType("application/octet-stream");
//		response.setContentLength(fileByte.length);
//		response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(resultList.getOrgFileNm(),"UTF-8")+"\";");
//		response.setHeader("Content-Transfer-Encoding", "binary");
//		response.getOutputStream().write(fileByte);
//		
//		response.getOutputStream().flush();
//		response.getOutputStream().close();
		
		if(file.exists()){
			EgovFormBasedFileUtil.downloadFileCustom(res, uploadPath, "엑셀업로드테스트용.xls");
		} else {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<script>alert('파일이 존재하지 않습니다.'); history.go(-1);</script>");
			out.flush();
		}
		
	}
	
	
}
