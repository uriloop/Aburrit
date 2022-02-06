/**
 * Modul del projecte
 */
module elpuig.uri.aburrit {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;



    opens elpuig.uri.aburrit to javafx.fxml;
    exports elpuig.uri.aburrit;
    exports elpuig.uri.aburrit.connection;
    opens elpuig.uri.aburrit.connection to javafx.fxml;
    exports elpuig.uri.aburrit.controllers;
    opens elpuig.uri.aburrit.controllers to javafx.fxml;
    exports elpuig.uri.aburrit.model;
    opens elpuig.uri.aburrit.model to javafx.fxml;
    exports elpuig.uri.aburrit.accesdata;
    opens elpuig.uri.aburrit.accesdata to javafx.fxml;
}