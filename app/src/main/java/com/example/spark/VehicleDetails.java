package com.example.spark;

public class VehicleDetails {
    private String carNumber;
    private String carModel;

    public VehicleDetails(){

    }
    public VehicleDetails(String carNumber, String carModel) {
        this.carNumber = carNumber;
        this.carModel = carModel;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }
}
