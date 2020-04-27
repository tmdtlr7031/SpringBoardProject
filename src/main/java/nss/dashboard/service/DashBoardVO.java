package nss.dashboard.service;

import nss.com.service.DefaultVO;

public class DashBoardVO extends DefaultVO {
	
	private static final long serialVersionUID = 1L;
	
	// 정해진게 없으니 일단 String으로 선언
	private int orderCode;
	private String userName;
	private String orderProduct;
	private int orderCnt;
	private String orderStatus;
	private String regDt;
	private String excelYn = "N";
	
	public String getExcelYn() {
		return excelYn;
	}
	public void setExcelYn(String excelYn) {
		this.excelYn = excelYn;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public int getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(int orderCode) {
		this.orderCode = orderCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(String orderProduct) {
		this.orderProduct = orderProduct;
	}
	public int getOrderCnt() {
		return orderCnt;
	}
	public void setOrderCnt(int orderCnt) {
		this.orderCnt = orderCnt;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
