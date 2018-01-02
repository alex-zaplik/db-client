package main.scenes.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.model.Subject;

public class LecturerView extends View {

    public LecturerView() {
        super();
    }

    void createView() {
        TableView table = setUpTable();
        add(table, 0, 3);

        Button submit = new Button("Submit");
        submit.setMinWidth(minButtonWidth * 4);
        submit.setOnAction(event -> {
            // TODO
        });
        HBox submitBox = new HBox();
        submitBox.setAlignment(Pos.CENTER);
        submitBox.getChildren().add(submit);
        add(submitBox, 0, 2);

        HBox buttons = setUpTypeButtons();
        add(buttons, 0, 1);
    }

    @Override
    TableView setUpTable() {
        TableView table = new TableView();

        return table;
    }

    @Override
    public void changeSubject(Subject subject) {
        System.out.println("Changing to: " + subject);
    }
}
