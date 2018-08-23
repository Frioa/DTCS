package com.example.dtcs.db;



import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by q9163 on 23/02/2018.
 */

public class Setting extends DataSupport {
    private int Hour;
    private int Minute;
    private String Sign_switch;
    private String image_switch;
    private String wifi_switch;

   public Setting(){}

    public int getHour() {
        return Hour;
    }

    public void setHour(int hour) {
        Hour = hour;
    }

    public int getMinute() {
        return Minute;
    }

    public void setMinute(int minute) {
        Minute = minute;
    }

    public String getSign_switch() {
        return Sign_switch;
    }

    public void setSign_switch(String sign_switch) {
        Sign_switch = sign_switch;
    }

    public String getImage_switch() {
        return image_switch;
    }

    public void setImage_switch(String image_switch) {
        this.image_switch = image_switch;
    }

    public String getWifi_switch() {
        return wifi_switch;
    }

    public void setWifi_switch(String wifi_switch) {
        this.wifi_switch = wifi_switch;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "Hour=" + Hour +
                ", Minute=" + Minute +
                ", Sign_switch='" + Sign_switch + '\'' +
                ", image_switch='" + image_switch + '\'' +
                ", wifi_switch='" + wifi_switch + '\'' +
                '}';
    }
}
