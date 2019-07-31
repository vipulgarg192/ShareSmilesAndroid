package com.cipher.sharesmilesandroid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.fragments.ActivityFragment;
import com.cipher.sharesmilesandroid.fragments.HomeFragment;
import com.cipher.sharesmilesandroid.fragments.ProfileFragment;
import com.cipher.sharesmilesandroid.ui.BottomNavigationViewBehavior;
import com.cipher.sharesmilesandroid.ui.CustomBottomNavigationView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    CustomBottomNavigationView bottomNavigationView;
    FrameLayout container;
//    LinearLayoutCompat llToolbar;

    Fragment active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        container = findViewById(R.id.container);
//        llToolbar = findViewById(R.id.llToolbar);


//        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) bottomNavigationView.getLayoutParams();
//        layoutParams.setBehavior(new BottomNavigationViewBehavior());


        switchToFragment1();

        /*final Fragment fragment1 = new HomeFragment();
        final Fragment fragment2 = new ActivityFragment();
        final Fragment fragment3 = new ProfileFragment();
        final FragmentManager fm = getSupportFragmentManager();
        active = fragment1;

        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container,fragment1, "1").commit();*/



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.homeItem:
                        switchToFragment1();
                        return true;

                    case R.id.activityItem:
                        switchToFragment2();
                        return true;

                    case R.id.profileItem:
                        switchToFragment3();
                        return true;
                }
                return false;
            }
        });




    }
    public void switchToFragment1() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }
    public void switchToFragment2() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new ActivityFragment()).commit();
    }
    public void switchToFragment3() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
    }

}
