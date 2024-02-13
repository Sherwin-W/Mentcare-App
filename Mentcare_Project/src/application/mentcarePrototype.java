//Sofia Mase
//CSE 360
//Mentcare PROTOTYPE 1
//Team 18


package application;
	
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.util.Random;

public class mentcarePrototype extends Application {

	//observablelist to store patient records
    private ObservableList<PatientRecord> patientRecords;
    //UI elements
    private TextField nameField;
    private TextField dateBirthField;
    private TextField phoneNumberField;
    private TextField addressField;
    private TextArea commentsArea;
    private TableView<PatientRecord> tableView;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //Create the welcome screen
    	WelcomeScreen welcomeScreen = new WelcomeScreen();

    	//Create the scene for welcome screen
        Scene welcomeScene = new Scene(welcomeScreen, 400, 200);
        primaryStage.setScene(welcomeScene);
        primaryStage.setTitle("Welcome to Mentcare");

        //set action for the "GO" Button on welcome screen
        welcomeScreen.getGoButton().setOnAction(e -> {
            String lastName = welcomeScreen.getLastNameField().getText();
            //switch to the main scene with provided last name
            primaryStage.setScene(createMainScene(lastName));
        });

        primaryStage.show();
    }
    
    //method to create the main scene  
    private Scene createMainScene(String lastName) {
        // Create the patient records list
        patientRecords = FXCollections.observableArrayList();

        // Create the UI elements
        GridPane gridPane = createGridPane();
        tableView = createTableView();
        Button addButton = createButton("Add");
        Button updateButton = createButton("Update");
        nameField = new TextField();
        nameField.setPromptText("Enter Name");
        dateBirthField = new TextField();
        dateBirthField.setPromptText("Enter D.O.B");
        phoneNumberField = new TextField();
        phoneNumberField.setPromptText("Enter Phone Number");
        addressField = new TextField();
        addressField.setPromptText("Enter Home Address");
        commentsArea = new TextArea();
        commentsArea.setPromptText("Enter Comments");
        
        // Add event handlers for buttons
        addButton.setOnAction(e -> addPatientRecord());
        updateButton.setOnAction(e -> updateSelectedPatientRecord());

        // Set the layout
        gridPane.add(tableView, 0, 0, 2, 1);
        gridPane.add(nameField, 0, 1);
        gridPane.add(dateBirthField, 0, 2);
        gridPane.add(phoneNumberField, 0, 3);
        gridPane.add(addressField, 0, 4);
        gridPane.add(commentsArea, 0, 5);
        gridPane.add(addButton, 0, 6);
        gridPane.add(updateButton, 1, 6);

        // Set the scene
        Scene scene = new Scene(gridPane, 600, 400);
        return scene;
    }

    //method to create a GridPane with default settings
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        return gridPane;
    }

    //method to create TableView for patient records
    private TableView<PatientRecord> createTableView() {
        TableView<PatientRecord> tableView = new TableView<>();
        tableView.setItems(patientRecords);

        //create columns for elements of PatientRecords
        TableColumn<PatientRecord, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<PatientRecord, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        
        TableColumn<PatientRecord, String> birthDateColumn = new TableColumn<>("D.O.B.");
        birthDateColumn.setCellValueFactory(data -> data.getValue().birthDateProperty());
        
        TableColumn<PatientRecord, String> phoneNumColumn = new TableColumn<>("Phone Number");
        phoneNumColumn.setCellValueFactory(data -> data.getValue().phoneNumProperty());
        
        TableColumn<PatientRecord, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(data -> data.getValue().addressProperty());
        
        TableColumn<PatientRecord, String> commentsColumn = new TableColumn<>("Comments");
        commentsColumn.setCellValueFactory(data -> data.getValue().commentsProperty());

        tableView.getColumns().addAll(idColumn, nameColumn, birthDateColumn, phoneNumColumn, addressColumn,commentsColumn);
      
    
     // listener to enable/disable update button based on row selection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setDisable(false);
                dateBirthField.setDisable(false);
                phoneNumberField.setDisable(false);
                addressField.setDisable(false);
                commentsArea.setDisable(false);
            } else {
                nameField.setDisable(true);
                dateBirthField.setDisable(true);
                phoneNumberField.setDisable(true);
                addressField.setDisable(true);
                commentsArea.setDisable(true);
            }
        });

        return tableView;   
    }

    //method to create a button with given text
    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(100);
        return button;
    }

    //method to add a new patient
    private void addPatientRecord() {
        //adding a new patient record
        String id = generateUniqueId();
        String name = nameField.getText(); // Replace with actual input from the user
        String dateOfBirth = dateBirthField.getText();
        String phoneNum = phoneNumberField.getText();
        String address = addressField.getText();
        String comments = commentsArea.getText(); //Replace with actual input from user
        PatientRecord patientRecord = new PatientRecord(id, name, dateOfBirth, phoneNum, address ,comments);
        patientRecords.add(patientRecord);
        clearInputFields();
    }

    //method to update selected patient record
    private void updateSelectedPatientRecord() {
        //updating the selected patient record
    	PatientRecord patientRecord = tableView.getSelectionModel().getSelectedItem();
    	if (patientRecord != null) {
            patientRecord.setName(nameField.getText());
            patientRecord.setDateOfBirth(dateBirthField.getText());
            patientRecord.setPhoneNum(phoneNumberField.getText());
            patientRecord.setAddress(addressField.getText());
            patientRecord.setComments(commentsArea.getText());
            clearInputFields();
        }
    }
    
    //method to clear input fields
    private void clearInputFields() {
        nameField.clear();
        dateBirthField.clear();
        phoneNumberField.clear();
        addressField.clear();
        commentsArea.clear();
    }

    //method to generate an unique ID number for patient
    private String generateUniqueId() {
        //generating a unique ID for a patient record
    	Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    //PatientRecord class representing a patient record
    public class PatientRecord {
    	private StringProperty id;
        private StringProperty name;
        private StringProperty dateOfBirth;
        private StringProperty phoneNum;
        private StringProperty address;
        private StringProperty comments;

        public PatientRecord(String id, String name, String dateOfBirth, String phoneNum, String address ,String comments) {
        	 this.id = new SimpleStringProperty(id);
             this.name = new SimpleStringProperty(name);
             this.dateOfBirth = new SimpleStringProperty(dateOfBirth);
             this.phoneNum = new SimpleStringProperty(phoneNum);
             this.address = new SimpleStringProperty(address);
             this.comments = new SimpleStringProperty(comments); 
        }

        //Getters and setters for various properties
        public StringProperty idProperty() {
            return id;
        }

        public StringProperty nameProperty() {
            return name;
        }

        public StringProperty birthDateProperty() {
            return dateOfBirth;
        }

        public StringProperty phoneNumProperty() {
            return phoneNum;
        }

        public StringProperty addressProperty() {
            return address;
        }

        public StringProperty commentsProperty() {
            return comments;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public String getDateOfBirth() {
            return dateOfBirth.get();
        }

        public void setDateOfBirth(String dateOfBirth) {
            this.dateOfBirth.set(dateOfBirth);
        }

        public String getPhoneNum() {
            return phoneNum.get();
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum.set(phoneNum);
        }

        public String getAddress() {
            return address.get();
        }

        public void setAddress(String address) {
            this.address.set(address);
        }

        public String getComments() {
            return comments.get();
        }

        public void setComments(String comments) {
            this.comments.set(comments);
        }
    }
}

