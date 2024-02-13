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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.sql.*;

public class PatientIntake {
	private Stage primaryStage;
	private String user;
	private Patient patient;
	private Connection connection;

	public PatientIntake(Stage primaryStage, String user) {
		this.primaryStage = primaryStage;
		this.user = user;
	}

	public Scene createScene() {
		StaffNavHandler handler = new StaffNavHandler();
		HBox navBar = handler.createNavStaff(primaryStage, user);

		BorderPane root = new BorderPane();
		root.setTop(navBar);
		BorderPane.setAlignment(navBar, Pos.TOP_CENTER);

		ScrollPane scroll = new ScrollPane();
		scroll.setFitToWidth(true);
		scroll.setPadding(new Insets(20));
		root.setCenter(scroll);
		BorderPane.setAlignment(scroll, Pos.CENTER);

		VBox centerContainer = new VBox();
		centerContainer.setAlignment(Pos.CENTER);
		centerContainer.setSpacing(30);

		// page 1
		Label header = new Label("MENTCARE CLINICAL STAFF");
		header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
		Label info = new Label("Patient Intake");
		info.setStyle("-fx-font-size: 16px;");

		TextField fnt = new TextField();
		fnt.setPromptText("First Name");
		fnt.setMaxWidth(200);
		TextField lnt = new TextField();
		lnt.setPromptText("Last Name");
		lnt.setMaxWidth(200);
		TextField dob = new TextField();
		dob.setPromptText("Date of Birth");
		dob.setMaxWidth(200);
		HBox pbar1 = new HBox();
		pbar1.setSpacing(5);
		pbar1.setAlignment(Pos.CENTER);
		Circle circle1 = new Circle(10, Color.BLUE);
		circle1.setStroke(Color.BLACK);
		Circle circle2 = new Circle(10, Color.WHITE);
		circle2.setStroke(Color.BLACK);
		Circle circle3 = new Circle(10, Color.WHITE);
		circle3.setStroke(Color.BLACK);
		pbar1.getChildren().addAll(circle1, circle2, circle3);

		Button nextPage1 = new Button("Next");
		nextPage1.setOnAction(e -> {
			patient = new Patient();
			patient.updateFirstName(fnt.getText());
			patient.updateLastName(lnt.getText());
			patient.updateDOB(dob.getText());
			primaryStage.setScene(pageTwo(primaryStage));
		});

		centerContainer.getChildren().addAll(header, info, fnt, lnt, dob, pbar1, nextPage1);

		scroll.setContent(centerContainer);

		Scene scene = new Scene(root, 600, 400);
		return scene;
	}

	private Scene pageTwo(Stage primaryStage) {
		StaffNavHandler handler = new StaffNavHandler();
		HBox navBar = handler.createNavStaff(primaryStage, user);

		BorderPane root = new BorderPane();
		root.setTop(navBar);
		BorderPane.setAlignment(navBar, Pos.TOP_CENTER);

		ScrollPane scroll = new ScrollPane();
		scroll.setFitToWidth(true);
		scroll.setPadding(new Insets(20));
		root.setCenter(scroll);
		BorderPane.setAlignment(scroll, Pos.CENTER);

		VBox centerContainer = new VBox();
		centerContainer.setAlignment(Pos.CENTER);
		centerContainer.setSpacing(30);

		// page 2
		Label vitInfo = new Label("Patient Intake Vitals");
		vitInfo.setStyle("-fx-font-size: 16px;");

		TextField height = new TextField();
		height.setPromptText("Patient Height");
		height.setMaxWidth(200);
		TextField weight = new TextField();
		weight.setPromptText("Patient Weight");
		weight.setMaxWidth(200);
		TextField temp = new TextField();
		temp.setPromptText("Patient Body Temp");
		temp.setMaxWidth(200);
		TextField blood = new TextField();
		blood.setPromptText("Patient Blood Pressure");
		blood.setMaxWidth(200);

		HBox pbar2 = new HBox();
		pbar2.setSpacing(5);
		pbar2.setAlignment(Pos.CENTER);
		Circle circle21 = new Circle(10, Color.BLUE);
		circle21.setStroke(Color.BLACK);
		Circle circle22 = new Circle(10, Color.BLUE);
		circle22.setStroke(Color.BLACK);
		Circle circle23 = new Circle(10, Color.WHITE);
		circle23.setStroke(Color.BLACK);
		pbar2.getChildren().addAll(circle21, circle22, circle23);

		Button nextPage2 = new Button("Next");
		nextPage2.setOnAction(e -> {
			patient.updateHeight(height.getText());
			patient.updateWeight(weight.getText());
			patient.updateBodyTemp(temp.getText());
			patient.updateBloodPress(blood.getText());
			primaryStage.setScene(pageThree(primaryStage));
		});

		centerContainer.getChildren().addAll(vitInfo, height, weight, temp, blood, pbar2, nextPage2);

		scroll.setContent(centerContainer);

		Scene scene = new Scene(root, 600, 400);
		return scene;
	}

	private Scene pageThree(Stage primaryStage) {
		StaffNavHandler handler = new StaffNavHandler();
		HBox navBar = handler.createNavStaff(primaryStage, user);

		BorderPane root = new BorderPane();
		root.setTop(navBar);
		BorderPane.setAlignment(navBar, Pos.TOP_CENTER);

		ScrollPane scroll = new ScrollPane();
		scroll.setFitToWidth(true);
		scroll.setPadding(new Insets(20));
		root.setCenter(scroll);
		BorderPane.setAlignment(scroll, Pos.CENTER);

		VBox centerContainer = new VBox();
		centerContainer.setAlignment(Pos.CENTER);
		centerContainer.setSpacing(30);

		// page 3
		Label notes = new Label("Patient Intake Additional Notes");
		notes.setStyle("-fx-font-size: 16px;");

		HBox top = new HBox(50);
		top.setAlignment(Pos.CENTER);
		TextField healthIssues = new TextField();
		healthIssues.setPromptText("Health Issues");
		healthIssues.setPrefSize(250, 100);
		TextField meds = new TextField();
		meds.setPromptText("Medications Taking");
		meds.setPrefSize(250, 100);
		top.getChildren().addAll(healthIssues, meds);

		TextField immunizations = new TextField();
		immunizations.setPromptText("Immunizations");
		immunizations.setMaxSize(500, 100);
		immunizations.setPrefSize(500, 100);

		Button save = new Button("Save");
		save.setTextFill(Color.WHITE);
		save.setPrefSize(100, 30);
		save.setStyle("-fx-background-color: #0F52BA; ");
		save.setOnAction(e -> {
			savePatientToDatabase(patient, healthIssues.getText(), meds.getText(), immunizations.getText(), save);
		});

		centerContainer.getChildren().addAll(notes, top, immunizations, save);

		scroll.setContent(centerContainer);

		Scene scene = new Scene(root, 600, 400);
		return scene;
	}
	private void connectToDatabase() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:patient.db");
		String createTableQuery = "CREATE TABLE IF NOT EXISTS patients (" +
				"id INTEGER PRIMARY KEY AUTOINCREMENT," +
				"first_name TEXT," +
				"last_name TEXT," +
				"dob TEXT," +
				"height TEXT," +
				"weight TEXT," +
				"body_temp TEXT," +
				"blood_pressure TEXT," +
				"health_issues TEXT," +
				"medications TEXT," +
				"immunizations TEXT," +
				"address TEXT," +
				"phone_number TEXT," +
				"health_number TEXT)";

		//execute the SQL query and updates against the database
		Statement statement = connection.createStatement();
		statement.execute(createTableQuery);
		statement.close();
	}

	private void savePatientToDatabase(Patient patient, String healthIssues, String medications, String immunizations, Button save) {
		String query = "INSERT INTO patients (first_name, last_name, dob, height, weight, body_temp, blood_pressure, health_issues, medications, immunizations) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			connectToDatabase();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, patient.getFirstName());
			statement.setString(2, patient.getLastName());
			statement.setString(3, patient.getDOB());
			statement.setString(4, patient.getHeight());
			statement.setString(5, patient.getWeight());
			statement.setString(6, patient.getBodyTemp());
			statement.setString(7, patient.getBloodPress());
			statement.setString(8, healthIssues);
			statement.setString(9, medications);
			statement.setString(10, immunizations);

			statement.executeUpdate();
			save.setText("Saved!");
			statement.close();
			connection.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
			save.setText("ERROR");
		}
	}
}
