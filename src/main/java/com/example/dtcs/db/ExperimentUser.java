package com.example.dtcs.db;

import org.litepal.crud.DataSupport;

/**
 * Created by q9163 on 26/12/2017.
 */

public class ExperimentUser extends DataSupport {
    private String user_id;
    private String Name;
    private int Grade;
    private int rank;
    private String remark;
    private String majornum;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getMajornum() {
        return majornum;
    }

    public void setMajornum(String majornum) {
        this.majornum = majornum;
    }

    public ExperimentUser(){

    }

    public ExperimentUser(String user_id, String name, int grade, int rank, String remark, String majornum) {
        this.user_id = user_id;
        Name = name;
        Grade = grade;
        this.rank = rank;
        this.remark = remark;
        this.majornum = majornum;
    }

    @Override
    public String toString() {
        return "ExperimentUser{" +
                "user_id='" + user_id + '\'' +
                ", Name='" + Name + '\'' +
                ", Grade=" + Grade +
                ", rank=" + rank +
                ", remark='" + remark + '\'' +
                ", majornum='" + majornum + '\'' +
                '}';
    }
}
