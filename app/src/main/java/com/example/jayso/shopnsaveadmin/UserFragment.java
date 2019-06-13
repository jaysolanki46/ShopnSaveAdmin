package com.example.jayso.shopnsaveadmin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;

import java.sql.ResultSet;
import java.sql.Statement;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    Button btnAdd = null;

    public UserFragment() {
        // Required empty public constructor
    }

    public void sessionCheck() {
        SharedPreferences prefs = getContext().getSharedPreferences("preferences-details", MODE_PRIVATE);
        String username = prefs.getString("username", "blank");
        if(username.equals("blank")){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sessionCheck();
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_user, container, false);
        btnAdd = (Button) v.findViewById(R.id.id_btn_add);

        // Add btn
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextUsername = (EditText) getActivity().findViewById(R.id.id_username);
                EditText editTextPassword = (EditText) getActivity().findViewById(R.id.id_password);
                EditText editTextEmail = (EditText) getActivity().findViewById(R.id.id_email);

                addUser(editTextUsername.getText().toString(), editTextPassword.getText().toString(), editTextEmail.getText().toString());
                Toast.makeText(getContext(), "User added", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    // Add to database
    public void addUser(String username, String password, String email) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("Insert into Users values ('"+ username +"', '"+ password +"', '"+ email +"');");
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
