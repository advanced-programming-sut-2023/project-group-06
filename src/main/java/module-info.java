module AP.Project {
    requires javafx.graphics;
    requires org.apache.commons.codec;
    requires com.google.gson;
    requires javafx.controls;
    requires javafx.fxml;

    exports GraphicTest;
    opens GraphicTest to javafx.fxml;
    exports org.example.View;
    opens org.example.View to javafx.fxml;
    exports org.example.View.GameMenus;
    opens org.example.View.GameMenus to javafx.fxml;
    exports org.example.View.GameMenus.TradeMenus;
    opens org.example.View.GameMenus.TradeMenus to javafx.fxml;

}