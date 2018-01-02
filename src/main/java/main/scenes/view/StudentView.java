package main.scenes.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import main.model.Subject;

public class StudentView extends View {

    public StudentView() {
        super();
    }

    void createView() {
        TableView table = setUpTable();
        add(table, 0, 2);

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
