package com.liar.hacpplestore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.liar.hacpplestore.database.Orders;

import org.litepal.LitePal;

import java.util.List;

public class OrdersDetailsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders_details);

		Toolbar toolbar = (Toolbar) findViewById(R.id.orders_details_toolbar);
		toolbar.setTitle("Details");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		EditText orderNumText = (EditText) findViewById(R.id.orders_details_order_num);
		EditText goodsNameText = (EditText) findViewById(R.id.orders_details_goods_name); 
		EditText goodsPriceText = (EditText) findViewById(R.id.orders_details_goods_price);
		EditText buyerNameText = (EditText) findViewById(R.id.orders_details_buyer_name);
		EditText buyerTelText = (EditText) findViewById(R.id.orders_details_buyer_tel);
		EditText buyerAddressText = (EditText) findViewById(R.id.orders_details_buyer_address);
		EditText transactionTimeText = (EditText) findViewById(R.id.orders_details_transaction_time);
		EditText transactionStateText = (EditText) findViewById(R.id.orders_details_transaction_state);

		String orderNum = null;
		String goodsName = null;
		String goodsPrice = null;
		String buyerName = null;
		String buyerTel = null;
		String buyerAddress = null;
		String transactionTime = null;
		String transactionState = null;
		
		Intent intent = getIntent();
//		String email = intent.getStringExtra("email");
		orderNum = intent.getStringExtra("order_num");
		List<Orders> orders = LitePal.where("orderNum = ?", orderNum).find(Orders.class);
		for (Orders order: orders) {
			goodsName = order.getGoodsName();
			goodsPrice = order.getGoodsPrice();
			buyerName = order.getBuyerName();
			buyerTel = order.getBuyerTel();
			buyerAddress = order.getBuyerAddress();
			transactionTime = order.getTransactionTime();
			transactionState = order.getTransactionStatus();
		}
		
		orderNumText.setText(orderNum);
		orderNumText.setEnabled(false);
		goodsNameText.setText(goodsName);
		goodsNameText.setEnabled(false);
		goodsPriceText.setText(goodsPrice);
		goodsPriceText.setEnabled(false);
		buyerNameText.setText(buyerName);
		buyerNameText.setEnabled(false);
		buyerTelText.setText(buyerTel);
		buyerTelText.setEnabled(false);
		buyerAddressText.setText(buyerAddress);
		buyerAddressText.setEnabled(false);
		transactionTimeText.setText(transactionTime);
		transactionTimeText.setEnabled(false);
		transactionStateText.setText(transactionState);
		transactionStateText.setEnabled(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			default:
		}
		return true;
	}
}