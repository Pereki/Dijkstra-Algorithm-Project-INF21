module view {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens view to javafx.fxml;
    exports view;
    exports controller;
    opens controller to javafx.fxml;
    exports application;
    opens application to javafx.fxml;
}