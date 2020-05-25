package nss.log.wlg.service.impl;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import nss.log.wlg.service.WebLog;

/**
 * @Class Name : WebLogDAO.java
 * @Description : 웹로그 관리를 위한 데이터 접근 클래스
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
@Repository("webLogDAO")
public class WebLogDAO extends EgovComAbstractDAO {

	/**
	 * 웹 로그를 기록한다.
	 *
	 * @param WebLog
	 * @return
	 * @throws Exception
	 */
	public void logInsertWebLog(WebLog webLog) {
		insert("WebLogDAO.logInsertWebLog", webLog);
	}


}
