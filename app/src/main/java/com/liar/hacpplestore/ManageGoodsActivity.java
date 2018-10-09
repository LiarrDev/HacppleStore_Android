package com.liar.hacpplestore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.liar.hacpplestore.database.Goods;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class ManageGoodsActivity extends AppCompatActivity {

	private List<GoodsItem> goodsItemList = new ArrayList<>();

	private GoodsItemAdapter adapter;

	String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manage_goods);

		Intent intent = getIntent();
		title = intent.getStringExtra("type");
		Log.e("===TITLE===", title);

		Toolbar toolbar = (Toolbar) findViewById(R.id.manage_goods_toolbar);
		toolbar.setTitle(title);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.manage_goods_toolbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			case R.id.add_goods:
				Intent intent = new Intent(ManageGoodsActivity.this, EditGoodsActivity.class);
				intent.putExtra("type", title);
				intent.putExtra("action", "add");
				startActivity(intent);
				break;

			default:
		}
		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();

		initGoodsItem();
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.manage_goods_recycler);
		GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
		recyclerView.setLayoutManager(layoutManager);
		adapter = new GoodsItemAdapter(goodsItemList);
		recyclerView.setAdapter(adapter);
	}

	private void initGoodsItem() {
		goodsItemList.clear();
		List<Goods> goods = LitePal.findAll(Goods.class);
		for (Goods good: goods) {
			GoodsItem goodsItem = new GoodsItem(good.getName(), good.getImage());
			goodsItemList.add(goodsItem);
		}
	}
}
