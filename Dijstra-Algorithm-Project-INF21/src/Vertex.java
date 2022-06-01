//represents a node in osm xml

public class Vertex {
    private int id;
    private int costs = 0;
    private Vertex precessor;
    private double lat;//Breitengrad
    private double lon;//Längengrad

    public Vertex(int id, double lat, double lon, Vertex precessor){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.precessor =  precessor;
    }
    public Vertex(int id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.precessor = null;
    }

    public int getId(){
        return id;
    }
}
