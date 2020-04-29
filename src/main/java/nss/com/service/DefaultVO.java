package nss.com.service;

import java.io.Serializable;

import egovframework.com.cmm.service.EgovProperties;

/**
 * @Class Name : DefaultVO.java
 * @Description : VO의 기본이 되는 클래스로 공통부분을 정의하는 클래스
 * @Modification Information @ @ 수정일 수정자 수정내용 @ ------- --------
 *               --------------------------- @ 2009.03.03 박지욱 최초 생성
 *
 * @author 공통서비스 개발팀 박지욱
 * @since 2009.03.03
 * @version 1.0
 * @see
 *
 */
public class DefaultVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8244004534407653069L;

	/**
	 * 현재페이지
	 */
	private int pageIndex = 1;

	/**
	 * 페이지갯수
	 */
	private int pageUnit = 10;

	/**
	 * 페이지사이즈
	 */
	private int pageSize = 5;

	/**
	 * firstIndex
	 */
	private int firstIndex = 0;

	/**
	 * lastIndex
	 */
	private int lastIndex = 1;

	private int rno = 0;

	/**
	 * recordCountPerPage
	 */
	private int recordCountPerPage = 0;

	/** 조회조건 */
	private String searchType = "";

	/** 조회어 */
	private String searchKeyword = "";

	/** 등록자id */
	private String regId = "";

	/** 수정자id */
	private String modId = "";

	/** 사용유무 */
	private String useYn = "";

	/** 등록일자 */
	private String regDate = "";

	/** 수정일자 */
	private String modDate = "";

	/** 시작일자 */
	private String startDate = "";

	/** 종료일자 */
	private String endDate = "";
	
	/** 페이징 유무 */
	private String pgYn = "Y";
	
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageUnit() {
		return pageUnit;
	}

	public void setPageUnit(int pageUnit) {
		this.pageUnit = pageUnit;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstIndex() {
		return firstIndex;
	}

	public void setFirstIndex(int firstIndex) {
		this.firstIndex = firstIndex;
	}

	public int getLastIndex() {
		return lastIndex;
	}

	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	public int getRno() {
		return rno;
	}

	public void setRno(int rno) {
		this.rno = rno;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPgYn() {
		return pgYn;
	}

	public void setPgYn(String pgYn) {
		this.pgYn = pgYn;
	}
	
	
}
