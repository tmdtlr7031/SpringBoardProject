package nss.fileboard.service;

import java.util.ArrayList;
import java.util.List;

import nss.com.service.BoardFileVO;
import nss.com.service.DefaultVO;

public class ComBoardVO extends DefaultVO{
	
	/**
	 * 공통 게시판 VO
	 */
	private static final long serialVersionUID = 1L;

	/* 게시판 구분 코드 */
	private String comBoardCode;
	
	/* 게시물 일련번호 */
	private int comBoardSeq;
	
	/* 게시글 제목 */
	private String boardTitle;
	
	/* 게시글 내용 */
	private String boardContent;
	
	/* 등록일시 */
	private String regDt;
	
	/* 수정일시 */
	private String modDt;

	/* 해당글의 댓글 */
	private List<CommentVO> replyList = new ArrayList<>();
	
	/* 해당글의 첨부파일 */
	private List<BoardFileVO> addFileList = new ArrayList<>();

	
	public List<BoardFileVO> getAddFileList() {
		return addFileList;
	}

	public void setAddFileList(List<BoardFileVO> addFileList) {
		this.addFileList = addFileList;
	}

	public List<CommentVO> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<CommentVO> replyList) {
		this.replyList = replyList;
	}

	public String getComBoardCode() {
		return comBoardCode;
	}

	public void setComBoardCode(String comBoardCode) {
		this.comBoardCode = comBoardCode;
	}

	public int getComBoardSeq() {
		return comBoardSeq;
	}

	public void setComBoardSeq(int comBoardSeq) {
		this.comBoardSeq = comBoardSeq;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
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
