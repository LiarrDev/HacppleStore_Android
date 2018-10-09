package com.liar.hacpplestore;

public class GoodsItem {

	private String name;        // 商品名字

	private byte[] imageByte;     // 图片

	public GoodsItem(String name, byte[] imageByte) {
		this.name = name;
		this.imageByte = imageByte;
	}

	public String getName() {
		return name;
	}

	public byte[] getImageByte() {
		return imageByte;
	}
}
