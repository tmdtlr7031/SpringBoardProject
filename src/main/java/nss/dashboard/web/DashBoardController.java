package nss.dashboard.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
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
//			resultList.get(i).setTgtCntStr(StringUtil.getThousandCommaSeperator(resultList.get(i).getTgtCnt())); 천단위 구분기호 쓴다면..
		}
		
		//		엑셀생성 영역(s)
		List selectResultList = resultList;	// 결과리스트 selectResultList에 설정
		boolean titleYn = true;	// 엑셀파일 타이틀 존재여부
		boolean combineYn = false; // 셀 병합 여부
		String sheetName = "sheet";	// 엑셀파일 시트명
		String title = "테스트 목록";
		String saveFileName = "testBoardList.xlsx";	// 사용자에게 저장시킬 엑셀파일명
		String filePath = EgovProperties.getProperty("Globals.filePath") + "excel/";	// 엑셀파일을 저장할 서버 경로
		final String[] titleArray = {"No", "주문번호", "고객명", "주문상품", "주문수량", "주문상태", "등록일시"};	// 타이틀명 설정
		final String[] keyArray = {"rno", "order_code", "user_name", "order_product", "order_cnt", "order_status","reg_dt"}; // VO에 있는 결과값 맵핑 (VO에 있는 필드값을 Underscore 형태로 작성)
		
		XlsxUtil xlsxUtil = new XlsxUtil();
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
		//		엑셀생성 영역(e)
	}
	
	@RequestMapping(value="/dashboard/selectExcelLayerPop.do")
	public String selectExcelLayerPop() {
		return "/dashboard/selectExcelLayerPop";
	}
}
