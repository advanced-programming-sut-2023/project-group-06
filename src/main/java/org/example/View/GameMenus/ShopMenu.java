package org.example.View.GameMenus;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Controller.GameControllers.GameController;
import org.example.Controller.GameControllers.ShopController;
import org.example.Model.Data;
import org.example.Model.ResourcesType;
import org.example.View.Response;
import org.example.View.Translator;

import java.util.regex.Matcher;

public class ShopMenu extends Application {
    public VBox vBox;
    public Text name;
    public Button sell;
    public Button buy;
    public ImageView resourcePic;
    public Text remainingResource;
    public Text balance;
    public ImageView coin;
    public TextField amount;
    public ToggleButton back;
    public Button trade;
    ShopController shopController;
    private static Stage stage;
    private static Scene scene;

    @FXML
    private HBox actionVBox;
    private ScrollPane scrollPane;
    private static BorderPane borderPane;

    @Override
    public void start(Stage stage) throws Exception {
        borderPane = FXMLLoader.load(ShopMenu.class.getResource("/FXML/ShopMenu.fxml"));
        scrollPane = createScrollPane();
        scene = new Scene(scrollPane);
        stage.setScene(scene);
        if(!stage.isFullScreen()) stage.setFullScreen(true);
        stage.show();
    }

    public void initialize() {
        makeVbox();
        resourcePic.setImage(new Image(getResourceImageAddress(ResourcesType.STONE)));
        coin.setImage(new Image(ShopMenu.class.getResource("/Images/coinIcon.png").toExternalForm()));
        Image image = new Image(ShopMenu.class.getResource("/Images/back.png").toExternalForm());
        ImagePattern imagePattern = new ImagePattern(image);
        BackgroundFill backgroundFill = new BackgroundFill(imagePattern, null, null);
        Background background = new Background(backgroundFill);
        back.setBackground(background);
        back.setOnMouseClicked(mouseEvent -> {
            //todo
        });
        trade.setOnMouseClicked(mouseEvent -> {
            try {
                new TradeMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        name = new Text("");
    }

    private ScrollPane createScrollPane() {
        ScrollPane scrollPane = new ScrollPane(borderPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    private void makeVbox() {
        vBox.setPrefSize(200, 200);
        vBox.setSpacing(40);
        int rowNumber = 0;
        HBox row = new HBox();
        for (int i = 0; i < ResourcesType.values().length; i++) {
            row.setSpacing(10);
            row.setMinHeight(20);
            row.setMaxWidth(200);
            VBox resourcePackage = resourceImage(ResourcesType.values()[i]);
            row.getChildren().add(resourcePackage);
            row.setAlignment(Pos.CENTER);
            if (i % 3 == 2) {
                rowNumber++;
                vBox.getChildren().add(row);
                row = new HBox();
            }
        }
        if (ResourcesType.values().length % 3 != 0) vBox.getChildren().add(row);
        vBox.setAlignment(Pos.CENTER);
    }

    public VBox resourceImage(ResourcesType resourcesType) {
        VBox resourcePackage = new VBox();
        String resourceAddress = getResourceImageAddress(resourcesType);
        ImageView resourceImage = new ImageView(resourceAddress);
        resourceImage.setPreserveRatio(true);
        resourceImage.setFitWidth(100);
        Text resourceName = new Text(resourcesType.getName());
        resourceName.setStyle("-fx-font-size: 15; -fx-font-weight: BOLD");
        resourcePackage.setAlignment(Pos.CENTER);
        resourcePackage.getChildren().addAll(resourceImage, resourceName);
        resourceImage.setOnMouseClicked(mouseEvent -> {
            showInfo(resourcesType);
        });
        return resourcePackage;
    }

    private void showInfo(ResourcesType resourcesType) {
        if (actionVBox.isVisible() && name.getText().equals(resourcesType.getName())) actionVBox.setVisible(false);
        else actionVBox.setVisible(true);
        resourcePic.setImage(new Image(getResourceImageAddress(resourcesType)));
        remainingResource.setText(GameController.currentPlayer.getResourceAmountByType(resourcesType) + "");
        balance.setText(GameController.currentPlayer.getWealth() + "");
        sell.setText("sell " + resourcesType.getSellPrice());
        buy.setText("buy " + resourcesType.getBuyPrice());
        name.setText(resourcesType.getName());
    }

    private String getResourceImageAddress(ResourcesType resourcesType) {
        String address = "/Images/";
        if (resourcesType == ResourcesType.STONE) address = address + "stone11.png";
        if (resourcesType == ResourcesType.WOOD) address = address + "wood.png";
        if (resourcesType == ResourcesType.HOPS) address = address + "hops.png";
        if (resourcesType == ResourcesType.IRON) address = address + "iron2.png";
        if (resourcesType == ResourcesType.WHEAT) address = address + "wheat3.png";
        if (resourcesType == ResourcesType.PITCH) address = address + "pitch.png";
        if (resourcesType == ResourcesType.FLOUR) address = address + "flour2.png";
        if (resourcesType == ResourcesType.ALE) address = address + "ale.png";
        return ShopMenu.class.getResource(address).toExternalForm();
        //todo get resource image address by resource type!
    }

    public void sell(MouseEvent mouseEvent) {
        try {
            int amount = (this.amount.getText().equals("")) ? 1 : Integer.parseInt(this.amount.getText());
            String resourceName = name.getText();
            Matcher matcher = Translator.getMatcherByGroups(Translator.SELL, String.valueOf(amount), resourceName);
            Response response = ShopController.sell(matcher);
            Alert alert;
            if (response == Response.SELL) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("Sell Success");
                alert.setContentText("You sold " + amount + " " + resourceName + " " + "successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Sell Error");
                alert.setContentText(response.message);
            }
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Sell Error");
            alert.setContentText("Invalid amount!");
            alert.showAndWait();
        }
    }

    public void buy(MouseEvent mouseEvent) {
        try {
            int amount = Integer.parseInt(this.amount.getText());
            String resourceName = name.getText();
            Matcher matcher = Translator.getMatcherByGroups(Translator.BUY, String.valueOf(amount), resourceName);
            Response response = ShopController.buy(matcher);
            Alert alert;
            if (response == Response.BUY) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("SUCCESS");
                alert.setHeaderText("Buy Success");
                alert.setContentText("You bought " + amount + " " + resourceName + " " + "successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Buy Error");
                alert.setContentText(response.message);
            }
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Buy Error");
            alert.setContentText("Invalid amount!");
            alert.showAndWait();
        }
    }


//    public MenuType run(Scanner scanner){
//        while(true){
//            String command = scanner.nextLine();
//            if (Commands.getMatcher(command,Commands.EXIT).find()) {
//                System.out.println(Response.CLOSE_SHOP_MENU.message);
//                break;
//            } else if (Commands.getMatcher(command,Commands.SHOW_PRICES_LIST).find()) {
//                System.out.println(ShopController.showPriceList());
//            } else if (Commands.getMatcher(command,Commands.BUY).find()) {
//                System.out.println(ShopController.buy(Commands.getMatcher(command,Commands.BUY)).message);
//            } else if (Commands.getMatcher(command,Commands.SELL).find()) {
//                System.out.println(ShopController.sell(Commands.getMatcher(command,Commands.SELL)).message);
//            }
//            else System.out.println(Response.INVALID_COMMAND.message);
//        }
//        return null;
//    }
}
