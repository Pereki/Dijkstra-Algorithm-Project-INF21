package service.XmlElements;

public class Node {
    private long id;
    private double lat;
    private double lon;
    private boolean isJunction = false;
    private String identifier = "";

    public void setId(long id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setJunction(boolean junction) {
        isJunction = junction;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public boolean isJunction() {
        return isJunction;
    }

    public String getIdentifier() {
        return identifier;
    }
}
