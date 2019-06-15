package com.example.jayso.shopnsaveadmin;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Category;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.UploadImage;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductCategoryFragment extends Fragment {

    Spinner spinner;
    List<Category> categories = null;
    String category_id = null;
    Button btnAdd = null;
    Button btnShow = null;
    ImageView btnImage = null;
    private static final int RESULT_LOAD_IMAGE = 1 ;

    public ProductCategoryFragment() {
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

    public List<Category> getCategories() {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        categories = new ArrayList<>();
        try {
            result = stmt.executeQuery("select * from Categories");
            while(result.next()){

                categories.add(new Category(
                        result.getString("cat_id"),
                        result.getString("cat_name"), 0));
            }
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sessionCheck();
        // List categories
        categories = getCategories();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_category, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner_categories);
        ArrayAdapter < Category > adapter =
                new ArrayAdapter < Category > (getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getSelectedItem();
                category_id = category.getCategory_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAdd = (Button) view.findViewById(R.id.id_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextProductCategoryName = (EditText) getActivity().findViewById(R.id.id_product_category_name);
                Bitmap image = ((BitmapDrawable) btnImage.getDrawable()).getBitmap();
                new UploadImage(image, "icon_"+ editTextProductCategoryName.getText().toString()).execute();

                addProductCategory(category_id, editTextProductCategoryName.getText().toString(), "icon_"+ editTextProductCategoryName.getText().toString());
                Toast.makeText(getContext(), "Product category added", Toast.LENGTH_SHORT).show();
            }
        });

        // Show all
        btnShow = (Button) view.findViewById(R.id.id_btn_show_all);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProductCategories.class);
                startActivity(intent);
            }
        });

        btnImage = (ImageView) view.findViewById(R.id.id_product_category_image);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });
        return view;
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
    public void addProductCategory(String product_category_id, String product_category_name, String product_category_image_name) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("Insert into Product_categories values ('"+ product_category_id +"','"+ product_category_name +"', '"+ product_category_image_name +"');");
        } catch (Exception e) {

        }
    }
}
