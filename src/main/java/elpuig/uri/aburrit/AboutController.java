package elpuig.uri.aburrit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    Button back;

    @FXML
    Label text;

    @FXML
    MenuItem close;

    @FXML
    public void closeApp(Event event){
        Platform.exit();
    }

    @FXML
    public void switchToSceneBored(ActionEvent event){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bored.fxml"));
        try {
            root= loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage= (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        text.setWrapText(true);

    }
}
