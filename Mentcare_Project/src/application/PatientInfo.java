package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientInfo {

    Stage primaryStage;
    Patient patient;
    Connection connection;

    public PatientInfo(Stage primaryStage, Patient patient) {
        this.primaryStage = primaryStage;
        this.patient = patient != null ? patient : new Patient();
    }

    public Scene createScene() {
        TextField txtFirstName = new TextField(patient.getFirstName());
        TextField txtLastName = new TextField(patient.getLastName());
        TextField txtDob = new TextField(patient.getDOB());
        TextField txtAddress = new TextField(patient.getAddress());
        TextField txtPhoneNumber = new TextField(patient.getPhoneNum());
        TextField txtHealthNumber = new TextField(patient.getHealthNum());
        TextField txtEmail = new TextField(patient.getEmail());
        TextField txtPassword = new TextField(patient.getPassword());

        Label lblFirstName = new Label("First Name: ");
        Label lblLastName = new Label("Last Name: ");
        Label lblDob = new Label("Date of Birth: ");
        Label lblAddress = new Label("Address: ");
        Label lblPhoneNumber = new Label("Phone Number: ");
        Label lblHealthNumber = new Label("Health Number: ");
        Label lblEmail = new Label("Email: ");
        Label lblPassword = new Label("Password: ");

        HBox hBoxFirstName = new HBox(lblFirstName, txtFirstName);
        HBox hBoxLastName = new HBox(lblLastName, txtLastName);
        HBox hBoxDob = new HBox(lblDob, txtDob);
        HBox hBoxAddress = new HBox(lblAddress, txtAddress);
        HBox hBoxPhoneNumber = new HBox(lblPhoneNumber, txtPhoneNumber);
        HBox hBoxHealthNumber = new HBox(lblHealthNumber, txtHealthNumber);
        HBox hBoxEmail = new HBox(lblEmail, txtEmail);
        HBox hBoxPassword = new HBox(lblPassword, txtPassword);

        Button save = new Button("Save Info");
        save.setStyle("-fx-background-color: #6495ED; -fx-text-fill: white;");
        save.setPrefHeight(50);
        save.setPrefWidth(150);

        save.setOnAction(e -> {
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();

            if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
                save.setText("Saved!");
                return;
            }

            try {
                connection = DriverManager.getConnection("jdbc:sqlite:patient.db");

                // Search for the patient by first name and last name
                String searchQuery = "SELECT * FROM patients WHERE first_name = ? AND last_name = ?";
                PreparedStatement searchStatement = connection.prepareStatement(searchQuery);
                searchStatement.setString(1, firstName);
                searchStatement.setString(2, lastName);

                ResultSet searchResult = searchStatement.executeQuery();

                if (searchResult.next()) {
                    int patientId = searchResult.getInt("id");

                    // Update the patient's information
                    String updateQuery = "UPDATE patients SET dob = ?, address = ?, phone_number = ?, health_number = ?, email = ?, password = ? WHERE id = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, txtDob.getText());
                    updateStatement.setString(2, txtAddress.getText());
                    updateStatement.setString(3, txtPhoneNumber.getText());
                    updateStatement.setString(4, txtHealthNumber.getText());
                    updateStatement.setString(5, txtEmail.getText());
                    updateStatement.setString(6, txtPassword.getText());
                    updateStatement.setInt(7, patientId);

                    int rowsUpdated = updateStatement.executeUpdate();

                    if (rowsUpdated > 0) {
                        save.setText("Saved!");
                    } else {
                        save.setText("ERROR");
                    }
                } else {
                    save.setText("ERROR");
                }

                searchResult.close();
                searchStatement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (connection != null)
                        connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        hBoxFirstName.setSpacing(10);
        hBoxFirstName.setAlignment(Pos.CENTER);
        hBoxLastName.setSpacing(10);
        hBoxLastName.setAlignment(Pos.CENTER);
        hBoxDob.setSpacing(10);
        hBoxDob.setAlignment(Pos.CENTER);
        hBoxAddress.setSpacing(10);
        hBoxAddress.setAlignment(Pos.CENTER);
        hBoxPhoneNumber.setSpacing(10);
        hBoxPhoneNumber.setAlignment(Pos.CENTER);
        hBoxHealthNumber.setSpacing(10);
        hBoxHealthNumber.setAlignment(Pos.CENTER);
        hBoxEmail.setSpacing(10);
        hBoxEmail.setAlignment(Pos.CENTER);
        hBoxPassword.setSpacing(10);
        hBoxPassword.setAlignment(Pos.CENTER);

        PatientNavHandler handler = new PatientNavHandler();
        HBox navBar = handler.createNav(primaryStage, patient);

        VBox vbox = new VBox(navBar, hBoxLastName, hBoxDob, hBoxAddress, hBoxPhoneNumber, hBoxHealthNumber, hBoxEmail, hBoxPassword, save);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 600, 400);
        return scene;
    }
}
