package com.cipher.sharesmilesandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cipher.sharesmilesandroid.activities.MainActivity;
import com.cipher.sharesmilesandroid.interfaces.FacebookInteface;
import com.cipher.sharesmilesandroid.modals.Users;
import com.cipher.sharesmilesandroid.ui.FacebookData;
import com.cipher.sharesmilesandroid.utilities.ProgressDialog;
import com.cipher.sharesmilesandroid.utilities.Validations;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;


public class LoginActivity extends BaseActivity  {

    AppCompatActivity activity = LoginActivity.this;
    CallbackManager callbackManager;

    private final String PUBLICPROFILE = "public_profile";
    private final String EMAIL = "email";


    private static final String TAG = "LoginActivity";

    TextInputLayout tilEmail;
    TextInputLayout tilPassword;

    EditText etEmail;
    EditText etPassword;

    MaterialButton btnSignIn;
    MaterialButton btnFB;
    LoginButton login_button;

    FirebaseAuth auth;


    LoginManager loginManager;

    private FirebaseFirestore firebaseFirestore ;
    private FirebaseFirestore dRef ;

    String profileImage ="" , description="" , dob="",address="",city="",zipcode="";
    private AVLoadingIndicatorView avlLoader;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(activity);

        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnFB = findViewById(R.id.btnFB);
        login_button = findViewById(R.id.login_button);
        avlLoader = findViewById(R.id.avlLoader);
        avlLoader.setVisibility(View.GONE);

        LoginManager.getInstance().logOut();

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbarHome =findViewById(R.id.tbHome);
        toolbarHome.setVisibility(View.GONE);

        Toolbar toolbar =findViewById(R.id.tbSimple);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LoginButton login_button = findViewById(R.id.login_button);
        login_button.setPermissions(Arrays.asList(EMAIL,PUBLICPROFILE));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
//                getFbInfo();
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                progressDialog.dismiss();
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                progressDialog.dismiss();
                // App code
            }
        });

    }

    private void handleFacebookAccessToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = auth.getCurrentUser();
                            addDataFBToFireStone(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                            progressDialog.dismiss();

                        }

                    }
                });
    }

    private void addDataFBToFireStone(FirebaseUser user) {

        String userId = user.getUid();
        String pic = String.valueOf(user.getPhotoUrl());
        Log.e(TAG, "pic: "+pic );
        Log.e(TAG, "getUid: "+userId );
        String[] names = user.getDisplayName().split(" ");
        String firstName = names[0];
        String lastName = "";
        if (names.length>1){
            lastName = names[1];
        }
        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("userId",user.getUid());
        userMap.put("email",user.getEmail());
        userMap.put("firstName", firstName);
        userMap.put("lastName",lastName);
        userMap.put("profilePic",pic);
        userMap.put("socialMedia",true);

        FirebaseFirestore dRef = FirebaseFirestore.getInstance();
        DocumentReference usersReference = dRef.collection("users").document(userId);
        usersReference.set(userMap,SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ShareSmilesPrefs.writeBool(activity,ShareSmilesPrefs.isLogin,true);
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.emailId,user.getEmail());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userName,user.getDisplayName());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userId,user.getUid());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userPic,pic);

                progressDialog.dismiss();
                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "failure: " +e.getMessage());
                progressDialog.dismiss();
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

//    private void getFbInfo() {
//        GraphRequest request = GraphRequest.newMeRequest(
//                AccessToken.getCurrentAccessToken(),
//                new GraphRequest.GraphJSONObjectCallback() {
//                    @Override
//                    public void onCompleted(
//                            JSONObject object,
//                            GraphResponse response) {
//                        try {
//                            Log.d(TAG, "fb json object: " + object);
//                            Log.d(TAG, "fb graph response: " + response);
//
//                            String id = object.getString("id");
//                            String first_name = object.getString("first_name");
//                            String last_name = object.getString("last_name");
//                            String gender = object.getString("gender");
//
//                            String birthday = "";
//                            if (object.has("birthday")) {
//                                birthday = object.getString("birthday");
//                            }
//
//                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
//                            String email = "";
//                            if (object.has("email")) {
//                                email = object.getString("email");
//                            }
//
//                            Users users = new Users();
//                            users.setUserID(id);
//                            users.setFirstName(first_name);
//                            users.setLastName(last_name);
//                            users.setEmail(email);
//                            users.setGender(gender);
//                            users.setDob(birthday);
//                            users.setUserImage(image_url);
//
//                           setFBInfo(users);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//        Bundle parameters = new Bundle();
//        parameters.putString("fields", "id,first_name,last_name,email,gender,birthday,is_verified,link,is_shared_login,verified"); // id,first_name,last_name,email,gender,birthday,cover,picture.type(large)
//        request.setParameters(parameters);
//        request.executeAsync();
//    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();

        switch (id){
            case R.id.btnSignIn:
                tilEmail.setError(null);
                tilPassword.setError(null);
                progressDialog.show();
                login();
                break;

            case R.id.tvForgetPWD:
                break;

            case R.id.btnFB:
                progressDialog.show();
                login_button.performClick();
                break;
            default:
                break;
        }
    }

    private void login() {

        if (isValid()){
          logIn(etEmail.getText().toString(), etPassword.getText().toString());
        }else {
            progressDialog.dismiss();
        }
    }


    private boolean isValid() {
        if (etEmail.getText().toString().isEmpty()){
            tilEmail.setError("Please Enter Email");
            return  false;
        }else if (!Validations.isValidEmailId(etEmail.getText().toString())){
            tilEmail.setError("Please Enter Valid Email");
            return  false;
        }
        else if (etPassword.getText().equals("")){
            tilPassword.setError("Please enter password");
            return false;
        }else if (etPassword.getText().length()<=6) {
            tilPassword.setError("password length should me more than 6");
            return false;
        }
//        }else if (Validations.isValidPassword(etPassword.getText())){
//            tilPassword.setError("Please enter valid password");
//            return false;
//        }
        return  true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


//    public void setFBInfo(Users users) {
//        auth.createUserWithEmailAndPassword(users.getEmail(), users.getEmail())
//                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        // If sign in fails, display a message to the user. If sign in succeeds
//                        // the auth state listener will be notified and logic to handle the
//                        // signed in user can be handled in the listener.
//                        if (!task.isSuccessful()) {
//                            Log.e(TAG, "onComplete: "+"Authentication failed." + task.getException().getMessage());
//                            if (task.getException().getMessage().contains("The email address is already in use by another account")){
//                                logIn(users.getEmail(),users.getEmail());
//                            }
//                        } else {
//                            startActivity(new Intent(activity, MainActivity.class));
//                            finish();
//                        }
//                    }
//                });
//    }

    public void logIn(String email , String password){
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            // there was an error
                            Log.e(TAG, "onComplete: "+task.getException().getMessage() );
                            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,"Wrong credentials Entered");
                            etEmail.setText("");
                            etPassword.setText("");
                            progressDialog.dismiss();

                        } else {
                            getProfileData(auth.getUid(),email);
                        }
                    }
                });
    }


    private void getProfileData(String uid, String email) {

        DocumentReference docRef = firebaseFirestore.collection("users").document(uid);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (!document.getData().isEmpty()){

                            String  firstName =  document.getData().get("firstName").toString();
                            String  lastName =  document.getData().get("lastName").toString();

                            if (document.getData().get("profilePic")!=null){
                                profileImage =  document.getData().get("profilePic").toString();
                            }

                            if (document.getData().get("description")!=null){
                                description =  document.getData().get("description").toString();
                            }

                            if (document.getData().get("dob")!=null){
                                dob =  document.getData().get("dob").toString();
                            }

                            if (document.getData().get("address")!=null){
                                address =  document.getData().get("address").toString();
                            }

                            if (document.getData().get("city")!=null){
                                city =  document.getData().get("city").toString();
                            }

                            if (document.getData().get("zipcode")!=null){
                                zipcode =  document.getData().get("zipcode").toString();
                            }

                            ShareSmilesPrefs.writeBool(activity,ShareSmilesPrefs.isLogin,true);
                            ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.emailId,email);
                            ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userName,firstName+" "+lastName);
                            ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userId,uid);
                            ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userPic,profileImage);

                            Log.e(TAG, "onComplete:profileImage "+profileImage );
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                            finish();



                        }
                    } else {
                        Log.d(TAG, "No such document");
                        progressDialog.dismiss();
                    }
                } else {
                    progressDialog.dismiss();
                    ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,task.getException().getMessage());
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDialog.dismiss();
    }
}
