package com.cipher.sharesmilesandroid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;
import com.cipher.sharesmilesandroid.fragments.ActivityFragment;
import com.cipher.sharesmilesandroid.fragments.HomeFragment;
import com.cipher.sharesmilesandroid.fragments.ProfileFragment;
import com.cipher.sharesmilesandroid.ui.BottomNavigationViewBehavior;
import com.cipher.sharesmilesandroid.ui.CustomBottomNavigationView;
import com.cipher.sharesmilesandroid.ui.GUIUtils;
import com.cipher.sharesmilesandroid.ui.OnRevealAnimationListener;
import com.cipher.sharesmilesandroid.utilities.DialogBoxs;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    AppCompatActivity activity = MainActivity.this;
    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;
    FrameLayout container;
    FloatingActionButton fbAddIcon;

    RelativeLayout mRlContainer;
    Toolbar tbHome;
//    LinearLayoutCompat llToolbar;

    Fragment active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mRlContainer = findViewById(R.id.mRlContainer);
        container = findViewById(R.id.container);
        fbAddIcon = findViewById(R.id.fbAddIcon);
        tbHome = findViewById(R.id.tbHome);

        setSupportActionBar(tbHome);

//            getSupportActionBar().setTitle("Main Page");

//        tbHome.inflateMenu(R.menu.logout);

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
//                        tbHome.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.categoryItem:
                        switchToFragment2();
//                        tbHome.setVisibility(View.VISIBLE);
                        break;

                    case R.id.activityItem:
                        switchToFragment2();
//                        tbHome.setVisibility(View.VISIBLE);
                        return true;

                    case R.id.profileItem:

//                        tbHome.setVisibility(View.GONE);
                        switchToFragment3();
                        return true;
                }
                return false;
            }
        });

        fbAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogBoxs dialogBoxs = new DialogBoxs();
                dialogBoxs.showDialog(activity,"as");

//                startActivity(new Intent(activity,AddProducts.class));
//                finish();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.logoutItem:
                ShareSmilesPrefs.logout(this);
                startActivity(new Intent(this,IntroActivity.class));
                break;
        }

        return  true;
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
