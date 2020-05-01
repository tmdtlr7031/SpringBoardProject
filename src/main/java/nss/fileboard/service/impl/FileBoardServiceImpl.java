package nss.fileboard.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import nss.fileboard.mappers.FileBoardMapper;
import nss.fileboard.service.FileBoardService;
import nss.fileboard.service.ComBoardVO;

@Service("fileboardService")
public class FileBoardServiceImpl implements FileBoardService {

	@Resource(name="fileboardMapper")
	FileBoardMapper fileboardmapper;
	
	
	
	
	@Override
	public List<ComBoardVO> selectFileBoardList(ComBoardVO paramVO) {
		return fileboardmapper.selectFileBoardList(paramVO);
	}

	@Override
	public int selectFileBoardListCnt(ComBoardVO paramVO) {
		return fileboardmapper.selectFileBoardListCnt(paramVO);
	}
	
}
