package main.scenes.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.Main;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Subject;

import java.util.List;
import java.util.stream.Collectors;

public abstract class View extends GridPane {

    Subject subject;
    TableView<Result> table;
    List<Result> results;

    Main main;

    int minButtonWidth = 70;

    View(Main main) {
        this.main = main;

        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(10, 0, 25, 0));

        createView();
    }

    void refreshTable() {
        if (subject == null || results == null)
            return;

        try {
            changeSubject(subject, main.getResults(subject, true, false, null));
        } catch (AccessDeniedException e) {
            Main.displayMessage("Error", e.getMessage(), "Unable to retrieve new data", Alert.AlertType.ERROR);
        }
    }

    HBox setUpTypeButtons() {
        Button all = new Button("All");
        all.setMinWidth(minButtonWidth);
        all.setOnAction(event -> {
            if (results == null)
                return;

            ObservableList<Result> resultObservableList = FXCollections.observableArrayList(p ->
                    new javafx.beans.Observable[] {p.whenGivenProperty(), p.resultValueProperty(), p.resultTypeProperty()}
            );

            resultObservableList.addAll(results);
            table.setItems(resultObservableList);
        });

        Button exams = new Button("Exams");
        exams.setMinWidth(minButtonWidth);
        exams.setOnAction(event -> {
            if (results == null)
                return;

            ObservableList<Result> resultObservableList = FXCollections.observableArrayList(p ->
                    new javafx.beans.Observable[] {p.whenGivenProperty(), p.resultValueProperty(), p.resultTypeProperty()}
            );

            resultObservableList.addAll(results.stream().filter(r -> r.getResultType().equals("exam")).collect(Collectors.toList()));
            table.setItems(resultObservableList);
        });

        Button midterms = new Button("Midterms");
        midterms.setMinWidth(minButtonWidth);
        midterms.setOnAction(event -> {
            if (results == null)
                return;

            ObservableList<Result> resultObservableList = FXCollections.observableArrayList(p ->
                    new javafx.beans.Observable[] {p.whenGivenProperty(), p.resultValueProperty(), p.resultTypeProperty()}
            );

            resultObservableList.addAll(results.stream().filter(r -> r.getResultType().equals("mid_term")).collect(Collectors.toList()));
            table.setItems(resultObservableList);
        });

        Button activity = new Button("Activity");
        activity.setMinWidth(minButtonWidth);
        activity.setOnAction(event -> {
            if (results == null)
                return;

            ObservableList<Result> resultObservableList = FXCollections.observableArrayList(p ->
                    new javafx.beans.Observable[] {p.whenGivenProperty(), p.resultValueProperty(), p.resultTypeProperty()}
            );

            resultObservableList.addAll(results.stream().filter(r -> r.getResultType().equals("activity")).collect(Collectors.toList()));
            table.setItems(resultObservableList);
        });

        Button problem = new Button("Problem sets");
        problem.setMinWidth(minButtonWidth);
        problem.setOnAction(event -> {
            if (results == null)
                return;

            ObservableList<Result> resultObservableList = FXCollections.observableArrayList(p ->
                    new javafx.beans.Observable[] {p.whenGivenProperty(), p.resultValueProperty(), p.resultTypeProperty()}
            );

            resultObservableList.addAll(results.stream().filter(r -> r.getResultType().equals("problem_set")).collect(Collectors.toList()));
            table.setItems(resultObservableList);
        });

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(all, exams, midterms, activity, problem);

        return buttons;
    }

    public abstract void changeSubject(Subject subject, List<Result> results);
    abstract void createView();
    abstract TableView setUpTable();
}
