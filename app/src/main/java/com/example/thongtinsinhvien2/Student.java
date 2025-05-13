package com.example.thongtinsinhvien2;

import java.io.Serializable;

public class Student implements Serializable {
    private int id;
    private String name;
    private String mssv;
    private String className;
    private String year;
    private String majors;
    private String plan;

    public Student() {
    }

    public Student(String name, String mssv, String className, String year, String majors, String plan) {
        this.name = name;
        this.mssv = mssv;
        this.className = className;
        this.year = year;
        this.majors = majors;
        this.plan = plan;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMajors() {
        return majors;
    }

    public void setMajors(String majors) {
        this.majors = majors;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getFullInfo() {
        StringBuilder info = new StringBuilder();
        info.append("HỌ VÀ TÊN: ").append(name).append("\n");
        info.append("MSSV: ").append(mssv).append("\n");
        info.append("LỚP: ").append(className).append("\n\n");
        info.append("SINH VIÊN NĂM: ").append(year).append("\n");
        info.append("CHUYÊN NGÀNH: ").append(majors).append("\n\n");
        info.append("KẾ HOẠCH PHÁT TRIỂN BẢN THÂN:\n");
        info.append(plan);
        return info.toString();
    }
}