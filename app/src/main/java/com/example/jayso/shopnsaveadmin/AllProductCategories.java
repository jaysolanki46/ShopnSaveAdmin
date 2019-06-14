package com.example.jayso.shopnsaveadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Category;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;
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

public class AllProductCategories extends AppCompatActivity {

    static String[] tblHeader = {"Id", "Category", "P.Category"};
    static String[][] tblData;
    List<ProductCategory> productCategories = null;
    String id_delete = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_product_categories);

        final TableView<String[]> categoryTableView = (TableView<String[]>) findViewById(R.id.tableview);
        categoryTableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tblHeader));
        categoryTableView.setColumnCount(3);

        productCategories = getProductCategories();
        tblData = new String[productCategories.size()][3];

        for(int i=0; i<productCategories.size(); i++) {

            ProductCategory productCategory = productCategories.get(i);
            tblData[i][0] = productCategory.getProd_cat_id();
            tblData[i][1] = productCategory.getCat_name();
            tblData[i][2] = productCategory.getProd_cat_name();

        }
        categoryTableView.setDataAdapter(new SimpleTableDataAdapter(this, tblData));

        categoryTableView.addDataClickListener(new TableDataClickListener<String[]>() {
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

    public List<ProductCategory> getProductCategories() {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;
        productCategories = new ArrayList<>();

        try {
            result = stmt.executeQuery("select cat.*, prod_cat.* from Product_categories prod_cat LEFT JOIN Categories cat ON cat.cat_id = prod_cat.cat_id");
            while(result.next()){
                productCategories.add(new ProductCategory(
                        result.getString("prod_cat_id"),
                        result.getString("cat_id"),
                        result.getString("cat_name"),
                        result.getString("prod_cat_name"),
                        0));
            }
            conn.connectionClose();

        } catch (SQLException e) {
                e.printStackTrace();
        }

        return productCategories;
    }

    public void deleteRow(String id) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("delete from Product_categories where prod_cat_id = '"+ id +"'");
            conn.connectionClose();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
