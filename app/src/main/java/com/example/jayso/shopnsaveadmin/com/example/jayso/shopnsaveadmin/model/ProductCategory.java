package com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model;

public class ProductCategory {

    private String prod_cat_id;
    private String cat_id;
    private String cat_name;
    private String prod_cat_name;
    private int prod_cat_image;

    public ProductCategory() {}

    public ProductCategory(String prod_cat_id, String cat_id, String cat_name, String prod_cat_name, int prod_cat_image) {
        this.prod_cat_id = prod_cat_id;
        this.cat_id = cat_id;
        this.cat_name = cat_name;
        this.prod_cat_name = prod_cat_name;
        this.prod_cat_image = prod_cat_image;
    }

    public String getProd_cat_id() {
        return prod_cat_id;
    }

    public void setProd_cat_id(String prod_cat_id) {
        this.prod_cat_id = prod_cat_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getProd_cat_name() {
        return prod_cat_name;
    }

    public void setProd_cat_name(String prod_cat_name) {
        this.prod_cat_name = prod_cat_name;
    }

    public int getProd_cat_image() {
        return prod_cat_image;
    }

    public void setProd_cat_image(int prod_cat_image) {
        this.prod_cat_image = prod_cat_image;
    }

    @Override
    public String toString() {
        return prod_cat_name;
    }
}