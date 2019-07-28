package com.cipher.sharesmilesandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginActivity extends BaseActivity {
    AppCompatActivity activity = LoginActivity.this;
    ImageView imgLogin;
    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    TextInputLayout email;

    CallbackManager callbackManager;



    private final String PUBLICPROFILE = "public_profile";
    private final String EMAIL = "email";

    LoginButton loginButton;

    private static final String TAG = "LoginActivity";

    CircleImageView img;


    LoginManager loginManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        LoginManager.getInstance().logOut();
        ButterKnife.bind(activity);

        TextInputLayout inputLayout = (TextInputLayout) findViewById(R.id.txlEmail);
        inputLayout.setError("First name is required");

//        etEmail = findViewById(R.id.etEmail);
//        email =(TextInputLayout) findViewById(R.id.txlEmail);
//        email.setErrorEnabled(true);
//        email.setError("asdasdsdsd");

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();

        loginButton = (LoginButton) findViewById(R.id.login_button);

        img = findViewById(R.id.img);

        img.setImageResource(R.drawable.splash_logo_bg);

        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        loginButton.setPermissions(Arrays.asList(EMAIL,PUBLICPROFILE));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                getFbInfo();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getFbInfo() {
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            Log.d(TAG, "fb json object: " + object);
                            Log.d(TAG, "fb graph response: " + response);

                            String id = object.getString("id");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
//                            String gender = object.getString("gender");
//                            String birthday = object.getString("birthday");
//                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
//
//                            String email;
//                            if (object.has("email")) {
//                                email = object.getString("email");
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday,is_verified,link,is_shared_login,verified"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void init() {
        super.init();
//        imgLogin = findViewById(R.id.imgLogin);
//        etEmail = findViewById(R.id.etEmail);
//        email =(TextInputLayout) findViewById(R.id.txlEmail);
//        email.setErrorEnabled(true);
//        email.setError("asdasdsdsd");
//        etPassword = findViewById(R.id.etPassword);
//        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (v.getId()== R.id.btnFB){
            loginButton.performClick();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
