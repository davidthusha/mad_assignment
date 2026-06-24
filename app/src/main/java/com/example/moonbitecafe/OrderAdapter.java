package com.example.moonbitecafe;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter
        extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    ArrayList<Order> orderList;

    public OrderAdapter(ArrayList<Order> orderList)
    {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType)
    {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.order_item,
                                parent,
                                false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position)
    {
        Order order = orderList.get(position);

        holder.txtOrderId.setText(
                "Order #" + order.orderId);

        holder.txtTotal.setText(
                "Rs. " + order.totalAmount);

        holder.txtStatus.setText(
                order.status);

        holder.itemView.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            v.getContext(),
                            OrderDetailsActivity.class);

            intent.putExtra(
                    "order_id",
                    order.orderId);

            intent.putExtra(
                    "status",
                    order.status);

            intent.putExtra(
                    "total",
                    order.totalAmount);

            v.getContext()
                    .startActivity(intent);
        });

        switch(order.status)
        {
            case "Pending":
                holder.txtStatus.setTextColor(
                        Color.parseColor("#B8860B"));
                break;

            case "Accepted":
                holder.txtStatus.setTextColor(
                        Color.BLUE);
                break;

            case "Preparing":
                holder.txtStatus.setTextColor(
                        Color.MAGENTA);
                break;

            case "Ready":
                holder.txtStatus.setTextColor(
                        Color.parseColor("#0F766E"));
                break;

            case "Completed":
                holder.txtStatus.setTextColor(
                        Color.GREEN);
                break;

            case "Rejected":
                holder.txtStatus.setTextColor(
                        Color.RED);
                break;
        }
    }

    @Override
    public int getItemCount()
    {
        return orderList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        TextView txtOrderId;
        TextView txtTotal;
        TextView txtStatus;

        public ViewHolder(View itemView)
        {
            super(itemView);

            txtOrderId =
                    itemView.findViewById(R.id.txtOrderId);

            txtTotal =
                    itemView.findViewById(R.id.txtTotal);

            txtStatus =
                    itemView.findViewById(R.id.txtStatus);
        }
    }
}