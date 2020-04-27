package nss.dashboard.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import nss.dashboard.mappers.DashBoardMapper;
import nss.dashboard.service.DashBoardService;
import nss.dashboard.service.DashBoardVO;

@Service("dashboardService")
public class DashBoardServiceImpl implements DashBoardService{

	// mapper inject (@Service에서 주입해서 쓰자)
	@Resource(name="dashBoardMapper")
	DashBoardMapper dashBoardMapper;
	
	
	@Override
	public List<DashBoardVO> selectTestList(DashBoardVO dashBoardVO) {
		return dashBoardMapper.selectTestList(dashBoardVO);
	}

	@Override
	public int selectTestListCnt(DashBoardVO dashBoardVO) {
		return dashBoardMapper.selectTestListCnt(dashBoardVO);
	}
}
