package elpuig.uri.aburrit.controllers;

import elpuig.uri.aburrit.AburritMain;
import elpuig.uri.aburrit.model.Bored;
import elpuig.uri.aburrit.connection.Connection;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller del model Bored
 */
public class BoredController implements Initializable {


    @FXML
   private Hyperlink link;
    @FXML
    private MenuItem light, dark, darcula, about;

    private Parent root;
   private  Stage stage;
   private  Scene scene;

    @FXML
  private   AnchorPane rootPane;
    @FXML
   private  RadioButton facil, mig, dificil;
    @FXML
  private   ChoiceBox tipus;
    @FXML
  private   CheckBox gratis;
    @FXML
   private  TextArea activitat;
    @FXML
  private   Button reset, buscar;

    @FXML
  private   TextField participants;
  private   Object defaultValueOfChoiceBox;
  private   String defaultTextMessage = "Escull les opcions que prefereixis (o no escullis res) i troba una activitat per vencer l'avorriment!";


    /**ÇExecuta un cambi d'escena a a l'escena about
     * @param event
     */
    @FXML
    private void switchToSceneAbout(Event event) {

        FXMLLoader loader = new FXMLLoader(AburritMain.class.getResource("fxml/about.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) buscar.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(AburritMain.class.getResource(AburritMain.theme).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }


    /**Executa un cambi d'escena a l'escena dels grafics
     * @param event
     */
    @FXML
    private  void switchToSceneGrafic(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(AburritMain.class.getResource("fxml/grafic.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage = (Stage) buscar.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        scene.getStylesheets().add(AburritMain.class.getResource(AburritMain.theme).toExternalForm());
        stage.setScene(scene);
        stage.show();
    }


    /**Apaga l'aplicació
     * @param event
     */
    @FXML
    private void closeApp(Event event) {
        Platform.exit();
    }

    /**Metode sobreescrit que executem a iniciar l'escena.
     * -creem la llista del choice box
     * -editem alguns paramtres i mostrem el text d'explicació .
     * -creem els Listeners per al cambi de tema de la app.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // preparem el choiceBox
        ObservableList<String> hola = FXCollections.observableArrayList(
                "", "Education", "Recreational", "Social", "DIY", "Charity", "Cooking", "Relaxation", "Music", "Busywork");
        defaultValueOfChoiceBox = tipus.getValue();
        tipus.setItems(hola);
        tipus.setTooltip(new Tooltip("Select type"));

        // fem que el text del quadre de text faci salt de línia i afegim el missatge inicial
        activitat.setWrapText(true);
        activitat.setText(defaultTextMessage);
        link.setText("https://www.boredapi.com/");

        // listeners per al cambi de color o tema de l'aplicació

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

    }


    /**
     * Reinicia els valors del formulari
     */
    @FXML
    private void reiniciar() {

        // reiniciar tots els valors a 0.
        facil.setSelected(false);
        mig.setSelected(false);
        dificil.setSelected(false);
        gratis.setSelected(false);
        tipus.show();
        participants.setText("");
        tipus.setValue(defaultValueOfChoiceBox);
        tipus.hide();
        activitat.setText(defaultTextMessage);
        link.setText("https://www.boredapi.com/");
    }

    /**
     * Gestiona la búsqueda convertint el formulari en un string de parametres. Adamés conecta amb la api i mostra el resultat
     */
    @FXML
    private void buscar() {


        // construim un String amb els parametres per enviar-los a la connection i rebre un objecte bored (activitat) i mostrar-lo
        Bored activity;
        StringBuilder sb = new StringBuilder();

        // Afegir un tipus/categoria si cal

        String tipusString = "";
        try {
            tipusString = tipus.getValue().toString();
        } catch (NullPointerException e) {
        } // No s'ha seleccionat cap valor del choice box i per tant el seu valor actual és null.

        switch (tipusString.toLowerCase(Locale.ROOT)) {
            case "education":
                sb.append("&type=education");
                break;
            case "recreational":
                sb.append("&type=recreational");
                break;
            case "social":
                sb.append("&type=social");
                break;
            case "diy":
                sb.append("&type=diy");
                break;
            case "charity":
                sb.append("&type=charity");
                break;
            case "cooking":
                sb.append("&type=cooking");
                break;
            case "relaxation":
                sb.append("&type=relaxation");
                break;
            case "music":
                sb.append("&type=music");
                break;
            case "busywork":
                sb.append("&type=busywork");
                break;
            case "":
                break;

        }


        // triar o no els participants
        if (!participants.getText().equals("")) sb.append("&participants=").append(participants.getText());

        // Afegir el preu a gratis si cal
        if (gratis.isSelected()) sb.append("&price=0.0");

        // comprobar els rangs de dificultat (es poden seleccionar varies dificultats)
        float max = 0.0f, min = 0.0f;
        if (mig.isSelected()) {
            max = 0.6f;
            min = 0.3f;
        }
        if (dificil.isSelected()) {
            max = 1;
            if (min == 0) min = 0.6f;
        }
        if (facil.isSelected()) {
            min = 0;
            if (max == 0) max = 0.3f;
        }
        if (max == 0 && min == 0) {
            // descartem el cas en que no s'escull res
        } else {
            sb.append("&minaccessibility=").append(String.valueOf(min)).append("&maxaccessibility=").append(String.valueOf(max));

        }

        // fer la conexió que ens retorna un objecte model.
        Connection connect = new Connection();

// aquest try vigila que no falli la conexió a causa de:  la resposta de la api és un json predefinit d'error i per tant no pot convertir-lo.
        // si passa aixó carreguem un missatge d'error i evitem el fallo
        try {
            activity = connect.getRandomActivityToDo(sb.toString());

            //Passar les dades a la textArea de la escena
            activitat.setText(activity.getActivity() + "\nTipus: " + activity.getType() + "\nParticipants: " + activity.getParticipants() + "\nDificulat: " + activity.getAccessibility() + "\nPreu: " + activity.getPrice());

            // passar el link al label
            if (!activity.getLink().equals("")) {
                link.setText(activity.getLink());
                link.setVisited(false);
            } else {
                link.setText("");
            }

        } catch (Exception e) {
            activitat.setText("error: No activity found with the specified parameters");
        }


    }


}