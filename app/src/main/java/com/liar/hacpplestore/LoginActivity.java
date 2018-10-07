package com.liar.hacpplestore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
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

				if (TextUtils.isEmpty(emailEdit.getText().toString())) {
					emptyAlert("Email");
				} else if (TextUtils.isEmpty(passwordEdit.getText().toString())) {
					emptyAlert("Password");
				} else if (email.equals(adminEmail) && password.equals(adminPassword)) {
					Intent intent = new Intent(LoginActivity.this, MainActivity.class);
					intent.putExtra("name", adminName);
					intent.putExtra("tel", adminTel);
					intent.putExtra("email", adminEmail);
					intent.putExtra("password", adminPassword);
					startActivity(intent);
					finish();
				} else if (!matcherEmail.matches()) {
					wrongAlert();
				} else if (!matcherPassword.matches()) {
					wrongAlert();
				} else {
					List<Users> users = LitePal.select("password").where("email = ?", email).find(Users.class);
					// TODO: 查看水机的LitePal代码看如何取出单条数据，还要查如何判断email是否存在；同时查到Name传过去MainActivity用于显示
					String dbPassword = "";     // TODO: 把查到的密码存在这里
					if (dbPassword == password) {
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						startActivity(intent);

						// TODO: 用SharePreference保存登录状态
						finish();
					} else {
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
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		dialog.show();
	}

	public void wrongAlert() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage("Your Email or Password must be wrong. Please check again.");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		dialog.show();
	}
}
