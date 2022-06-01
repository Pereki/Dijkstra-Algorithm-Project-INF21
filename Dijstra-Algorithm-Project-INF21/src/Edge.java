public class Edge {
    private Vertex v1;
    private Vertex v2;
    private int marking;

    public Edge(Vertex v1, Vertex v2, int marking ){
        this.v1 = v1;
        this.v2 = v2;
        this.marking = marking;
    }

    public Vertex getStartingVertex(){
        return v1;
    }

    public Vertex getEndingVertex(){
        return v2;
    }

    public int getMarking(){
        return marking;
    }
}
