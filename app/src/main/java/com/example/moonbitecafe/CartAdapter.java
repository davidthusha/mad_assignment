package com.example.moonbitecafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartAdapter
        extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    ArrayList<CartItem> cartList;

    public CartAdapter(ArrayList<CartItem> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                android.R.layout.simple_list_item_2,
                                parent,
                                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        CartItem item = cartList.get(position);

        holder.title.setText(item.foodName);

        holder.subtitle.setText(
                "Rs. " + item.price +
                        " | Qty: " + item.quantity);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView title;
        TextView subtitle;

        public ViewHolder(View itemView) {
            super(itemView);

            title =
                    itemView.findViewById(
                            android.R.id.text1);

            subtitle =
                    itemView.findViewById(
                            android.R.id.text2);
        }
    }
}