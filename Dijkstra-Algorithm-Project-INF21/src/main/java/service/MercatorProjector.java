package service;

import model.CoordinatePair;

public class MercatorProjector {
    private CoordinatePair center;

    /**
     * Creates a MercatorProjector with the given geo coordinates as {@code (0,0)}.
     * @param latitude The latitude in degrees
     * @param longitude The longitude in degrees
     */
    public MercatorProjector(double latitude, double longitude) {
        this.center = new CoordinatePair(latitude, longitude);
    }

    /**
     * Projects the given longitude to an x-coordinate in the Mercator-system.
     * @param longitude The longitude in degrees
     * @return The x-coordinate in radians
     */
    public double getX(double longitude) {
        return Math.toRadians(longitude - center.getLongitude());
    }

    /**
     * Projects the given latitude to a y-coordinate in the Mercator-system.
     * @param latitude The latitude in degrees
     * @return The y-coordinate in radians
     */
    public double getY(double latitude) {
        return Math.log(Math.tan(Math.PI/4 + Math.toRadians(latitude)/2));
    }

    /**
     * Projects the given x-coordinate to the longitude in the Mercator-system.
     * @param x The x-coordinate in radians
     * @return The longitude in degrees
     */
    public double getLongitude(double x) {
        return Math.toDegrees(x) + center.getLongitude();
    }

    /**
     * Projects the given y-coordinate to the latitude in the Mercator-system.
     * @param y The y-coordinate in radians
     * @return The latitude in degrees
     */
    public double getLatitude(double y) {
        return Math.toDegrees(Math.asin(Math.tanh(y)));
    }
}
