package main.scenes;

import main.Main;
import main.scenes.view.StudentView;

class StudentScene extends UserScene {

    StudentScene(Main main) {
        super(main);

        view = new StudentView();
        resetView();
    }
}
