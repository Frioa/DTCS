package com.example.dtcs.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by q9163 on 10/02/2018.
 */

public class Chk extends DataSupport {
    private Date cdate;
    private int chka;
    private int chkb;
    private int chkc;
    private int chkd;
    private int chke;
    private int chkf;
    private int chkg;
    private String name;
    public Chk(){}

    public Date getCdate() {
        return cdate;
    }

    public void setCdate(Date cdate) {
        this.cdate = cdate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Chk{" +
                "cdate=" + cdate +
                ", chka=" + chka +
                ", chkb=" + chkb +
                ", chkc=" + chkc +
                ", chkd=" + chkd +
                ", chke=" + chke +
                ", chkf=" + chkf +
                ", chkg=" + chkg +
                ", name='" + name + '\'' +
                '}';
    }
}
