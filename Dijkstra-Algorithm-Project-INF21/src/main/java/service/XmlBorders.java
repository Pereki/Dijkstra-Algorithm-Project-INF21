package service;

import model.Edge;
import model.Graph;
import model.Vertex;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class XmlBorders {
    private String path;
    private Graph g;
    private int resolution;

    public XmlBorders(String pathToFile, int resolution) {
        this.path = pathToFile;
        this.resolution = resolution;
        parseXml();
    }

    private void parseXml(){
        Vertex v1 = null;
        Vertex v2 = null;

        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            line = line.toLowerCase();

            String[] entries = line.split(" ");

            for(int i=0;i<entries.length;i++){
                if(entries[i].contains("<nd")){
                    entries[i+1] = entries[i+1].replace("lat=","");
                    entries[i+1] = entries[i+1].replace("\"","");

                    entries[i+2] = entries[i+2].replace("lon=","");
                    entries[i+2] = entries[i+2].replace("\"","");
                    entries[i+2] = entries[i+2].replace("/>","");

                    v1 = new Vertex(0,Long.parseLong(entries[i+1]), Long.parseLong(entries[i+2]));

                }
            }
            v2 = v1;
            line = br.readLine();

            while(true){

                if(line==null){
                    break;
                }

                entries = line.split(" ");
                for(int i=0;i<entries.length;i++){
                    if(entries[i].contains("<nd")){
                        if(i%resolution==0){
                            entries[i+1] = entries[i+1].replace("lat=","");
                            entries[i+1] = entries[i+1].replace("\"","");

                            entries[i+2] = entries[i+2].replace("lon=","");
                            entries[i+2] = entries[i+2].replace("\"","");
                            entries[i+2] = entries[i+2].replace("/>","");

                            v1 = new Vertex(0,Long.parseLong(entries[i+1]), Long.parseLong(entries[i+2]));
                            Edge e = new Edge(v1, v2);
                            g.addEdge(e);
                            v2 = v1;
                        }
                    }
                }

                line = br.readLine();
            }

            v1 = g.getVertexList().get(0);
            Edge e = new Edge(v1, v2);
            g.addEdge(e);
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    public Graph getGraph(){
        return this.g;
    }
}
