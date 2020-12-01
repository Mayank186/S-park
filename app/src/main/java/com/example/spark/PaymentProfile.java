package com.example.spark;

public class PaymentProfile {
    public String name;
    public String id;

    public PaymentProfile(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentProfile(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
