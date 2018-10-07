package com.liar.hacpplestore.database;

import org.litepal.crud.LitePalSupport;

public class Orders extends LitePalSupport {

	String orderNum;         // 采用邮箱+时间yyyyMMddHHmmss的方式记录

	String goodsName;

	String price;

	String name;

	String tel;

	String address;

	String transactionStatus;       // 交易状态：交易取消Cancelled，交易完成Completed，交易中Trading

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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
