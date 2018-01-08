package main.scenes;

import main.Main;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Subject;
import main.scenes.view.LecturerView;

import java.util.List;

class LecturerScene extends UserScene {

    LecturerScene(Main main) throws AccessDeniedException {
        super(main);

        isLecturer = true;
        createScene();

        view = new LecturerView(main);
        resetView();
    }

    @Override
    List<Result> getResults(Subject subject) throws AccessDeniedException {
        return main.getResults(subject, true, false, null);
    }
}
