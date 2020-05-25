package nss.fileboard.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import nss.com.service.BoardFileVO;
import nss.dashboard.service.DashBoardVO;
import nss.fileboard.mappers.FileBoardMapper;
import nss.fileboard.service.FileBoardService;
import nss.fileboard.service.ComBoardVO;
import nss.fileboard.service.CommentVO;

@Service("fileboardService")
public class FileBoardServiceImpl extends EgovAbstractServiceImpl implements FileBoardService {

	@Resource(name="fileboardMapper")
	FileBoardMapper fileboardmapper;
	
	@Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
	
	
	@Override
	public List<ComBoardVO> selectFileBoardList(ComBoardVO paramVO) {
		return fileboardmapper.selectFileBoardList(paramVO);
	}

	@Override
	public int selectFileBoardListCnt(ComBoardVO paramVO) {
		return fileboardmapper.selectFileBoardListCnt(paramVO);
	}

	@Override
	public Map<String, Object> insertComBoardFormAjax(Map<String, MultipartFile> files, ComBoardVO boardParamVO) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<FileVO> fileList = null;
		
		try {
			
			fileList = fileUtil.parseFileInf(files, "", 0, "", "Globals.filePath", "");
			
			// 첨부파일없이 등록한 경우
			if (fileList.isEmpty() || fileList.size() == 0) {
				// 공통테이블 insert
				fileboardmapper.insertComBoardOne(boardParamVO);
				
			}else {
				
				fileboardmapper.insertComBoardOne(boardParamVO);
				
				// fileSeq의 값을 마지막값+1해야하기 때문에 bulk insert는 불가. 따라서 하나씩 insert
				for (FileVO fileVO : fileList) {
					BoardFileVO useAdd = new BoardFileVO();
					// 전체 파일 크기에 대한 유효성(view단에도 걸어주자) - 10mb미만으로 넣을 수 있게 10*1024*1000 (10mb)
//					fileVO.getFileMg()
					
					useAdd.setRegId(boardParamVO.getRegId());
					useAdd.setBoardCode(boardParamVO.getComBoardCode()); // 게시판 구분 코드
					useAdd.setBoardSeq(boardParamVO.getComBoardSeq()); // 원본 게시글 일련번호
//					useAdd.setFileSeq();  // 첨부파일 일련번호 (등록이라서 세팅할 필요없음)
					useAdd.setFilePath(fileVO.getFileStreCours()); // 첨부파일 경로
					useAdd.setOrgFileNm(fileVO.getOrignlFileNm().substring(0, fileVO.getOrignlFileNm().lastIndexOf("."))); // 첨부파일명(원본)
					useAdd.setFileNm(fileVO.getStreFileNm());// 첨부파일명(물리)
					useAdd.setFileExt(fileVO.getFileExtsn()); // 첨부파일 확장자
					fileboardmapper.insertComBoardFile(useAdd);	
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("status", "FAIL");
			returnMap.put("msg", "예기치 못한 오류가 발생하여 등록에 실패했습니다.");
			
			// 업로드 오류 발생 시 실제 파일은 날리기 위해
			for (FileVO fileVO : fileList) {
				File file = new File(fileVO.getFileStreCours()+fileVO.getStreFileNm()); // 첨부파일 경로 + 물리파일명
				if(file.exists()) {
					file.delete();
				}
			}
			return returnMap;
		}
		returnMap.put("status", "OK");
		returnMap.put("msg", "게시물 등록이 완료되었습니다.");
		
		return returnMap;
	}

	@Override
	public ComBoardVO selectComBoardView(ComBoardVO fileBoardVO) {
		return fileboardmapper.selectComBoardView(fileBoardVO);
	}

	@Override
	public BoardFileVO selectFileBoardDownloadOne(BoardFileVO fileVO) {
		return fileboardmapper.selectFileBoardDownloadOne(fileVO);
	}

	@Override
	public Map<String, Object> updateComBoardViewAjax(Map<String, MultipartFile> files, ComBoardVO fileBoardVO) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<FileVO> fileList = null;
		
		/*
		 * 등록처리든 수정처리든 Checked Exception인 경우 롤백이 되지않음. (rollback-for = exception을 작성해도..)
		 * 물론 에러를 잘못내서 그럴수도 있는데 일단 Unchecked Exception만 롤백이됨. 따라서 글은 작성되고 파일은 등록안되는 경우도 생길수도..? 근데 이런경우는
		 * 개발하면서 잡아야하는게 맞는듯. compile상 에러니..
		 *
		 **/
		
		try {
			fileList = fileUtil.parseFileInf(files, "", 0, "", "Globals.filePath", "");
			ComBoardVO selectVO = fileboardmapper.selectComBoardView(fileBoardVO);
			
			// 공통테이블 update (CASE 2 : 처음부터 첨부파일 없이 내용만 등록한 경우)
			fileboardmapper.updateComBoardOne(fileBoardVO);
			
			// 첨부파일 삭제 + 내용 수정 OR 처음부터 첨부파일 없이 내용만 등록한 경우
			if (fileList.isEmpty() || fileList.size() == 0) {
				
				// CASE 1 : 기존에 있던 실제 파일+데이터 삭제
				for (int i = 0; i < fileBoardVO.getAddFileList().size(); i++) {
					BoardFileVO boardfileVO = fileBoardVO.getAddFileList().get(i);
					
					if("Y".equals(boardfileVO.getUpdateYn())) {
						fileboardmapper.deleteComBoardOne(boardfileVO); // 특정 파일만 DB삭제
						
						for (BoardFileVO selectResultVO : selectVO.getAddFileList()) { // 특정 파일만 실제파일 삭제
							String compareCode = selectResultVO.getBoardCode();
							int compareSeq = selectResultVO.getBoardSeq();
							int compareFileSeq = selectResultVO.getFileSeq();
							
							if (compareCode.equals(boardfileVO.getBoardCode()) && compareSeq == boardfileVO.getBoardSeq() && compareFileSeq == boardfileVO.getFileSeq()) {
								File file = new File(selectResultVO.getFilePath()+selectResultVO.getFileNm());
								if (file.isFile()) {
									file.delete();
								}
							}
						}
					}
				}
				
			}else { // 첨부파일 있는 경우
				// 파일이 바뀐경우 기존파일 삭제
				FileAndDataDelete(fileBoardVO,selectVO);
				
				// 상세에서 신규(or 변경된 파일) 추가
				for (FileVO fileVO : fileList) {
					BoardFileVO useAdd = new BoardFileVO();
					useAdd.setRegId(fileBoardVO.getRegId());
					useAdd.setBoardCode(fileBoardVO.getComBoardCode()); // 게시판 구분 코드
					useAdd.setBoardSeq(fileBoardVO.getComBoardSeq()); // 원본 게시글 일련번호
					useAdd.setFilePath(fileVO.getFileStreCours()); // 첨부파일 경로
					useAdd.setOrgFileNm(fileVO.getOrignlFileNm().substring(0, fileVO.getOrignlFileNm().lastIndexOf("."))); // 첨부파일명(원본)
					useAdd.setFileNm(fileVO.getStreFileNm());// 첨부파일명(물리)
					useAdd.setFileExt(fileVO.getFileExtsn()); // 첨부파일 확장자
					fileboardmapper.insertComBoardFile(useAdd);	
				}
				
			}
 
			
			
		} catch (Exception e) {
			egovLogger.error(e.getMessage());
			returnMap.put("status", "FAIL");
			returnMap.put("msg", "예기치 못한 오류가 발생하여 등록에 실패했습니다.");
			
			// 업로드 오류 발생 시 실제 파일은 날리기 위해
			for (FileVO fileVO : fileList) {
				File file = new File(fileVO.getFileStreCours()+fileVO.getStreFileNm()); // 첨부파일 경로 + 물리파일명
				if(file.exists()) {
					file.delete();
				}
			}
			return returnMap;
		}
		
		returnMap.put("status", "OK");
		returnMap.put("msg", "게시물 수정이 완료되었습니다.");
		
		return returnMap;
	}
	
	private void FileAndDataDelete(ComBoardVO fileBoardVO, ComBoardVO selectVO) {
		for (int i = 0; i < fileBoardVO.getAddFileList().size(); i++) {
			BoardFileVO boardfileVO = fileBoardVO.getAddFileList().get(i);
			
			if("Y".equals(boardfileVO.getUpdateYn())) {
				fileboardmapper.deleteComBoardOne(boardfileVO); // 특정 파일만 DB삭제
				
				for (BoardFileVO selectResultVO : selectVO.getAddFileList()) { // 특정 파일만 실제파일 삭제
					String compareCode = selectResultVO.getBoardCode();
					int compareSeq = selectResultVO.getBoardSeq();
					int compareFileSeq = selectResultVO.getFileSeq();
					
					if (compareCode.equals(boardfileVO.getBoardCode()) && compareSeq == boardfileVO.getBoardSeq() && compareFileSeq == boardfileVO.getFileSeq()) {
						File file = new File(selectResultVO.getFilePath()+selectResultVO.getFileNm());
						if (file.isFile()) {
							file.delete();
						}
					}
				}
			}
		}
	}

	@Override
	public Map<String, Object> insertReplyAjax(CommentVO commentVO) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		 
		int resultCnt = fileboardmapper.insertReplyAjax(commentVO);
		
		if (resultCnt > 0) {
			returnMap.put("status", "OK");
		} else {
			returnMap.put("status", "FAIL");
		}
		return returnMap;
	}

	@Override
	public ComBoardVO selectComBoardViewAddReply(ComBoardVO fileBoardVO) {
		return fileboardmapper.selectComBoardViewAddReply(fileBoardVO);
	}

	@Override
	public Map<String, Object> deleteReplyAjax(CommentVO commentVO) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		int resultCnt = fileboardmapper.deleteReplyAjax(commentVO);
		
		if (resultCnt > 0) {
			returnMap.put("status", "OK");
		} else {
			returnMap.put("status", "FAIL");
		}
		
		return returnMap;
	}
	
}
