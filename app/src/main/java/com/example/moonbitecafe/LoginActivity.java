package com.example.moonbitecafe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtPassword;
    Button btnLogin;

    String URL = "http://10.0.2.2/moonbite/api/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL,

                response -> {

                    try {

                        JSONObject obj =
                                new JSONObject(response);

                        boolean success =
                                obj.getBoolean("success");

                        if(success)
                        {
                            SharedPreferences sp =
                                    getSharedPreferences(
                                            "MoonBite",
                                            MODE_PRIVATE);

                            sp.edit()
                                    .putString(
                                            "customer_id",
                                            obj.getString("customer_id"))
                                    .apply();

                            Toast.makeText(
                                    LoginActivity.this,
                                    "Login Successful",
                                    Toast.LENGTH_SHORT
                            ).show();

                            Intent intent =
                                    new Intent(
                                            LoginActivity.this,
                                            HomeActivity.class
                                    );

                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Invalid Email or Password",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }

                    } catch (Exception e) {

                        Toast.makeText(
                                LoginActivity.this,
                                e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }

                },

                error -> Toast.makeText(
                        LoginActivity.this,
                        error.toString(),
                        Toast.LENGTH_LONG
                ).show()

        ) {

            @Override
            protected Map<String, String> getParams() {

                Map<String,String> params =
                        new HashMap<>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue queue =
                Volley.newRequestQueue(this);

        queue.add(request);
    }
}