package nss.log.wlg.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import nss.log.wlg.service.EgovWebLogService;
import nss.log.wlg.service.WebLog;
//import nss.system.service.FuncVO;
//import nss.system.service.SystemService;
import nss.uat.uia.service.EgovLoginService;

/**
 * @Class Name : EgovWebLogInterceptor.java
 * @Description : 웹로그 생성을 위한 인터셉터 클래스
 * @Modification Information
 *
 *    수정일        수정자         수정내용
 *    -------      -------     -------------------
 *    2009. 3. 9.   이삼섭         최초생성
 *    2011. 7. 1.   이기하         패키지 분리(sym.log -> sym.log.wlg)
 *
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 9.
 * @version
 * @see
 *
 */
public class EgovWebLogInterceptor extends HandlerInterceptorAdapter {

	@Resource(name="EgovWebLogService")
	private EgovWebLogService webLogService;
	
	@Resource(name = "loginService")
	private EgovLoginService loginService;
	
	@Resource(name = "systemService")
	private SystemService systemService;

	/**
	 * 웹 로그정보를 생성한다.
	 *
	 * @param HttpServletRequest request, HttpServletResponse response, Object handler
	 * @return
	 * @throws IOException 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

		WebLog webLog = new WebLog();
		String reqURL = request.getRequestURI();
		String reqServletPath = request.getServletPath();
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if(!isAuthenticated){
			response.sendRedirect(request.getContextPath()+"/uat/uia/egovLoginUsr.do");
			return false;
		}
		
		//허용되지 않은 ip 에서 접근시 접근제한 페이지로 이동
		String clientIp = request.getRemoteAddr();
		
		List<LoginVO> ipList = systemService.selectUseAccessIpList();

		if(!EgovWebUtil.isAccessIp(ipList, clientIp)) {
			response.sendRedirect(request.getContextPath()+"/accessDeny.do");
			return false;
		}
		
		
	    if( reqURL.indexOf("List.do") > -1 ){
	    	webLog.setCaotWk("L");
	    }else if( reqURL.indexOf("Excel.do") > -1 ){
	    	webLog.setCaotWk("E");
	    }else if( reqURL.indexOf("View") > -1 ){
	    	webLog.setCaotWk("V");
	    }else if( reqURL.indexOf("insert") > -1 ){
	    	webLog.setCaotWk("I");
	    }else if( reqURL.indexOf("update") > -1 ){
	    	webLog.setCaotWk("U");
	    }else if( reqURL.indexOf("delete") > -1 ){
	    	webLog.setCaotWk("D");
	    }
		
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		webLog.setUrl(reqURL.replaceFirst(request.getContextPath(), ""));
		webLog.setId(user.getUsrId());
		webLog.setIp(request.getRemoteAddr());
		
		//기능별 권한 체크
		FuncVO param = new FuncVO();
		param.setFuncUrl(reqServletPath);
		param.setRoleCode(user.getRoleCode());
		
		FuncVO authResult = loginService.selectAdminFuncAuth(param);
		
		boolean authCheck = false;
		
		if(authResult != null) {
			if("C".equals(authResult.getCrud())) {
				if("Y".equals(authResult.getInstYn())) {
					authCheck = true;
				}
			} else if("R".equals(authResult.getCrud())) {
				if("Y".equals(authResult.getSeltYn())) {
					authCheck = true;
				}
			} else if("U".equals(authResult.getCrud())) {
				if("Y".equals(authResult.getUpdtYn())) {
					authCheck = true;
				}
			} else if("D".equals(authResult.getCrud())) {
				if("Y".equals(authResult.getDeltYn())) {
					authCheck = true;
				}
			}
			
			request.setAttribute("authResult", authResult);
		}
		
		if(!authCheck) {
			response.sendRedirect(request.getContextPath()+"/accessDeny.do");
			return false;
		}

		if(!reqURL.contains(".jsp") && (webLog.getCaotWk() != null && !"".equals(webLog.getCaotWk()) )) {
			webLogService.logInsertWebLog(webLog);
		}
		
		return true;
	}
	
}
