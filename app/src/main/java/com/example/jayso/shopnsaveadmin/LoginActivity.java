package com.example.jayso.shopnsaveadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;

import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity  extends AppCompatActivity {

    boolean LOGIN_FLAG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void getLogin(View view) {

        EditText username = findViewById(R.id.id_username);
        EditText password = findViewById(R.id.id_password);

        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("select * from Users where username = '"+ username.getText().toString() +"' and password = '"+ password.getText().toString() +"'");
            if(result.next()) {
                SharedPreferences prefs = getApplicationContext().getSharedPreferences("preferences-details", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("username", username.getText().toString());
                editor.commit();
                LOGIN_FLAG = true;
            } else  {
                LOGIN_FLAG = false;
            }
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(LOGIN_FLAG) {
            Toast.makeText(getApplicationContext(),"Shop N Save welcomes you",Toast.LENGTH_SHORT).show();
            Intent intentHome = new Intent(this, HomeActivity.class);
            startActivity(intentHome);
        } else {
            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}
