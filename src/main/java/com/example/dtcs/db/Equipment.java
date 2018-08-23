package com.example.dtcs.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by q9163 on 09/02/2018.
 */

public class Equipment extends DataSupport {
    private String e_id;
    private String name;
    private String version;
    private int type;
    private int count;
    private int symbol;
    private Date checkdate;
    private int status;
    private String remark;

    public Equipment(){}


    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }

    public Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "e_id='" + e_id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", type=" + type +
                ", count=" + count +
                ", symbol=" + symbol +
                ", checkdate=" + checkdate +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }

    public Equipment(String e_id, String name, String version, int type, int count, int symbol, Date checkdate, int status, String remark) {
        this.e_id = e_id;
        this.name = name;
        this.version = version;
        this.type = type;
        this.count = count;
        this.symbol = symbol;
        this.checkdate = checkdate;
        this.status = status;
        this.remark = remark;
    }
}
