package com.example.dtcs.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by q9163 on 12/01/2018.
 */

public class SignLogin extends DataSupport{

    private String user_id;
    private Date date;
    public SignLogin(){}

    public SignLogin(String id, Date date) {
        this.user_id = id;
        this.date = date;
    }

    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SignLogin{" +
                "id='" + user_id + '\'' +
                ", date=" + date +
                '}';
    }
}
