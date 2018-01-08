package main.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Main;
import main.exceptions.AccessDeniedException;

public class LoginScene extends GridPane {

    private Main main;

    private String student = "Student";
    private String lecturer = "Lecturer";
    private String admin = "Admin";

    public LoginScene(Main main) {
        this.main = main;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        createScene();
    }

    private void createScene() {
        Text welcome = new Text("Welcome!");
        welcome.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        add(welcome, 0, 0, 2, 1);

        Label userName = new Label("Login:");
        add(userName, 0, 1);

        TextField userTextField = new TextField();
        add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        add(pwBox, 1, 2);

        Label tl = new Label("Type:");
        add(tl, 0, 3);

        ChoiceBox<String> types = new ChoiceBox<>();
        types.getItems().addAll(student, lecturer, admin);
        types.setValue(student);
        types.setMinWidth(150);
        add(types, 1, 3);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        add(hbBtn, 1, 5);

        btn.setOnAction(event -> {
            String type;
            if (types.getSelectionModel().getSelectedItem().equals(admin))
                type = "admin";
            else if (types.getSelectionModel().getSelectedItem().equals(lecturer))
                type = "lecturer";
            else
                type = "student";

            boolean loggedIn;
            try {
                loggedIn = main.login(userTextField.getText(), pwBox.getText(), type);
            } catch (AccessDeniedException e) {
                Main.displayMessage("Unable to log in", e.getMessage(), "Unable to log in. Please try again", Alert.AlertType.WARNING);
                return;
            }
            if (userTextField.getText().length() > 0 && pwBox.getText().length() > 0 && loggedIn) {

                Pane scene;

                try {
                    if (types.getValue().equals(lecturer))
                        scene = new LecturerScene(main);
                    else if (types.getValue().equals(admin))
                        scene = new AdminScene(main);
                    else
                        scene = new StudentScene(main);
                } catch (AccessDeniedException e) {
                    return;
                }

                main.changeScene(scene);
            } else {
                Main.displayMessage("Error", "Unable to connect", "Make sure that all given data is correct and try again", Alert.AlertType.ERROR);
            }
        });
    }
}
