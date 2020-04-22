package nss.uat.uia.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.com.cmm.LoginVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
//import four.system.service.FuncVO;
//import four.system.service.MenuVO;
import nss.uat.uia.service.EgovLoginService;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 구현 클래스
 *
 * @author 운영지원팀 강종빈
 * @since 2018.01.25
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *  2018.02.21	강종빈		최초 생성
 *  </pre>
 */
@Service("loginService")
public class EgovLoginServiceImpl extends EgovAbstractServiceImpl implements EgovLoginService {

    @Resource(name="loginDAO")
    private LoginDAO loginDAO;

    /**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 */
    @Override
	public LoginVO actionLogin(LoginVO vo) {

    	// 1. 아이디와 암호화된 비밀번호가 DB와 일치하는지 확인한다.
    	LoginVO loginVO = loginDAO.actionLogin(vo);

    	return loginVO;
    }
    
    /**
	 * 마지막 로그인 일시
	 * @param usrId
	 * @return LoginVO
	 */
	@Override
	public LoginVO selectLastLoginTime(String usrId) {
		return loginDAO.selectLastLoginTime(usrId);
	}

	/**
	 * 접속 로그 등록 처리
	 * @param resultVO LoginVO
	 * @return 
	 */
	@Override
	public void insertLoginLog(LoginVO vo) {
		loginDAO.insertLoginLog(vo);
	}

	/**
	 * 메뉴 리스트 조회
	 * @param 
	 * @return List<MenuVO>
	 */
//	@Override
//	public List<MenuVO> selectAdminMenuList() {
//		return loginDAO.selectAdminMenuList();
//	}

	/**
	 * 메뉴 권한 리스트 조회
	 * @param roleCode
	 * @return List<MenuVO>
	 */
//	@Override
//	public List<MenuVO> selectAdminAuthMenuList(String roleCode) {
//		return loginDAO.selectAdminAuthMenuList(roleCode);
//	}

	/**
	 * 기능 권한 조회
	 * @param FuncVO
	 * @return FuncVO
	 */
//	@Override
//	public FuncVO selectAdminFuncAuth(FuncVO funcVo) {
//		return loginDAO.selectAdminFuncAuth(funcVo);
//	}

	/**
	 * 로그인실패횟수 수정
	 * @param resultVO LoginVO
	 * @return
	 */
	@Override
	public void updateFailCnt(LoginVO resultVO) {
		loginDAO.updateFailCnt(resultVO);
	}
}
