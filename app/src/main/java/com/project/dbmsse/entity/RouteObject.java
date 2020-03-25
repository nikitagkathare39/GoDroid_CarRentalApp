package com.project.dbmsse.entity;


import java.util.List;

public class RouteObject {

    private List<LegsObject> legs;

    public RouteObject(List<LegsObject> legs) {
        this.legs = legs;
    }

    public List<LegsObject> getLegs() {
        return legs;
    }
}
