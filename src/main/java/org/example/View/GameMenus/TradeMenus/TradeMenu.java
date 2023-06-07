package org.example.View.GameMenus.TradeMenus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.TradeController;
import org.example.Model.Data;

import java.net.URL;

public class TradeMenu extends Application {
    TradeController tradeController;
    private static Stage stage;
    private static BorderPane borderPane;
    private Scene scene;
    private static VBox mainVbox;


//    public MenuType run(Scanner scanner){
//        System.out.println(TradeController.tradeNotification());
//        System.out.println(TradeController.showAllPlayers());
//        while (true) {
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.EXIT).find()) {
//                System.out.println(Response.CLOSE_TRADE_MENU.message);
//                return null;
//            } else if (Commands.getMatcher(command,Commands.SEND_TRADE_REQUEST).find()) {
//                System.out.println(TradeController.sendTrade(Commands.getMatcher(command,Commands.SEND_TRADE_REQUEST)).message);
//            } else if (Commands.getMatcher(command,Commands.ACCEPT_TRADE_REQUEST).find()) {
//                System.out.println(TradeController.acceptTrade(Commands.getMatcher(command,Commands.ACCEPT_TRADE_REQUEST)).message);
//            } else if (Commands.getMatcher(command,Commands.TRADE_LIST).find()) {
//                System.out.println(TradeController.tradeList());
//            } else if (Commands.getMatcher(command,Commands.TRADE_HISTORY).find()) {
//                System.out.println(TradeController.tradeHistory());
//            } else if (Commands.getMatcher(command, Commands.REJECT_TRADE_REQUEST).find()) {
//                System.out.println(TradeController.rejectTrade(Commands.getMatcher(command, Commands.REJECT_TRADE_REQUEST)).message);
//            }else System.out.println(Response.INVALID_COMMAND.message);
//        }
//    }

    @Override
    public void start(Stage stage) throws Exception {
        Data.loadData("src/main/java/org/example/Model/Data.json");
        borderPane = FXMLLoader.load(new URL(
                TradeMenu.class.getResource("/FXML/TradeMenu.fxml").toExternalForm()
        ));
        TradeMenu.stage = stage;
        Scene scene = new Scene(borderPane);
        setMainVbox();
        stage.setScene(scene);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    private void setMainVbox(){
        VBox vBox = new VBox();
        HBox hbox = new HBox();
        vBox.setLayoutX(670);
        vBox.setLayoutY(340);
        vBox.setSpacing(30);
        hbox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        hbox.setAlignment(Pos.CENTER);
        Button newTrade = new Button("make a trade");
        Button showTrades = new Button("show trades");
        Button back = new Button("back");
        String style = "-fx-min-width: 250; -fx-max-width: 250; -fx-background-color: #e81625;" +
                " -fx-text-fill: #fdd94b; -fx-min-height: 50; -fx-max-height: 50;" +
                " -fx-font-size: 23; -fx-font-family: 'Book Antiqua'";
        newTrade.setStyle(style);
        showTrades.setStyle(style);
        back.setStyle(style);
        newTrade.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new MakeTradeMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        showTrades.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    new ShowTradeMenu().start(stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        hbox.getChildren().addAll(newTrade, showTrades);
        vBox.getChildren().addAll(hbox, back);
        mainVbox = vBox;
        borderPane.getChildren().add(vBox);
    }
}
