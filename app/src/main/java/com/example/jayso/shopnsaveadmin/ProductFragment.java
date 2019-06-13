package com.example.jayso.shopnsaveadmin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Category;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ProductCategory;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    Spinner spinnerCategory;
    Spinner spinnerProductCategory;
    List<Category> categories = null;
    List<ProductCategory> productCategories = null;

    String tbl_category_id = null;
    String tbl_prod_cat_id = null;
    String tbl_prod_name = null;
    String tbl_prod_store_counter = null;
    String tbl_prod_image = null;
    String tbl_pak_n_save_price = null;
    String tbl_coundown_price = null;
    String new_world_price = null;


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

        }

        return categories;
    }

    public List<ProductCategory> getProductCategories(String cat_id) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        productCategories = new ArrayList<>();
        try {
            result = stmt.executeQuery("select * from Product_categories where cat_id ="+ cat_id +"");
            while(result.next()){

                productCategories.
                        add(new ProductCategory(
                                result.getString("prod_cat_id"),
                                result.getString("cat_id"),
                                result.getString("prod_cat_name"),
                                0));
            }
            conn.connectionClose();
        } catch (Exception e) {

        }
        return productCategories;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Listing categories
        categories = getCategories();

        // Category spinner
        spinnerCategory = (Spinner) view.findViewById(R.id.spinner_categories);
        ArrayAdapter< Category > categoryArrayAdapter =
                new ArrayAdapter < Category > (getActivity(), android.R.layout.simple_spinner_item, categories);
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryArrayAdapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) parent.getSelectedItem();
                tbl_category_id = category.getCategory_id();
                populateProductCategories(category);
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
                    editText_paknsave.setVisibility(view.VISIBLE);
                } else {
                    editText_paknsave.setVisibility(view.INVISIBLE);
                }
            }
        });
        checkBox_coundown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editText_coundown.setVisibility(view.VISIBLE);
                } else {
                    editText_coundown.setVisibility(view.INVISIBLE);
                }
            }
        });
        checkBox_newworld.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editText_newworld.setVisibility(view.VISIBLE);
                } else {
                    editText_newworld.setVisibility(view.INVISIBLE);
                }
            }
        });


        // make btn call listerner
        return view;
    }

    private void populateProductCategories(Category category) {

        productCategories = getProductCategories(category.getCategory_id());
        spinnerProductCategory = (Spinner) getActivity().findViewById(R.id.spinner_product_categories);
        ArrayAdapter< ProductCategory > productCategoryArrayAdapter =
                new ArrayAdapter < ProductCategory > (getActivity(), android.R.layout.simple_spinner_item, productCategories);
        productCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductCategory.setAdapter(productCategoryArrayAdapter);
    }
}
