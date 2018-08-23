package com.example.dtcs.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by q9163 on 13/02/2018.
 */

public class Lesson extends DataSupport {
    private String LessonID;
    private String name;
    private int longs;
    private Date sdata;
    private int type;
    private int status;
    private String taech;
    private int point;
    private String remark;

    public Lesson() {
    }

    public String getLessonID() {
        return LessonID;
    }

    public void setLessonID(String lessonID) {
        LessonID = lessonID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLongs() {
        return longs;
    }

    public void setLongs(int longs) {
        this.longs = longs;
    }

    public Date getSdata() {
        return sdata;
    }

    public void setSdata(Date sdata) {
        this.sdata = sdata;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaech() {
        return taech;
    }

    public void setTaech(String taech) {
        this.taech = taech;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "LessonID='" + LessonID + '\'' +
                ", name='" + name + '\'' +
                ", sdata=" + sdata +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                ", taech='" + taech + '\'' +
                ", point='" + point + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
