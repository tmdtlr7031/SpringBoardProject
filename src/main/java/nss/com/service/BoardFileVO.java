package nss.com.service;

import java.io.Serializable;

public class BoardFileVO implements Serializable {
	
	/* 게시판 구분 코드*/
	private String boardCode;
	
	/* 원본 게시글 일련번호*/
	private Integer boardSeq;
	
	/* 첨부파일 일련번호*/
	private Integer fileSeq;
	
	/* 첨부파일 경로*/
	private String filePath;
	
	/* 첨부파일명(원본)*/
	private String orgFileNm;
	
	/* 첨부파일명(물리)*/
	private String fileNm;
	
	/* 첨부파일 확장자명*/
	private String fileExt;
	
	/* 등록자*/
	private String regId;
	
	/* 등록일시*/
	private String regDt;
	
	/* Flag*/
	private String updateYn;


	public String getBoardCode() {
		return boardCode;
	}

	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}

	public Integer getBoardSeq() {
		return boardSeq;
	}

	public void setBoardSeq(Integer boardSeq) {
		this.boardSeq = boardSeq;
	}

	public Integer getFileSeq() {
		return fileSeq;
	}

	public void setFileSeq(Integer fileSeq) {
		this.fileSeq = fileSeq;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getOrgFileNm() {
		return orgFileNm;
	}

	public void setOrgFileNm(String orgFileNm) {
		this.orgFileNm = orgFileNm;
	}

	public String getFileNm() {
		return fileNm;
	}

	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getUpdateYn() {
		return updateYn;
	}

	public void setUpdateYn(String updateYn) {
		this.updateYn = updateYn;
	}
}
