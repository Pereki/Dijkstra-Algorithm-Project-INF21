package model;//represents a node in osm xml

import java.io.Serializable;
import java.util.Objects;

public class Vertex implements Serializable {
    private long id;
    private int costs = 0;
    private Vertex predecessor;
    private double lat;//Breitengrad
    private double lon;//Längengrad
    private String identifier="";//Bezeichner
    private boolean junction=false;//anschlussstelle
    private boolean crossing=false;//Kreuzung

    public Vertex(long id, double lat, double lon, Vertex predecessor, String identifier, boolean junction){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.predecessor =  predecessor;
        this.identifier = identifier;
        this.junction = junction;
    }

    public Vertex(long id, double lat, double lon, String identifier, boolean junction){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.identifier = identifier;
        this.predecessor =  null;

        this.junction = junction;
    }

    public Vertex(long id, double lat, double lon){
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.predecessor = null;
        this.identifier = null;
        this.junction = false;
    }

    public long getId(){
        return id;
    }

    public int getCosts(){return costs;}

    public Vertex getPredecessor(){return predecessor;}

    public double getLat(){return lat;}

    public double getLon(){return  lon;}

    public String getIdentifier(){return identifier;}

    public boolean getJunction(){return junction;}

    public boolean isCrossing() {
        return crossing;
    }

    public void setIdentifier(String identifier){
        this.identifier = identifier;
    }

    public void setJunction(Boolean b){
        this.junction = b;
    }
    public void setCrossing(boolean isCrossing){
        this.crossing = isCrossing;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.getLat(), getLat()) == 0 && Double.compare(vertex.getLon(), getLon()) == 0 ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCosts(), getLat(), getLon(), getIdentifier(), getJunction());
    }
}
