package com.example.android.app2go;

import java.io.Serializable;

/**
 * Created by shaharbarsheshet on 03/03/2016.
 */
public class LocationPoint implements Serializable {

    private String source;
    private String destination;
    private int endLatitude;
    private int endLongitude;
    private int duration;
    private String durationText;

    /**
     * @return The source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source The source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return The destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination The destination
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * @return The endLatitude
     */
    public int getEndLatitude() {
        return endLatitude;
    }

    /**
     * @param endLatitude The endLatitude
     */
    public void setEndLatitude(int endLatitude) {
        this.endLatitude = endLatitude;
    }

    /**
     * @return The endLongitude
     */
    public int getEndLongitude() {
        return endLongitude;
    }

    /**
     * @param endLongitude The endLongitude
     */
    public void setEndLongitude(int endLongitude) {
        this.endLongitude = endLongitude;
    }

    /**
     * @return The duration
     */
    public int getDuration() {
        return duration;
    }

    /**
     * @param duration The duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * @return The durationText
     */
    public String getDurationText() {
        return durationText;
    }

    /**
     * @param durationText The durationText
     */
    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    @Override
    public String toString() {
        return "LocationPoint{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", endLatitude=" + endLatitude +
                ", endLongitude=" + endLongitude +
                ", duration=" + duration +
                ", durationText='" + durationText + '\'' +
                '}';
    }
}
