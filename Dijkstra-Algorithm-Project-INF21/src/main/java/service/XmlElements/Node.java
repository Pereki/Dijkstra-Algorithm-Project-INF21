package service.XmlElements;

/**
 * this class represents a node in an .osm file
 * @author i21017
 */
public class Node {
    private long id;
    private double lat;
    private double lon;
    private boolean isJunction = false;//Is this node a junction?
    private String identifier = "";//the identifier is the name of the junction, if the node is not a junction, the identifier should be "" (empty)


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
