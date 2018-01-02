package main.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.Main;
import main.model.Subject;
import main.scenes.view.LecturerView;
import main.scenes.view.View;

import java.util.*;
import java.util.stream.Collectors;

abstract class UserScene extends BorderPane {

    Main main;
    View view;

    private List<Subject> subjects = null;
    private List<String> subjectsStr = new ArrayList<>();

    UserScene(Main main) {
        this.main = main;

        setPadding(new Insets(25, 25, 0, 25));
        createScene();
    }

    private void createScene() {
        while (subjects == null) {
            subjects = main.getSubjects(true);

            if (subjects != null) {
                subjectsStr = subjects.stream().map(s -> s.toString()).collect(Collectors.toList());
            }
        }

        Label subjectsLabel = new Label("Subjects:");

        ChoiceBox<String> subjectsBox = new ChoiceBox<>();
        subjectsBox.getItems().addAll(subjectsStr);
        subjectsBox.setMinWidth(250);
        subjectsBox.setOnAction(event -> subjectChanged(getByString(subjectsBox.getSelectionModel().getSelectedItem())));

        HBox box = new HBox();
        box.setSpacing(15);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(subjectsLabel, subjectsBox);
        setTop(box);
        setAlignment(getTop(), Pos.CENTER);

        if (view != null) {
            setCenter(view);
            setAlignment(getCenter(), Pos.CENTER);
        }
    }

    protected void resetView() {
        if (view != null) {
            setCenter(view);
            setAlignment(getCenter(), Pos.CENTER);
        }
    }

    private Subject getByString(String str) {
        if (subjects == null) return null;

        Subject s = null;

        for (Subject ss : subjects)
            if (str.equals(ss.toString()))
                s = ss;

        return s;
    }

    private void subjectChanged(Subject subject) {
        if (view != null) view.changeSubject(subject);
    }
}
