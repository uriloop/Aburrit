package elpuig.uri.aburrit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.connection.Connection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GraficController implements Initializable {

    JSONcontrol jsonC;
    BoredsList boreds;

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    Button buscar;
    @FXML
    MenuItem about;
    @FXML
    MenuItem close;
    @FXML
    MenuItem bored;
    @FXML
    MenuItem reset;

    //tab
    @FXML
    BarChart<String,Integer> dificultat;

    //tab
    @FXML
    TextArea jsonArea;



    XYChart.Series<String,Integer> barraFacil = new XYChart.Series();
    XYChart.Series<String,Integer> barraMig = new XYChart.Series();
    XYChart.Series<String,Integer> barraDificil = new XYChart.Series();
    XYChart.Data<String,Integer> facilData;
    XYChart.Data<String,Integer> migData;
    XYChart.Data<String,Integer> dificilData;


    @FXML
    private void buscar(Event event){

        Connection connect = new Connection();
        connect.getRandomActivityToDo("");

        barraFacil.getData().add(new XYChart.Data<String,Integer>("facil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()<=0.3).count()));
        barraMig.getData().add(new XYChart.Data<String,Integer>("mig",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.3&& a.getAccessibility()<=0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String,Integer>("dificil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.6).count()));
        try {
            boreds=jsonC.load();
            jsonArea.setText(new ObjectMapper().writeValueAsString(boreds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.jsonC = new JSONcontrol();
        boreds = jsonC.load();

        try {
            jsonArea.setText(new ObjectMapper().writeValueAsString(boreds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        //  El que ha d'agafar
        //


        barraFacil.getData().add(new XYChart.Data<String,Integer>("facil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()<=0.3).count()));
        barraMig.getData().add(new XYChart.Data<String,Integer>("mig",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.3&& a.getAccessibility()<=0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String,Integer>("dificil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.6).count()));dificultat.getData().add(barraFacil);
        dificultat.getData().add(barraMig);
        dificultat.getData().add(barraDificil);

    }

    @FXML
    public void closeApp(Event event) {
        Platform.exit();
    }

    @FXML
    private void switchToSceneAbout(Event event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) jsonArea.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        stage.setScene(scene);
        stage.show();

    }
    @FXML
    private void resetDades(Event event){


        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Cuidado!");
        alert.setContentText("Segur que vols esborrar els registres?");
        alert.close();
        jsonC.deleteDades();
        try {
            boreds=jsonC.load();
            jsonArea.setText(new ObjectMapper().writeValueAsString(boreds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // fem update de les columnes
        barraFacil.getData().add(new XYChart.Data<String,Integer>("facil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()<=0.3).count()));
        barraMig.getData().add(new XYChart.Data<String,Integer>("mig",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.3&& a.getAccessibility()<=0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String,Integer>("dificil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.6).count()));


    }

    @FXML
    public void switchToSceneBored(ActionEvent event){

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bored.fxml"));
        try {
            root= loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage= (Stage) jsonArea.getScene().getWindow();
        scene= new Scene(root,420,420);
        stage.setScene(scene);
        stage.show();
    }



}
