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
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ProductCategory;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends Fragment {

    Spinner spinnerCategory;
    Spinner spinnerProductCategory;


    public ProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_product, container, false);

        // Grap category from database
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Category 1"));
        categories.add(new Category(2, "Category 2"));
        categories.add(new Category(3, "Category 3"));

        // Need update as per category
        List<ProductCategory> productCategories = new ArrayList<>();
        productCategories.add(new ProductCategory(1, 2,"Product Category 1"));
        productCategories.add(new ProductCategory(2, 1, "Product Category 2"));
        productCategories.add(new ProductCategory(3, 2,"Product Category 3"));

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
                updateProductCategorySnipper(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Product Category spinner
        spinnerProductCategory = (Spinner) view.findViewById(R.id.spinner_product_categories);
        ArrayAdapter< ProductCategory > productCategoryArrayAdapter =
                new ArrayAdapter < ProductCategory > (getActivity(), android.R.layout.simple_spinner_item, productCategories);
        productCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductCategory.setAdapter(productCategoryArrayAdapter);


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

        return view;
    }

    public void getSelectedCategory(View view) {
        Category category = (Category) spinnerCategory.getSelectedItem();
    }

    private void updateProductCategorySnipper(Category category) {
        String name = category.getCat_name();
        Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();

        // Code for update product category snipper
        // Product Category spinner
//        spinner = (Spinner) view.findViewById(R.id.spinner_product_categories);
//        ArrayAdapter< ProductCategory > productCategoryArrayAdapter =
//                new ArrayAdapter < ProductCategory > (getActivity(), android.R.layout.simple_spinner_item, productCategories);
//        productCategoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(productCategoryArrayAdapter);
    }
}
