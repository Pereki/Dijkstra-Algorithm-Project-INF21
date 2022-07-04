package service;

import javafx.scene.shape.SVGPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.io.File;
import java.util.List;

@Testable
public class SVGParserTest {

    @Test
    public void test1() {
        File f = new File(
                "C:\\Users\\David\\OneDrive\\Dokumente\\Beruflich\\Duales Studium\\DH\\Vorlesungen\\2. Semester\\Programmieren\\Programmierprojekt\\Sonstige\\de.svg"
        );
        List<List<SVGPath>> svgs = null;
        try {
            svgs = SVGParser.parse(f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(svgs.size() > 0);
    }

}
