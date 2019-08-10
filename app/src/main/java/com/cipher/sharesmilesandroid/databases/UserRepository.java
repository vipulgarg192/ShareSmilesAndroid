package com.cipher.sharesmilesandroid.databases;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.cipher.sharesmilesandroid.interfaces.UserDao;
import com.cipher.sharesmilesandroid.modals.Users;
import com.google.firebase.firestore.auth.User;


import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private UserDao mUserDao;
    private List<Users> getAllUsers;


    public UserRepository(Application application) {
        UserRoomDatabase userDB = UserRoomDatabase.getDatabase(application);
        this.mUserDao = userDB.userDao();
        this.getAllUsers = userDB.userDao().getAllUsers();
    }

    List<Users>  getGetAllUsers() {
        return getAllUsers;
    }


    public void insert (Users users) {
        new insertAsyncTask(mUserDao).execute(users);
    }



    private static class insertAsyncTask extends AsyncTask<Users, Void, Void> {

        private UserDao mAsyncTaskDao;

        insertAsyncTask(UserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            mAsyncTaskDao.insertUsers(users[0]);
            return null;
        }
    }


}
