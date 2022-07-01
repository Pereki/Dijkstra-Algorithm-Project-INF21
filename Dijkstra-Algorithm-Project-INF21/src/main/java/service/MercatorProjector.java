package service;

import model.CoordinatePair;

public class MercatorProjector {
    private CoordinatePair center;

    public MercatorProjector(CoordinatePair center) {
        this.center = center;
    }

    public MercatorProjector(double latitude, double longitude) {
        this.center = new CoordinatePair(latitude, longitude);
    }

    public CoordinatePair project(CoordinatePair coordinates) {
        return project(coordinates.getLatitude(), coordinates.getLongitude());
    }

    public CoordinatePair project(double latitude, double longitude) {
        return new CoordinatePair(getX(latitude), getY(longitude));
    }

    public CoordinatePair unproject(double x, double y) {
        return new CoordinatePair(getLongitude(x), getLatitude(y));
    }

    public static CoordinatePair project(CoordinatePair coordinates, CoordinatePair center) {
        return project(coordinates.getLatitude(), coordinates.getLongitude(), center);
    }

    public static CoordinatePair project(double latitude, double longitude, CoordinatePair center) {
        return new MercatorProjector(center).project(latitude, longitude);
    }

    private double getX(double longitude) {
        return longitude - center.getLongitude();
    }

    private double getY(double latitude) {
        return Math.log(Math.tan(Math.PI/4 + latitude/2));
    }

    private double getLongitude(double x) {
        return x + center.getLongitude();
    }

    private double getLatitude(double y) {
        return Math.asin(Math.tanh(y));
    }
}
