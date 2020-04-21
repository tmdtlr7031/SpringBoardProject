package nss.uat.uia.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.impl.EgovComAbstractDAO;
import four.system.service.FuncVO;
import four.system.service.MenuVO;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 DAO 클래스
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
@Repository("loginDAO")
public class LoginDAO extends EgovComAbstractDAO {
	// <TODO> 수정해야함

	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 * @exception Exception
	 */
    public LoginVO actionLogin(LoginVO vo) {
    	return (LoginVO)selectOne("LoginDAO.actionLogin", vo);
    }
    
    /**
	 * 마지막 로그인 일시
	 * @param usrId
	 * @return LoginVO
	 */
	public LoginVO selectLastLoginTime(String usrId) {
		return selectOne("LoginDAO.selectLastLoginTime", usrId);
	}
	
	/**
	 * 접속 로그 등록 처리
	 * @param resultVO LoginVO
	 * @return 
	 */
	public void insertLoginLog(LoginVO vo) {
		insert("LoginDAO.insertLoginLog", vo);
	}
	
	/**
	 * 메뉴 리스트 조회
	 * @param 
	 * @return List<MenuVO>
	 */
	public List<MenuVO> selectAdminMenuList() {
		return selectList("LoginDAO.selectAdminMenuList");
	}
	
	/**
	 * 메뉴 권한 리스트 조회
	 * @param roleCode
	 * @return List<MenuVO>
	 */
	public List<MenuVO> selectAdminAuthMenuList(String roleCode) {
		return selectList("LoginDAO.selectAdminAuthMenuList", roleCode);
	}

	/**
	 * 기능 권한 조회
	 * @param FuncVO
	 * @return FuncVO
	 */
	public FuncVO selectAdminFuncAuth(FuncVO funcVo) {
		return selectOne("LoginDAO.selectAdminFuncAuth", funcVo);
	}

	/**
	 * 로그인실패횟수 수정
	 * @param resultVO LoginVO
	 * @return
	 */
	public void updateFailCnt(LoginVO resultVO) {
		update("LoginDAO.updateFailCnt", resultVO);
	}
}
