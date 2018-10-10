package com.liar.hacpplestore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.liar.hacpplestore.database.Orders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FillOrderActivity extends AppCompatActivity {

	String buyerName;
	String buyerTel;
	String buyerAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fill_order);

		Toolbar toolbar = (Toolbar) findViewById(R.id.fill_order_toolbar);
		toolbar.setTitle("Order");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		Intent intent = getIntent();
		final String goodsName = intent.getStringExtra("goods_name");
		final String goodsPrice = intent.getStringExtra("goods_price");
		final String goodsType = intent.getStringExtra("goods_type");
		byte[] goodsImg = intent.getByteArrayExtra("goods_img");
		final String email = intent.getStringExtra("email");

		ImageView goodsImgView = (ImageView) findViewById(R.id.goods_order_image);
		Glide.with(this).load(goodsImg).into(goodsImgView);

		TextView goodsNameText = (TextView) findViewById(R.id.goods_order_name);
		goodsNameText.setText(goodsName);

		final EditText buyerNameEdit = (EditText) findViewById(R.id.buyer_name);
		final EditText buyerTelEdit = (EditText) findViewById(R.id.buyer_tel);
		final EditText buyerAddressEdit = (EditText) findViewById(R.id.buyer_address);

		AppCompatButton submit = (AppCompatButton) findViewById(R.id.order_submit);
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				buyerName = buyerNameEdit.getText().toString();
				buyerTel = buyerTelEdit.getText().toString();
				buyerAddress = buyerAddressEdit.getText().toString();

				String regChineseName = "[\u4E00-\u9FA5]{2,4}";
				Pattern patternChineseName = Pattern.compile(regChineseName);
				Matcher matcherChineseName = patternChineseName.matcher(buyerName);
				String regEnglishName = "([A-Z][a-z]*( |$))+";
				Pattern patternEnglishName = Pattern.compile(regEnglishName);
				Matcher matcherEnglishName = patternEnglishName.matcher(buyerName);

				String regTel = "(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}";
				Pattern patternTel = Pattern.compile(regTel);
				Matcher matcherTel = patternTel.matcher(buyerTel);

				if (TextUtils.isEmpty(buyerNameEdit.getText()) || TextUtils.isEmpty(buyerTelEdit.getText()) || TextUtils.isEmpty(buyerAddressEdit.getText())) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(FillOrderActivity.this);
					dialog.setTitle(R.string.app_name);
					dialog.setMessage("Please make sure that you've complete each item.");
					dialog.setCancelable(false);
					dialog.setPositiveButton("OK", null);
					dialog.show();
				} else if (!matcherChineseName.matches() && !matcherEnglishName.matches()) {
					illegalAlert("Name", "You can input 2 to 4 Chinese characters or English words begin with capital letter.");
				} else if (!matcherTel.matches()) {
					illegalAlert("Tel", "Please input a right Tel with 11 numbers.");
				} else {
					Date date = new Date();
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
					String dateString = formatter.format(date);
					String orderNum = goodsType + dateString + email;

					Orders orders = new Orders();
					orders.setOrderNum(orderNum);
					orders.setGoodsName(goodsName);
					orders.setGoodsType(goodsType);
					orders.setGoodsPrice(goodsPrice);
					orders.setUserEmail(email);
					orders.setBuyerName(buyerName);
					orders.setBuyerTel(buyerTel);
					orders.setBuyerAddress(buyerAddress);
					orders.setTransactionStatus("Trading");     // 设置交易状态为【交易中】
					orders.save();

					Toast.makeText(FillOrderActivity.this, "Succeed", Toast.LENGTH_LONG).show();
					finish();
				}
			}
		});
	}

	public void illegalAlert(String item, String errorDetail) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage("Illegal " + item + ". " + errorDetail);
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", null);
		dialog.show();
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
