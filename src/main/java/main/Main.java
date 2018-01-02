package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.model.Subject;
import main.networking.Client;
import main.scenes.LoginScene;

import java.util.List;

public class Main extends Application {

    private Stage stage;
    private Client client;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        client = new Client(this, "localhost", 4444);

        primaryStage.setTitle("Student Database");
        primaryStage.setScene(new Scene(new LoginScene(this)));
        primaryStage.show();
    }

    public List<Subject> getSubjects(boolean isLecturer) {
        if (client != null) {
            return client.getSubjects(isLecturer);
        }

        return null;
    }

    public boolean login(String login, String password) {
        return client != null && client.login(login, password);
    }

    public void changeScene(Pane pane) {
        stage.setScene(new Scene(pane));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
