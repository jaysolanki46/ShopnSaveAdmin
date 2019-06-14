package com.example.jayso.shopnsaveadmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.Category;
import com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model.ConnectionClass;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class AllCategories  extends AppCompatActivity {

    static String[] tblHeader = {"Id", "Category"};
    static String[][] tblData;
    List<Category> categories = null;
    String id_delete = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_categories);

        final TableView<String[]> categoryTableView = (TableView<String[]>) findViewById(R.id.tableview);
        categoryTableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, tblHeader));
        categoryTableView.setColumnCount(2);

        categories = getCategories();
        tblData = new String[categories.size()][2];

        for(int i=0; i<categories.size(); i++) {

            Category category = categories.get(i);
            tblData[i][0] = category.getCategory_id();
            tblData[i][1] = category.getCategory_name();
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

        } catch (SQLException e) {
                e.printStackTrace();
        }

        return categories;
    }

    public void deleteRow(String id) {
        ConnectionClass conn = new ConnectionClass();
        Statement stmt = conn.getConnection();
        ResultSet result = null;

        try {
            result = stmt.executeQuery("delete from Categories where cat_id = '"+ id +"'");
            conn.connectionClose();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
