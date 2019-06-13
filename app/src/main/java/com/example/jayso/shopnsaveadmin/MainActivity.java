package com.example.jayso.shopnsaveadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;

    public void sessionCheck() {
        SharedPreferences prefs = getBaseContext().getSharedPreferences("preferences-details", MODE_PRIVATE);
        String username = prefs.getString("username", "blank");
        if(username.equals("blank")){
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionCheck();
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent categoriesIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(categoriesIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
