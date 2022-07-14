package model;

public class GeoBounds {
    double north;
    double east;
    double south;
    double west;

    // for Germany: W: 5.866342; E: 15.041892; N: 55.124401; S: 47.270112;
    public GeoBounds(double westLon, double eastLon, double northLat, double southLat) {
        this.north = northLat;
        this.east = eastLon;
        this.south = southLat;
        this.west = westLon;
    }

    public double getWidth() {
        return Math.abs(this.west - this.east);
    }

    public double getHeight() {
        return Math.abs(this.north - this.south);
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }
}
