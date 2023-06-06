package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.KingdomController;
import org.example.Controller.GameControllers.MapController;
import org.example.View.Commands;
import org.example.View.Response;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GameMenu extends Application { @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        stage.setTitle("Main Menu");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
}
