package com.example.moonbitecafe;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerFoods;

    ArrayList<Food> foodList;

    FoodAdapter adapter;

    Button btnLogout;

    Button btnOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnCart =
                findViewById(R.id.btnCart);

        btnCart.setOnClickListener(v -> {

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            CartActivity.class);

            startActivity(intent);
        });

        recyclerFoods =
                findViewById(R.id.recyclerFoods);

        foodList = new ArrayList<>();

        adapter = new FoodAdapter(foodList);

        recyclerFoods.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerFoods.setAdapter(adapter);

        loadFoods();

        btnLogout =
                findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(v -> {

            SharedPreferences sp =
                    getSharedPreferences(
                            "MoonBite",
                            MODE_PRIVATE);

            sp.edit().clear().apply();

            Intent intent =
                    new Intent(
                            HomeActivity.this,
                            LoginActivity.class);

            startActivity(intent);

            finish();
        });

        btnOrders =
                findViewById(R.id.btnOrders);

        btnOrders.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            HomeActivity.this,
                            MyOrdersActivity.class));
        });
    }

    private void loadFoods()
    {
        String url =
                "http://10.0.2.2/moonbite/api/get_foods.php";

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

                                    foodList.add(
                                            new Food(
                                                    obj.getString("food_id"),
                                                    obj.getString("food_name"),
                                                    obj.getString("price"),
                                                    obj.getString("image"),
                                                    obj.getString("description")
                                            )
                                    );
                                }

                                adapter.notifyDataSetChanged();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        },

                        error -> error.printStackTrace()
                );

        Volley.newRequestQueue(this)
                .add(request);
    }
}