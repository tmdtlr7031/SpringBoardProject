package nss.dashboard.mappers;

import java.util.List;


import egovframework.rte.psl.dataaccess.mapper.Mapper;
import nss.dashboard.service.DashBoardVO;

/**
 * mapper 인터페이스방식으로 mybatis이용하기
 *
 */
@Mapper("dashBoardMapper") // egov에서 제공하는 것으로 해당 어노테이션이 붙은 인터페이스만 검색
public interface DashBoardMapper {
	
	public List<DashBoardVO> selectTestList(DashBoardVO dashBoardVO);
	public int selectTestListCnt(DashBoardVO dashBoardVO);
	public int insertDashBoardOne(DashBoardVO dashBoardVO);
	public int insertDashBoardAll(DashBoardVO paramVO);
	public int selectGetBoardSeq(String param);
}
