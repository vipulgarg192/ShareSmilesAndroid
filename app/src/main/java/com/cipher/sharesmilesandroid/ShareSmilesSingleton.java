package com.cipher.sharesmilesandroid;

import com.cipher.sharesmilesandroid.modals.Users;
import com.cipher.sharesmilesandroid.utilities.DialogBoxs;
import com.cipher.sharesmilesandroid.utilities.ProgressDialog;

import java.util.ArrayList;
import java.util.List;

public class ShareSmilesSingleton {

    static DialogBoxs dialogBoxs;
    static ProgressDialog progressDialog;

    private static final ShareSmilesSingleton ourInstance = new ShareSmilesSingleton();

    public static List<Users> usersArrayList = null;

    public static ShareSmilesSingleton getInstance() {
        dialogBoxs = new DialogBoxs();

        usersArrayList = new ArrayList<>();
        return ourInstance;
    }

    private ShareSmilesSingleton() {
    }

    public DialogBoxs getDialogBoxs() {
        return dialogBoxs;
    }

    public List<Users> getArray() {
        return this.usersArrayList;
    }
    //Add element to array
    public void addUsers(Users value) {
        usersArrayList.add(value);
    }

    public void deleteUser(Users users){
        if (usersArrayList.contains(users)){
            usersArrayList.remove(users);
        }
    }

    public Users getUser(String users){
        for (Users users1 : usersArrayList){
            if (users1.getUserID().equalsIgnoreCase(users)){
                return users1;
            }
        }
        return null;
    }
}
