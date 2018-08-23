package com.example.dtcs.db;

import java.util.Date;

/**
 * Created by q9163 on 20/01/2018.
 */

public class GsonSafe {
    private String Name;
    private Date date;
    private int chka;
    private int chkb;
    private int chkc;
    private int chkd;
    private int chke;
    private int chkf;
    private int chkg;

    public GsonSafe(){}


    public GsonSafe(String name, Date date, int chka, int chkb, int chkc, int chkd, int chke, int chkf, int chkg) {
        Name = name;
        this.date = date;
        this.chka = chka;
        this.chkb = chkb;
        this.chkc = chkc;
        this.chkd = chkd;
        this.chke = chke;
        this.chkf = chkf;
        this.chkg = chkg;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getChka() {
        return chka;
    }

    public void setChka(int chka) {
        this.chka = chka;
    }

    public int getChkb() {
        return chkb;
    }

    public void setChkb(int chkb) {
        this.chkb = chkb;
    }

    public int getChkc() {
        return chkc;
    }

    public void setChkc(int chkc) {
        this.chkc = chkc;
    }

    public int getChkd() {
        return chkd;
    }

    public void setChkd(int chkd) {
        this.chkd = chkd;
    }

    public int getChke() {
        return chke;
    }

    public void setChke(int chke) {
        this.chke = chke;
    }

    public int getChkf() {
        return chkf;
    }

    public void setChkf(int chkf) {
        this.chkf = chkf;
    }

    public int getChkg() {
        return chkg;
    }

    public void setChkg(int chkg) {
        this.chkg = chkg;
    }

    @Override
    public String toString() {
        return "GsonSafe{" +
                "Name='" + Name + '\'' +
                ", date=" + date +
                ", chka=" + chka +
                ", chkb=" + chkb +
                ", chkc=" + chkc +
                ", chkd=" + chkd +
                ", chke=" + chke +
                ", chkf=" + chkf +
                ", chkg=" + chkg +
                '}';
    }
}
