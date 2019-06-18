package com.example.jayso.shopnsaveadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Category;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Product;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class UpdateProductActivity extends AppCompatActivity {

    List<Product> products = null;
    Spinner spinnerProduct;
    Integer tbl_prod_store_counter = 0;
    Button btnUpdate;
    Product product;
    String tbl_pak_n_save_price = null;
    String tbl_coundown_price = null;
    String tbl_new_world_price = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        final CheckBox chkbx_pak_n_save = (CheckBox) findViewById(R.id.checkbox_pak_n_save);
        final CheckBox chkbx_coundown = (CheckBox) findViewById(R.id.checkbox_coundown);
        final CheckBox chkbx_new_world = (CheckBox) findViewById(R.id.checkbox_new_world);
        final EditText editText_paknsave = (EditText) findViewById(R.id.edittext_pak_n_save);
        final EditText editText_coundown = (EditText) findViewById(R.id.edittext_coundown);
        final EditText editText_newworld = (EditText) findViewById(R.id.edittext_new_world);

        products = getProducts();

        spinnerProduct = (Spinner) findViewById(R.id.spinner_products);
        ArrayAdapter< Product > productArrayAdapter =
                new ArrayAdapter < Product > (this, android.R.layout.simple_spinner_item, products);
        productArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProduct.setAdapter(productArrayAdapter);

        spinnerProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

                product = (Product) parent.getSelectedItem();
                tbl_prod_store_counter = 0;

                if(!product.getProduct_pak_n_save_price().equals("N/A")){
                    tbl_prod_store_counter += 1;
                    editText_paknsave.setText(product.getProduct_pak_n_save_price());
                    editText_paknsave.setVisibility(VISIBLE);
                    chkbx_pak_n_save.setChecked(true);
                } else {
                    editText_paknsave.setVisibility(INVISIBLE);
                    chkbx_pak_n_save.setChecked(false);
                }

                if(!product.getProduct_coundown_price().equals("N/A")){
                    tbl_prod_store_counter += 1;
                    editText_coundown.setText(product.getProduct_coundown_price());
                    editText_coundown.setVisibility(VISIBLE);
                    chkbx_coundown.setChecked(true);
                } else {
                    editText_coundown.setVisibility(INVISIBLE);
                    chkbx_coundown.setChecked(false);
                }

                if(!product.getProduct_new_world_price().equals("N/A")){
                    tbl_prod_store_counter += 1;
                    editText_newworld.setText(product.getProduct_new_world_price());
                    editText_newworld.setVisibility(VISIBLE);
                    chkbx_new_world.setChecked(true);
                } else {
                    editText_newworld.setVisibility(INVISIBLE);
                    chkbx_new_world.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        chkbx_pak_n_save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            tbl_prod_store_counter += 1;
                            editText_paknsave.setVisibility(VISIBLE);
                        } else {
                            tbl_prod_store_counter -= 1;
                            editText_paknsave.setVisibility(INVISIBLE);
                            editText_paknsave.setText(null);
                        }
                    }
                });

        chkbx_coundown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            tbl_prod_store_counter += 1;
                            editText_coundown.setVisibility(VISIBLE);
                        } else {
                            tbl_prod_store_counter -= 1;
                            editText_coundown.setVisibility(INVISIBLE);
                            editText_coundown.setText(null);
                        }
                    }
                });
        chkbx_new_world.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(isChecked){
                            tbl_prod_store_counter += 1;
                            editText_newworld.setVisibility(VISIBLE);
                        } else {
                            tbl_prod_store_counter -= 1;
                            editText_newworld.setVisibility(INVISIBLE);
                            editText_newworld.setText(null);
                        }
                    }
                });

        btnUpdate = (Button) findViewById(R.id.id_btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editText_paknsave.getText().toString().isEmpty()) {
                    tbl_pak_n_save_price = editText_paknsave.getText().toString();
                } else {
                    tbl_pak_n_save_price = "N/A";
                }
                if(!editText_coundown.getText().toString().isEmpty()) {
                    tbl_coundown_price = editText_coundown.getText().toString();
                } else {
                    tbl_coundown_price = "N/A";
                }
                if(!editText_newworld.getText().toString().isEmpty()) {
                    tbl_new_world_price = editText_newworld.getText().toString();
                } else {
                    tbl_new_world_price = "N/A";
                }

                updateProductStoreCounter(product.getProduct_id(), tbl_prod_store_counter);
                updateProductPrice(product.getProduct_id(), tbl_pak_n_save_price, tbl_coundown_price, tbl_new_world_price);
                Toast.makeText(getApplicationContext(), "Product updated", Toast.LENGTH_LONG).show();
            }
        });


    }

    public List<Product> getProducts() {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        products = new ArrayList<>();
        try {
            result = stmt.executeQuery("select prod.*, prodprice.* from Products prod LEFT JOIN Product_prices prodprice ON prod.prod_id = prodprice.prod_id");
            while(result.next()){

                products.add(new Product(
                        result.getString("prod_id"),
                        result.getString("cat_id"),
                        result.getString("prod_cat_id"),
                        result.getString("prod_name"),
                        result.getString("prod_store_counter"),
                        0,
                        result.getString("pak_n_save_price"),
                        result.getString("coundown_price"),
                        result.getString("new_world_price")
                ));
            }
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProductStoreCounter(String prod_id, Integer counter) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        try {
            result = stmt.executeQuery("Update Products set prod_store_counter = '"+ counter +"' where prod_id = "+ prod_id +"");
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProductPrice(String prod_id, String tbl_pak_n_save_price, String tbl_coundown_price, String tbl_new_world_price) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        try {
            result = stmt.executeQuery("Update Product_prices set pak_n_save_price = '"+ tbl_pak_n_save_price +"', coundown_price = '"+ tbl_coundown_price +"', new_world_price = '"+ tbl_new_world_price +"' where prod_id = "+ prod_id +"");
            conn.connectionClose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
