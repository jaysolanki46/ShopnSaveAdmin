package com.example.jayso.shopnsaveadmin.com.example.jayso.shopnsaveadmin.model;

public class Category {

    private String category_id;
    private int category_image;
    private String category_name;

    public Category(){}

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public int getCategory_image() {
        return category_image;
    }

    public void setCategory_image(int category_image) {
        this.category_image = category_image;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Category(String category_id, String category_name, int category_image) {
        this.category_id = category_id;
        this.category_image = category_image;
        this.category_name = category_name;
    }

    @Override
    public String toString() {
        return category_name;
    }
}
