package com.project.dbmsse.models;


public class CarListObject {

    private int id;
    private int catId;
    private String carName;
    private String carImage;
    private String carType;
    private String seatNumber;
    private String mileage;
    private String fuelType;
    private String status;
    private float price;

    public CarListObject(int id, int catId, String carName, String carImage, String carType, String seatNumber, String mileage, String fuelType, String status, float price) {
        this.id = id;
        this.catId = catId;
        this.carName = carName;
        this.carImage = carImage;
        this.carType = carType;
        this.seatNumber = seatNumber;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.status = status;
        this.price = price;
    }

    public CarListObject(int catId, String carName, String carImage, String carType, String seatNumber, String mileage, String fuelType, String status, float price) {
        this.catId = catId;
        this.carName = carName;
        this.carImage = carImage;
        this.carType = carType;
        this.seatNumber = seatNumber;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.status = status;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getCatId() {
        return catId;
    }

    public String getCarName() {
        return carName;
    }

    public String getCarImage() {
        return carImage;
    }

    public String getCarType() {
        return carType;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public String getMileage() {
        return mileage;
    }

    public String getFuelType() {
        return fuelType;
    }

    public String getStatus() {
        return status;
    }

    public float getPrice() {
        return price;
    }
}
