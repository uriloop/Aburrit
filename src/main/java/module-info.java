module elpuig.uri.aburrit {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;



    opens elpuig.uri.aburrit to javafx.fxml;
    exports elpuig.uri.aburrit;
    exports elpuig.uri.aburrit.connection;
    opens elpuig.uri.aburrit.connection to javafx.fxml;
}