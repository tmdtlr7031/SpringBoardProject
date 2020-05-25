package nss.log.wlg.service;

import java.io.Serializable;

/**
 * @Class Name : WebLog.java
 * @Description : 웹 로그 관리를 위한 VO 클래스
 * @Modification Information
 *
 *    수정일         수정자         수정내용
 *    -------        -------     -------------------
 *    2009. 3. 11.   이삼섭         최초생성
 *    2011. 7. 01.   이기하         패키지 분리(sym.log -> sym.log.wlg)
 *    2011.09.14     서준식       화면에 검색일자를 표시하기위한 멤버변수 추가.
 * @author 공통 서비스 개발팀 이삼섭
 * @since 2009. 3. 11.
 * @version
 * @see
 *
 */
public class WebLog implements Serializable {

	private static final long serialVersionUID = -7768865822788140496L;

	/**
	 * 페이지 설명
	 */
	private String caotCont = "";

	/**
	 * 처리타입
	 */
	private String caotWk = "";

	/**
	 * ID
	 */
	private String id = "";

	/**
	 * IP
	 */
	private String ip = "";


	/**
	 * URL
	 */
	private String url = "";


	public String getCaotCont() {
		return caotCont;
	}

	public void setCaotCont(String caotCont) {
		this.caotCont = caotCont;
	}

	public String getCaotWk() {
		return caotWk;
	}

	public void setCaotWk(String caotWk) {
		this.caotWk = caotWk;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


}
