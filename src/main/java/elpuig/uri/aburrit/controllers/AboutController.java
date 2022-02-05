package elpuig.uri.aburrit.controllers;

import elpuig.uri.aburrit.AburritMain;
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
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public class AboutController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    Button back;

    @FXML
    TextArea text;

    @FXML
    MenuItem close;

    @FXML
    AnchorPane rootPane;

    @FXML
    MenuItem light, dark, darcula;

    @FXML
    public void closeApp(Event event) {
        Platform.exit();
    }

    @FXML
    public void switchToSceneBored(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(AburritMain.class.getResource("model/bored.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) back.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(AburritMain.theme);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void setLightTheme(Event event){
        AburritMain.theme="light.css";

    }
    @FXML
    public void setDarkTheme(Event event){
        AburritMain.theme="dark.css";

    }
    @FXML
    public void setDarculaTheme(Event event){
        AburritMain.theme="darcula.css";

    }

    @FXML
    public void switchToSceneGrafic(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(AburritMain.class.getResource("model/grafic.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) back.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(AburritMain.theme);
        stage.setScene(scene);
        stage.show();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        text.setMinSize(200, 200);


        text.setText(
                "\nAbout: \n\n" +
                "What's the purpose behind this API?\n" +
                "\n" +
                "The Bored API helps you find things to do when you're bored! There are fields like the number of participants, activity type, and more that help you narrow down your results.\n" +
                "\n" +
                "What did you build the API with?\n" +
                "\n" +
                "This whole application is a MEVN web application hosted on Heroku. The tech stack is listed below:\n" +
                "\n" +
                "Frontend\n" +
                "\n" +
                "    Vue.js\n" +
                "    Webpack\n" +
                "\n" +
                "Backend\n" +
                "\n" +
                "    Node.js\n" +
                "    MongoDB and Mongoose\n" +
                "\n" +
                "How do I use the this?\n" +
                "\n" +
                "Please check out the documentation for examples. If you still can't figure it out, feel free to submit a help ticket.\n" +
                "\n" +
                "This is awesome! How can I contribute?\n" +
                "\n" +
                "Any new suggestions or features would be very helpful! If you have an idea for an activity, we have a suggestion form on the contributing page. Otherwise, feel free to submit a pull request or a feature request.");



        text.setWrapText(true);


        // listeners per al cambi de color

        light.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                AburritMain.theme="light.css";
                // Limpiar los estilos que tuviera anteriormente
                rootPane.getScene().getStylesheets().clear();
                // Aplicar la hoja de estilos
                rootPane.getScene().getStylesheets().add(
                        (AburritMain.theme));
            }
        });
        dark.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                AburritMain.theme="dark.css";
                // Limpiar los estilos que tuviera anteriormente
                rootPane.getScene().getStylesheets().clear();
                // Aplicar la hoja de estilos
                rootPane.getScene().getStylesheets().add(
                        (AburritMain.theme));
            }
        });
        darcula.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                AburritMain.theme="darcula.css";
                // Limpiar los estilos que tuviera anteriormente
                rootPane.getScene().getStylesheets().clear();
                // Aplicar la hoja de estilos
                rootPane.getScene().getStylesheets().add(
                        (AburritMain.theme));
            }
        });
    }
}
