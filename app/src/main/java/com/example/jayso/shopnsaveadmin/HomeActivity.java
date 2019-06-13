package com.example.jayso.shopnsaveadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private ProductCategoryFragment productCategoryFragment;
    private ProductFragment productFragment;
    private UserFragment userFragment;

    public void sessionCheck() {
        SharedPreferences prefs = getBaseContext().getSharedPreferences("preferences-details", MODE_PRIVATE);
        String username = prefs.getString("username", "blank");
        if(username.equals("blank")){
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //sessionCheck();
        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_menu);

        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        productCategoryFragment = new ProductCategoryFragment();
        productFragment = new ProductFragment();
        userFragment = new UserFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.nav_home :
                        setFragment(homeFragment);
                        return true;

                    case R.id.nav_category :
                        setFragment(categoryFragment);
                        return true;

                    case R.id.nav_product_category :
                        setFragment(productCategoryFragment);
                        return true;

                    case R.id.nav_product :
                        setFragment(productFragment);
                        return true;

                    case R.id.nav_user :
                        setFragment(userFragment);
                        return true;

                        default:
                            return  false;
                }
            }
        });
    }

    public void setFragment(Fragment fragment) {

        FragmentTransaction transaction =  getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame,  fragment);
        transaction.commit();
    }

    public void redirectLogIn() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("preferences-details", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("username");
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_logout:
                redirectLogIn();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
