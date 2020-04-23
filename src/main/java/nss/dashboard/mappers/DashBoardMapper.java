package nss.dashboard.mappers;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import nss.dashboard.service.DashBoardVO;

/**
 * mapper 인터페이스방식으로 mybatis이용하기 (실제 db연결 테스트는 안해봄)
 *
 */
@Mapper(value="dashBoardMapper") // egov에서 제공하는 것으로 해당 어노테이션이 붙은 인터페이스만 검색(실제로 안돌려봐서 모르지만..안되면 특정 어노테이션만 붙은 인터페이스만 보도록 작성하자(context-mapper.xml)
public interface DashBoardMapper {
	
	public List<DashBoardVO> selectTestList(DashBoardVO dashBoardVO);
}
