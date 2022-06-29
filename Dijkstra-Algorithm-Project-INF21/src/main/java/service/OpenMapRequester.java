package Services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class OpenMapRequester {

    public static void main(String[] args) throws IOException {
        OpenMapRequester r = new OpenMapRequester();
        String t = r.getCompleteReturn(new String[]{"8.681495", "49.41461"}, new String[]{"8.5164553", "50.5808110"});
        System.out.println(t);
    }

    public double getDuration(String[] start, String[] end) throws IOException {
        String result = getCompleteReturn(start, end);

        String[] resultsplit = result.split("\"summary\":");
        String[] returnsplit = resultsplit[1].split("},\"way_points\"");
        String[] returnsplited = returnsplit[0].split(",\"duration\":");
        return Double.parseDouble(returnsplited[1]);
    }


    public double getDistance(String[] start, String[] end) throws IOException {
        String result = getCompleteReturn(start, end);

        String[] resultsplit = result.split("\"summary\":");
        String[] returnsplit = resultsplit[1].split(",");
        String[] returner =  returnsplit[0].split(":");
        return Double.parseDouble(returner[1]);
    }


    public String getCompleteReturn(String[] start, String[] end) throws IOException {
        String url = "https://api.openrouteservice.org/v2/directions/driving-car?api_key=5b3ce3597851110001cf6248595d0baf4b204b90ad00de85ee212c7f&start=" + start[0] + "," + start[1] + "&end=" + end[0] + "," + end[1];
        URL apicall = new URL(url);
        URLConnection connection = apicall.openConnection();

        InputStreamReader stream = new InputStreamReader(connection.getInputStream());
        BufferedReader in = new BufferedReader(stream);

        String inputLine;
        String returner = "";

        while ((inputLine = in.readLine()) != null){
            returner += inputLine;
        }

        in.close();
        return returner;
        }
}
