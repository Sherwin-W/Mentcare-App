package application;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatientAppointment {

    Stage primaryStage;
    Patient patient;
    ArrayList<Appointment> appts = new ArrayList<Appointment>();
    public PatientAppointment(Stage primaryStage, Patient patient) {
        this.primaryStage = primaryStage;
        this.patient = patient;
    }

    public Scene createScene() {
        Label welcome = new Label("Future Appointments");
        Label fut = new Label("You have no upcoming appointments");
        GridPane grid = new GridPane();
        for (int i = 0; i < appts.size(); i++) {
        	for(int j = 0; i < 4; i++) {
        		grid.add(new Label(appts.get(i).get(j)), j, i);
        	}
        }


        PatientNavHandler handler = new PatientNavHandler();
        HBox navBar = handler.createNav(primaryStage, patient);

        VBox root = new VBox(5);
        root.getChildren().addAll(navBar, welcome, grid);
        if(appts.size() == 0){
            root.getChildren().add(fut);
        }
        root.setPadding(new Insets(10));
        root.setSpacing(10);

        Scene scene = new Scene(root, 600, 400);
        return scene;
    }
}
