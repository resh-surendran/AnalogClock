package com.example.resh.analogclock;

import java.io.Serializable;

public class ClockPart implements Serializable {
    String name;
    String colorString;

    public ClockPart(String name, String colorString) {
        this.name = name;
        this.colorString = colorString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }
}
