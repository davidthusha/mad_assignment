package com.example.moonbitecafe;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FoodDetailsActivity
        extends AppCompatActivity {

    ImageView imgFood;

    TextView txtFoodName,
            txtPrice,
            txtDescription;

    String foodId;
    String foodName;
    String price;
    String image;
    String description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_food_details);

        imgFood =
                findViewById(R.id.imgFood);

        txtFoodName =
                findViewById(R.id.txtFoodName);

        txtPrice =
                findViewById(R.id.txtPrice);

        txtDescription =
                findViewById(R.id.txtDescription);

        Button btnAddCart =
                findViewById(R.id.btnAddCart);

        foodId =
                getIntent().getStringExtra("food_id");

        foodName =
                getIntent().getStringExtra("food_name");

        price =
                getIntent().getStringExtra("price");

        image =
                getIntent().getStringExtra("image");

        description =
                getIntent().getStringExtra("description");

        txtFoodName.setText(foodName);

        txtPrice.setText(
                "Rs. " + price);

        txtDescription.setText(description);

        Glide.with(this)
                .load(image)
                .into(imgFood);

        btnAddCart.setOnClickListener(v -> addToCart());

        findViewById(R.id.txtBack)
                .setOnClickListener(v -> finish());
    }

    private void addToCart()
    {
        boolean found = false;

        for(CartItem item : CartManager.cartList)
        {
            if(item.foodId.equals(foodId))
            {
                item.quantity++;
                found = true;
                break;
            }
        }

        if(!found)
        {
            CartManager.cartList.add(
                    new CartItem(
                            foodId,
                            foodName,
                            price,
                            image
                    )
            );
        }

        Toast.makeText(
                this,
                "Added To Cart",
                Toast.LENGTH_SHORT
        ).show();
    }

}