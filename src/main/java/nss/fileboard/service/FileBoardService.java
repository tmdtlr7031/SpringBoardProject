package nss.fileboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import nss.com.service.BoardFileVO;

public interface FileBoardService {
	
	public List<ComBoardVO> selectFileBoardList(ComBoardVO paramVO);

	public int selectFileBoardListCnt(ComBoardVO fileBoardVO);

	public Map<String, Object> insertComBoardFormAjax(Map<String, MultipartFile> files, ComBoardVO fileBoardVO);

	public ComBoardVO selectComBoardView(ComBoardVO fileBoardVO);

	public BoardFileVO selectFileBoardDownloadOne(BoardFileVO fileVO);

	public Map<String, Object> updateComBoardViewAjax(Map<String, MultipartFile> files, ComBoardVO fileBoardVO);

	public Map<String, Object> insertReplyAjax(CommentVO commentVO);

	public ComBoardVO selectComBoardViewAddReply(ComBoardVO fileBoardVO);

	public Map<String, Object> deleteReplyAjax(CommentVO commentVO);
}
