package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientCreateAccount {

    private Stage primaryStage;
    private Connection connection;

    public PatientCreateAccount(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public Scene createScene() {
        try {
            //connect to the SQLite database
            connectToDatabase();
            primaryStage.setTitle("Create Account");

            //create the labels and text fields
            Label emailLabel = new Label("Email:");
            TextField emailTextField = new TextField();
            Label passwordLabel = new Label("Password:");
            PasswordField passwordField = new PasswordField();
            Button registerButton = new Button("Register");
            Button cancelButton = new Button("Cancel");

            //handle Register button action
            registerButton.setOnAction(e -> {
                //save the email and password to the database
                String email = emailTextField.getText();
                String password = passwordField.getText();

                if (saveUserAccount(email, password)) {
                    //registration successful, redirect to the login page
                    PatientLogin loginPage = new PatientLogin(primaryStage);
                    primaryStage.setScene(loginPage.createScene());
                } else {
                    //registration failed, show message
                    passwordField.clear();
                    emailTextField.clear();
                    emailTextField.setPromptText("Email Exists!");
                }
            });

            // Handle Cancel button action
            cancelButton.setOnAction(e -> {
                //redirect back to the homepage without creating an account
                Homepage homepage = new Homepage(primaryStage);
                primaryStage.setScene(homepage.createHomepage());
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
            gridPane.add(registerButton, 0, 2);
            gridPane.add(cancelButton, 1, 2);

            return new Scene(gridPane, 300, 200);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void connectToDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:patientLogins.db");
        String createTableQuery = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT," +
                "password TEXT)";

        try (PreparedStatement statement = connection.prepareStatement(createTableQuery)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean saveUserAccount(String email, String password) {
        //query can find rows where the email = ?
        String checkQuery = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
            //sets ? to be the input email
            checkStatement.setString(1, email);
            //resultSet has rows where email exists
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                if (resultSet.next()) {
                    //if there is a next row, then the email exists and user is not saved
                    return false;
                }
            }
            //exceptions are fails as well
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        //insert the user account into the database after checking
        String insertQuery = "INSERT INTO users (email, password) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setString(1, email);
            insertStatement.setString(2, password);
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
