package com.cipher.sharesmilesandroid.databases;

import android.util.Log;

import com.cipher.sharesmilesandroid.modals.Users;

import java.util.List;

public class Respo {
    private static final String TAG = "Respo";

    public static  void updateTask(UserRoomDatabase userDb, Users users) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: "+users.getUserID() );
                userDb.userDao().insertUsers(users);
            }
        });
    }

    public static void retrieveTask(UserRoomDatabase userDb , RoomDBCallBacks callBacks) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                List<Users> persons = userDb.userDao().getAllUsers();
                callBacks.getUsersListSize(persons.size());
                callBacks.getUsersList(persons);
            }
        });
    }

    public static void deleteUsersTask(UserRoomDatabase userDb , RoomDBCallBacks callBacks) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDb.userDao().deleteUsers();
            }
        });
    }

}
