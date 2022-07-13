package application;

import controller.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;

public class Application extends javafx.application.Application {
    public static Controller controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mapview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Dijkstra Routenfinder");
        stage.setScene(scene);
        InputStream stream = ResourceLoader.get("logo.png");
        if (stream != null) {
            Image icon = new Image(stream);
            stage.getIcons().add(icon);
        }
        stage.show();
        controller = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        launch();
    }

}