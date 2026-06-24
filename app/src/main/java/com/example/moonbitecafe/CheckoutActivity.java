package com.example.moonbitecafe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity
        extends AppCompatActivity {

    EditText txtName;
    EditText txtPhone;
    EditText txtAddress;

    Button btnPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_checkout);

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);

        btnPlaceOrder =
                findViewById(R.id.btnPlaceOrder);

        btnPlaceOrder.setOnClickListener(
                v -> placeOrder());

        findViewById(R.id.txtBack)
                .setOnClickListener(v -> finish());
    }

    private void placeOrder()
    {
        SharedPreferences sp =
                getSharedPreferences(
                        "MoonBite",
                        MODE_PRIVATE);

        final String customerId =
                sp.getString(
                        "customer_id",
                        "");

        double total = 0;

        for(CartItem item : CartManager.cartList)
        {
            total +=
                    Double.parseDouble(item.price)
                            * item.quantity;
        }

        final double finalTotal = total;

        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        "http://10.0.2.2/moonbite/api/place_order.php",

                        response -> {

                            try {

                                JSONObject obj =
                                        new JSONObject(response);

                                int orderId =
                                        obj.getInt("order_id");

                                for(CartItem item :
                                        CartManager.cartList)
                                {
                                    saveOrderItem(
                                            orderId,
                                            item.foodId,
                                            item.quantity,
                                            item.price
                                    );
                                }

                                Toast.makeText(
                                        this,
                                        "Order Placed",
                                        Toast.LENGTH_LONG
                                ).show();

                                CartManager.cartList.clear();

                                finish();

                            }
                            catch (Exception e)
                            {
                                Toast.makeText(
                                        this,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        },

                        error -> {

                            Toast.makeText(
                                    this,
                                    error.toString(),
                                    Toast.LENGTH_LONG
                            ).show();
                        })

                {
                    @Override
                    protected Map<String,String> getParams()
                    {
                        Map<String,String> params =
                                new HashMap<>();

                        params.put(
                                "customer_id",
                                customerId);

                        params.put(
                                "total_amount",
                                String.valueOf(finalTotal));

                        return params;
                    }
                };

        Volley.newRequestQueue(this)
                .add(request);
    }

    private void saveOrderItem(
            int orderId,
            String foodId,
            int quantity,
            String price)
    {

        StringRequest request =
                new StringRequest(
                        Request.Method.POST,
                        "http://10.0.2.2/moonbite/api/save_order_items.php",

                        response -> {},

                        error -> {})

                {
                    @Override
                    protected Map<String,String> getParams()
                    {
                        Map<String,String> params =
                                new HashMap<>();

                        params.put(
                                "order_id",
                                String.valueOf(orderId));

                        params.put(
                                "food_id",
                                foodId);

                        params.put(
                                "quantity",
                                String.valueOf(quantity));

                        params.put(
                                "price",
                                price);

                        return params;
                    }
                };

        Volley.newRequestQueue(this)
                .add(request);
    }
}