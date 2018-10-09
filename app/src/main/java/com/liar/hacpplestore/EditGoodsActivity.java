package com.liar.hacpplestore;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.liar.hacpplestore.database.Goods;

import org.litepal.LitePal;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class EditGoodsActivity extends AppCompatActivity {

	public static final int CHOOSE_PHOTO = 2;

	EditText goodsNameEdit;
	EditText goodsTypeEdit;
	EditText goodsPriceEdit;
	EditText goodsDetailEdit;

	ImageView goodsImg;
	AppCompatButton upload;

	String type;        // 用来记录商品的 Type，可用于显示在 Title 上，以及存进数据库中
	String action;      // 用来记录管理员的操作，当为 add 时，则表示添加操作，当为 edit 时，则表示更新操作

	byte[] image;       // 用于存储商品图片

	String goodsName;
	String goodsPrice;
	String goodsDetail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_goods);

		Intent intent = getIntent();
		type = intent.getStringExtra("type");
		action = intent.getStringExtra("action");

		Toolbar toolbar = (Toolbar) findViewById(R.id.edit_goods_toolbar);
		// 根据传过来的 action 设置 Toolbar 的 Title
		if (action.equals("add")) {
			toolbar.setTitle("Add");
		} else if (action.equals("edit")) {
			toolbar.setTitle("Edit");
		}
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}

		goodsImg = (ImageView) findViewById(R.id.goods_img);
		upload = (AppCompatButton) findViewById(R.id.upload_picture);
		upload.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (ContextCompat.checkSelfPermission(EditGoodsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(EditGoodsActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
				} else {
					openAlbum();        // 打开相册
				}
			}
		});

		goodsTypeEdit = (EditText) findViewById(R.id.goods_type);
		// 设置 goodsType 的默认值且设为不可编辑
		goodsTypeEdit.setText(type);
		goodsTypeEdit.setEnabled(false);

		goodsNameEdit = (EditText) findViewById(R.id.goods_name);
		goodsPriceEdit = (EditText) findViewById(R.id.goods_price);
		goodsDetailEdit = (EditText) findViewById(R.id.goods_detail);

		if (action.equals("edit")) {        // 如果传过来的 action 为 edit，则需要把数据库中的内容显示在上面
			List<Goods> goods = LitePal.where("name = ?", intent.getStringExtra("goods_name")).find(Goods.class);
			for (Goods good: goods) {
				goodsNameEdit.setText(good.getName());
				goodsTypeEdit.setText(good.getType());
				goodsPriceEdit.setText(good.getPrice());
				goodsDetailEdit.setText(good.getDetail());
				goodsImg.setImageBitmap(BitmapFactory.decodeByteArray(good.getImage(), 0, good.getImage().length));
			}
			goodsNameEdit.setEnabled(false);
		}
	}

	/**
	 * 打开相册
	 */
	private void openAlbum() {
		Intent intent = new Intent("android.intent.action.GET_CONTENT");
		intent.setType("image/*");
		startActivityForResult(intent, CHOOSE_PHOTO);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case 1:
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					openAlbum();
				} else {
					Toast.makeText(this, "You denied the permission.", Toast.LENGTH_LONG).show();
				}
				break;

			default:
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case CHOOSE_PHOTO:
				if (resultCode == RESULT_OK) {
					// 判断手机版本号
					if (Build.VERSION.SDK_INT >= 19) {
						// Android 4.4 及以上系统使用这个方法处理图片
						handleImageOnKitKat(data);
					} else {
						// Android 4.4 以下系统使用这个方法处理图片
						handleImageBeforeKitKat(data);
					}
				}
				break;

			default:
				break;
		}
	}

	private void handleImageBeforeKitKat(Intent data) {
		Uri uri = data.getData();
		String imagePath = getImagePath(uri, null);
		displayImage(imagePath);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void handleImageOnKitKat(Intent data) {
		String imagePath = null;
		Uri uri = data.getData();
		if (DocumentsContract.isDocumentUri(this, uri)) {
			// 如果是 document 类型 Uri，则通过 document id 处理
			String docId = DocumentsContract.getDocumentId(uri);
			if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
				String id = docId.split(":")[1];        // 解析出数字格式的 id
				String selection = MediaStore.Images.Media._ID + "=" + id;
				imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
			} else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
				imagePath = getImagePath(contentUri, null);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) {
			// 如果是 content 类型的 Uri，则使用普通方式处理
			imagePath = getImagePath(uri, null);
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			// 如果是 file 类型的 Uri，直接获取图片路径即可
			imagePath = uri.getPath();
		}
		displayImage(imagePath);
	}

	private String getImagePath(Uri uri, String selection) {
		String path = null;
		// 通过 Uri 和 selection 来获取真实的图片路径
		Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			cursor.close();
		}
		return path;
	}

	private void displayImage(String imagePath) {
		if (imagePath != null) {
			Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
			goodsImg.setImageBitmap(bitmap);
		} else {
			Toast.makeText(this, "Failed to get image.", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.edit_goods_toolbar, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				break;

			case R.id.done:
				goodsName = goodsNameEdit.getText().toString();
				goodsPrice = goodsPriceEdit.getText().toString();
				goodsDetail = goodsDetailEdit.getText().toString();

				if (action.equals("add")) {
					if (TextUtils.isEmpty(goodsNameEdit.getText()) || TextUtils.isEmpty(goodsPriceEdit.getText()) || TextUtils.isEmpty(goodsDetailEdit.getText())) {        // 不能有空项
						emptyAlert();
					} else if (isImageEmpty()) {        // 必须上传图片
						AlertDialog.Builder dialog = new AlertDialog.Builder(this);
						dialog.setTitle(R.string.app_name);
						dialog.setMessage("Please upload a picture.");
						dialog.setCancelable(false);
						dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {}
						});
						dialog.show();
					} else {        // 开始数据库操作
						List<Goods> goodsData = LitePal.select("name").where("name = ?", goodsName).find(Goods.class);
						if (goodsData.size() != 0) {        // 如果 size 不为零，则证明该商品已存在，给出提示
							AlertDialog.Builder dialog = new AlertDialog.Builder(this);
							dialog.setTitle(R.string.app_name);
							dialog.setMessage("This commodity has been exist.");
							dialog.setCancelable(false);
							dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialogInterface, int i) {}
							});
							dialog.show();
						} else {        // 否则即可存库
							goodsImg.setDrawingCacheEnabled(true);
							Bitmap bitmap = Bitmap.createBitmap(goodsImg.getDrawingCache());        // 获取到 Bitmap 图片

							ByteArrayOutputStream stream = new ByteArrayOutputStream();
							bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
							image = stream.toByteArray();

							Goods goods = new Goods();
							goods.setName(goodsName);
							goods.setType(type);
							goods.setPrice(goodsPrice);
							goods.setDetail(goodsDetail);
							goods.setImage(image);
							goods.save();

							goodsImg.setDrawingCacheEnabled(false);
							Toast.makeText(this, "Succeed", Toast.LENGTH_LONG).show();
							finish();
						}
					}
				} else if (action.equals("edit")) {
					if (TextUtils.isEmpty(goodsPriceEdit.getText()) || TextUtils.isEmpty(goodsDetailEdit.getText())) {
						emptyAlert();
					} else {
						goodsImg.setDrawingCacheEnabled(true);
						Bitmap bitmap = Bitmap.createBitmap(goodsImg.getDrawingCache());        // 获取到 Bitmap 图片

						ByteArrayOutputStream stream = new ByteArrayOutputStream();
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
						image = stream.toByteArray();

						Goods goods = new Goods();
						goods.setImage(image);
						goods.setPrice(goodsPrice);
						goods.setDetail(goodsDetail);
						goods.updateAll("name = ?", goodsName);

						goodsImg.setDrawingCacheEnabled(false);
						Toast.makeText(this, "Succeed", Toast.LENGTH_LONG).show();
						finish();
					}
				}
				break;

			default:
		}
		return true;
	}

	/**
	 * 用于判断图片是否有上传
	 * @return 如果上传了则为 false，未上传则为 true
	 */
	private boolean isImageEmpty() {
		Drawable.ConstantState defaultState = getResources().getDrawable(R.drawable.ic_logo).getConstantState();
		Drawable.ConstantState state = goodsImg.getDrawable().getConstantState();
		return defaultState.equals(state);
	}

	public void emptyAlert() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.app_name);
		dialog.setMessage("Please make sure that you've complete each item.");
		dialog.setCancelable(false);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {}
		});
		dialog.show();
	}
}
