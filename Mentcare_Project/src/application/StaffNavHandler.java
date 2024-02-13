package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class StaffNavHandler {
	StaffNavHandler(){
}
	public HBox createNavStaff(Stage primaryStage, String user) {
		Button homeButton = new Button("Home");
	    Button patientIntakeButton = new Button("Patient Intake");
	    Button messagesButton = new Button("Messages");
	    Button patientSearchButton = new Button("PatientSearch");
	    Button logoutButton = new Button("Log Out");
	    setButtons(homeButton, patientIntakeButton, messagesButton,patientSearchButton, logoutButton, primaryStage, user);
	    HBox navBar = new HBox(homeButton, patientIntakeButton, messagesButton, patientSearchButton, logoutButton);
	    navBar.getStyleClass().add("nav-bar");
	    navBar.setPadding(new Insets(10));
	    navBar.setSpacing(10);
		navBar.setAlignment(Pos.CENTER);
	    return navBar;
	}
	
	public void setButtons(Button home, Button intake, Button messages, Button patientSearch ,Button logout, Stage primaryStage, String user){
		home.setOnAction(e -> {
			StaffHome staffhome = new StaffHome(primaryStage, user);
			primaryStage.setScene(staffhome.createScene());
		});
		intake.setOnAction(e -> {
			PatientIntake patientIntake = new PatientIntake(primaryStage, user);
			primaryStage.setScene(patientIntake.createScene());
		});
	    patientSearch.setOnAction(e -> {
	    	StaffPatientSearch staffPatientSearch = new StaffPatientSearch(primaryStage, user);
	    	primaryStage.setScene(staffPatientSearch.createScene());
	    });
		messages.setOnAction(e -> {
		   StaffMessages staffMessages = new StaffMessages(primaryStage, user);
		   primaryStage.setScene(staffMessages.createScene());
		});
		logout.setOnAction(e -> {
			Homepage homepage = new Homepage(primaryStage);
        		primaryStage.setScene(homepage.createHomepage());
		});
	}
}
