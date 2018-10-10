package com.liar.hacpplestore;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.liar.hacpplestore.database.Goods;

import org.litepal.LitePal;

import java.util.List;

public class GoodsDetailsActivity extends AppCompatActivity {

	String goodsName;
	String email;

	String goodsPrice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_goods_details);

		Toolbar toolbar = (Toolbar) findViewById(R.id.goods_details_toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		Intent intent = getIntent();
		goodsName = intent.getStringExtra("goods_name");
		email = intent.getStringExtra("email");

		CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.goods_details_collapsing_toolbar);
		ImageView goodsImageView = (ImageView) findViewById(R.id.goods_image_view);
		TextView goodsPriceText = (TextView) findViewById(R.id.goods_details_price);
		TextView goodsDetailText = (TextView) findViewById(R.id.goods_detail_text);

		collapsingToolbar.setTitle(goodsName);
		List<Goods> goods = LitePal.where("name = ?", goodsName).find(Goods.class);
		for (Goods good: goods) {
			Glide.with(this).load(good.getImage()).into(goodsImageView);
			goodsPrice = good.getPrice();
			goodsPriceText.setText("￥ " + goodsPrice);
			goodsDetailText.setText(good.getDetail());
		}

		FloatingActionButton buy = (FloatingActionButton) findViewById(R.id.buy_fab);
		buy.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(GoodsDetailsActivity.this, FillOrderActivity.class);
				intent.putExtra("goods_name", goodsName);
				intent.putExtra("goods_price", goodsPrice);
				intent.putExtra("email", email);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
