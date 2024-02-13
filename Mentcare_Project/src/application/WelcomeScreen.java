//Sofia Mase
//CSE 360
//Mentcare PROTOTYPE 1
//Team 18

package application;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class WelcomeScreen extends GridPane {
    //create textfield for last name input for Nurses
	private TextField lastNameField;
    //Create go Button to access next screen
	private Button goButton;

	//Welcome Screen public class
    public WelcomeScreen() {
        //set layout
    	setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        //Nurse must enter name to access patient intake
        Label lastNameLabel = new Label("Nurse:");
        lastNameField = new TextField();
        lastNameField.setPromptText("Enter Last Name");
        goButton = new Button("Go");

        add(lastNameLabel, 0, 0);
        add(lastNameField, 1, 0);
        add(goButton, 2, 0);
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    //get button
    public Button getGoButton() {
        return goButton;
    }
}
