package nss.fileboard.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import nss.fileboard.service.FileBoardService;
import nss.dashboard.service.DashBoardVO;
import nss.fileboard.service.ComBoardVO;

@Controller
public class FileBoardController {
	
	@Resource(name="fileboardService")
	private FileBoardService fileboardservice;

	
	@RequestMapping(value="/fileboard/selectFileBoardList.do")
	public String selectFileBoardList (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, Model model) {
		
		/* 페이징 */
		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(fileBoardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(fileBoardVO.getPageUnit());
		paginationInfo.setPageSize(fileBoardVO.getPageSize());

		fileBoardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		fileBoardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		fileBoardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		
		/* 조회 */
		List<ComBoardVO> resultList = fileboardservice.selectFileBoardList(fileBoardVO);
		paginationInfo.setTotalRecordCount(fileboardservice.selectFileBoardListCnt(fileBoardVO));
		
		model.addAttribute("paginationInfo",paginationInfo);
		model.addAttribute("resultList",resultList);
		
		return "/fileboard/selectFileBoardList";
	}
	
	@RequestMapping(value="/fileboard/selectComBoardForm.do")
	public String selectComBoardForm (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, Model model) {
		return "/fileboard/selectComBoardForm";
	}
	
}
