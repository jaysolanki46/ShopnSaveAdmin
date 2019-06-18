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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Category;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ProductCategory;
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
public class ProductFragment extends Fragment {

    Spinner spinnerCategory;
    Spinner spinnerProductCategory;
    List<Category> categories = null;
    List<ProductCategory> productCategories = null;
    Button btnShow = null;
    Button btnUpdate = null;
    ImageView btnImage = null;
    private static final int RESULT_LOAD_IMAGE = 1 ;

    String tbl_category_id = null;
    String tbl_prod_cat_id = null;
    String tbl_prod_name = null;
    Integer tbl_prod_store_counter = 0;
    String tbl_prod_image = null;
    String tbl_pak_n_save_price = null;
    String tbl_coundown_price = null;
    String tbl_new_world_price = null;

    public void sessionCheck() {
        SharedPreferences prefs = getContext().getSharedPreferences("preferences-details", MODE_PRIVATE);
        String username = prefs.getString("username", "blank");
        if(username.equals("blank")){
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    public ProductFragment() {
        // Required empty public constructor
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

    public List<ProductCategory> getProductCategories(String cat_id) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        productCategories = new ArrayList<>();
        try {
            if(cat_id == "all") {
                result = stmt.executeQuery("select * from Product_categories");
            } else {
                result = stmt.executeQuery("select * from Product_categories where cat_id ="+ cat_id +"");
            }

            while(result.next()){

                productCategories.
                        add(new ProductCategory(
                                result.getString("prod_cat_id"),
                                result.getString("cat_id"),
                                "",
                                result.getString("prod_cat_name"),
                                0));
            }
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productCategories;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sessionCheck();
        final View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Listing categories
        categories = getCategories();

             // Category spinner
            spinnerCategory = (Spinner) view.findViewById(R.id.spinner_categories);
            spinnerProductCategory = (Spinner) view.findViewById(R.id.spinner_product_categories);
            ArrayAdapter< Category > categoryArrayAdapter =
                    new ArrayAdapter < Category > (getActivity(), android.R.layout.simple_spinner_item, categories);
            categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerCategory.setAdapter(categoryArrayAdapter);

            spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Category category = (Category) parent.getSelectedItem();
                    tbl_category_id = category.getCategory_id();

                    productCategories = getProductCategories(tbl_category_id);
                    ArrayAdapter< ProductCategory > productCategoryArrayAdapter =
                            new ArrayAdapter < ProductCategory > (getActivity(), android.R.layout.simple_spinner_item, productCategories);
                    productCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerProductCategory.setAdapter(productCategoryArrayAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

                spinnerProductCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        ProductCategory productCategory = (ProductCategory) parent.getSelectedItem();
                        tbl_prod_cat_id = productCategory.getProd_cat_id();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        CheckBox checkBox_paknsave = (CheckBox) view.findViewById(R.id.checkbox_pak_n_save);
        CheckBox checkBox_coundown = (CheckBox) view.findViewById(R.id.checkbox_coundown);
        CheckBox checkBox_newworld = (CheckBox) view.findViewById(R.id.checkbox_new_world);

        final EditText editText_paknsave = (EditText) view.findViewById(R.id.edittext_pak_n_save);
        final EditText editText_coundown = (EditText) view.findViewById(R.id.edittext_coundown);
        final EditText editText_newworld = (EditText) view.findViewById(R.id.edittext_new_world);

        checkBox_paknsave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tbl_prod_store_counter += 1;
                    editText_paknsave.setVisibility(view.VISIBLE);
                } else {
                    tbl_prod_store_counter -= 1;
                    editText_paknsave.setVisibility(view.INVISIBLE);
                }
            }
        });
        checkBox_coundown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tbl_prod_store_counter += 1;
                    editText_coundown.setVisibility(view.VISIBLE);
                } else {
                    tbl_prod_store_counter -= 1;
                    editText_coundown.setVisibility(view.INVISIBLE);
                }
            }
        });
        checkBox_newworld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tbl_prod_store_counter += 1;
                    editText_newworld.setVisibility(view.VISIBLE);
                } else {
                    tbl_prod_store_counter -= 1;
                    editText_newworld.setVisibility(view.INVISIBLE);
                }
            }
        });

        Button btnAdd = (Button) view.findViewById(R.id.id_btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText prod_name= (EditText) getActivity().findViewById(R.id.edittext_product_name);
                EditText pak_n_save_price = (EditText) getActivity().findViewById(R.id.edittext_pak_n_save);
                EditText coundown_price= (EditText) getActivity().findViewById(R.id.edittext_coundown);
                EditText new_world_price= (EditText) getActivity().findViewById(R.id.edittext_new_world);

                tbl_prod_name = prod_name.getText().toString();

                if(!pak_n_save_price.getText().toString().isEmpty()) {
                    tbl_pak_n_save_price = pak_n_save_price.getText().toString();
                } else {
                    tbl_pak_n_save_price = "N/A";
                }
                if(!coundown_price.getText().toString().isEmpty()) {
                    tbl_coundown_price = coundown_price.getText().toString();
                } else {
                    tbl_coundown_price = "N/A";
                }
                if(!new_world_price.getText().toString().isEmpty()) {
                    tbl_new_world_price = new_world_price.getText().toString();
                } else {
                    tbl_new_world_price = "N/A";
                }

                Bitmap image = ((BitmapDrawable) btnImage.getDrawable()).getBitmap();
                tbl_prod_image = "icon_"+ tbl_prod_name;
                new UploadImage(image, tbl_prod_image).execute();

                addProduct(tbl_category_id, tbl_prod_cat_id, tbl_prod_name, tbl_prod_store_counter, tbl_prod_image);
                addProductPrice(tbl_pak_n_save_price, tbl_coundown_price, tbl_new_world_price);
                Toast.makeText(getContext(), "Product added", Toast.LENGTH_SHORT).show();
            }
        });

        // Show all
        btnShow = (Button) view.findViewById(R.id.id_btn_show_all);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AllProducts.class);
                startActivity(intent);
            }
        });

        btnImage = (ImageView) view.findViewById(R.id.id_product_image);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        btnUpdate = (Button) view.findViewById(R.id.id_btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UpdateProductActivity.class);
                startActivity(intent);
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

    public void addProduct(String tbl_category_id, String tbl_prod_cat_id, String tbl_prod_name, Integer tbl_prod_store_counter, String tbl_prod_image) {

        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        categories = new ArrayList<>();
        try {
            result = stmt.executeQuery("Insert into Products values ('"+ tbl_category_id +"', '"+ tbl_prod_cat_id +"', '"+ tbl_prod_name +"', '"+ tbl_prod_store_counter +"', '"+ tbl_prod_image +"')");
            conn.connectionClose();
        } catch (Exception e) {

        }
    }

    public void addProductPrice(String tbl_pak_n_save_price, String tbl_coundown_price, String tbl_new_world_price) {

        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        categories = new ArrayList<>();
        String tbl_prod_id = null;
        try {
            result = stmt.executeQuery("SELECT TOP 1 * FROM Products ORDER BY prod_id DESC");
            while(result.next()){
                tbl_prod_id = result.getString("prod_id");
            }

            result = stmt.executeQuery("Insert into Product_prices values ('"+ tbl_prod_id +"', '"+ tbl_pak_n_save_price +"', '"+  tbl_coundown_price+"', '"+ tbl_new_world_price +"')");

            conn.connectionClose();
        } catch (Exception e) {

        }
    }
}
