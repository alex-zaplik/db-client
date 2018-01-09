package main.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.Main;
import main.exceptions.AccessDeniedException;

public class AdminScene extends GridPane {

    private Main main;

    AdminScene(Main main) {
        this.main = main;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        createScene();
    }

    private void createScene() {
        Label name = new Label("Name:");
        TextField nameText = new TextField("dumpname.sql");

        Button backup = new Button("Backup");
        backup.setOnAction(event -> {
            try {
                main.doBackupRestore(nameText.getText(), true);
            } catch (AccessDeniedException e) {
                Main.displayMessage("Error", e.getMessage(), "Unable to perform a backup", Alert.AlertType.ERROR);
            }
        });

        Button restore = new Button("Restore");
        restore.setOnAction(event -> {
            try {
                main.doBackupRestore(nameText.getText(), false);
            } catch (AccessDeniedException e) {
                Main.displayMessage("Error", e.getMessage(), "Unable to perform a backup", Alert.AlertType.ERROR);
            }
        });

        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(name, nameText, backup, restore);
        add(box, 0, 0);
    }
}
