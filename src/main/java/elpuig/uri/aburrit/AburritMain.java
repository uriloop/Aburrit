package elpuig.uri.aburrit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AburritMain extends Application {

    public static String theme= "darcula.css";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AburritMain.class.getResource("model/bored.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(theme);
        stage.setTitle("Bored?");
        stage.setScene(scene);
        stage.getIcons().add(new Image("bored.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}