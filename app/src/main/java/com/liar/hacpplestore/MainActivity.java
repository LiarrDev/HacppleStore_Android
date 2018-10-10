package com.liar.hacpplestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	DrawerLayout drawerLayout;
	NavigationView navigationView;
	TextView nameNav;

	String name = null;
	String tel = null;
	String email = null;
	String password = null;

	String adminEmail = "admin@admin.com";      // 管理员 Email

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		tel = intent.getStringExtra("tel");
		email = intent.getStringExtra("email");
		password = intent.getStringExtra("password");

		Log.e("===GET===", name + "");
		Log.e("===GET===", tel + "");
		Log.e("===GET===", email + "");
		Log.e("===GET===", password + "");

		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
		navigationView = (NavigationView) findViewById(R.id.nav_view);
		View headerView = navigationView.getHeaderView(0);
		nameNav = (TextView) headerView.findViewById(R.id.header_name);
		nameNav.setText(name);

		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {

				switch (item.getItemId()) {
					case R.id.nav_orders:
						Intent orderIntent = new Intent(MainActivity.this, FillOrderActivity.class);
						startActivity(orderIntent);
						break;

					case R.id.nav_tel:
						Intent telIntent = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(telIntent);
						break;

					case R.id.nav_logout:
						Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(logoutIntent);
						SharedPreferences.Editor editor = getSharedPreferences("loginData", MODE_PRIVATE).edit();
						editor.clear();
						editor.apply();
						finish();
						break;

					case R.id.nav_about:
						Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
						startActivity(aboutIntent);
						break;

					default:
				}

				drawerLayout.closeDrawers();
				return true;
			}
		});

		Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setHomeAsUpIndicator(R.drawable.ic_user);
		}

		ImageView macImg = (ImageView) findViewById(R.id.mac);
		ImageView iPhoneImg = (ImageView) findViewById(R.id.iphone);
		ImageView iPadImg = (ImageView) findViewById(R.id.ipad);
		ImageView watchImg = (ImageView) findViewById(R.id.watch);
		ImageView accessoriesImg = (ImageView) findViewById(R.id.accessories);
		ImageView supportImg = (ImageView) findViewById(R.id.support);

		macImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (email.equals(adminEmail)) {     // 管理员则打开管理页面
					Intent intent = new Intent(MainActivity.this, ManageGoodsActivity.class);
					intent.putExtra("type", "Mac");
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainActivity.this, ShowGoodsActivity.class);
					intent.putExtra("type", "Mac");
					intent.putExtra("email", email);    // 用户则把其 Email 发过去
					startActivity(intent);
				}
			}
		});

		iPhoneImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (email.equals(adminEmail)) {     // 管理员则打开管理页面
					Intent intent = new Intent(MainActivity.this, ManageGoodsActivity.class);
					intent.putExtra("type", "iPhone");
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainActivity.this, ShowGoodsActivity.class);
					intent.putExtra("type", "iPhone");
					intent.putExtra("email", email);
					startActivity(intent);
				}
			}
		});

		iPadImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (email.equals(adminEmail)) {     // 管理员则打开管理页面
					Intent intent = new Intent(MainActivity.this, ManageGoodsActivity.class);
					intent.putExtra("type", "iPad");
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainActivity.this, ShowGoodsActivity.class);
					intent.putExtra("type", "iPad");
					intent.putExtra("email", email);
					startActivity(intent);
				}
			}
		});

		watchImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (email.equals(adminEmail)) {     // 管理员则打开管理页面
					Intent intent = new Intent(MainActivity.this, ManageGoodsActivity.class);
					intent.putExtra("type", "Watch");
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainActivity.this, ShowGoodsActivity.class);
					intent.putExtra("type", "Watch");
					intent.putExtra("email", email);
					startActivity(intent);
				}
			}
		});

		accessoriesImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (email.equals(adminEmail)) {     // 管理员则打开管理页面
					Intent intent = new Intent(MainActivity.this, ManageGoodsActivity.class);
					intent.putExtra("type", "Accessories");
					startActivity(intent);
				} else {
					Intent intent = new Intent(MainActivity.this, ShowGoodsActivity.class);
					intent.putExtra("type", "Accessories");
					intent.putExtra("email", email);
					startActivity(intent);
				}
			}
		});

		supportImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				drawerLayout.openDrawer(GravityCompat.START);
				break;

			default:
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawers();
		} else {
			super.onBackPressed();
		}
	}
}