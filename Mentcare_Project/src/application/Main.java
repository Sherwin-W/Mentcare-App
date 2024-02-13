//run to test patient portal

package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Homepage homepage = new Homepage(primaryStage);
        primaryStage.setScene(homepage.createHomepage());
        primaryStage.setTitle("Welcome to Mentcare");
        primaryStage.show();
    }
}
