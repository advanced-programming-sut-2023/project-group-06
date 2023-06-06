package GraphicTest;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.MapController;
import org.example.Model.Data;
import org.example.Model.Tile;
import org.example.View.Graphics.SuperImage;

import java.net.URL;

public class GraphicTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Canvas canvas = new Canvas(100, 100);
        borderPane.setCenter(canvas);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        canvas.setHeight(600);
        canvas.setWidth(800);

        Image image = SuperImage.SEA.getImage();

        Tile[][] map = Data.loadMap("test");
        MapController.mapGraphicProcessor(canvas, map, 300, 1000);

        // graphicsContext.drawImage(image, 0, 0);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setHeight(800);
        stage.setWidth(1000);
        stage.show();
    }
}
