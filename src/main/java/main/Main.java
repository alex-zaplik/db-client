package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Student;
import main.model.Subject;
import main.networking.Client;
import main.scenes.LoginScene;

import java.io.IOException;
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

    public void disconnect() {
        try {
            client.disconnect();
        } catch (IOException e) {
            displayMessage("Error", "Networking error", "Unable to disconnect properly. Closing the app...", Alert.AlertType.ERROR);
            Platform.exit();
        }
    }

    public static void displayMessage(String title, String header, String msg, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public List<Subject> getSubjects(boolean isLecturer) throws AccessDeniedException {
        if (client != null) {
            return client.getSubjects(isLecturer);
        }

        return null;
    }

    public List<Result> getResults(Subject subject, boolean isLecturer, boolean useLogin, String input) throws AccessDeniedException {
        return client.getResults(subject.getID(), isLecturer, useLogin, input);
    }

    public void deleteResult(Result result, boolean isLecturer) throws AccessDeniedException {
        client.deleteResult(result.getID(), isLecturer);
    }

    public List<Student> getStudents(Subject subject, boolean isLecturer) throws AccessDeniedException {
        return client.getStudents(subject.getID(), isLecturer);
    }

    public boolean login(String login, String password, String type) throws AccessDeniedException {
        boolean done = done = client.loginToServer(login, password, type);
        return client != null && done;
    }

    public void changeScene(Pane pane) {
        stage.setScene(new Scene(pane));
    }

    public void newResults(List<Result> results, int groupID, boolean isLecturer) throws AccessDeniedException {
        client.newResults(results, groupID, isLecturer);
    }

    public void doBackupRestore(String file, boolean isBackup) throws AccessDeniedException {
        client.doBackupRestore(file, isBackup);
    }

    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
