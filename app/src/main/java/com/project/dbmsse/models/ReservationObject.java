package com.project.dbmsse.models;


public class ReservationObject {

    private int id;
    private String carName;
    private String pickUpLocation;
    private String pickUpDate;
    private String pickUpTime;
    private String price;
    private String imagePath;

    public ReservationObject(int id, String carName, String pickUpLocation, String pickUpDate, String pickUpTime, String price, String imagePath) {
        this.id = id;
        this.carName = carName;
        this.pickUpLocation = pickUpLocation;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.price = price;
        this.imagePath = imagePath;
    }

    public ReservationObject(String carName, String pickUpLocation, String pickUpDate, String pickUpTime, String price, String imagePath) {
        this.carName = carName;
        this.pickUpLocation = pickUpLocation;
        this.pickUpDate = pickUpDate;
        this.pickUpTime = pickUpTime;
        this.price = price;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(String pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
