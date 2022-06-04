module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.kordamp.bootstrapfx.core;

    opens application to javafx.fxml;
    exports application;
    exports application.model;
}