//represents a node in osm xml

public class Vertex {
    private int id;

    private double lat;//Breitengrad
    private double lon;//Längengrad

    public Vertex(int id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
    }
}
