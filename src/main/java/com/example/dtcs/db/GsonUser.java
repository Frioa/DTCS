package com.example.dtcs.db;

/**
 * Created by q9163 on 28/02/2018.
 */

public class GsonUser {
    private String userID;
    private String passWord;
    public GsonUser(){}
    public GsonUser(String userID, String passWord) {
        this.userID = userID;
        this.passWord = passWord;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "GsonUser{" +
                "userID='" + userID + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
