package nss.dynamicList.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import nss.dashboard.service.DashBoardService;
import nss.dashboard.service.DashBoardVO;

@Controller
public class DynamicListController {
	
	@Resource(name="dashboardService")
	private DashBoardService dashboardService;
	
	@RequestMapping(value="/dynamicList/list/selectDynamicBoardList.do")
	public String selectDynamicBoardList(@ModelAttribute("comonVO")DashBoardVO dashBoardVO, Model model) {
		/* 페이징 */
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(dashBoardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(dashBoardVO.getPageUnit());
		paginationInfo.setPageSize(dashBoardVO.getPageSize());

//		dashBoardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
//		dashBoardVO.setLastIndex(paginationInfo.getLastRecordIndex());
//		dashBoardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
//		
//		/* 조회 */
//		List<DashBoardVO> resultList = dashboardService.selectTestList(dashBoardVO);
//		paginationInfo.setTotalRecordCount(dashboardService.selectTestListCnt(dashBoardVO));
//		
		model.addAttribute("paginationInfo",paginationInfo);
//		model.addAttribute("resultList",resultList);
		
		return "/dynamicList/selectDynamicBoardList";
	}
	
	@RequestMapping(value="/dynamicList/list/selectDynamicBoardListAjax.do")
	public String insertDashBoardExcelFile(@ModelAttribute("comonVO")DashBoardVO dashBoardVO, Model model) {
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
		
		return "/dynamicList/selectDynamicBoardListTemplete";
	}
}
