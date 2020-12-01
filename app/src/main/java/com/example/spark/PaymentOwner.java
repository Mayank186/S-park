package com.example.spark;

public class PaymentOwner {
    public String uname;
    public String googleid;
    public int amount;

    public PaymentOwner(){

    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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

    public PaymentOwner(String uname, String googleid, int amount) {
        this.uname = uname;
        this.googleid = googleid;
        this.amount = amount;
    }
}
