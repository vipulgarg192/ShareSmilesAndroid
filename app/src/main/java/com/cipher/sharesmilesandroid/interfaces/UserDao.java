package com.cipher.sharesmilesandroid.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cipher.sharesmilesandroid.modals.Users;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

    @Query("Select * from UsersTable")
    List<Users> getAllUsers();

    @Insert
    void insertUsers(Users users);

    @Update
    void updateUsers(Users users);

    @Query("DELETE FROM UsersTable")
    void deleteUsers();

    @Query("SELECT * FROM UsersTable WHERE userID = :id")
    Users loadPersonById(String id);

}
