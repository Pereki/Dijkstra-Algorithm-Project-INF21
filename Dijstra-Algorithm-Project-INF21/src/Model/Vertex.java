package Model;//represents a node in osm xml

public class Vertex {
    private int id;
    private int costs = 0;
    private Vertex precessor;
    private double lat;//Breitengrad
    private double lon;//Längengrad
    private String identifier;//Bezeichner
    private boolean junction;//anschlussstelle

    public Vertex(int id, double lat, double lon, Vertex precessor, String identifier, boolean junction){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.precessor =  precessor;
        this.identifier = identifier;
        this.junction = junction;
    }
    public Vertex(int id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.precessor = null;
        this.identifier = null;
        this.junction = false;
    }

    public int getId(){
        return id;
    }

    public int getCosts(){return costs;}

    public Vertex getPrecessor(){return precessor;}

    public double getLat(){return lat;}

    public double getLon(){return  lon;}

    public String getIdentifier(){return identifier;}

    public boolean getJunction(){return junction;}


    public boolean equals(Vertex vertex){
        if(
            vertex.id == this.id &&
            vertex.lat == this.lat &&
            vertex.lon == this.lon &&
            vertex.identifier == this.identifier &&
            vertex.junction == this.junction
        ){
            return true;
        }else {return false;}
    }
}
