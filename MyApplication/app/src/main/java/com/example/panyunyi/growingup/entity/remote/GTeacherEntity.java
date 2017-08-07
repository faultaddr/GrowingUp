package com.example.panyunyi.growingup.entity.remote;

import java.io.Serializable;

public class GTeacherEntity implements Serializable{
    private String teacherId;
    private String teacherName;
    private String teacherMajor;
    private String teacherMobileNumber;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherMajor() {
        return teacherMajor;
    }

    public void setTeacherMajor(String teacherMajor) {
        this.teacherMajor = teacherMajor;
    }

    public String getTeacherMobileNumber() {
        return teacherMobileNumber;
    }

    public void setTeacherMobileNumber(String teacherMobileNumber) {
        this.teacherMobileNumber = teacherMobileNumber;
    }

}