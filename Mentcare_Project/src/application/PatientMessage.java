package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.xpath.XPath;
import java.sql.*;

public class PatientMessage {

    private Patient patient;
    private ObservableList<Message> messages;
    private ListView<Message> messageListView;
    private TextField subjectField;
    private TextField recipientField;
    private TextArea contentArea;
    private Connection connection;

    Stage primaryStage;

    public PatientMessage(Stage primaryStage, Patient patient){
        this.primaryStage = primaryStage;
        this.patient = patient;
    }

    public Scene createScene() {
        try {
            //connect to the SQLite database
            connectToDatabase();

            //load existing messages from the database
            loadMessagesFromDatabase();

            //create the message list
            messageListView = new ListView<>();
            messageListView.setItems(messages);
            messageListView.setCellFactory(param -> new ListCell<Message>() {
                //updateItem is called whenever a new cell is created
                @Override
                //either sets to empty or displays the subject
                protected void updateItem(Message message, boolean empty) {
                    super.updateItem(message, empty);
                    if (empty || message == null) {
                        setText(null);
                    } else {
                        setText(message.getSubject());
                    }
                }
            });

            //create the message details pane
            Label subjectLabel = new Label("Subject:");
            Label recipientLabel = new Label("Recipient:");
            Label contentLabel = new Label("Message:");

            subjectField = new TextField();
            recipientField = new TextField();
            contentArea = new TextArea();
            contentArea.setWrapText(true);
            setFieldsEditable(false);

            Button createButton = new Button("Compose");
            createButton.setOnAction(e -> {
                //pop up a new window to compose a message
                showComposeDialog();
            });

            Button deleteButton = new Button("Delete Message");
            deleteButton.setOnAction(e -> {
                Message selectedMessage = messageListView.getSelectionModel().getSelectedItem();
                if (selectedMessage != null) {
                    deleteMessage(selectedMessage);
                    messages.remove(selectedMessage);
                    clearFields();
                }
            });

            PatientNavHandler handler = new PatientNavHandler();
            HBox navBar = handler.createNav(primaryStage, patient);

            VBox messageDetailsPane = new VBox(10, subjectLabel, subjectField, recipientLabel, recipientField, contentLabel, contentArea);
            messageDetailsPane.setPadding(new Insets(10));

            //create the general layout
            BorderPane root = new BorderPane();
            HBox buttons = new HBox(createButton, deleteButton);
            buttons.setPadding(new Insets(10));
            buttons.setSpacing(10);
            VBox centerPane = new VBox(messageDetailsPane, buttons);
            SplitPane splitPane = new SplitPane(messageListView, centerPane);
            splitPane.setDividerPositions(0.3);
            VBox entirePane = new VBox(navBar, splitPane);

            root.setCenter(entirePane);

            messageListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                //update the message details pane based on the selected message
                if (newValue != null) {
                    subjectField.setText(newValue.getSubject());
                    recipientField.setText(newValue.getRecipient());
                    contentArea.setText(newValue.getContent());
                    setFieldsEditable(false);
                } else {
                    clearFields();
                }
            });

            Scene scene = new Scene(root, 600, 400);
            return scene;

            //exceptions for the database
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; //return null in case of an exception
    }

    private void connectToDatabase() throws SQLException {
        //connect to the SQLite database
        //the connection represents the session between the Java application and the database, interacting with the database
        connection = DriverManager.getConnection("jdbc:sqlite:message.db");

        //create the messages table if it doesn't exist
        //the SQL statement creates a table with four columns: id, subject, recipient, content
        //the integer id is each row's unique identifier
        String createTableQuery = "CREATE TABLE IF NOT EXISTS messages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "subject TEXT," +
                "recipient TEXT," +
                "content TEXT)";

        //execute the SQL query and updates against the database
        Statement statement = connection.createStatement();
        statement.execute(createTableQuery);
        statement.close();
    }

    private void loadMessagesFromDatabase() throws SQLException {
        messages = FXCollections.observableArrayList();

        //retrieve existing messages from the database
        String selectQuery = "SELECT * FROM messages WHERE recipient = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
        preparedStatement.setString(1, patient.getEmail());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String subject = resultSet.getString("subject");
            String recipient = resultSet.getString("recipient");
            String content = resultSet.getString("content");
            Message message = new Message(id, subject, recipient, content);
            messages.add(message);
        }
        preparedStatement.close();
    }

    private void showComposeDialog() {
        Stage composeStage = new Stage();
        composeStage.setTitle("Compose Message");
        // The new window is in front until closed
        composeStage.initModality(Modality.APPLICATION_MODAL);

        Label subjectLabel = new Label("Subject:");
        Label recipientLabel = new Label("Recipient:");
        Label contentLabel = new Label("Content:");

        TextField subjectField = new TextField();
        TextField recipientField = new TextField();
        TextArea contentArea = new TextArea();
        contentArea.setWrapText(true);

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e -> {
            String subject = subjectField.getText();
            String recipient = recipientField.getText();
            String content = contentArea.getText();

            Message newMessage = new Message(subject, recipient, content);
            insertMessage(newMessage);
            messages.add(newMessage);
            composeStage.close();
        });

        VBox composePane = new VBox(10, subjectLabel, subjectField, recipientLabel, recipientField, contentLabel, contentArea, sendButton);
        composePane.setPadding(new Insets(10));

        Scene composeScene = new Scene(composePane, 400, 300);
        composeStage.setScene(composeScene);
        composeStage.showAndWait();
    }

    private void insertMessage(Message message) {
        try {
            //insert the message into the database
            //prepared statements involve parameters
            String insertQuery = "INSERT INTO messages (subject, recipient, content) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, message.getSubject());
            preparedStatement.setString(2, message.getRecipient());
            preparedStatement.setString(3, message.getContent());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteMessage(Message message) {
        try {
            //delete the message from the database
            String deleteQuery = "DELETE FROM messages WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, message.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        subjectField.clear();
        recipientField.clear();
        contentArea.clear();
    }

    private void setFieldsEditable(boolean editable) {
        subjectField.setEditable(editable);
        recipientField.setEditable(editable);
        contentArea.setEditable(editable);
    }

}