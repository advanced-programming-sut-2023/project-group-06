module AP.Project {
    requires javafx.graphics;
    requires org.apache.commons.codec;
    requires com.google.gson;

    exports GraphicTest;
    opens GraphicTest to javafx.fxml;
}