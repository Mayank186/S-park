package com.example.spark;

public class ParkingSpot{
    public String account, car_standing,city,email,name, place_name;
    public int time,cost,distance,phone;
    public Double lat, lng;

    public ParkingSpot() {
    }

    public ParkingSpot(String account, String car_standing, String city, String email, String name, String place_name, int time, int cost, int distance, int phone, Double lat, Double lng) {
        this.account = account;
        this.car_standing = car_standing;
        this.city = city;
        this.email = email;
        this.name = name;
        this.place_name = place_name;
        this.time = time;
        this.cost = cost;
        this.distance = distance;
        this.phone = phone;
        this.lat = lat;
        this.lng = lng;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCar_standing() {
        return car_standing;
    }

    public void setCar_standing(String car_standing) {
        this.car_standing = car_standing;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}