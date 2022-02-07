package elpuig.uri.aburrit.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elpuig.uri.aburrit.AburritMain;
import elpuig.uri.aburrit.model.Bored;
import elpuig.uri.aburrit.model.BoredsList;
import elpuig.uri.aburrit.accesdata.JSONcontrol;
import elpuig.uri.aburrit.connection.Connection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Controler dels grafics
 */
public class GraficController implements Initializable {

    private JSONcontrol jsonC;
    private BoredsList boreds;

    private Parent root;
    private Stage stage;
    private Scene scene;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button buscar;
    @FXML
    private MenuItem close;
    @FXML
    private MenuItem bored;
    @FXML
    private MenuItem reset;
    @FXML
    private MenuItem light, dark, darcula, about;

    //tab
    @FXML
    private BarChart<String, Integer> dificultat;

    //tab
    @FXML
    private BarChart<String, Integer> participantsChart;

    //tab
    @FXML
    private TextArea jsonArea;

    @FXML
    private PieChart tipusChart;

    // dificultat barres
    private XYChart.Series<String, Integer> barraFacil = new XYChart.Series();
    private XYChart.Series<String, Integer> barraMig = new XYChart.Series();
    private XYChart.Series<String, Integer> barraDificil = new XYChart.Series();

    // participants
    private List<Bored> boredsFiltrats;


    /**
     * Executa una busqueda aleatoria per a poder veure els cambis als gràfics en temps real.
     * carrega els cambis als diferents gràfics de cada tab
     *
     * @param event parametre d'entrada del metode
     */
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

        // participants

        boredsFiltrats = boreds.getBoreds().stream()
                .filter(distinctByKey(p -> p.getParticipants()))
                .collect(Collectors.toList());

        List<XYChart.Series<String, Integer>> participantsXYCharts = new ArrayList<>();
        for (Bored bor :
                boredsFiltrats) {
            participantsXYCharts.add(
                    new XYChart.Series<String, Integer>());
            participantsXYCharts.get(participantsXYCharts.size() - 1).getData().add(
                    new XYChart.Data<String, Integer>(
                            String.valueOf(bor.getParticipants()
                            ), (int) boreds.getBoreds().stream().filter(
                            a -> a.getParticipants() == bor.getParticipants()).count()));

        }

        participantsChart.getData().setAll(participantsXYCharts);

        // tipus

        tipusChart.getData().clear();
        PieChart.Data slice = new PieChart.Data("recreational "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("recreational"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("recreational"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("education "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("education"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("education"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("social "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("social"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("social"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("diy "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("diy"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("diy"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("charity "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("charity"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("charity"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("cooking "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("cooking"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("cooking"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("relaxation "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("relaxation"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("relaxation"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("music "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("music"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("music"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("busywork "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("busywork"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("busywork"))
                .count()
        );
        tipusChart.getData().add(slice);


    }

    /**
     * S'executa al iniciar l'aplicació
     * -carrega una llista d'activitats (boreds) de l'arxiu dades.json
     * -carrega els diferents tabs amb la informació
     * -creem els Listeners per al cambi de tema de la app.
     *
     * @param url parametre d'entrada del metode
     * @param resourceBundle parametre d'entrada del metode
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // carreguem els
        this.jsonC = new JSONcontrol();
        boreds = jsonC.load();


        // omplim el text area amb el json

        try {
            jsonArea.setText(jsonC.getJSONasString());
        } catch (Exception e) {
            e.printStackTrace();
        }


        // iniciem la taula de dificultat
        barraFacil.getData().add(new XYChart.Data<String, Integer>("facil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() <= 0.3).count()));
        barraMig.getData().add(new XYChart.Data<String, Integer>("mig", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.3 && a.getAccessibility() <= 0.6).count()));
        barraDificil.getData().add(new XYChart.Data<String, Integer>("dificil", (int) boreds.getBoreds().stream().filter(a -> a.getAccessibility() > 0.6).count()));
        dificultat.getData().add(barraFacil);
        dificultat.getData().add(barraMig);
        dificultat.getData().add(barraDificil);

        // participants

        boredsFiltrats = boreds.getBoreds().stream()
                .filter(distinctByKey(p -> p.getParticipants()))
                .collect(Collectors.toList());

        List<XYChart.Series<String, Integer>> participantsXYCharts = new ArrayList<>();
        for (Bored bor :
                boredsFiltrats) {
            participantsXYCharts.add(
                    new XYChart.Series<String, Integer>());
            participantsXYCharts.get(participantsXYCharts.size() - 1).getData().add(
                    new XYChart.Data<String, Integer>(
                            String.valueOf(bor.getParticipants()
                            ), (int) boreds.getBoreds().stream().filter(
                            a -> a.getParticipants() == bor.getParticipants()).count()));

        }
        participantsChart.getData().setAll(participantsXYCharts);


        // listeners per al cambi de color

        light.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                AburritMain.theme = "styles/light.css";
                // Limpiar los estilos que tuviera anteriormente
                rootPane.getScene().getStylesheets().clear();
                // Aplicar la hoja de estilos
                rootPane.getScene().getStylesheets().add(
                        AburritMain.class.getResource(AburritMain.theme).toExternalForm());
            }
        });
        dark.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                AburritMain.theme = "styles/dark.css";
                // Limpiar los estilos que tuviera anteriormente
                rootPane.getScene().getStylesheets().clear();
                // Aplicar la hoja de estilos
                rootPane.getScene().getStylesheets().add(
                        AburritMain.class.getResource(AburritMain.theme).toExternalForm());
            }
        });
        darcula.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                AburritMain.theme = "styles/darcula.css";
                // Limpiar los estilos que tuviera anteriormente
                rootPane.getScene().getStylesheets().clear();
                // Aplicar la hoja de estilos
                rootPane.getScene().getStylesheets().add(
                        AburritMain.class.getResource(AburritMain.theme).toExternalForm());
            }
        });

        // tipus

        tipusChart.getData().clear();
        PieChart.Data slice = new PieChart.Data("recreational "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("recreational"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("recreational"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("education "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("education"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("education"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("social "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("social"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("social"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("diy "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("diy"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("diy"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("charity "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("charity"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("charity"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("cooking "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("cooking"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("cooking"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("relaxation "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("relaxation"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("relaxation"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("music "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("music"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("music"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("busywork "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("busywork"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("busywork"))
                .count()
        );
        tipusChart.getData().add(slice);

    }

    /**
     * Metode per a comparar els boreds per participants
     *
     * @param keyExtractor parametre d'entrada del metode
     * @param <T> parametre d'entrada del metode
     * @return parametre de sortida
     */
    private static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * Finalitza l'aplicació
     *
     * @param event parametre d'entrada del metode
     */
    @FXML
    private void closeApp(Event event) {
        Platform.exit();
    }


    /**
     * Executa el cambi d'escena a l'escena about
     *
     * @param event parametre d'entrada del metode
     */
    @FXML
    private void switchToSceneAbout(Event event) {

        FXMLLoader loader = new FXMLLoader(AburritMain.class.getResource("fxml/about.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) jsonArea.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(AburritMain.class.getResource(AburritMain.theme).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    /**
     * Elimina totes les dades de l'arxiu dades.json i per tant reinicia totes les estadístiques
     *
     * @param event parametre d'entrada del metode
     */
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


        // participants

        boredsFiltrats = boreds.getBoreds().stream()
                .filter(distinctByKey(p -> p.getParticipants()))
                .collect(Collectors.toList());

        List<XYChart.Series<String, Integer>> participantsXYCharts = new ArrayList<>();
        for (Bored bor :
                boredsFiltrats) {
            participantsXYCharts.add(
                    new XYChart.Series<String, Integer>());
            participantsXYCharts.get(participantsXYCharts.size() - 1).getData().add(
                    new XYChart.Data<String, Integer>(
                            String.valueOf(bor.getParticipants()
                            ), (int) boreds.getBoreds().stream().filter(
                            a -> a.getParticipants() == bor.getParticipants()).count()));

        }
        participantsChart.getData().setAll(participantsXYCharts);

        // tipus

        tipusChart.getData().clear();
        PieChart.Data slice = new PieChart.Data("recreational "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("recreational"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("recreational"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("education "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("education"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("education"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("social "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("social"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("social"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("diy "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("diy"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("diy"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("charity "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("charity"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("charity"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("cooking "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("cooking"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("cooking"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("relaxation "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("relaxation"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("relaxation"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("music "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("music"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("music"))
                .count()
        );
        tipusChart.getData().add(slice);
        slice = new PieChart.Data("busywork "+boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("busywork"))
                .count(), boreds.getBoreds().stream()
                .filter(p -> p.getType().equals("busywork"))
                .count()
        );
        tipusChart.getData().add(slice);
    }

    /**
     * Executa el cambi d'escena a l'escena principal
     *
     * @param event parametre d'entrada del metode
     */
    @FXML
    private void switchToSceneBored(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(AburritMain.class.getResource("fxml/bored.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) jsonArea.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(AburritMain.class.getResource(AburritMain.theme).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


}
