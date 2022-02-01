package elpuig.uri.aburrit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class BoredController implements Initializable {


    @FXML
    MenuItem about;

    @FXML
    TextArea textArea;

    Parent root;
    Stage stage;
    Scene scene;




    @FXML
    private void switchToSceneAbout(Event event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        try {
            root= loader.load();


        } catch (IOException e) {
            e.printStackTrace();
        }

        stage= (Stage) textArea.getScene().getWindow();
        scene= new Scene(root,360,420);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void closeApp(Event event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


       /* about.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
                try {
                    root = loader.load();
                    stage = (Stage) ((Node)e.getSource()).getParent().getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });*/

    }
}