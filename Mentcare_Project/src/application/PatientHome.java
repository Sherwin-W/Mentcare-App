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

public class PatientHome {
    String name;
    Stage primaryStage;
    Patient patient;

    public PatientHome(Stage primaryStage, Patient patient) {
        this.primaryStage = primaryStage;
        this.patient = patient;
    }

    public PatientHome(String name, Stage primaryStage, Patient patient) {
        this.name = name;
        this.primaryStage = primaryStage;
        this.patient = patient;
    }

    public Scene createScene() {
        // Create the layout for the patient home
        Label welcome = new Label("Welcome " + this.name + " !");
        welcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Label info = new Label("This is the patient portal homepage.");
        info.setStyle("-fx-font-size: 16px;");

        // Add image
        ImageView img = new ImageView(new Image("patientHomeImage.jpg"));
        img.setPreserveRatio(true);
        img.setFitWidth(400);

        PatientNavHandler handler = new PatientNavHandler();
        HBox navBar = handler.createNav(primaryStage, patient);
        
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
