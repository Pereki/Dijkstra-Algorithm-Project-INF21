import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class XmlParser {
    private String path;

    public XmlParser(String path){
        this.path = path;
    }

    public void parseXmlToGraph() {

        //get all important relations
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line = br.readLine();

            while(!(line.equals(null))){
                getRelations(line);

                line = br.readLine();
            }

            br.close();
        }catch(Exception e){
            System.out.println("File not found or IO Exception!");
        }

        //next step: get all important ways

        //last step: get all important nodes
    }

    private void getRelations(String line){
        String entries [] = line.split(" ");

        if(entries[0].contains("node")){

        } else if (entries[0].contains("way")) {

        }
    }
}
