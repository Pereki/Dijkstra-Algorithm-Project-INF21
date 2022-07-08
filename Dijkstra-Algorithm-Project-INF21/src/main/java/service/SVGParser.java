package service;

import javafx.scene.shape.SVGPath;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SVGParser {

    public static List<List<SVGPath>> parse(File file) throws Exception {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            if (e instanceof FileNotFoundException) throw (FileNotFoundException) e;
            System.out.println("IO Exception!");
            e.printStackTrace();
        }
        String content = sb.toString();
        List<String> svgStrings = extractSvgStrings(content);
        List<List<SVGPath>> svgs = new ArrayList<>();
        for (String s : svgStrings) {
            svgs.add(createSvgFromString(s));
        }
        return svgs;
    }

    private static final String svgStart = "<svg";
    private static final String svgEnd = "</svg>";

    private static List<String> extractSvgStrings(String content) throws Exception {
        List<String> strings = filterStringForTag(content, svgStart, svgEnd);
        for (int i = 0; i < strings.size(); i++) {
            String s = strings.get(i);
            s = s.substring(s.indexOf(">") + 1);
            s = s.substring(0, s.indexOf("</svg>"));
            strings.set(i, s);
        }
        return strings;
    }

    private static final String pathStart = "<path";
    private static final String pathEnd = "</path>";

    private static List<SVGPath> createSvgFromString(String string) throws Exception {
        List<SVGPath> paths = new ArrayList<>();
        List<String> strings = filterStringForTag(string, pathStart, pathEnd);
        for (String s : strings) {
            SVGPath p = new SVGPath();
            s = s.substring(s.indexOf("d=\"") + 3);
            s = s.substring(0, s.indexOf("\""));
            p.setContent(s);
            paths.add(p);
        }
        return paths;
    }

    private static List<String> filterStringForTag(String string, String startString, String endString) throws Exception {
        List<String> list = new ArrayList<>();
        int tagOpenStart = string.indexOf(startString);
        while (tagOpenStart != -1) {
            int tagOpenEnd = tagOpenStart + string.substring(tagOpenStart).indexOf(">");
            int tagEnd = string.indexOf(endString);
            if (tagEnd < tagOpenStart) throw new Exception("Malformed SVG String");
            list.add(
                    string.substring(tagOpenStart, tagEnd + endString.length())
            );
            string = string.substring(tagEnd + endString.length());
            tagOpenStart = string.indexOf(startString);
        }
        return list;
    }

}
