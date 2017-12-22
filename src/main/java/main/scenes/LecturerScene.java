package main.scenes;

import main.Main;
import main.scenes.view.LecturerView;

class LecturerScene extends UserScene {

    LecturerScene(Main main) {
        super(main);

        view = new LecturerView();
        resetView();
    }

}
