package com.example.dtcs.db;

/**
 * Created by q9163 on 20/01/2018.
 */

public class GsonName {
    private String uesrName;

    public String getUesrName() {
        return uesrName;
    }

    public void setUesrName(String uesrName) {
        this.uesrName = uesrName;
    }

    @Override
    public String toString() {
        return "uesrname{" +
                "userID='" + uesrName + '\'' +
                '}';
    }
}
