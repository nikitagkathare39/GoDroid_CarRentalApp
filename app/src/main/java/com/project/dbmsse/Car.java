package com.project.dbmsse;

public class Car {
    int mileage,noofseats,dailyprice,totalamount;
    String fueltype,engine,fueleconomy,name;
    public Car(){   }
    public Car(String name, int mileage, String fueltype, String engine, int noofseats, String fueleconomy, int dailyprice, int totalamount){
        this.name = name;
        this.mileage = mileage;
        this.fueltype = fueltype;
        this.engine=engine;
        this.noofseats=noofseats;
        this.fueleconomy=fueleconomy;
        this.dailyprice=dailyprice;
        this.totalamount=totalamount;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getMileage(){
        return this.mileage;
    }

    public void setMileage(int mileage){
        this.mileage = mileage;
    }

    public String getFueltype(){
        return this.fueltype;
    }

    public void setFueltype(String fueltype){
        this.fueltype = fueltype;
    }

    public String getEngine(){
        return this.engine;
    }

    public void setEngine(String fueltype){
        this.engine = engine;
    }

    public int getNoofseats(){
        return this.noofseats;
    }

    public void setNoofseats(int mileage){
        this.noofseats = noofseats;
    }

    public String getFueleconomy(){
        return this.fueleconomy;
    }

    public void setFueleconomy(String fueleconomy){
        this.fueleconomy = fueleconomy;
    }

    public int getDailyprice(){
        return this.dailyprice;
    }

    public void setDailyprice(int dailyprice){
        this.dailyprice = dailyprice;
    }

    public int getTotalamount(){
        return this.totalamount;
    }

    public void setTotalamount(int totalamount){
        this.totalamount = totalamount;
    }



}