package com.example.android.app2go;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shaharbarsheshet on 03/03/2016.
 */
public class Route implements Serializable {
    ArrayList<LocationPoint> points;

    public ArrayList<LocationPoint> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<LocationPoint> points) {
        this.points = points;
    }

    public Route(ArrayList<LocationPoint> points) {
        this.points = points;
    }
}
