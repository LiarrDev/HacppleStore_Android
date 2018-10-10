package com.liar.hacpplestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liar.hacpplestore.database.Users;

import org.litepal.LitePal;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

	String email;
	String password;

	String adminName = "Admin";                 // 管理员 Name
	String adminTel = "";                       // 管理员 Tel
	String adminEmail = "admin@admin.com";      // 管理员 Email
	String adminPassword = "adminadmin";        // 管理员 Password

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		SharedPreferences preferences = getSharedPreferences("loginData", MODE_PRIVATE);
		String prefEmail = preferences.getString("email", "");
		String prefPassword = preferences.getString("password", "");

		// 由于管理员信息写死而没有存进数据库，所以管理员每次都需要手动登录，用户则将 SharePreferences 中保存的信息查库，以防止 Email 或 Password 改变之后仍可登录
		List<Users> users = LitePal.select("name", "password", "tel").where("email = ?", prefEmail).find(Users.class);
		String dbPassword = null;
		String dbName = null;
		String dbTel = null;
		for (Users user: users) {
			dbPassword = user.getPassword();
			dbName = user.getName();
			dbTel = user.getTel();
			Log.e("===LoginActivity==", dbPassword);
		}
		if (prefPassword.equals(dbPassword)) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			intent.putExtra("name", dbName);
			intent.putExtra("email", prefEmail);
			intent.putExtra("tel", dbTel);
			intent.putExtra("password", dbPassword);
			startActivity(intent);
			finish();
		} else {
			SharedPreferences.Editor editor = preferences.edit();
			editor.clear();
			editor.apply();
		}

		final EditText emailEdit = (EditText) findViewById(R.id.email);
		final EditText passwordEdit = (EditText) findViewById(R.id.password);

		AppCompatButton loginBtn = (AppCompatButton) findViewById(R.id.login);
		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				email = emailEdit.getText().toString();
				password = passwordEdit.getText().toString();

				String regEmail = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
				Pattern patternEmail = Pattern.compile(regEmail);
				Matcher matcherEmail = patternEmail.matcher(email);

				String regPassword = "[0-9a-zA-Z]{6,16}";
				Pattern patternPassword = Pattern.compile(regPassword);
				Matcher matcherPassword = patternPassword.matcher(password);

				if (TextUtils.isEmpty(emailEdit.getText().toString())) {        // Email 为空
					emptyAlert("Email");
				} else if (TextUtils.isEmpty(passwordEdit.getText().toString())) {  // Password 为空
					emptyAlert("Password");
				} else if (email.equals(adminEmail) && password.equals(adminPassword)) {        // 管理员登录
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra("name", adminName);
					intent.putExtra("tel", adminTel);
					intent.putExtra("email", adminEmail);
					intent.putExtra("password", adminPassword);
					startActivity(intent);

					SharedPreferences.Editor editor = getSharedPreferences("loginData", MODE_PRIVATE).edit();
					editor.putString("email", email);
					editor.putString("password", password);
					editor.apply();

					finish();
				} else if (!matcherEmail.matches()) {       // Email 格式不对
					wrongAlert();
				} else if (!matcherPassword.matches()) {    // Password 格式不对
					wrongAlert();
				} else {        // 格式正确，开始查库
					List<Users> users = LitePal.select("name", "tel", "password").where("email = ?", email).find(Users.class);
					String dbPassword = null;
					String dbName = null;
					String dbTel = null;
					for (Users user: users) {
						dbPassword = user.getPassword();
						dbName = user.getName();
						dbTel = user.getTel();
						Log.e("===LoginActivity==", dbPassword);
					}
					if (password.equals(dbPassword)) {      // 查库得到密码，符合则可登录
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						intent.putExtra("email", email);
						intent.putExtra("tel", dbTel);
						intent.putExtra("name", dbName);
						intent.putExtra("password", dbPassword);
						startActivity(intent);

						// 用SharePreference保存登录状态
						SharedPreferences.Editor editor = getSharedPreferences("loginData", MODE_PRIVATE).edit();
						editor.putString("email", email);
						editor.putString("password", password);
						editor.apply();

						finish();
					} else {        // 查库密码错误或不存在
						wrongAlert();
					}
				}
			}
		});

		TextView signupLink = (TextView) findViewById(R.id.link_signup);
		signupLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	public void emptyAlert(String item) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage(item + " is empty.");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", null);
		dialog.show();
	}

	public void wrongAlert() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage("Your Email or Password must be wrong. Please check again.");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", null);
		dialog.show();
	}
}