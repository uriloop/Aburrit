package elpuig.uri.aburrit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.connection.Connection;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
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
    PieChart tipus;


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
    BarChart<String, Integer> dificultat;

    //tab
    @FXML
    BarChart<String, Integer> participantsChart;

    //tab
    @FXML
    TextArea jsonArea;


    // dificultat barres
    XYChart.Series<String, Integer> barraFacil = new XYChart.Series();
    XYChart.Series<String, Integer> barraMig = new XYChart.Series();
    XYChart.Series<String, Integer> barraDificil = new XYChart.Series();

    // participants
    // List<XYChart.Series<String, Integer>> opcionsParticipants = new ArrayList<>();



    /*private List<XYChart.Series<String, Integer>> getparticipantsSeries() {
        Map<Integer, Integer> mapaParticipants = new HashMap<>();
        for (Bored bor :
                boreds.getBoreds()) {
            if (mapaParticipants.containsKey(bor.getParticipants())) {
                mapaParticipants.replace(bor.getParticipants(), mapaParticipants.get(bor.getParticipants()) + 1);
            }
            mapaParticipants.putIfAbsent(bor.getParticipants(), 1);
        }
        System.out.println(mapaParticipants.toString());

        for (int i = 0; i < mapaParticipants.size(); i++) {
            opcionsParticipants.add(new XYChart.Series<>());
        }
        return opcionsParticipants;
    }*/


    @FXML
    private void buscar(Event event) {

        Connection connect = new Connection();
        connect.getRandomActivityToDo("");

        // update barres dificultat
        barraFacil.getData().add(new XYChart.Data<String, Integer>("facil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() <= 0.3).count()));
        barraMig.getData().add(new XYChart.Data<String, Integer>("mig", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.3 && a.getAccessibility() <= 0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String, Integer>("dificil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.6).count()));

        // update textarea json
        try {
            boreds = jsonC.load();
            jsonArea.setText(new ObjectMapper().writeValueAsString(boreds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
//        getparticipantsSeries();

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
        barraFacil.getData().add(new XYChart.Data<String, Integer>("facil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() <= 0.3).count()));
        barraMig.getData().add(new XYChart.Data<String, Integer>("mig", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.3 && a.getAccessibility() <= 0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String, Integer>("dificil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.6).count()));
        dificultat.getData().add(barraFacil);
        dificultat.getData().add(barraMig);
        dificultat.getData().add(barraDificil);

        //List<Integer> distinctNumbers= boreds.getBoreds().stream().map(a -> a.getParticipants()).distinct().collect(Collectors.toList());

        // tipus

        tipus.getData().add(new PieChart.Data("Recreatonial "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("recreational")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("Educational "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("education")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("Relaxation "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("relaxation")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("Social "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("social")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("DIY "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("diy")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("Charity "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("charity")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("Cooking "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("cooking")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("Music "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("music")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));
        tipus.getData().add(new PieChart.Data("Busywork "+(boreds.getBoreds().stream().filter(a -> a.getType().equals("busywork")).count()),boreds.getBoreds().stream().filter(a -> a.getType().equals("Recreational")).count()));


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
    private void resetDades(Event event) {


        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setTitle("Cuidado!");
        alert.setContentText("Segur que vols esborrar els registres?");
        alert.close();
        jsonC.deleteDades();
        try {
            boreds = jsonC.load();
            jsonArea.setText(new ObjectMapper().writeValueAsString(boreds));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // fem update de les columnes
        barraFacil.getData().add(new XYChart.Data<String, Integer>("facil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() <= 0.3).count()));
        barraMig.getData().add(new XYChart.Data<String, Integer>("mig", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.3 && a.getAccessibility() <= 0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String, Integer>("dificil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.6).count()));

        // getparticipantsSeries();

    }

    @FXML
    public void switchToSceneBored(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("bored.fxml"));
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

    private void refreshOnTabChange() {

    }

}
