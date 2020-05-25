package nss.fileboard.web;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.Gson;

import egovframework.com.utl.fcc.service.EgovFormBasedFileUtil;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import nss.fileboard.service.FileBoardService;
import nss.com.service.BoardFileVO;
import nss.fileboard.service.ComBoardVO;
import nss.fileboard.service.CommentVO;

@Controller
public class FileBoardController {
	
	@Resource(name="fileboardService")
	private FileBoardService fileboardservice;

	
	@RequestMapping(value="/fileboard/{typeChk}/selectFileBoardList.do")
	public String selectFileBoardList (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, Model model, @PathVariable("typeChk") String typeCheck) {
		
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
		model.addAttribute("typeCheck", typeCheck);
		
		return "/fileboard/selectFileBoardList"; // 파일첨부용 페이지
	}
	
	@RequestMapping(value="/fileboard/selectComBoardForm.do")
	public String selectComBoardForm (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, Model model) {
		return "/fileboard/selectComBoardForm";
	}
	
	@ResponseBody
	@RequestMapping(value="/fileboard/insertComBoardFormAjax.do", produces="application/json; charset=utf-8;")
	public String insertComBoardFormAjax (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, final MultipartHttpServletRequest multiReq) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final Map<String,MultipartFile> files = multiReq.getFileMap();
		
		// 임시 
		fileBoardVO.setComBoardCode("01"); //게시판 구분코드
		fileBoardVO.setRegId("test"); //로그인 ID
		
		resultMap = fileboardservice.insertComBoardFormAjax(files, fileBoardVO);
		
		return new Gson().toJson(resultMap);
	}
	
	@RequestMapping(value="/fileboard/selectComBoardView.do")
	public String selectComBoardView (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, Model model) {
		
		// 해당 게시물의 첨부파일을 상세페이지에서 다운로드 받을 수 있게 하기 위해 resultMap 사용
		ComBoardVO result = fileboardservice.selectComBoardView(fileBoardVO);
		
		model.addAttribute("result",result);
		return "/fileboard/selectComBoardView";
	}
	
	/**
	 * 사용자 페이지 느낌나게 댓글 대댓글 페이지
	 * 
	 * */
	@RequestMapping(value="/fileboard/selectComBoardViewForUser.do")
	public String selectComBoardViewForUser (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, Model model, HttpSession session) {
		
		String tempLoginUser = (String) session.getAttribute("loginUserId");
		
		ComBoardVO result = fileboardservice.selectComBoardViewAddReply(fileBoardVO);
		
		model.addAttribute("result",result);
		model.addAttribute("tempLoginUser",tempLoginUser);
		return "/fileboard/selectComBoardViewForUser";
	}
	
	@RequestMapping(value="/fileboard/ComBoardFileDownload.do")
	public void ComBoardFileDownload(BoardFileVO fileVO, HttpServletResponse res) throws Exception {
		
		BoardFileVO resultVO = fileboardservice.selectFileBoardDownloadOne(fileVO);
		
		String uploadPath = resultVO.getFilePath() + resultVO.getFileNm();
		String orgFileName = resultVO.getOrgFileNm() + "." + resultVO.getFileExt();
		File file = new File(uploadPath);
		
		if(file.exists()){
			EgovFormBasedFileUtil.downloadFileCustom(res, uploadPath, orgFileName);
		} else {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html; charset=UTF-8");
			PrintWriter out = res.getWriter();
			out.println("<script>alert('파일이 존재하지 않습니다.'); history.go(-1);</script>");
			out.flush();
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="/fileboard/updateComBoardViewAjax.do",produces="application/json; charset=utf-8;")
	public String updateComBoardViewAjax (@ModelAttribute("comonVO")ComBoardVO fileBoardVO, final MultipartHttpServletRequest multiReq) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		final Map<String,MultipartFile> files = multiReq.getFileMap();
		
		resultMap = fileboardservice.updateComBoardViewAjax(files, fileBoardVO);
		
		return new Gson().toJson(resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value="/fileboard/insertReplyAjax.do",produces="application/json; charset=utf-8;")
	public String insertReplyAjax (@ModelAttribute("comonVO")CommentVO commentVO, HttpSession session) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String user = (String) session.getAttribute("loginUserId");
		commentVO.setRegUser(user);
		
		resultMap = fileboardservice.insertReplyAjax(commentVO);
		
		return new Gson().toJson(resultMap);
	}
	
	@ResponseBody
	@RequestMapping(value="/fileboard/deleteReplyAjax.do",produces="application/json; charset=utf-8;")
	public String deleteReplyAjax (@ModelAttribute("comonVO")CommentVO commentVO) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap = fileboardservice.deleteReplyAjax(commentVO);
		
		return new Gson().toJson(resultMap);
	}
	
}
