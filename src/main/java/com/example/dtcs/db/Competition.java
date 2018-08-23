package com.example.dtcs.db;

import org.litepal.crud.DataSupport;

/**
 * Created by q9163 on 15/02/2018.
 */

public class Competition extends DataSupport {
    private String Userid;
    private String Name;
    private String Depatment;
    private String Major;
    private int Class_;
    private double Grade;
    private int Symbol;
    private String Remark;

    public Competition(){}

    public Competition(String userid, String name, String depatment, String major, int class_, double grade, int symbol, String remark) {
        Userid = userid;
        Name = name;
        Depatment = depatment;
        Major = major;
        Class_ = class_;
        Grade = grade;
        Symbol = symbol;
        Remark = remark;
    }

    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDepatment() {
        return Depatment;
    }

    public void setDepatment(String depatment) {
        Depatment = depatment;
    }

    public String getMajor() {
        return Major;
    }

    public void setMajor(String major) {
        Major = major;
    }

    public int getClass_() {
        return Class_;
    }

    public void setClass_(int class_) {
        Class_ = class_;
    }

    public double getGrade() {
        return Grade;
    }

    public void setGrade(double grade) {
        Grade = grade;
    }

    public int getSymbol() {
        return Symbol;
    }

    public void setSymbol(int symbol) {
        Symbol = symbol;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "Userid='" + Userid + '\'' +
                ", Name='" + Name + '\'' +
                ", Depatment='" + Depatment + '\'' +
                ", Major='" + Major + '\'' +
                ", Class=" + Class_ +
                ", Grade=" + Grade +
                ", Symbol=" + Symbol +
                ", Remark='" + Remark + '\'' +
                '}';
    }
}
