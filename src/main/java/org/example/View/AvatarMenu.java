package org.example.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.Model.Data;

import java.net.URL;
import java.util.ArrayList;

public class AvatarMenu extends Application {
    private Scene scene;
    private static Stage stage;
    private static BorderPane borderPane;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(new URL(
                AvatarMenu.class.getResource("/FXML/AvatarMenu.fxml").toExternalForm()
        ));
        AvatarMenu.stage = stage;
        ArrayList<Circle> circles = new ArrayList<>();
        for(int i = 1; i <= 11; i++){
            double y = i < 5 ? 200 : i < 9 ? 400 : 600;
            double x = ((i - 1) % 4 + 1) * 250;
            Circle circle = new Circle(x, y - 50, 90);
            Image image = new Image(AvatarMenu.class.getResource("/Images/avatar" + i + ".jpg").toExternalForm());
            circle.setFill(new ImagePattern(image));
            circle.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Data.getCurrentUser().setAvatar(image);
                }
            });
            circles.add(circle);
            borderPane.getChildren().add(circle);
        }
        Button select = new Button("select file from system");;
        select.setStyle("-fx-background-color: #0a74c7; -fx-min-width: 300;" +
                "    -fx-text-fill: white;\n" +
                "    -fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;");
        Button back = new Button("back");
        back.setStyle("-fx-background-color: #2196F3; -fx-min-width: 300;" +
                "    -fx-text-fill: white;\n" +
                "    -fx-font-size: 16px;\n" +
                "    -fx-font-weight: bold;");
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    back();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        VBox hBox = new VBox(select, back);
        hBox.setSpacing(10);
        hBox.setLayoutX(940);
        hBox.setLayoutY(510);
        borderPane.getChildren().add(hBox);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    private void back() throws Exception {
        new ProfileMenu().start(stage);
    }
}
