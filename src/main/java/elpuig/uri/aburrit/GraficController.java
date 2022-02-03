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
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    AreaChart<String,Integer> participantsChart;

    //tab
    @FXML
    TextArea jsonArea;



    // dificultat barres
    XYChart.Series<String,Integer> barraFacil = new XYChart.Series();
    XYChart.Series<String,Integer> barraMig = new XYChart.Series();
    XYChart.Series<String,Integer> barraDificil = new XYChart.Series();

    // participants




    @FXML
    private void buscar(Event event){

        Connection connect = new Connection();
        connect.getRandomActivityToDo("");

        // update barres dificultat
        barraFacil.getData().add(new XYChart.Data<String,Integer>("facil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()<=0.3).count()));
        barraMig.getData().add(new XYChart.Data<String,Integer>("mig",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.3&& a.getAccessibility()<=0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String,Integer>("dificil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.6).count()));

        // update textarea json
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


        // omplim el text area amb el json
        try {
            jsonArea.setText(new ObjectMapper().writeValueAsString(boreds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        // iniciem la taula de dificultat
        barraFacil.getData().add(new XYChart.Data<String,Integer>("facil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()<=0.3).count()));
        barraMig.getData().add(new XYChart.Data<String,Integer>("mig",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.3&& a.getAccessibility()<=0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String,Integer>("dificil",(int)boreds.getBoreds().stream().filter(a -> a.getAccessibility()>0.6).count()));
        dificultat.getData().add(barraFacil);
        dificultat.getData().add(barraMig);
        dificultat.getData().add(barraDificil);

        /*// iniciem el grafic de participants
        Collection<Bored> integers=boreds.getBoreds().stream().collect(Collectors.toMap(Bored::getParticipants, p -> p, (p, q) -> p)).values();
        int numOpcions=integers.size();
        List<XYChart.Series<String,Integer>> participantsXYCharts= new ArrayList<>();
        for (Bored bor:
             integers) {
            participantsXYCharts.add(new XYChart.Series<Integer,Integer>().getData().add(new XYChart.Data<Integer,Integer>(
                    bor.getParticipants(),(int)integers.stream().filter(a -> a.getParticipants()==bor.getParticipants())
            )));
        }
        */
        /*List<Bored> bors=boreds.getBoreds().stream().filter(distinctByKey(p -> p.getFname()).distinct().collect(Collectors.toList());

        Stream<Bored> distincts=boreds.getBoreds()
                .stream()
                .distinct();
        List<XYChart.Series<String,Integer>> participantsXYCharts= new ArrayList<>();*/
        // per cada numero de participants diferent.
        /*for (Bored bor:
             distincts.toList()) {
            // per cada opció diferent he de fer un label de tipus de valor, més tard per cada contar tots els casos en que el valor és igual a aquest label.
            // és a dir. fer un distinct per quedar-me totes les opcions possibles. després fer un count on filter = a.getParticipants==label.getParticipants();
            *//* participantsXYCharts.add(
                    new XYChart.Series<Integer,Integer>().getData().add( new XYChart.Data<Integer,Integer>((int)bor.getParticipants(),
                            (int) boreds.getBoreds()
                            .stream()
                            .filter(a -> a.getParticipants()== bor.getParticipants()).count())));*//*

        }*/


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
