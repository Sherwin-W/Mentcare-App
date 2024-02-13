package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Homepage {
    private Stage primaryStage;

    public Homepage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createHomepage() {
    	 primaryStage.setTitle("Medical System Homepage");

    	    // Create the top section with the title
    	    Text title = new Text("HOMEPAGE");
    	    title.setFont(Font.font(24));

    	    VBox topSection = new VBox(title);
    	    topSection.setAlignment(Pos.CENTER);
    	    topSection.setPadding(new Insets(20));

    	    // Create the button for Patients
    	    Button patientButton = new Button("Patients");
    	    patientButton.setPrefWidth(120);
    	    patientButton.setStyle("-fx-font-size: 16px;");
    	    patientButton.setOnAction(e -> {
    	        // Handle button click for Patients
    	        primaryStage.setScene(new PatientLogin(primaryStage).createScene());
    	    });

    	    // Create the button for Medical Staff
    	    Button medicalStaffButton = new Button("Medical Staff");
    	    medicalStaffButton.setPrefWidth(120);
    	    medicalStaffButton.setStyle("-fx-font-size: 16px;");
    	    medicalStaffButton.setOnAction(e -> {
    	        // Handle button click for Medical Staff
    	        primaryStage.setScene(new StaffLogin(primaryStage).createScene());
    	    });

    	    HBox buttonSection = new HBox(medicalStaffButton, patientButton);
    	    buttonSection.setSpacing(10);
    	    buttonSection.setAlignment(Pos.CENTER);

    	    // Create the main layout
    	    BorderPane root = new BorderPane();
    	    root.setTop(topSection);
    	    root.setCenter(buttonSection);
    	    root.setStyle("-fx-background-color: #f0f0f0;");

    	    return new Scene(root, 400, 200);
    }
}
