package com.example.jayso.shopnsaveadmin;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private HomeFragment homeFragment;
    private CategoryFragment categoryFragment;
    private ProductCategoryFragment productCategoryFragment;
    private ProductFragment productFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        frameLayout = (FrameLayout) findViewById(R.id.main_frame);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_menu);

        homeFragment = new HomeFragment();
        categoryFragment = new CategoryFragment();
        productCategoryFragment = new ProductCategoryFragment();
        productFragment = new ProductFragment();

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
}
