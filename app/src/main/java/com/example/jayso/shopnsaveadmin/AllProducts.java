package com.example.jayso.shopnsaveadmin;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Product;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ProductCategory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class AllProducts extends AppCompatActivity {

    static String[] tblHeader = {"Id", "Name", "Pak N Save", "Coundown", "New World"};
    static String[][] tblData;
    List<Product> products = null;
    String id_delete = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_products);

        final TableView<String[]> productTableView = (TableView<String[]>) findViewById(R.id.tableview);
        productTableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tblHeader));
        productTableView.setColumnCount(5);

        products = getProducts();
        tblData = new String[products.size()][5];

        for(int i=0; i<products.size(); i++) {

            Product product = products.get(i);
            tblData[i][0] = product.getProduct_id();
            tblData[i][1] = product.getProduct_name();
            tblData[i][2] = String.valueOf(product.getProduct_pak_n_save_price());
            tblData[i][3] = String.valueOf(product.getProduct_coundown_price());
            tblData[i][4] = String.valueOf(product.getProduct_new_world_price());

        }
        productTableView.setDataAdapter(new SimpleTableDataAdapter(this, tblData));

        productTableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                String id = ((String[])clickedData)[0];
                if(!id_delete.equals(id)) {
                    id_delete = id;
                } else {

                    deleteRow(id);
                    finish();
                    startActivity(getIntent());

                }
            }
        });

    }

    public List<Product> getProducts() {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        products = new ArrayList<>();

        try {
            result = stmt.executeQuery("select prod.*, price.* from Products prod LEFT JOIN Product_prices price ON prod.prod_id = price.prod_id");
            while(result.next()){
                String price_paknsave = "";
                String price_coundown = "";
                String price_newworld = "";

                String result_price_paknsave = result.getString("pak_n_save_price");
                String result_price_coundown = result.getString("coundown_price");
                String result_price_newworld = result.getString("new_world_price");

                if(result_price_paknsave != null) {
                    price_paknsave = result.getString("pak_n_save_price");
                } else {
                    price_paknsave = "N/A";
                }

                if(result_price_coundown != null) {
                    price_coundown = result.getString("coundown_price");
                } else {
                    price_paknsave = "N/A";
                }

                if(result_price_newworld != null) {
                    price_newworld = result.getString("new_world_price");
                } else {
                    price_paknsave = "N/A";
                }

                products.add(new Product(
                        result.getString("prod_id"),
                        result.getString("cat_id"),
                        result.getString("prod_cat_id"),
                        result.getString("prod_name"),
                        result.getString("prod_store_counter"),
                        0,
                        price_paknsave,
                        price_coundown,
                        price_newworld));
            }
            conn.connectionClose();

        } catch (SQLException e) {
                e.printStackTrace();
        }

        return products;
    }

    public void deleteRow(String id) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("delete from Products where prod_id = '"+ id +"'");
            conn.connectionClose();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        conn = new ConnectionClass();
        stmt = conn.getConnection();
        result = null;

        try {
            result = stmt.executeQuery("delete from Product_prices where prod_id = '"+ id +"'");
            conn.connectionClose();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
