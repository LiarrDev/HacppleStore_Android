package com.liar.hacpplestore;

public class OrdersItem {

	private String goodsName;

	private String transactionStatus;

	private String orderNum;

	public OrdersItem(String goodsName, String transactionStatus, String orderNum) {
		this.goodsName = goodsName;
		this.transactionStatus = transactionStatus;
		this.orderNum = orderNum;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public String getOrderNum() {
		return orderNum;
	}
}
