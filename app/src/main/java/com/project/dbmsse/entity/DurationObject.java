package com.project.dbmsse.entity;


public class DurationObject {

    private String text;

    private String value;

    public DurationObject(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }
}
