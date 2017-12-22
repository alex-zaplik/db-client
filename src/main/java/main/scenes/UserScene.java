package main.scenes;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import main.Main;
import main.scenes.view.LecturerView;
import main.scenes.view.View;

import java.util.List;

class UserScene extends BorderPane {

    Main main;
    View view;

    UserScene(Main main) {
        this.main = main;
        createScene();
    }

    private void createScene() {
        List<String> subjects = null;
        while (subjects == null) {
            subjects = main.getSubjects(true);
        }

        ChoiceBox<String> subjectsBox = new ChoiceBox<>();
        subjectsBox.getItems().addAll(subjects);
        subjectsBox.setValue(subjects.get(0));
        subjectsBox.setMinWidth(250);
        setTop(subjectsBox);

        if (view != null) setCenter(view);
    }

    protected void resetView() {
        if (view != null) setCenter(view);
    }
}
