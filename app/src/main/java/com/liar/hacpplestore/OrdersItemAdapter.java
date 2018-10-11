package com.liar.hacpplestore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class OrdersItemAdapter extends RecyclerView.Adapter<OrdersItemAdapter.ViewHolder> {

	private Context mContext;

	private List<OrdersItem> mOrdersItemList;

	String adminEmail = "admin@admin.com";      // 管理员 Email

	String email = "";

	public void setEmail(String email) {
		this.email = email;
	}

	static class ViewHolder extends RecyclerView.ViewHolder {
		CardView cardView;
		TextView goodsNameText;
		TextView orderStateText;

		public ViewHolder(View view) {
			super(view);
			cardView = (CardView) view;
			goodsNameText = (TextView) view.findViewById(R.id.order_goods_name);
			orderStateText = (TextView) view.findViewById(R.id.order_state);
		}
	}

	public OrdersItemAdapter(List<OrdersItem> ordersItemList) {
		mOrdersItemList = ordersItemList;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (mContext == null) {
			mContext = parent.getContext();
		}
		View view = LayoutInflater.from(mContext).inflate(R.layout.orders_item, parent, false);
		final ViewHolder holder = new ViewHolder(view);
		holder.cardView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int position = holder.getAdapterPosition();
				OrdersItem ordersItem = mOrdersItemList.get(position);

				if (email.equals(adminEmail)) {         // 管理员
					Intent intent = new Intent(mContext, ManageOrderDetailsActivity.class);
					intent.putExtra("order_num", ordersItem.getOrderNum());
					mContext.startActivity(intent);
				} else {            // 用户
					Intent intent = new Intent(mContext, OrdersDetailsActivity.class);
//					intent.putExtra("email", email);
					intent.putExtra("order_num", ordersItem.getOrderNum());
					mContext.startActivity(intent);
				}
			}
		});
		return holder;
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
		OrdersItem ordersItem = mOrdersItemList.get(position);
		holder.goodsNameText.setText(ordersItem.getGoodsName());
		holder.orderStateText.setText(ordersItem.getTransactionStatus());
	}

	@Override
	public int getItemCount() {
		return mOrdersItemList.size();
	}
}
