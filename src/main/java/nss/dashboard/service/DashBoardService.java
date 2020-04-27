package nss.dashboard.service;

import java.util.List;

public interface DashBoardService {
	
	public List<DashBoardVO> selectTestList(DashBoardVO dashBoardVO);

	public int selectTestListCnt(DashBoardVO dashBoardVO);

}
