package nss.log.wlg.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import nss.log.wlg.service.EgovWebLogService;
import nss.log.wlg.service.WebLog;

/**
 * @Class Name : EgovWebLogServiceImpl.java
 * @Description : 웹로그 관리를 위한 서비스 구현 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.wlg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
@Service("EgovWebLogService")
public class EgovWebLogServiceImpl extends EgovAbstractServiceImpl implements
	EgovWebLogService {

	@Resource(name="webLogDAO")
	private WebLogDAO webLogDAO;

	/**
	 * 웹 로그를 기록한다.
	 *
	 * @param WebLog
	 */
	@Override
	public void logInsertWebLog(WebLog webLog) {

		webLogDAO.logInsertWebLog(webLog);
	}


}
