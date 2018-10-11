package com.liar.hacpplestore.database;

import org.litepal.crud.LitePalSupport;

public class Orders extends LitePalSupport {

	private String orderNum;         // 采用邮箱+时间yyyyMMddHHmmss的方式记录

	private String goodsName;

	private String goodsType;

	private String goodsPrice;

	private String userEmail;

	private String buyerName;

	private String buyerTel;

	private String buyerAddress;

	private String transactionTime;     // 交易时间

	private String transactionStatus;       // 交易状态：交易取消Cancelled，交易完成Completed，交易中Trading

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerTel() {
		return buyerTel;
	}

	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}

	public String getBuyerAddress() {
		return buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
