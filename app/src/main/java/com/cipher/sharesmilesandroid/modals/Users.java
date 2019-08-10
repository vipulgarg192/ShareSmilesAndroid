package com.cipher.sharesmilesandroid.modals;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.database.annotations.NotNull;

@Entity(tableName = "UsersTable")
public class Users  {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "userID")
    private String userID;

    @NotNull
    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    @ColumnInfo(name = "description")
    private String description;

    @NotNull
    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "dob")
    private String dob;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "userType")
    private int userType;

    @ColumnInfo(name = "userImage")
    private String userImage;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "zipcode")
    private String zipcode;



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }


    public Users(int id, String userID, String firstName, String lastName, String description, String email, String password, String phone, String dob, String gender, int userType, String userImage, String address, String city, String zipcode) {
        this.id = id;
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.userType = userType;
        this.userImage = userImage;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
    }

    @Ignore
    public Users(String userID, String firstName, String lastName, String description, String email, String password, String phone, String dob, String gender, int userType, String userImage, String address, String city, String zipcode) {

        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.description = description;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.userType = userType;
        this.userImage = userImage;
        this.address = address;
        this.city = city;
        this.zipcode = zipcode;
    }

    public Users(){

    }
}
