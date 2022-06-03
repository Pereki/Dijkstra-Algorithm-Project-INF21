package Services;

import Model.Graph;
import Services.XmlElements.Node;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class XmlParser {
    private String path;
    private ArrayList<Node> listOfNodes;
    private Graph graph;

    public XmlParser(String path){
        this.path = path;
        listOfNodes = new ArrayList<Node>();
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
                    }
                } else if (line.contains("</node>")&&areWeInANode) {
                    areWeInANode = false;
                }

                line = br.readLine();
            }

            br.close();
        }catch(Exception e){
            System.out.println("File not found or IO Exception!");
        }
    }

    private void getRelations(String line){
        String entries [] = line.split(" ");

        if(entries[0].contains("node")){

        } else if (entries[0].contains("way")) {

        }
    }

    public Graph getGraph(){
        return graph;
    }
    
}
