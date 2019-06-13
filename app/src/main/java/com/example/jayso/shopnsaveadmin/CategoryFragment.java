package com.example.jayso.shopnsaveadmin;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {


    private static final int RESULT_LOAD_IMAGE = 1 ;
    Button btnAdd = null;
    ImageView btnImage = null;


    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v =  inflater.inflate(R.layout.fragment_category, container, false);
       btnAdd = (Button) v.findViewById(R.id.id_btn_add);
       btnImage = (ImageView) v.findViewById(R.id.id_category_image);

       // Upload Image
       btnImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
               startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
           }
       });

       // Add btn
       btnAdd.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               EditText editTextCategoryName = (EditText) getActivity().findViewById(R.id.id_category_name);
               addCategory(editTextCategoryName.getText().toString(), "");
               Toast.makeText(getContext(), "Category added", Toast.LENGTH_SHORT).show();
           }
       });
       return v;
    }

    // Show image on ImageView
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            btnImage.setImageURI(selectedImage);

        }
    }

    // Add to database
    public void addCategory(String category_name, String category_image_name) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("Insert into Categories values ('"+ category_name +"', '"+ category_image_name +"');");
        } catch (Exception e) {

        }
    }
}
