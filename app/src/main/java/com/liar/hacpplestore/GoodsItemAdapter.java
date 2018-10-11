package com.liar.hacpplestore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class GoodsItemAdapter extends RecyclerView.Adapter<GoodsItemAdapter.ViewHolder> {

	private Context mContext;

	private List<GoodsItem> mGoodsItemList;

	String email = "";

	public void setEmail(String email) {
		this.email = email;
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		CardView cardView;
		ImageView goodsImage;
		TextView goodsName;

		public ViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
			goodsImage = (ImageView) view.findViewById(R.id.goods_image);
			goodsName = (TextView) view.findViewById(R.id.goods_name);
		}
	}

	public GoodsItemAdapter(List<GoodsItem> goodsItemList) {
		mGoodsItemList = goodsItemList;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (mContext == null) {
			mContext = parent.getContext();
		}
		View view = LayoutInflater.from(mContext).inflate(R.layout.goods_item, parent, false);
		final ViewHolder holder = new ViewHolder(view);
		holder.cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int position = holder.getAdapterPosition();
				GoodsItem goodsItem = mGoodsItemList.get(position);

				// TODO: 判断是用户还是管理员，用户的话还需把email传过去，并打开商品详情页面
				if (email.equals("")) {     // 管理员
					Intent intent = new Intent(mContext, EditGoodsActivity.class);
					intent.putExtra("goods_name", goodsItem.getName());
					intent.putExtra("action", "edit");
					mContext.startActivity(intent);
				} else {        // 用户
					Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
					intent.putExtra("goods_name", goodsItem.getName());
					intent.putExtra("email", email);
					mContext.startActivity(intent);
				}
			}
		});
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		GoodsItem goodsItem = mGoodsItemList.get(position);
		holder.goodsName.setText(goodsItem.getName());
		Glide.with(mContext).load(goodsItem.getImageByte()).into(holder.goodsImage);
	}

	@Override
	public int getItemCount() {
		return mGoodsItemList.size();
	}
}