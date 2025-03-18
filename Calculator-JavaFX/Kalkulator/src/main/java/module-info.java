module com.example.kalkulator {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.logging;

    opens com.example.kalkulator to javafx.fxml;
    exports com.example.kalkulator;
}