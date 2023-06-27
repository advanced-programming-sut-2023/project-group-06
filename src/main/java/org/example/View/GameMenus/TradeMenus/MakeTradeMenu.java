package org.example.View.GameMenus.TradeMenus;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.TradeController;
import org.example.Model.Data;
import org.example.Model.ResourcesType;
import org.example.Model.User;
import org.example.View.Response;
import org.example.View.Translator;

import java.util.Objects;

public class MakeTradeMenu extends Application {
    public VBox mainVBox;
    private static Stage stage;
    public Button backButton;
    private Scene scene;
    private static BorderPane borderPane;
    private static Label tradeLabel;
    private static ResourcesType resourceType;
    private static Label amountLabel;
    private static int amount;
    private static HBox priceHBox;
    private static TextField massage;
    private static User dealer;

    @Override
    public void start(Stage stage) throws Exception {
        MakeTradeMenu.stage = stage;
        borderPane = FXMLLoader.load(MakeTradeMenu.class.getResource("/FXML/MakeTradeMenu.fxml"));
        ScrollPane scrollPane = new ScrollPane(borderPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        borderPane.requestFocus();
        borderPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(Objects.equals(keyEvent.getCode().getName(), "Equals")
                        || Objects.equals(keyEvent.getCode().getName(), "Add")){
                    amount++;
                    amountLabel.setText("amount: " + amount);
                }
                if(Objects.equals(keyEvent.getCode().getName(), "Minus")
                        || Objects.equals(keyEvent.getCode().getName(), "Subtract")){
                    if(amount > 0) amount--;
                    amountLabel.setText("amount: " + amount);
                }
            }
        });
        scene = new Scene(scrollPane);
        stage.setScene(scene);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    public void initialize(){
        for(User user : Data.getUsers()){
            Button label = new Button(user.getUsername());
            label.setStyle("-fx-min-width: 400; -fx-max-width: 400; -fx-background-color: #e81625;" +
                    " -fx-text-fill: #fdd94b; -fx-min-height: 50; -fx-max-height: 50; -fx-font-size: 23;" +
                    "-fx-font-family: 'Book Antiqua'");
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    dealer = user;
                    makeTrade(user);
                }
            });
            addLabel(label, mainVBox);
        }
    }

    private void makeTrade(User user) {
        backButton.setVisible(false);
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        Text text = new Text();
        text.setText("make trade with " + user.getUsername());
        text.setFont(Font.font("Book Antiqua", 40));
        vBox.getChildren().addAll(text, createEmptyLabel(30));
        makeVbox(vBox);
        vBox.getChildren().add(createEmptyLabel(50));
        Label label1 = new Label("trade merchant:  ");
        Label label2 = new Label("amount: " + amount);
        tradeLabel = label1;
        amountLabel = label2;
        Button plusButton = new Button("+");
        Button minusButton = new Button("-");
        String style1 = "-fx-max-width: 27; -fx-min-width: 27; -fx-border-radius: 14;" +
                " -fx-background-radius: 14; -fx-background-color: #108bd7; -fx-font-size: 13";
        plusButton.setStyle(style1);
        minusButton.setStyle(style1);
        plusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                amount++;
                label2.setText("amount: " + amount);
            }
        });
        minusButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(amount > 0) amount--;
                label2.setText("amount: " + amount);
            }
        });
        String style = "-fx-font-family: 'Book Antiqua'; -fx-font-size: 20; -fx-background-color: #ffad00;" +
                " -fx-min-width: 230; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #b68e43";
        label1.setStyle(style);
        label2.setStyle(style);
        HBox hBox = new HBox(label1, label2, plusButton, minusButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        Button request = new Button("request");
        Button donate = new Button("donate");
        String style2 = "-fx-min-width: 200; -fx-max-width: 200; -fx-background-color: #e81625;" +
                " -fx-text-fill: #fdd94b; -fx-min-height: 50; -fx-max-height: 50; -fx-font-size: 23;" +
                "-fx-font-family: 'Book Antiqua'";
        request.setStyle(style2);
        donate.setStyle(style2);
        request.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                request();
            }
        });
        donate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                donate();
            }
        });
        HBox hBox1 = new HBox(request, donate);
        hBox1.setSpacing(10);
        hBox1.setAlignment(Pos.CENTER);
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(createEmptyLabel(40));
        vBox.getChildren().add(hBox1);
        Label label = new Label("massage: ");
        TextField textField = new TextField();
        massage = textField;
        textField.setMinWidth(550);
        textField.setMaxWidth(550);
        textField.setMinHeight(50);
        textField.setStyle("-fx-background-color: #faeb8b; -fx-font-size: 17; -fx-font-family: 'Book Antiqua'");
        HBox hBox2 = new HBox(label, textField);
        hBox2.setAlignment(Pos.CENTER);
        vBox.getChildren().add(createEmptyLabel(6));
        Label label3 = new Label("price:");
        label3.setMinWidth(75);
        label3.setMaxWidth(75);
        Label price = new Label("0");
        TextField priceTextField = new TextField();
        priceTextField.setStyle("-fx-min-width: 60; -fx-max-width: 60; -fx-background-color: #abffc7; -fx-font-size: 12");
        price.setMaxWidth(80);
        price.setMinWidth(80);
        priceTextField.setVisible(false);
        StackPane stackPane = new StackPane(price, priceTextField);
        Button save = new Button("save");
        save.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                save();
            }
        });
        Button change = new Button("change");
        change.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                change();
            }
        });
        String style3 = "-fx-min-width: 100; -fx-max-width: 100; -fx-background-color: #e81625;" +
                " -fx-text-fill: #fdd94b; -fx-min-height: 30; -fx-max-height: 30; -fx-font-size: 14;" +
                "-fx-font-family: 'Book Antiqua'";
        save.setStyle(style3);
        change.setStyle(style3);
        save.setVisible(false);
        StackPane stackPane1 = new StackPane(change, save);
        HBox hBox3 = new HBox(label3, stackPane, stackPane1);
        priceHBox = hBox3;
        hBox3.setAlignment(Pos.CENTER);
        hBox3.setSpacing(5);
        hBox3.setVisible(false);
        vBox.getChildren().add(hBox3);
        vBox.getChildren().add(createEmptyLabel(6));
        vBox.getChildren().add(hBox2);
        vBox.getChildren().add(createEmptyLabel(40));
        Button confirm = new Button("confirm");
        Button back = new Button("back");
        confirm.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                confirm();
            }
        });
        back.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    back(mouseEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        confirm.setStyle(style2);
        back.setStyle(style2);
        HBox hBox4 = new HBox(confirm, back);
        hBox4.setSpacing(20);
        hBox4.setAlignment(Pos.CENTER);
        vBox.getChildren().add(hBox4);
        borderPane.setCenter(vBox);
    }

    private void confirm() {
        String type = resourceType == null ? "sth" : resourceType.getName();
        Response response = TradeController.sendTrade(Translator.getMatcherByGroups(
                Translator.SEND_TRADE_REQUEST, type, Objects.toString(amount), massage.getText(),
                ((Label)((StackPane)priceHBox.getChildren().get(1)).getChildren().get(0)).getText(), dealer.getUsername()));
        Alert alert;
        if(response != Response.TRADE_REQUEST_CREATED){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(response.message);
            alert.setHeaderText("send request failed!");
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText(response.message);
            alert.setHeaderText("trade request sent");
        }
        alert.showAndWait();
    }

    private void change() {
        ((StackPane) priceHBox.getChildren().get(1)).getChildren().get(1).setVisible(true);
        ((StackPane) priceHBox.getChildren().get(1)).getChildren().get(0).setVisible(false);
        ((StackPane) priceHBox.getChildren().get(2)).getChildren().get(1).setVisible(true);
        ((StackPane) priceHBox.getChildren().get(2)).getChildren().get(0).setVisible(false);
    }

    private void save() {
        String text = ((TextField)((StackPane) priceHBox.getChildren().get(1)).getChildren().get(1)).getText();
        if(!text.matches("\\d*")) return;
        if(text.equals("")) text = "0";
        ((StackPane) priceHBox.getChildren().get(1)).getChildren().get(0).setVisible(true);
        ((StackPane) priceHBox.getChildren().get(1)).getChildren().get(1).setVisible(false);
        ((StackPane) priceHBox.getChildren().get(2)).getChildren().get(0).setVisible(true);
        ((StackPane) priceHBox.getChildren().get(2)).getChildren().get(1).setVisible(false);
        ((Label)((StackPane) priceHBox.getChildren().get(1)).getChildren().get(0)).setText(text);
    }

    private void donate() {
        priceHBox.setVisible(false);
        ((Label)((StackPane)priceHBox.getChildren().get(1)).getChildren().get(0)).setText("0");
        ((TextField)((StackPane)priceHBox.getChildren().get(1)).getChildren().get(1)).setText("0");
    }

    private void request() {
        priceHBox.setVisible(true);
    }

    public void back(MouseEvent mouseEvent) throws Exception {
        new TradeMenu().start(stage);
    }

    private void addLabel(Button label, VBox vBox){
        vBox.getChildren().add(label);
    }

    private void makeVbox(VBox vBox) {
        HBox row = new HBox();
        for (int i = 0; i < ResourcesType.values().length; i++) {
            row.setSpacing(10);
            row.setMinHeight(20);
            row.setMaxWidth(200);
            VBox resourcePackage = resourceImage(ResourcesType.values()[i]);
            row.getChildren().add(resourcePackage);
            row.setAlignment(Pos.CENTER);
            if (i == 7) {
                vBox.getChildren().add(row);
                row = new HBox();
            }
        }
        if (ResourcesType.values().length % 3 != 0) vBox.getChildren().add(row);
    }

    public VBox resourceImage(ResourcesType resourcesType) {
        VBox resourcePackage = new VBox();
        String resourceAddress = getResourceImageAddress(resourcesType);
        ImageView resourceImage = new ImageView(resourceAddress);
        resourceImage.setPreserveRatio(true);
        resourceImage.setFitWidth(100);
        Text resourceName = new Text(resourcesType.getName());
        resourceName.setStyle("-fx-font-size: 15; -fx-font-family: 'Book Antiqua'");
        resourcePackage.setAlignment(Pos.CENTER);
        resourcePackage.getChildren().addAll(resourceImage, resourceName);
        resourceImage.setOnMouseClicked(mouseEvent -> {
            tradeLabel.setText("trade merchant: " + resourcesType.getName());
            resourceType = resourcesType;
        });
        return resourcePackage;
    }

    private String getResourceImageAddress(ResourcesType resourcesType) {
        if(resourcesType == ResourcesType.WHEAT)
            return MakeTradeMenu.class.getResource("/Images/wheat3.png").toString();
        else if(resourcesType == ResourcesType.FLOUR)
            return MakeTradeMenu.class.getResource("/Images/flour2.png").toString();
        else if(resourcesType == ResourcesType.STONE)
            return MakeTradeMenu.class.getResource("/Images/stone11.png").toString();
        else if(resourcesType == ResourcesType.WOOD)
            return MakeTradeMenu.class.getResource("/Images/wood.png").toString();
        else if(resourcesType == ResourcesType.IRON)
            return MakeTradeMenu.class.getResource("/Images/iron2.png").toString();
        else if(resourcesType == ResourcesType.HOPS)
            return MakeTradeMenu.class.getResource("/Images/hops.png").toString();
        else if(resourcesType == ResourcesType.ALE)
            return MakeTradeMenu.class.getResource("/Images/ale.png").toString();
        else return MakeTradeMenu.class.getResource("/Images/pitch.png").toString();
    }

    private Label createEmptyLabel(int width){
        Label label = new Label();
        label.setStyle("-fx-min-height: " + width);
        return label;
    }
}
