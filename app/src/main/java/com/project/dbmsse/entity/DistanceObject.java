package com.project.dbmsse.entity;

public class DistanceObject {

    private String text;

    private String value;

    public DistanceObject(String text, String value) {
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
