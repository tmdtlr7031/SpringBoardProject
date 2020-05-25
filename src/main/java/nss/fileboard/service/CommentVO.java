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
	private String replyGroup;
	
	/* 원본 게시물 일련번호 */
	private int orgBoardSeq;
	
	/* 댓글 순서 */
	private String replyGroupOdr;
	
	/* 댓글 depth */
	private String replyDepth;
	
	/* 댓글내용 */
	private String replyContent;
	
	/* 등록자 */
	private String regUser;
	
	/* 등록일시 */
	private String regDateTime;
	
	/* 수정자 */
	private String modUser;
	
	/* 수정일시 */
	private String modDateTime;

	
	public String getReplySeq() {
		return replySeq;
	}

	public void setReplySeq(String replySeq) {
		this.replySeq = replySeq;
	}

	public String getReplyGroup() {
		return replyGroup;
	}

	public void setReplyGroup(String replyGroup) {
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

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getRegUser() {
		return regUser;
	}

	public void setRegUser(String regUser) {
		this.regUser = regUser;
	}

	public String getRegDateTime() {
		return regDateTime;
	}

	public void setRegDateTime(String regDateTime) {
		this.regDateTime = regDateTime;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public String getModDateTime() {
		return modDateTime;
	}

	public void setModDateTime(String modDateTime) {
		this.modDateTime = modDateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
