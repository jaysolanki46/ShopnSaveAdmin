package com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model;

public class ProductCategory {

    private int prod_cat_id;
    private int cat_id;
    private String prod_cat_name;

    public ProductCategory(){}

    public ProductCategory(int prod_cat_id, int cat_id, String prod_cat_name) {
        this.prod_cat_id = prod_cat_id;
        this.cat_id = cat_id;
        this.prod_cat_name = prod_cat_name;
    }

    public int getProd_cat_id() {
        return prod_cat_id;
    }

    public void setProd_cat_id(int prod_cat_id) {
        this.prod_cat_id = prod_cat_id;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getProd_cat_name() {
        return prod_cat_name;
    }

    public void setProd_cat_name(String prod_cat_name) {
        this.prod_cat_name = prod_cat_name;
    }

    @Override
    public String toString() {
        return prod_cat_name;
    }
}
