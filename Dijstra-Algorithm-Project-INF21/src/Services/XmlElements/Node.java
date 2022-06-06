package Services.XmlElements;

public class Node {
    private int id;
    private double lat;
    private double lon;

    private boolean isJunction;

    public void setId(int id) {
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

    public int getId() {
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
}
