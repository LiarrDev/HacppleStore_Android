package com.liar.hacpplestore;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.liar.hacpplestore.database.Users;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

	String name;
	String tel;
	String email;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		final EditText nameEdit = (EditText) findViewById(R.id.name);
		final EditText telEdit = (EditText) findViewById(R.id.tel);
		final EditText emailEdit = (EditText) findViewById(R.id.email);
		final EditText passwordEdit = (EditText) findViewById(R.id.password);

		AppCompatButton signupBtn = (AppCompatButton) findViewById(R.id.signup);
		signupBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				name = nameEdit.getText().toString();
				tel = telEdit.getText().toString();
				email = emailEdit.getText().toString();
				password = passwordEdit.getText().toString();

				String regChineseName = "[\u4E00-\u9FA5]{2,4}";
				Pattern patternChineseName = Pattern.compile(regChineseName);
				Matcher matcherChineseName = patternChineseName.matcher(name);
				String regEnglishName = "([A-Z][a-z]*( |$))+";
				Pattern patternEnglishName = Pattern.compile(regEnglishName);
				Matcher matcherEnglishName = patternEnglishName.matcher(name);

				String regTel = "(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}";
				Pattern patternTel = Pattern.compile(regTel);
				Matcher matcherTel = patternTel.matcher(tel);

				String regEmail = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
				Pattern patternEmail = Pattern.compile(regEmail);
				Matcher matcherEmail = patternEmail.matcher(email);

				String regPassword = "[0-9a-zA-Z]{6,16}";
				Pattern patternPassword = Pattern.compile(regPassword);
				Matcher matcherPassword = patternPassword.matcher(password);

				if (TextUtils.isEmpty(nameEdit.getText())) {
					emptyAlert("Name");
				} else if (TextUtils.isEmpty(telEdit.getText().toString())) {
					emptyAlert("Tel");
				} else if (TextUtils.isEmpty(emailEdit.getText().toString())) {
					emptyAlert("Email");
				} else if (TextUtils.isEmpty(passwordEdit.getText().toString())) {
					emptyAlert("Password");
				} else if (!matcherChineseName.matches() && !matcherEnglishName.matches()) {
					illegalAlert("Name", "You can input 2 to 4 Chinese characters or English words begin with capital letter.");
				} else if (!matcherTel.matches()) {
					illegalAlert("Tel", "Please input a right Tel with 11 numbers.");
				} else if (!matcherEmail.matches()) {
					illegalAlert("Email", "");
				} else if (!matcherPassword.matches()) {
					illegalAlert("Password", "You can input 6 to 16 letters or numbers.");
				} else {

					// TODO: 查库看tel和Email是否存在否则注册失败

//					Users user = new Users();
//					user.setName(name);
//					user.setTel(tel);
//					user.setEmail(email);
//					user.setPassword(password);
//					user.save();

					AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
					dialog.setTitle(R.string.app_name);
					dialog.setMessage("Sign Up succeed. Please login.");
					dialog.setCancelable(false);
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
							startActivity(intent);
							finish();
						}
					});
					dialog.show();
				}
			}
		});

		TextView loginLink = (TextView) findViewById(R.id.link_login);
		loginLink.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	public void emptyAlert(String item) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage(item + " is empty.");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		dialog.show();
	}

	public void illegalAlert(String item, String errorDetail) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(SignupActivity.this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage("Illegal " + item + ". " + errorDetail);
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {}
		});
		dialog.show();
	}
}
