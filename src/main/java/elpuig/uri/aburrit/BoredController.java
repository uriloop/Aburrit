package elpuig.uri.aburrit;

import elpuig.uri.aburrit.connection.Connection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BoredController implements Initializable {


    @FXML
    MenuItem about;

    @FXML
    TextArea textArea;

    Parent root;
    Stage stage;
    Scene scene;

    @FXML
    Slider dificultat;
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


    @FXML
    private void switchToSceneAbout(Event event) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("elpuig.uri.aburrit/about.fxml"));
        try {
            root= loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage= (Stage) textArea.getScene().getWindow();
        scene= new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void closeApp(Event event){
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> hola = FXCollections.observableArrayList(
                "", "Education", "Recreational", "Social", "DIY", "Charity", "Cooking", "Relaxation", "Music", "Busywork");
        defaultValueOfChoiceBox = tipus.getValue();

        tipus.setItems(hola);
        tipus.setTooltip(new Tooltip("Select type"));
        dificultat.setMin(0);
        dificultat.setMax(1);

    }

    @FXML
    private void reiniciar() {
        System.out.println("El valor de la barra de dificultat: "+dificultat.getValue());
        dificultat.setValue(0);
        gratis.setSelected(false);
        tipus.show();
        participants.setText("");
        tipus.setValue(defaultValueOfChoiceBox);
        tipus.hide();

    }
    @FXML
    private void buscar() {
        StringBuilder sb = new StringBuilder();
        if (gratis.isSelected()) sb.append("?price=0.0&");
        if (!participants.getText().equals("")) sb.append("?participants=" + participants.getText() + "&");
        sb.append("?minaccessibility=0&maxaccessibility=" + dificultat.getValue() + "&");
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
        Connection connect = new Connection();



        Bored activity = connect.getRandomActivityToDo(sb.toString());

        activitat.setText(activity.getActivity());
        participants.setText(String.valueOf(activity.getParticipants()));
        if (activity.getPrice() == 0) gratis.setSelected(true);
        else gratis.setSelected(false);

        dificultat.setValue(activity.getAccessibility());
        tipus.setValue(activity.getType());

    }
}