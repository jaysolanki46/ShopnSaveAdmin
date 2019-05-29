package com.example.jayso.shopnsaveadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void getHome(View view) {

        Toast.makeText(getApplicationContext(),"Welcome admin",Toast.LENGTH_SHORT).show();
        Intent intentHome = new Intent(this, HomeActivity.class);
        startActivity(intentHome);
    }
}
