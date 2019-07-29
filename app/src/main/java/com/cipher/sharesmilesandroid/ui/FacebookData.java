package com.cipher.sharesmilesandroid.ui;

import android.os.Bundle;
import android.util.Log;

import com.cipher.sharesmilesandroid.interfaces.FacebookInteface;
import com.cipher.sharesmilesandroid.modals.Users;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class FacebookData  {




    private static final String TAG = "FacebookData";

    private void getFbInfo() {

        Users users = new Users();
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
                            String gender = object.getString("gender");

                            String birthday = "";
                            if (object.has("birthday")) {
                                birthday = object.getString("birthday");
                            }

                            String image_url = "http://graph.facebook.com/" + id + "/picture?type=large";
                            String email = "";
                            if (object.has("email")) {
                                email = object.getString("email");
                            }

                            users.setUserID(id);
                            users.setFirstName(first_name);
                            users.setLastName(last_name);
                            users.setEmail(email);
                            users.setGender(gender);
                            users.setDob(birthday);
                            users.setUserImage(image_url);


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

}
