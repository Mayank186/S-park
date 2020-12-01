package com.example.spark;

public class UserPayment {
    public String ownername, googleid;
    public int amount;

    public UserPayment() {

    }

    public UserPayment(String ownername, String googleid, int amount) {
        this.ownername = ownername;
        this.googleid = googleid;
        this.amount = amount;
    }
}


