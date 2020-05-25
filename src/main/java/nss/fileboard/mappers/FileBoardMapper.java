package nss.fileboard.mappers;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import nss.com.service.BoardFileVO;
import nss.fileboard.service.ComBoardVO;
import nss.fileboard.service.CommentVO;

@Mapper("fileboardMapper")
public interface FileBoardMapper {
	
	public List<ComBoardVO> selectFileBoardList(ComBoardVO paramVO);

	public int selectFileBoardListCnt(ComBoardVO paramVO);

	public void insertComBoardOne(ComBoardVO boardParamVO);

	public void insertComBoardFile(BoardFileVO fileParamVO);

	public ComBoardVO selectComBoardView(ComBoardVO fileBoardVO);

	public BoardFileVO selectFileBoardDownloadOne(BoardFileVO fileVO);

	public void updateComBoardOne(ComBoardVO fileBoardVO);

	public void deleteComBoardOne(BoardFileVO boardfileVO);

	public int insertReplyAjax(CommentVO commentVO);

	public ComBoardVO selectComBoardViewAddReply(ComBoardVO fileBoardVO);

	public int deleteReplyAjax(CommentVO commentVO);

}
