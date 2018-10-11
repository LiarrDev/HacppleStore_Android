package com.liar.hacpplestore;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.liar.hacpplestore.database.Orders;

import org.litepal.LitePal;

import java.util.List;

public class ManageOrderDetailsActivity extends AppCompatActivity {

	EditText orderNumText;
	EditText goodsNameText;
	EditText goodsTypeText;
	EditText goodsPriceText;
	EditText userEmailText;
	EditText buyerNameText;
	EditText buyerTelText;
	EditText buyerAddressText;
	EditText transactionTimeText;
	EditText transactionStateText;
	AppCompatButton changeStateBtn;

	String orderNum = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_order_details);

		Toolbar toolbar = (Toolbar) findViewById(R.id.manage_order_details_toolbar);
		toolbar.setTitle("Details");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		orderNumText = (EditText) findViewById(R.id.manage_order_details_order_num);
		goodsNameText = (EditText) findViewById(R.id.manage_order_details_goods_name);
		goodsTypeText = (EditText) findViewById(R.id.manage_order_details_goods_type);
		goodsPriceText = (EditText) findViewById(R.id.manage_order_details_goods_price);
		userEmailText = (EditText) findViewById(R.id.manage_order_details_user_email);
		buyerNameText = (EditText) findViewById(R.id.manage_order_details_buyer_name);
		buyerTelText = (EditText) findViewById(R.id.manage_order_details_buyer_tel);
		buyerAddressText = (EditText) findViewById(R.id.manage_order_details_buyer_address);
		transactionTimeText = (EditText) findViewById(R.id.manage_order_details_transaction_time);
		transactionStateText = (EditText) findViewById(R.id.manage_order_details_transaction_state);
		changeStateBtn = (AppCompatButton) findViewById(R.id.change_transaction_state);

		String goodsName = null;
		String goodsType = null;
		String goodsPrice = null;
		String userEmail = null;
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
			goodsType = order.getGoodsType();
			userEmail = order.getUserEmail();
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
		goodsTypeText.setText(goodsType);
		goodsTypeText.setEnabled(false);

		userEmailText.setText(userEmail);
		userEmailText.setEnabled(false);

		buyerNameText.setText(buyerName);
		buyerTelText.setText(buyerTel);
		buyerAddressText.setText(buyerAddress);

		transactionTimeText.setText(transactionTime);
		transactionTimeText.setEnabled(false);
		transactionStateText.setText(transactionState);
		transactionStateText.setEnabled(false);

		final String stateItems[] = {"Trading", "Completed", "Cancelled"};
		changeStateBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				AlertDialog.Builder dialog = new AlertDialog.Builder(ManageOrderDetailsActivity.this);
				dialog.setTitle(R.string.app_name);
				dialog.setItems(stateItems, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialogInterface, int i) {
						transactionStateText.setText(stateItems[i]);
					}
				});
				dialog.show();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			case R.id.done:
				Orders orders = new Orders();
				orders.setBuyerName(buyerNameText.getText().toString());
				orders.setBuyerTel(buyerTelText.getText().toString());
				orders.setBuyerAddress(buyerAddressText.getText().toString());
				orders.setTransactionStatus(transactionStateText.getText().toString());
				orders.updateAll("orderNum = ?", orderNum);
				Toast.makeText(this, "Succeed", Toast.LENGTH_LONG).show();;
				finish();
				break;

			default:
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.manage_order_details_toolbar, menu);
		return true;
	}
}
