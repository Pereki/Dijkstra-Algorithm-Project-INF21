package Services;

import Model.Graph;
import Model.Vertex;
import Services.XmlElements.Node;
import Services.XmlElements.Way;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class XmlParser {
    private String path;
    private ArrayList<Node> listOfNodes;
    private ArrayList<Way> listOfWays;
    private Graph graph;

    public XmlParser(String path){
        this.path = path;
        listOfNodes = new ArrayList<Node>();
        listOfWays = new ArrayList<Way>();
        graph = new Graph();

        parseXmlToGraph();
    }

    public void parseXmlToGraph() {

        //get all nodes and save them in listOfNodes
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();
            Boolean areWeInANode = false;

            while(!(line.equals(null))){
                line = line.toLowerCase();

                if(line.contains("<node")){
                    if(line.contains("/>")){
                        areWeInANode = false;
                    }else {
                        areWeInANode = true;
                    }

                    listOfNodes.add(new Node());

                    String entries[] = line.split(" ");

                    for(int i=0; i<entries.length; i++){
                        if(entries[i].contains("id=")){
                            entries[i]=entries[i].replace("id=","");
                            entries[i]=entries[i].replace("\"","");
                            
                            Node speicher = listOfNodes.get(listOfNodes.size()-1);
                            speicher.setId(Integer.parseInt(entries[i]));
                            listOfNodes.remove(listOfNodes.size()-1);
                            listOfNodes.add(listOfNodes.size()-1,speicher);
                        } else if (entries[i].contains("lat=")) {
                            entries[i]=entries[i].replace("lat=","");
                            entries[i]=entries[i].replace("\"","");

                            Node speicher = listOfNodes.get(listOfNodes.size()-1);
                            speicher.setLat(Double.parseDouble(entries[i]));
                            listOfNodes.remove(listOfNodes.size()-1);
                            listOfNodes.add(listOfNodes.size()-1,speicher);
                        } else if (entries[i].contains("lon=")) {
                            entries[i]=entries[i].replace("lon=","");
                            entries[i]=entries[i].replace("\"","");

                            Node speicher = listOfNodes.get(listOfNodes.size()-1);
                            speicher.setLon(Double.parseDouble(entries[i]));
                            listOfNodes.remove(listOfNodes.size()-1);
                            listOfNodes.add(listOfNodes.size()-1,speicher);
                        }
                    }
                } else if (line.contains("<tag")&&areWeInANode) {
                    String entries[] = line.split(" ");

                    if(entries[1].contains("k=\"highway\"")&&entries[2].contains("v=\"motorway_junction\"")){
                        Node speicher = listOfNodes.get(listOfNodes.size()-1);
                        speicher.setJunction(true);

                        listOfNodes.remove(listOfNodes.size()-1);
                        listOfNodes.add(listOfNodes.size()-1,speicher);
                    } else if(entries[1].contains("k=\"name\"")){
                        Node speicher = listOfNodes.get(listOfNodes.size()-1);
                        entries[2] = entries[2].replace("v=\"", "");
                        entries[2] = entries[2].replace("\"", "");
                        speicher.setIdentifier(entries[2]);

                        listOfNodes.remove(listOfNodes.size()-1);
                        listOfNodes.add(listOfNodes.size()-1, speicher);
                    }
                } else if (line.contains("</node>")&&areWeInANode) {
                    areWeInANode = false;
                } else if (line.contains("<way")) {
                    areWeInANode = false;

                    listOfWays.add(new Way());
                } else if (line.contains("<nd")) {
                    String entries[] = line.split(" ");

                    entries[1] = entries[1].replace("ref=\"", "");
                    entries[1] = entries[1].replace("\"/>", "");
                    int nodeId = Integer.parseInt(entries[1]);

                    Way speicher = listOfWays.get(listOfWays.size()-1);
                    speicher.addNode(listOfNodes.get(getIndexOfNodeById(nodeId)));
                    listOfWays.set(listOfWays.size()-1, speicher);
                }

                line = br.readLine();
            }

            br.close();
        }catch(Exception e){
            System.out.println("File not found or IO Exception!");
        }
        //fertig mit Parsen
        //nächster Schritt: Graph bauen

        for(int i = 0; i<listOfWays.size();i++){
            Way speicher = listOfWays.get(i);

            for(int a = 0;a< speicher.size()-1;a=a+2){
                Node n1 = speicher.getNode(a);
                Node n2 = speicher.getNode(a+1);
                Vertex v1 = new Vertex(n1.getId(),n1.getLat(), n1.getLon(),n1.getIdentifier(),n1.isJunction());
                Vertex v2 = new Vertex(n2.getId(),n2.getLat(), n2.getLon(),n2.getIdentifier(),n2.isJunction());

            }
        }
    }

    private int getIndexOfNodeById(int id){
        int ergebnis = -1;
        for(int i=0;i<listOfWays.size();i++){
            if(listOfNodes.get(i).getId()==id){
                ergebnis = i;
            }
        }
        return ergebnis;
    }

    public Graph getGraph(){
        return graph;
    }
    
}
