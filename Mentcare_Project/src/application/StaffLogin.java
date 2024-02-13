package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StaffLogin {

    protected Stage primaryStage;
    private Connection connection;

    public StaffLogin(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createScene() {
        primaryStage.setTitle("Staff Login");

        //create the labels and text fields
        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button createAccountButton = new Button("Create Account");
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-pref-width: 100px;");

        //handle createAccount button which redirects to PatientCreateAccount
        createAccountButton.setOnAction(e -> {
            StaffCreateAccount createAccountPage = new StaffCreateAccount(primaryStage);
            primaryStage.setScene(createAccountPage.createScene());
        });

        //handle Login button action
        loginButton.setOnAction(e -> {
            String email = emailTextField.getText();
            String password = passwordField.getText();
            //if user has an email and password in the database, go to homepage
            if (validateUser(email, password)) {
                StaffHome staffHome = new StaffHome(primaryStage, email);
                primaryStage.setScene(staffHome.createScene());
            } else {
                passwordField.clear();
                emailTextField.clear();
                emailTextField.setPromptText("INVALID");
            }
        });

        //create the layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(emailLabel, 0, 0);
        gridPane.add(emailTextField, 1, 0);
        gridPane.add(passwordLabel, 0, 1);
        gridPane.add(passwordField, 1, 1);
        gridPane.add(loginButton, 0, 3, 2, 1);
        gridPane.add(createAccountButton, 0, 4, 2, 1);

        return new Scene(gridPane, 300, 200);
    }

    private boolean validateUser(String email, String password) {
        try {
            connectToDatabase();

            //prepare the SQL statement to check if the email and password combination exists in the database
            String query = "SELECT COUNT(*) FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            //execute the query
            ResultSet resultSet = statement.executeQuery();

            //check if any rows are returned
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    //connects to same database that holds the registration info
    private void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:patientLogins.db");
    }

    private void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
