package nss.fileboard.service;

import nss.com.service.DefaultVO;

public class CommentVO extends DefaultVO{
	
	/**
	 *  댓글 VO
	 */
	private static final long serialVersionUID = 1L;

	/* 댓글 일련번호 */
	private String replySeq;
	
	/* 게시판 구분코드 */
	private int replyGroup;
	
	/* 원본 게시물 일련번호 */
	private int orgBoardSeq;
	
	/* 댓글 순서 */
	private String replyGroupOdr;
	
	/* 댓글 depth */
	private String replyDepth;
	
	/* 댓글제목 */
	private String replyTitle;
	
	/* 댓글내용 */
	private String replyContent;
	
	/* 등록일시 */
	private String regDt;
	
	/* 수정일시 */
	private String modDt;

	public String getReplySeq() {
		return replySeq;
	}

	public void setReplySeq(String replySeq) {
		this.replySeq = replySeq;
	}

	public int getReplyGroup() {
		return replyGroup;
	}

	public void setReplyGroup(int replyGroup) {
		this.replyGroup = replyGroup;
	}

	public int getOrgBoardSeq() {
		return orgBoardSeq;
	}

	public void setOrgBoardSeq(int orgBoardSeq) {
		this.orgBoardSeq = orgBoardSeq;
	}

	public String getReplyGroupOdr() {
		return replyGroupOdr;
	}

	public void setReplyGroupOdr(String replyGroupOdr) {
		this.replyGroupOdr = replyGroupOdr;
	}

	public String getReplyDepth() {
		return replyDepth;
	}

	public void setReplyDepth(String replyDepth) {
		this.replyDepth = replyDepth;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getRegDt() {
		return regDt;
	}

	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}

	public String getModDt() {
		return modDt;
	}

	public void setModDt(String modDt) {
		this.modDt = modDt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
