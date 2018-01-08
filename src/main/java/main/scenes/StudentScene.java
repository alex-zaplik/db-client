package main.scenes;

import main.Main;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Subject;
import main.scenes.view.StudentView;

import java.util.List;

class StudentScene extends UserScene {

    StudentScene(Main main) throws AccessDeniedException {
        super(main);

        createScene();

        view = new StudentView(main);
        resetView();
    }

    @Override
    List<Result> getResults(Subject subject) throws AccessDeniedException {
        return main.getResults(subject, false, false, null);
    }
}
