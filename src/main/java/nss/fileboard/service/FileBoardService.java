package nss.fileboard.service;

import java.util.List;

public interface FileBoardService {
	
	public List<ComBoardVO> selectFileBoardList(ComBoardVO paramVO);

	public int selectFileBoardListCnt(ComBoardVO fileBoardVO);
}
