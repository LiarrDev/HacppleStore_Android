package com.liar.hacpplestore;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ManageGoodsActivity extends AppCompatActivity {

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

	// TODO: 查看生命周期，是否在onResume中查库显示，为了能够及时刷新
}
