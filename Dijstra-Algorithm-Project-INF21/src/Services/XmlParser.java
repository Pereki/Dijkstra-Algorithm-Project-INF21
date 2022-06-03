package Services;

import Model.Graph;

public class XmlParser {
    private String path;
    private Graph graph;

    public XmlParser(String path){
        this.path = path;

        parseXmlToGraph();
    }

    private void parseXmlToGraph(){

    }

    public Graph getGraph(){
        return graph;
    }
    
}
