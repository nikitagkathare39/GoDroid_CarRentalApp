package com.project.dbmsse.models;


/*public class CarCategoryObject {

    private int id;
    private String imagePath;
    private String imageName;

    public CarCategoryObject(int id, String imagePath, String imageName) {
        this.id = id;
        this.imagePath = imagePath;
        this.imageName = imageName;
    }

    public CarCategoryObject(String imagePath, String imageName) {
        this.imagePath = imagePath;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getImageName() {
        return imageName;
    }
}*/

public class CarCategoryObject {

    private int id;
    private String imagePath;
    private  String imageName;

    public CarCategoryObject(int id, String imagePath, String imageName) {
        this.id = id;
        this.imagePath = imagePath;
        this.imageName = imageName;
    }

    public CarCategoryObject(int id, String imageName) {
        this.id = id;
        this.imageName = imageName;
    }

    public int getId() {
        return id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public  String getImageName() {
        return imageName;
    }
}
