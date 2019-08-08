package com.cipher.sharesmilesandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cipher.sharesmilesandroid.BaseActivity;
import com.cipher.sharesmilesandroid.R;
import com.cipher.sharesmilesandroid.ShareSmilesPrefs;
import com.cipher.sharesmilesandroid.ShareSmilesSingleton;
import com.cipher.sharesmilesandroid.utilities.Validations;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import butterknife.BindView;

public class RegisterActivity extends BaseActivity {

    AppCompatActivity activity = RegisterActivity.this;
    private static final String TAG = "RegisterActivity";

    TextInputLayout tilFirstName;
    TextInputLayout tilLastName;
    TextInputLayout tilEmail;
    TextInputLayout tilPassword;
    TextInputLayout tilCPassword;
    TextInputLayout tilPhone;

    TextInputEditText etFirstName;
    TextInputEditText etLastName;
    TextInputEditText etEmail;
    TextInputEditText etPassword;
    TextInputEditText etCPassword;
    TextInputEditText etPhone;


    LoginManager loginManager;
    CallbackManager callbackManager;
    LoginButton login_button;

    private final String PUBLICPROFILE = "public_profile";
    private final String EMAIL = "email";

    FirebaseAuth auth;
    FirebaseFirestore dRef = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        login_button = findViewById(R.id.login_button);
        login_button.setPermissions(Arrays.asList(EMAIL,PUBLICPROFILE));
        // If you are using in a fragment, call loginButton.setFragment(this);
        // Callback registration
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, "FacebookException onSuccess: "+loginResult.getAccessToken() );
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                // App code
                Log.e(TAG, "FacebookException onCancel: " );
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e(TAG, "FacebookException onError: "+exception );
            }
        });

        auth = FirebaseAuth.getInstance();
        init();
    }


    public void init() {


         tilFirstName = findViewById(R.id.tilFirstName);
         tilLastName = findViewById(R.id.tilLastName);
         tilEmail = findViewById(R.id.tilEmail);
         tilPassword = findViewById(R.id.tilPassword);
         tilCPassword = findViewById(R.id.tilCPassword);
         tilPhone = findViewById(R.id.tilPhone);

         etFirstName = findViewById(R.id.etFirstName);
         etLastName = findViewById(R.id.etLastName);
         etEmail = findViewById(R.id.etEmail);
         etPassword = findViewById(R.id.etPassword);
         etCPassword = findViewById(R.id.etCPassword);
         etPhone = findViewById(R.id.etPhone);

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();




    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId()== R.id.btnSignUp) {
            tilFirstName.setError(null);
            tilLastName.setError(null);
            tilEmail.setError(null);
            tilPassword.setError(null);
            tilCPassword.setError(null);
            tilPhone.setError(null);
            if (validation()) {
                createUser();
            }
        }else if (v.getId() == R.id.btnFB){
            LoginManager.getInstance().logOut();
            login_button.performClick();
        }
    }

    private void createUser() {

        auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.e(TAG, "onComplete: "+task.isSuccessful() );
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            printToast(activity,"Authentication failed." + task.getException());

                        } else {
                            addDataToFireStone();
                        }
                    }
                });
    }

    private void addDataToFireStone() {

        String userId = auth.getUid();

        Map<String,Object> userMap = new HashMap<>();
        userMap.put("userId",userId);
        userMap.put("email",etEmail.getText().toString());
        userMap.put("firstName", etFirstName.getText().toString());
        userMap.put("lastName",etLastName.getText().toString());
        userMap.put("password",etPassword.getText().toString());

        DocumentReference newCityRef = dRef.collection("users").document(userId);
        newCityRef.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ShareSmilesPrefs.writeBool(activity,ShareSmilesPrefs.isLogin,true);
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.emailId,etEmail.getText().toString());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userName,etFirstName.getText().toString()+" "+etLastName.getText().toString());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userId,userId);
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userPic,"");

                startActivity(new Intent(activity,MainActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "failure: " +e.getMessage());
            }
        });
       /* dRef.collection("users")
                .add(userMap).addOnSuccessListener(new OnSuccessListener< Void >() {
            @Override
            public void onSuccess(Void aVoid) {
                printToast(activity,"Data Added");

                Log.e(TAG, "onSuccess: " );
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", e.toString());
                    }
                });*/
    }

    private boolean validation() {

        String emailStr = etEmail.getText().toString();
        String validEmailStr = Validations.emailPattern.toString();

        Log.e(TAG, "validation: "+emailStr );
        Log.e(TAG, "validation: "+validEmailStr );
        if (etFirstName.getText().toString().isEmpty()){
            tilFirstName.setError("Please enter first name");
            return false;

        }else if (etLastName.getText().toString().isEmpty()){
            tilLastName.setError("Please enter last name");
            return false;
        }else if (etEmail.getText().toString().isEmpty()){
            tilEmail.setError("Please enter email");
            return false;
        }else if (emailStr.matches(validEmailStr)){
            tilEmail.setError("Please enter valid email");
            return false;
        }else if (etPassword.getText().toString().isEmpty()){
            tilPassword.setError("Pleas enter password");
            return false;
        }else if (!Validations.isValidPassword(etPassword.getText().toString())){
            tilPassword.setError("Please valid valid password");
            return false;
        }
        else if (etCPassword.getText().toString().isEmpty()){
            tilPassword.setError("Please enter confirm password");
            return false;
        }else if (!Validations.isValidPassword(etCPassword.getText().toString())){
            tilCPassword.setError("Please enter valid confirm password");
            return false;
        }else if (!etPassword.getText().toString().equals(etCPassword.getText().toString())){
            tilCPassword.setError("password and confirm password not matched");
            return false;
        }

        return true;
    }


    private void handleFacebookAccessToken(AccessToken accessToken) {

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            addFBDataToFireStone(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            ShareSmilesSingleton.getInstance().getDialogBoxs().showDismissBox(activity,"User already registered");

                        }

                    }
                });
    }

    private void addFBDataToFireStone(FirebaseUser user) {

        String userId = auth.getUid();

        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("userId",user.getUid());
        userMap.put("email",user.getEmail());
        userMap.put("firstName", user.getDisplayName());
        userMap.put("lastName",user.getDisplayName());
        userMap.put("profilePic",String.valueOf(user.getPhotoUrl()));

        DocumentReference usersReference = dRef.collection("users").document(userId);
        usersReference.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                ShareSmilesPrefs.writeBool(activity,ShareSmilesPrefs.isLogin,true);
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.emailId,user.getEmail());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userName,user.getDisplayName());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userId,user.getUid());
                ShareSmilesPrefs.writeString(activity,ShareSmilesPrefs.userPic,String.valueOf(user.getPhotoUrl()));


                Intent intent = new Intent(activity, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "failure: " +e.getMessage());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

}
