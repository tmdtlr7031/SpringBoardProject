package nss.uat.uia.web;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.EgovWebUtil;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import nss.common.util.enc.SHA256;
import four.system.service.MenuVO;
import four.system.service.SystemService;
import nss.uat.uia.service.EgovLoginService;

/**
 * 로그인관련 컨트롤러 클래스
 *
 * @author 신승택
 * @since 2018.03.28
 * @version 1.0
 * @see
 *
 *      <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2018.03.28  신승택          최초 생성
 *
 *      </pre>
 */
@Controller
public class EgovLoginController {

	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;
	
	@Resource(name = "systemService")
	private SystemService systemService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	/** log */
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovLoginController.class);

	/**
	 * 로그인 페이지
	 *
	 * @return
	 */
	@RequestMapping(value = "/uat/uia/egovLoginUsr.do")
	public String loginForm(@ModelAttribute("searchVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) {
		
		String clientIp = request.getRemoteAddr();
		
		List<LoginVO> ipList = systemService.selectUseAccessIpList();

		if(!EgovWebUtil.isAccessIp(ipList, clientIp)) {	
			return "redirect:/accessDeny.do";
		}
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		//로그인  된 사용자의 경우 메인페이지로 이동
		if (isAuthenticated) {
			return "redirect:/main.do";
		}

		return "egovframework/com/uat/uia/EgovLoginUsr";
	}

	/**
	 * 일반(세션) 로그인을 처리한다
	 *
	 * @param vo
	 *            - 아이디, 비밀번호가 담긴 LoginVO
	 * @param request
	 *            - 세션처리를 위한 HttpServletRequest
	 * @return result - 로그인결과(세션정보)
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value = "/actionLogin.do")
	public String actionLogin(@ModelAttribute("searchVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) throws NoSuchAlgorithmException {

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		//로그인  된 사용자의 경우 메인페이지로 이동
		if (isAuthenticated) {
			return "redirect:/main.do";
		}

		if ("".equals(loginVO.getUsrId())) {
			model.put("message", "아이디를 입력하지 않았습니다.");
			model.put("action", "/uat/uia/egovLoginUsr.do");
			model.put("function", " $(document).ready(function(){ document.frm.submit(); }); ");
			return "process";
		}

		LoginVO resultVO = loginService.actionLogin(loginVO);

		
		if (resultVO != null) {
			if(resultVO.getFailCnt() < 5) {
				loginVO.setUsrPwd(SHA256.encryptSHA256(loginVO.getUsrPwd(), loginVO.getUsrId()));
	
				if (resultVO.getUsrPwd().equals(loginVO.getUsrPwd())) {
					List<MenuVO> menues = loginService.selectAdminAuthMenuList(resultVO.getRoleCode());
					if(menues.size() == 0) return "egovframework/error/accessDeny";
					
					resultVO.setFailCnt(0);
					loginService.updateFailCnt(resultVO);
					LOGGER.info("로그인성공 FailCnt 초기화");
					
					// 업체별 사용자 로그인일 경우 Y 
					if( "ROLE_COMPANY_USER".equals(resultVO.getRoleCode()) ) {
						resultVO.setCompanyUseCode("Y");
					} else {
						resultVO.setCompanyUseCode("N");
					}
					
					// 로그인 정보를 세션에 저장
					request.getSession().setAttribute("ADMIN", resultVO);
	
					// 세션에서 가져온 값
					model.put("useYn", resultVO.getUseYn()); // 사용여부
					model.put("regdate", resultVO.getRegDt()); // 가입년월일
					model.put("usrId", resultVO.getUsrId()); // 관리자 아이디
					model.put("usrName", resultVO.getUsrName()); // 관리자 이름
					model.put("usrSeq", resultVO.getUsrSeq()); // 사용자고유번호
					model.put("success", "Y"); // 로그인 성공 여부
					
					String ip = request.getHeader("X-FORWARDED-FOR");
			        if (ip == null || ip.length() == 0) {
			            ip = request.getHeader("Proxy-Client-IP");
			        }
			        if (ip == null || ip.length() == 0) {
			            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
			        }
			        if (ip == null || ip.length() == 0) {
			            ip = request.getRemoteAddr();
			        }
					resultVO.setIp(ip);
					loginService.insertLoginLog(resultVO);
					
					model.put("action", "/main.do");
	
				} else {
					if(resultVO.getFailCnt() == 4) {
						resultVO.setFailCnt(5);
					} else {
						resultVO.setFailCnt(1);
					}
					loginService.updateFailCnt(resultVO);
					LOGGER.info("로그인실패 FailCnt up");
					model.put("message", "로그인 정보를 확인하여 주세요.");
					model.put("action", "/uat/uia/egovLoginUsr.do");
				}
			} else {
				LOGGER.info("로그인실패 FailCnt=5 계정잠김");
				model.put("message", "비밀번호 5회 이상 실패하여 잠겼습니다.");
				model.put("action", "/uat/uia/egovLoginUsr.do");
			}
		} else {
			model.put("message", "로그인 정보를 확인하여 주세요.");
			model.put("action", "/uat/uia/egovLoginUsr.do");
		}

		model.put("function", "$(document).ready(function(){ document.frm.submit(); });");
		return "process";
	}

	/**
	 * 로그아웃한다.
	 *
	 * @return String
	 * @exception Exception
	 */
	@RequestMapping(value = "/actionLogout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) {

		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/uat/uia/egovLoginUsr.do";
		}

		request.getSession().setAttribute("ADMIN", null);

		model.put("message", "정상적으로 로그아웃 하셨습니다.");
		model.put("action", "/uat/uia/egovLoginUsr.do");
		model.put("function", " $(document).ready(function(){ document.frm.submit(); }); ");
		return "process";
	}

}
