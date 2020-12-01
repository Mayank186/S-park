package com.example.spark;

public class PaymentUser {
    public String ownername;
    public String googleid;
    public int amount;

    public PaymentUser(){

    }

    public PaymentUser(String ownername, String googleid, int  amount) {
        this.ownername = ownername;
        this.googleid = googleid;
        this.amount = amount;
    }

    public String getOwnername() {
        return ownername;
    }

    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getGoogleid() {
        return googleid;
    }

    public void setGoogleid(String googleid) {
        this.googleid = googleid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
