package com.cipher.sharesmilesandroid.activities;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bumptech.glide.Glide;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;
import com.cipher.sharesmilesandroid.fragments.ActivityFragment;
import com.cipher.sharesmilesandroid.fragments.HomeFragment;
import com.cipher.sharesmilesandroid.fragments.ProfileFragment;
import com.cipher.sharesmilesandroid.fragments.UsersAndSearchFragment;
import com.cipher.sharesmilesandroid.modals.Products;
import com.cipher.sharesmilesandroid.ui.BeautifullTextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.RelativeLayout.LayoutParams;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    AppCompatActivity activity = MainActivity.this;
    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;
    FrameLayout container;
    FloatingActionButton fbAddIcon;

    RelativeLayout mRlContainer;
    Toolbar tbHome;
    AppCompatImageView imgToolbar;

    TextSwitcher tsTitle;
    private long titleAnimDuration;
    private int countryOffset1;
    private int countryOffset2;



    private final String[] titles = {"Home", "Activity", "All Users", "Profile"};
    int animH[]=new int[] {R.anim.skide_in_left, R.anim.slide_out_right};;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        mRlContainer = findViewById(R.id.mRlContainer);
        container = findViewById(R.id.container);
        fbAddIcon = findViewById(R.id.fbAddIcon);
        tbHome = findViewById(R.id.tbHome);
        imgToolbar = findViewById(R.id.imgToolbar);

        tsTitle = findViewById(R.id.tsTitle);
        tsTitle.setForegroundGravity(Gravity.CENTER);


        setSupportActionBar(tbHome);
        switchToHomeFragment();

        animH[0] = R.anim.skide_in_left;
        animH[1] = R.anim.slide_out_right;


        titleAnimDuration = 700;
        tsTitle.setFactory(new TextViewFactory(R.style.title, true));
        tsTitle.setCurrentText(getString(R.string.app_name));

        tsTitle.setInAnimation(activity, animH[0] );
        tsTitle.setOutAnimation(activity, animH[1]);

        String userPic = ShareSmilesPrefs.readString(getApplicationContext(),ShareSmilesPrefs.userPic,null);
        if (userPic!=null){
            Glide.with(this).load(userPic).placeholder(R.drawable.ic_profile).into(imgToolbar);
        }



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
                        tsTitle.setText(getString(R.string.app_name));
                        switchToHomeFragment();
                        return true;

                    case R.id.usersItem:
                        tsTitle.setText(getString(R.string.users));
                        switchToUsersAndSearchFragment();
                        return true;

                    case R.id.activityItem:
                        tsTitle.setText(getString(R.string.title_notifications));
                        switchToActivityFragment();
                        return true;

                    case R.id.profileItem:
                        tsTitle.setText(getString(R.string.profile));
                        switchToProfileFragment();
                        return true;
                }
                return false;
            }
        });

        fbAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               ShareSmilesSingleton.getInstance().getDialogBoxs().showDialog(activity,"asd");
                startActivity(new Intent(activity,AddProducts.class));
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
                ShareSmilesPrefs.logout(activity);
                startActivity(new Intent(activity,IntroActivity.class));
                finish();
                break;
            case R.id.editProfileItem:
                startActivity(new Intent(activity,EditProfile.class));

                break;

        }

        return  true;
    }

    public void switchToHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new HomeFragment()).commit();
    }
    public void switchToActivityFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new ActivityFragment()).commit();
    }
    public void switchToProfileFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new ProfileFragment()).commit();
    }

    public void switchToUsersAndSearchFragment() {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.container, new UsersAndSearchFragment()).commit();
    }



    private class TextViewFactory implements  ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(MainActivity.this);


                textView.setGravity(Gravity.CENTER);
                textView.setForegroundGravity(Gravity.CENTER);
                textView.setHeight(100);
//                textView.setWidth(1200);
                textView.setTextAppearance(MainActivity.this, styleId);


            return textView;
        }

    }

    private class ImageViewFactory implements ViewSwitcher.ViewFactory {
        @Override
        public View makeView() {
            final ImageView imageView = new ImageView(MainActivity.this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            final FrameLayout.LayoutParams lp = new ImageSwitcher.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(lp);

            return imageView;
        }
    }
}
