package main.scenes.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.model.Subject;

public abstract class View extends GridPane {

    int minButtonWidth = 100;

    public View() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10, 0, 25, 0));

        createView();
    }

    HBox setUpTypeButtons() {
        Button exams = new Button("Exams");
        exams.setMinWidth(minButtonWidth);
        exams.setOnAction(event -> {
            // TODO: Refresh tables
        });

        Button midterms = new Button("Midterms");
        midterms.setMinWidth(minButtonWidth);
        midterms.setOnAction(event -> {
            // TODO: Refresh tables
        });

        Button activity = new Button("Activity");
        activity.setMinWidth(minButtonWidth);
        activity.setOnAction(event -> {
            // TODO: Refresh tables
        });

        Button problem = new Button("Problem sets");
        problem.setMinWidth(minButtonWidth);
        problem.setOnAction(event -> {
            // TODO: Refresh tables
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(exams, midterms, activity, problem);

        return buttons;
    }

    public abstract void changeSubject(Subject subject);
    abstract void createView();
    abstract TableView setUpTable();
}
