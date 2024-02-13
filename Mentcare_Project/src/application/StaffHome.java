package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StaffHome {
    String user;
    Stage primaryStage;

    public StaffHome(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public StaffHome(Stage primaryStage, String user) {
        this.user = user;
        this.primaryStage = primaryStage;
    }

    public Scene createScene() {
        // Create the layout for the staff home
        Label welcome = new Label("Welcome " + this.user + " !");
        welcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label info = new Label("This is the staff portal homepage.");
        info.setStyle("-fx-font-size: 16px;");

        // Add image
        ImageView img = new ImageView(new Image("nurseCartoon.png"));
        img.setPreserveRatio(true);
        img.setFitWidth(400);

        StaffNavHandler handler = new StaffNavHandler();
        HBox navBar = handler.createNavStaff(primaryStage, user);
        
        VBox textContainer = new VBox(welcome, info);
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setSpacing(10);

        BorderPane root = new BorderPane();
        root.setTop(navBar);
        BorderPane.setAlignment(navBar, Pos.TOP_CENTER);
        
        StackPane imagePane = new StackPane(img);
        
        VBox centerContainer = new VBox(textContainer, imagePane);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(20);
        
        root.setCenter(centerContainer);
        BorderPane.setMargin(centerContainer, new Insets(10));
        
        Scene scene = new Scene(root, 600, 400);
        return scene;
    }
}
