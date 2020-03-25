package com.project.dbmsse.models;



public class SearchObject {

    private int id;
    private String carImage;
    private String carName;
    private String carPrice;
    private String carFeatures;
    private String carDuration;

    public SearchObject(String carImage, String carName, String carPrice, String carFeatures, String carDuration) {
        this.carImage = carImage;
        this.carName = carName;
        this.carPrice = carPrice;
        this.carFeatures = carFeatures;
        this.carDuration = carDuration;
    }

    public SearchObject(int id, String carImage, String carName, String carPrice, String carFeatures, String carDuration) {
        this.id = id;
        this.carImage = carImage;
        this.carName = carName;
        this.carPrice = carPrice;
        this.carFeatures = carFeatures;
        this.carDuration = carDuration;
    }

    public int getId() {
        return id;
    }

    public String getCarImage() {
        return carImage;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarPrice() {
        return carPrice;
    }

    public String getCarFeatures() {
        return carFeatures;
    }

    public String getCarDuration() {
        return carDuration;
    }
}
