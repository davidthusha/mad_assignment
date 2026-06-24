package com.example.moonbitecafe;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class OrderDetailsActivity
        extends AppCompatActivity {

    TextView txtOrderTitle;
    TextView txtItems;

    TextView txtStatus;
    TextView txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_order_details);

        txtOrderTitle =
                findViewById(R.id.txtOrderTitle);

        txtItems =
                findViewById(R.id.txtItems);

        String orderId =
                getIntent().getStringExtra(
                        "order_id");

        txtOrderTitle.setText(
                "Order #" + orderId);

        txtStatus =
                findViewById(R.id.txtStatus);

        txtTotal =
                findViewById(R.id.txtTotal);

        String status =
                getIntent().getStringExtra(
                        "status");

        String total =
                getIntent().getStringExtra(
                        "total");

        txtStatus.setText(
                "Status: " + status);

        txtTotal.setText(
                "Total: Rs. " + total);

        loadItems(orderId);

        findViewById(R.id.txtBack)
                .setOnClickListener(v -> finish());
    }

    private void loadItems(String orderId)
    {
        String url =
                "http://10.0.2.2/moonbite/api/get_order_details.php?order_id="
                        + orderId;

        JsonArrayRequest request =
                new JsonArrayRequest(
                        Request.Method.GET,
                        url,
                        null,

                        response -> {

                            StringBuilder items =
                                    new StringBuilder();

                            try {

                                for(int i=0;
                                    i<response.length();
                                    i++)
                                {
                                    JSONObject obj =
                                            response.getJSONObject(i);

                                    items.append(
                                                    obj.getString("food_name"))
                                            .append(" x")
                                            .append(
                                                    obj.getString("quantity"))
                                            .append("\n");
                                }

                                txtItems.setText(
                                        items.toString());

                            }
                            catch(Exception e)
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