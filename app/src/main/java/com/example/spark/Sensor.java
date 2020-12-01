package com.example.spark;

import java.io.Serializable;

/**
 * Created by LUCIFER on 22-03-2018.
 */

public class Sensor implements Serializable {
    boolean free;
    long timer;

    public Sensor(boolean free, long timer) {
        this.free = free;
        this.timer = timer;
    }

    public Sensor(boolean free) {
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public long getTimer() {
        return timer;
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    public String toString(int i) {
        return "Sensor " + i +"{" + "free=" + free +", timer=" + timer +'}';
    }
}
