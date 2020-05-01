package nss.fileboard.mappers;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import nss.fileboard.service.ComBoardVO;

@Mapper("fileboardMapper")
public interface FileBoardMapper {
	
	public List<ComBoardVO> selectFileBoardList(ComBoardVO paramVO);

	public int selectFileBoardListCnt(ComBoardVO paramVO);
}
