package com.liar.hacpplestore;

public class OrdersItem {

	private String goodsName;

	private String transactionStatus;

	public OrdersItem(String goodsName, String transactionStatus) {
		this.goodsName = goodsName;
		this.transactionStatus = transactionStatus;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}
}
