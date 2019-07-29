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
import com.cipher.sharesmilesandroid.utilities.Validations;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.HashSet;

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

    private FirebaseAuth auth;

    private FirebaseFirestore dRef = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        auth = FirebaseAuth.getInstance();
        init();



       /* myRef.setValue("Hello, World!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.e(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });*/
    }

    @Override
    public void init() {
        super.init();

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
        }
    }

    private void createUser() {

        auth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPassword.getText().toString())
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        printToast(activity,"createUserWithEmail:onComplete:" + task.isSuccessful());

                        Log.e(TAG, "onComplete: "+task.isSuccessful() );
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "onComplete: "+task.isSuccessful() );
                            printToast(activity,"Authentication failed." + task.getException());

                        } else {
                            addDataToFireStone();
                        }
                    }
                });
    }

    private void addDataToFireStone() {

        String userId = auth.getUid();

        HashMap<String,Object> userMap = new HashMap<>();
        userMap.put("userId",userId);
        userMap.put("email",etEmail.getText().toString());
        userMap.put("firstName", etFirstName.getText().toString());
        userMap.put("lastName",etLastName.getText().toString());
        userMap.put("password",etPassword.getText().toString());

        DocumentReference newCityRef = dRef.collection("users").document(userId.toString());
        newCityRef.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
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
}
