package com.cipher.sharesmilesandroid.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cipher.sharesmilesandroid.interfaces.UserDao;
import com.cipher.sharesmilesandroid.modals.Users;

@Database(entities = {Users.class}, version = 1)
public abstract class UserRoomDatabase extends RoomDatabase {
    private static volatile UserRoomDatabase INSTANCE;

    public abstract UserDao userDao();

    public static UserRoomDatabase getDatabase(final Context context){

        if (INSTANCE == null){
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "user_databse")
                            .build();
                }
            }
        }

        return INSTANCE;
    }
}
