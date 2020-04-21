package nss.uat.uia.service;

import java.util.List;

import egovframework.com.cmm.LoginVO;
import four.system.service.FuncVO;
import four.system.service.MenuVO;

/**
 * 일반 로그인, 인증서 로그인을 처리하는 비즈니스 인터페이스 클래스
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
public interface EgovLoginService {
	// <TODO> 수정해야함
	
	/**
	 * 일반 로그인을 처리한다
	 * @param vo LoginVO
	 * @return LoginVO
	 */
    LoginVO actionLogin(LoginVO vo);
    
    /**
	 * 마지막 로그인 일시
	 * @param usrId
	 * @return LoginVO
	 */
	LoginVO selectLastLoginTime(String usrId);
	
	/**
	 * 접속 로그 등록 처리
	 * @param resultVO LoginVO
	 * @return 
	 */
	void insertLoginLog(LoginVO resultVO);
	
	/**
	 * 메뉴 리스트 조회
	 * @param 
	 * @return List<MenuVO>
	 */
	List<MenuVO> selectAdminMenuList();
	
	/**
	 * 메뉴 권한 리스트 조회
	 * @param roleCode
	 * @return List<MenuVO>
	 */
	List<MenuVO> selectAdminAuthMenuList(String roleCode);
	
	/**
	 * 기능 권한 조회
	 * @param FuncVO
	 * @return FuncVO
	 */
	FuncVO selectAdminFuncAuth(FuncVO funcVo);

	/**
	 * 로그인실패횟수 수정
	 * @param resultVO LoginVO
	 * @return
	 */
	void updateFailCnt(LoginVO resultVO);
}
