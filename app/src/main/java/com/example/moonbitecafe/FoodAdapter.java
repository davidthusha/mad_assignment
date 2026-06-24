package com.example.moonbitecafe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    ArrayList<Food> foodList;

    public FoodAdapter(ArrayList<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_item,
                        parent,
                        false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position)
    {
        Food food = foodList.get(position);

        holder.txtFoodName.setText(food.food_name);

        holder.txtPrice.setText(
                "Rs. " + food.price);

        Glide.with(holder.itemView.getContext())
                .load(food.image)
                .into(holder.imgFood);

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    holder.itemView.getContext(),
                    FoodDetailsActivity.class);

            intent.putExtra(
                    "food_id",
                    food.food_id);

            intent.putExtra(
                    "food_name",
                    food.food_name);

            intent.putExtra(
                    "price",
                    food.price);

            intent.putExtra(
                    "image",
                    food.image);

            intent.putExtra(
                    "description",
                    food.description);

            holder.itemView.getContext()
                    .startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder
            extends RecyclerView.ViewHolder {

        ImageView imgFood;
        TextView txtFoodName;
        TextView txtPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood =
                    itemView.findViewById(R.id.imgFood);

            txtFoodName =
                    itemView.findViewById(R.id.txtFoodName);

            txtPrice =
                    itemView.findViewById(R.id.txtPrice);
        }
    }
}