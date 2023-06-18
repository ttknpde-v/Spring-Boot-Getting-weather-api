package com.ttknpdev.springbootretrieveweatherapi.exception.entity;

public class Warning {
    private Short status;
    private String course;

    public Warning(Short status, String course) {
        this.status = status;
        this.course = course;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
