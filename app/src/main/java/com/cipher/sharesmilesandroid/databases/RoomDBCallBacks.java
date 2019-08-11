package com.cipher.sharesmilesandroid.databases;

import com.cipher.sharesmilesandroid.modals.Users;

import java.util.ArrayList;
import java.util.List;

public interface RoomDBCallBacks {

     public abstract void getUsersListSize(int size);

    public abstract List<Users> getUsersList(List<Users> usersList);

}
