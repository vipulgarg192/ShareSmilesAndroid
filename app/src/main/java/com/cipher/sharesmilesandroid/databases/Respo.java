package com.cipher.sharesmilesandroid.databases;

import com.cipher.sharesmilesandroid.modals.Users;
import java.util.List;

public class Respo {
    private static final String TAG = "Respo";

    public static  void updateTask(UserRoomDatabase userDb, Users users) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDb.userDao().insertUsers(users);
            }
        });
    }

    public static  void updateDataTask(UserRoomDatabase userDb, Users users) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDb.userDao().updateUsers(users);
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

    public static void deleteUsersTask(UserRoomDatabase userDb) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                userDb.userDao().deleteUsers();
            }
        });
    }

}
