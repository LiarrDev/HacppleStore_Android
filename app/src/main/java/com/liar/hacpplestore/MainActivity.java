package com.liar.hacpplestore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

	DrawerLayout drawerLayout;
	NavigationView navigationView;

	String name;
	String email;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		name = intent.getStringExtra("name");
		email = intent.getStringExtra("email");
		password = intent.getStringExtra("password");

		drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
		navigationView = (NavigationView) findViewById(R.id.nav_view);

		navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {

				switch (item.getItemId()) {
					case R.id.nav_orders:
						Intent orderIntent = new Intent(MainActivity.this, SignupActivity.class);
						startActivity(orderIntent);
						break;

					case R.id.nav_tel:
						Intent telIntent = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(telIntent);
						break;

					case R.id.nav_logout:
						Intent logoutIntent = new Intent(MainActivity.this, LoginActivity.class);
						startActivity(logoutIntent);
						// TODO: 清除 SharePreference 保存的登录状态
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
				Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//				intent.putExtra("mac", "mac");
//				// 传过去之后根据这个值查数据库拿信息，然后把这个值set到Toolbar上
				startActivity(intent);
			}
		});

		iPhoneImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, SignupActivity.class);
				startActivity(intent);
			}
		});

		iPadImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, FillOrderActivity.class);
				startActivity(intent);
			}
		});

		watchImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		accessoriesImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

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
