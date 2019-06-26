package com.example.jayso.shopnsaveadmin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.UploadImage;

import org.apache.http.HttpConnection;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    private static final int RESULT_LOAD_IMAGE = 1 ;
    Button btnAdd = null;
    Button btnShow = null;

    public CategoryFragment() {
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
        // Inflate the layout for this fragment
       View v =  inflater.inflate(R.layout.fragment_category, container, false);
       sessionCheck();

       btnAdd = (Button) v.findViewById(R.id.id_btn_add);
       btnShow = (Button) v.findViewById(R.id.id_btn_show_all);

       // Add btn
       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditText editTextCategoryName = (EditText) getActivity().findViewById(R.id.id_category_name);

               addCategory(editTextCategoryName.getText().toString(), "icon_"+ editTextCategoryName.getText().toString());
               Toast.makeText(getContext(), "Category added", Toast.LENGTH_SHORT).show();
               editTextCategoryName.setText(null);
           }
       });

        // Show all
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllCategories.class);
                startActivity(intent);
            }
        });
       return v;
    }

    // Add to database
    public void addCategory(String category_name, String category_image_name) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("Insert into Categories values ('"+ category_name +"', '"+ category_image_name +"');");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
