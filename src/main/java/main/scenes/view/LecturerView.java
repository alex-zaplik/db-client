package main.scenes.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LecturerView extends View {

    public LecturerView() {
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        Text welcome = new Text("Lecturer");
        welcome.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        add(welcome, 0, 0, 2, 1);
    }
}
