module Tic.Tac.Toe {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.base;

    exports com.fx to javafx.graphics, javafx.fxml;
    opens com.fx to javafx.fxml;
}