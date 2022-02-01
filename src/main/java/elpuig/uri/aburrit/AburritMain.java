package elpuig.uri.aburrit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AburritMain extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AburritMain.class.getResource("bored.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene = new Scene(root, 360, 420);
        stage.setTitle("Bored?");
        stage.setScene(scene);
        stage.getIcons().add(new Image("bored.png"));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}