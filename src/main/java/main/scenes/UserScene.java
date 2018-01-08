package main.scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import main.Main;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Subject;
import main.scenes.view.LecturerView;
import main.scenes.view.View;

import java.util.*;
import java.util.stream.Collectors;

abstract class UserScene extends BorderPane {

    Main main;
    View view;

    boolean isLecturer = false;

    private Map<Subject, List<Result>> subjects;
    private List<String> subjectsStr = new ArrayList<>();

    UserScene(Main main) {
        this.main = main;

        setPadding(new Insets(25, 25, 0, 25));
    }

    void createScene() throws AccessDeniedException {
        subjects = new HashMap<>();
        List<Subject> subjectList = null;

        while (subjectList == null) {
            try {
                subjectList = main.getSubjects(isLecturer);

                for (Subject subject : subjectList)
                    subjects.put(subject, new ArrayList<>());
            } catch (AccessDeniedException e) {
                Main.displayMessage("Error", e.getMessage(), "Unable to retrieve subject data. Please try again", Alert.AlertType.ERROR);
                main.disconnect();
                throw new AccessDeniedException();
            }

            if (subjects != null) {
                subjectsStr = subjects.keySet().stream().map(s -> s.toString()).collect(Collectors.toList());
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

    void resetView() {
        if (view != null) {
            setCenter(view);
            setAlignment(getCenter(), Pos.CENTER);
        }
    }

    private Subject getByString(String str) {
        if (subjects == null) return null;

        Subject s = null;

        for (Subject ss : subjects.keySet())
            if (str.equals(ss.toString()))
                s = ss;

        return s;
    }

    private void subjectChanged(Subject subject) {
        if (view != null) {
            if (subjects.get(subject).size() == 0) {
                try {
                    subjects.replace(subject, getResults(subject));
                } catch (AccessDeniedException e) {
                    Main.displayMessage("Error", e.getMessage(), "Unable to retrieve data", Alert.AlertType.ERROR);
                    main.disconnect();
                    main.changeScene(new LoginScene(main));
                    return;
                }
            }

            view.changeSubject(subject, subjects.get(subject));
        }
    }

    abstract List<Result> getResults(Subject subject) throws AccessDeniedException;
}
