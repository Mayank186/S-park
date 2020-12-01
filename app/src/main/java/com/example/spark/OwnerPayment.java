package com.example.spark;

public class OwnerPayment {
    public String uname,googleid;
    public int amount;

    public OwnerPayment(){

    }

    public OwnerPayment(String uname, String googleid, int amount) {
        this.uname = uname;
        this.googleid = googleid;
        this.amount = amount;
    }
}
