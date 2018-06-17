package com.zinyakov.mapkitheroespoi;

public class PointOfInterest {

    public String osmId;
    public GeoPosition position;
    public String type;

    public PointOfInterest(String osmId, GeoPosition position, String type) {
        this.osmId = osmId;
        this.position = position;
        this.type = type;
    }

}
