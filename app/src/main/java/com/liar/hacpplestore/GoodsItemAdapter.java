package com.liar.hacpplestore;

import android.content.Context;
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
		return new ViewHolder(view);
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
