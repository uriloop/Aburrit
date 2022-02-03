package elpuig.uri.aburrit;

import elpuig.uri.aburrit.connection.Connection;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.util.concurrent.*;

public class BoredController implements Initializable {


    @FXML
    MenuItem about;
    @FXML
    Hyperlink link;


    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    RadioButton facil, mig, dificil;
    @FXML
    ChoiceBox tipus;
    @FXML
    CheckBox gratis;
    @FXML
    TextArea activitat;
    @FXML
    Button reset;
    @FXML
    Button buscar;
    @FXML
    TextField participants;
    Object defaultValueOfChoiceBox;
    String defaultTextMessage = "Escull les opcions que prefereixis (o no escullis res) i troba una activitat per vencer l'avorriment!";


    @FXML
    private void switchToSceneAbout(Event event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("about.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage) buscar.getScene().getWindow();
        scene = new Scene(root, 420, 420);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void switchToSceneGrafic(ActionEvent event){

        FXMLLoader loader = new FXMLLoader(AburritMain.class.getResource("grafic.fxml"));
        try {
            root= loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage= (Stage) buscar.getScene().getWindow();
        scene= new Scene(root,420,420);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void closeApp(Event event) {
        Platform.exit();
    }

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

    }


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

    @FXML
    private void buscar() {

        Bored activity;
        StringBuilder sb = new StringBuilder();

        // Afegir el preu a gratis si cal
        if (gratis.isSelected()) sb.append("?price=0.0&");

        // triar o no els participants
        if (!participants.getText().equals("")) sb.append("?participants=" + participants.getText() + "&");

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

        } else
            sb.append("?minaccessibility=").append(String.valueOf(min)).append("&maxaccessibility=").append(String.valueOf(max)).append("&");

        // Afegir un tipus/categoria si cal
        if (tipus.isShowing()) {
            switch (tipus.getValue().toString()) {
                case "Education":
                    sb.append("?type=education&");
                    break;
                case "Recreational":
                    sb.append("?type=recreational&");
                    break;
                case "Social":
                    sb.append("?type=social&");
                    break;
                case "DIY":
                    sb.append("?type=diy&");
                    break;
                case "Charity":
                    sb.append("?type=charity&");
                    break;
                case "Cooking":
                    sb.append("?type=cooking&");
                    break;
                case "Relaxation":
                    sb.append("?type=relaxation&");
                    break;
                case "Music":
                    sb.append("?type=music&");
                    break;
                case "Busywork":
                    sb.append("?type=busywork&");
                    break;
                case "":
                    break;
            }
        }
        // fer la conexió que ens retorna un objecte model.
        Connection connect = new Connection();
        activity = connect.getRandomActivityToDo(sb.toString());


        //Passar les dades a la textArea de la escena
        activitat.setText(activity.getActivity() + "\nTipus: " + activity.getType() + "\nParticipants: " + activity.getParticipants() + "\nDificulat: " + activity.getAccessibility() + "\nPreu: " + activity.getPrice());

        // passar el link al label
        if (!activity.getLink().equals("")) {
            link.setText(activity.getLink());
            link.setVisited(false);
        }else{
            link.setText("");
        }

    }
}