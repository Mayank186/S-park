package com.example.spark;

public class OwnerProfile {
    public String name;
    public String gid;

    public OwnerProfile(String name, String gid) {
        this.name = name;
        this.gid = gid;
    }

    public OwnerProfile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
