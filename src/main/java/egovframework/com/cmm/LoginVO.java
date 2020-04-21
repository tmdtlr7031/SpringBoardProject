package egovframework.com.cmm;

import nss.com.service.DefaultVO;

/**
 * @Class Name : LoginVO.java
 * @Description : Login VO class
 * @Modification Information @ @ 수정일 수정자 수정내용 @ ------- --------
 *               --------------------------- @ 2009.03.03 박지욱 최초 생성
 *
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.03
 * @version 1.0
 * @see
 *
 */
public class LoginVO extends DefaultVO {

	/**
	 *
	 */
	private static final long serialVersionUID = -8274004534207618049L;

	/** 시퀀스 */
	private int usrSeq = 0;
	/** 아이디 */
	private String usrId = "";
	/** 이름 */
	private String usrName = "";
	/** 비밀번호 */
	private String usrPwd = "";
	/** 권한설정 */
	private String roleCode = "";
	/** 사용자 IP정보 */
	private String ip = "";
	/** 등록일자 */
	private String regDt = "";
	/** 로그일련번호 */
	private int logSeq = 0;
	/** 로그인시간 */
	private String logDt = "";
	/** 접속IP일련번호 */
	private int ipSeq = 0;
	/** 로그인실패횟수 */
	private int failCnt = 0;
	/** 업체별 기능 관리 */
	private String companyUseCode = "";

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getUsrSeq() {
		return usrSeq;
	}

	public void setUsrSeq(int usrSeq) {
		this.usrSeq = usrSeq;
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

	public String getUsrPwd() {
		return usrPwd;
	}

	public void setUsrPwd(String usrPwd) {
		this.usrPwd = usrPwd;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public int getLogSeq() {
		return logSeq;
	}

	public void setLogSeq(int logSeq) {
		this.logSeq = logSeq;
	}

	public String getLogDt() {
		return logDt;
	}

	public void setLogDt(String logDt) {
		this.logDt = logDt;
	}

	public int getIpSeq() {
		return ipSeq;
	}

	public void setIpSeq(int ipSeq) {
		this.ipSeq = ipSeq;
	}

	public int getFailCnt() {
		return failCnt;
	}

	public void setFailCnt(int failCnt) {
		this.failCnt = failCnt;
	}

	public String getCompanyUseCode() {
		return companyUseCode;
	}

	public void setCompanyUseCode(String companyUseCode) {
		this.companyUseCode = companyUseCode;
	}

}
