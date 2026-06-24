package com.example.moonbitecafe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONObject;

import java.util.ArrayList;

public class MyOrdersActivity
        extends AppCompatActivity {

    RecyclerView recyclerOrders;

    ArrayList<Order> orderList =
            new ArrayList<>();

    TextView txtNoOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_my_orders);

        recyclerOrders =
                findViewById(R.id.recyclerOrders);

        recyclerOrders.setLayoutManager(
                new LinearLayoutManager(this));

        txtNoOrders =
                findViewById(R.id.txtNoOrders);

        findViewById(R.id.txtBack)
                .setOnClickListener(v -> finish());

        loadOrders();
    }
    private void loadOrders()
    {
        SharedPreferences sp =
                getSharedPreferences(
                        "MoonBite",
                        MODE_PRIVATE);

        String customerId =
                sp.getString(
                        "customer_id",
                        "");

        String url =
                "http://10.0.2.2/moonbite/api/get_orders.php?customer_id="
                        + customerId;

        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,

                        response -> {

                            try {

                                for(int i=0;
                                    i<response.length();
                                    i++)
                                {
                                    JSONObject obj =
                                            response.getJSONObject(i);

                                    orderList.add(
                                            new Order(
                                                    obj.getString("order_id"),
                                                    obj.getString("total_amount"),
                                                    obj.getString("status")
                                            )
                                    );
                                }

                                recyclerOrders.setAdapter(
                                        new OrderAdapter(orderList));

                                if(orderList.size() == 0)
                                {
                                    txtNoOrders.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    txtNoOrders.setVisibility(View.GONE);
                                }

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }

                        },

                        error -> error.printStackTrace()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }
}