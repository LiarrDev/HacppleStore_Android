package com.liar.hacpplestore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.liar.hacpplestore.database.Orders;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class OrdersActivity extends AppCompatActivity {

	private List<OrdersItem> ordersItemList = new ArrayList<>();

	private OrdersItemAdapter adapter;

	String adminEmail = "admin@admin.com";      // 管理员 Email

	String email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orders);

		Toolbar toolbar = (Toolbar) findViewById(R.id.orders_toolbar);
		toolbar.setTitle("Orders");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		Intent intent = getIntent();
		email = intent.getStringExtra("email");

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

	@Override
	protected void onResume() {
		super.onResume();

		// 在这里进行加载是为了及时更新页面数据
		initOrdersItem(email);
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.orders_recycler);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new OrdersItemAdapter(ordersItemList);
		recyclerView.setAdapter(adapter);
	}

	public void initOrdersItem(String email) {
		ordersItemList.clear();
		if (email.equals(adminEmail)) {     // 管理员，查询所有订单记录显示
			List<Orders> orders = LitePal.findAll(Orders.class);
			for (Orders order: orders) {
				OrdersItem ordersItem = new OrdersItem(order.getGoodsName(), order.getTransactionStatus());
				Log.e("===ORDER===", order.getGoodsName());
				Log.e("===ORDER===", order.getTransactionStatus());
				ordersItemList.add(ordersItem);
			}
		} else {        // 用户，根据 Email 查询该用户记录显示
			List<Orders> orders = LitePal.select("goodsName", "transactionStatus", "orderNum").where("userEmail = ?", email).order("transactionTime desc").find(Orders.class);
			for (Orders order: orders) {
				OrdersItem ordersItem = new OrdersItem(order.getGoodsName(), order.getTransactionStatus());
				Log.e("===ORDER===", order.getGoodsName());
				Log.e("===ORDER===", order.getTransactionStatus());
				Log.e("===ORDER===", order.getOrderNum());
				ordersItemList.add(ordersItem);
			}
		}
	}
}
