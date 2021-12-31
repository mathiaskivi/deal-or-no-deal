module ee.mathiaskivi.dond {
    requires javafx.controls;
    requires javafx.fxml;

    opens ee.mathiaskivi.dond to javafx.fxml;
    exports ee.mathiaskivi.dond;
}