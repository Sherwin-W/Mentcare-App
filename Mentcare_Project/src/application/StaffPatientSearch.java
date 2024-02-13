package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class StaffPatientSearch{

    private Connection connection;
    private String user;
    private Stage primaryStage;

    public StaffPatientSearch(Stage primaryStage, String user){
        this.primaryStage = primaryStage;
        this.user = user;
    }

    public Scene createScene() {
        primaryStage.setTitle("Patient Search");

        TextField searchField = new TextField();
        searchField.setPromptText("Enter patient first name");
        searchField.setMaxWidth(200);

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchPatients(searchField.getText()));

        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchField, searchButton);

        VBox patientListContainer = new VBox(10);
        patientListContainer.setAlignment(Pos.CENTER);
        patientListContainer.setPadding(new Insets(20));

        ScrollPane scrollPane = new ScrollPane(patientListContainer);
        scrollPane.setFitToWidth(true);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);

        StaffNavHandler handler = new StaffNavHandler();
        HBox navBar = handler.createNavStaff(primaryStage, user);
        VBox contentBox = new VBox(10);
        contentBox.getChildren().addAll(searchBox, root);

        VBox mainBox = new VBox();
        mainBox.getChildren().addAll(navBar, contentBox);

        Scene scene = new Scene(mainBox, 600, 400);
        return scene;
    }

    private void searchPatients(String firstName) {
        try {
            connectToDatabase();

            String query = "SELECT * FROM patients WHERE first_name LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, firstName + "%");

            ResultSet resultSet = statement.executeQuery();

            VBox contentBox = (VBox) primaryStage.getScene().getRoot();
            VBox patientListContainer = (VBox) contentBox.getChildren().get(1);
            patientListContainer.setAlignment(Pos.CENTER);

            patientListContainer.getChildren().clear();

            while (resultSet.next()) {
                String firstNameResult = resultSet.getString("first_name");
                String lastNameResult = resultSet.getString("last_name");

                String dob = resultSet.getString("dob");
                String height = resultSet.getString("height");
                String weight = resultSet.getString("weight");
                String bodyTemp = resultSet.getString("body_temp");
                String bloodPressure = resultSet.getString("blood_pressure");
                String healthIssues = resultSet.getString("health_issues");
                String medications = resultSet.getString("medications");
                String immunizations = resultSet.getString("immunizations");

                Label nameLabel = new Label(firstNameResult + " " + lastNameResult);
                nameLabel.setOnMouseClicked(e -> displayPatientInfo(
                        firstNameResult, lastNameResult, dob, height, weight, bodyTemp,
                        bloodPressure, healthIssues, medications, immunizations));

                patientListContainer.getChildren().add(nameLabel);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayPatientInfo(
            String firstName, String lastName, String dob, String height, String weight,
            String bodyTemp, String bloodPressure, String healthIssues, String medications,
            String immunizations) {
        try {
            Stage stage = new Stage();
            stage.setTitle("Patient Information");

            VBox infoContainer = new VBox(10);
            infoContainer.setAlignment(Pos.CENTER);
            infoContainer.setPadding(new Insets(20));

            Label nameLabel = new Label(firstName + " " + lastName);
            nameLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label dobLabel = new Label("Date of Birth: " + dob);
            Label heightLabel = new Label("Height: " + height);
            Label weightLabel = new Label("Weight: " + weight);
            Label bodyTempLabel = new Label("Body Temperature: " + bodyTemp);
            Label bloodPressureLabel = new Label("Blood Pressure: " + bloodPressure);
            Label healthIssuesLabel = new Label("Health Issues: " + healthIssues);
            Label medicationsLabel = new Label("Medications: " + medications);
            Label immunizationsLabel = new Label("Immunizations: " + immunizations);

            infoContainer.getChildren().addAll(
                    nameLabel, dobLabel, heightLabel, weightLabel, bodyTempLabel,
                    bloodPressureLabel, healthIssuesLabel, medicationsLabel, immunizationsLabel);

            Scene scene = new Scene(infoContainer);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:patient.db");
    }
}
