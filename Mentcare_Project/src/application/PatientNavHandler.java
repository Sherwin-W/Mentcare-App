package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PatientNavHandler {
    PatientNavHandler(){
    }

    public HBox createNav(Stage primaryStage, Patient patient){
        Button homeButton = new Button("Home");
        Button appointmentsButton = new Button("Appointments");
        Button messagesButton = new Button("Messages");
        //Sofia added MyInfo Button and edits
        Button myInfoButton = new Button("MyInfo");
        Button logoutButton = new Button("Log Out");
        //Sofia updated to include myInfoButton
        setButtons(homeButton, appointmentsButton, messagesButton, myInfoButton, logoutButton, primaryStage, patient);
        HBox navBar = new HBox(homeButton, appointmentsButton, messagesButton, myInfoButton, logoutButton);
        navBar.getStyleClass().add("nav-bar");
        navBar.setPadding(new Insets(10));
        navBar.setSpacing(10);
        navBar.setAlignment(Pos.CENTER);
        return navBar;
    }
    public void setButtons(Button home, Button appointments, Button messages, Button myInfo, Button logout, Stage primaryStage, Patient patient){
        home.setOnAction(e -> {
            PatientHome patienthome = new PatientHome(primaryStage, patient);
            primaryStage.setScene(patienthome.createScene());
        });
        appointments.setOnAction(e -> {
            PatientAppointment patientAppointment = new PatientAppointment(primaryStage, patient);
            primaryStage.setScene(patientAppointment.createScene());
        });
        messages.setOnAction(e -> {
            PatientMessage patientMessage = new PatientMessage(primaryStage, patient);
            primaryStage.setScene(patientMessage.createScene());
        });
        myInfo.setOnAction(e -> {
        	PatientInfo patientinfo = new PatientInfo(primaryStage, patient);
        	primaryStage.setScene(patientinfo.createScene());
        });
        logout.setOnAction(e -> {
            Homepage homepage = new Homepage(primaryStage);
            primaryStage.setScene(homepage.createHomepage());
        });
    }
}
