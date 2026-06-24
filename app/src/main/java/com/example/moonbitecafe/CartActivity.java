package com.example.moonbitecafe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import android.content.Intent;

public class CartActivity
        extends AppCompatActivity {

    RecyclerView recyclerCart;

    TextView txtTotal;

    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);

        recyclerCart =
                findViewById(R.id.recyclerCart);

        recyclerCart.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerCart.setAdapter(
                new CartAdapter(
                        CartManager.cartList));

        txtTotal =
                findViewById(R.id.txtTotal);

        double total = 0;

        for(CartItem item : CartManager.cartList)
        {
            total +=
                    Double.parseDouble(item.price)
                            * item.quantity;
        }

        txtTotal.setText(
                String.format(
                        "Total: Rs. %.2f",
                        total));

        btnCheckout =
                findViewById(R.id.btnCheckout);

        btnCheckout.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            CartActivity.this,
                            CheckoutActivity.class);

            startActivity(intent);
        });

        findViewById(R.id.txtBack)
                .setOnClickListener(v -> finish());
    }
}