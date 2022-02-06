package elpuig.uri.aburrit;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe Main d'esde on llancem la app
 */
public class AburritMain extends Application {

    public static String theme= "styles/darcula.css";

    /**Metode sobreescrit que executa la app
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AburritMain.class.getResource("fxml/bored.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(getClass().getResource(theme).toExternalForm());
        stage.setTitle("Bored?");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResource("img/bored.png").toExternalForm()));
        stage.show();
    }

    /**El fil principal per executar la app
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}