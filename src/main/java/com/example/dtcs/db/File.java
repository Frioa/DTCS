package com.example.dtcs.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by q9163 on 26/01/2018.
 */

public class File extends DataSupport {
    private int fid;
    private String pid;
    private String rid;
    private String fname;
    private String ftype;
    private double size;
    private Date fdate;
    private String fdescript;
    private String fstatus;
    private String remark;

    public File(){}


    public File(int fid, String pid, String rid, String fname, String ftype, double size, Date fdate, String fdescript, String fstatus, String remark) {
        this.fid = fid;
        this.pid = pid;
        this.rid = rid;
        this.fname = fname;
        this.ftype = ftype;
        this.size = size;
        this.fdate = fdate;
        this.fdescript = fdescript;
        this.fstatus = fstatus;
        this.remark = remark;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFtype() {
        return ftype;
    }

    public void setFtype(String ftype) {
        this.ftype = ftype;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public Date getFdate() {
        return fdate;
    }

    public void setFdate(Date fdate) {
        this.fdate = fdate;
    }

    public String getFdescript() {
        return fdescript;
    }

    public void setFdescript(String fdescript) {
        this.fdescript = fdescript;
    }

    public String getFstatus() {
        return fstatus;
    }

    public void setFstatus(String fstatus) {
        this.fstatus = fstatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "File{" +
                "fid=" + fid +
                ", pid='" + pid + '\'' +
                ", rid='" + rid + '\'' +
                ", fname='" + fname + '\'' +
                ", ftype='" + ftype + '\'' +
                ", size=" + size +
                ", fdate=" + fdate +
                ", fdescript='" + fdescript + '\'' +
                ", fstatus='" + fstatus + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
