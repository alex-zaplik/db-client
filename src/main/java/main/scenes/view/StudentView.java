package main.scenes.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import main.Main;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Subject;

import java.util.List;
import java.util.Observable;

public class StudentView extends View {

    public StudentView(Main main) {
        super(main);
    }

    void createView() {
        table = setUpTable();
        add(table, 0, 3);

        Button refresh = new Button("Refresh");
        refresh.setMinWidth(minButtonWidth * 5);
        refresh.setOnAction(event -> refreshTable());

        HBox submitBox = new HBox();
        submitBox.setAlignment(Pos.CENTER);
        submitBox.getChildren().addAll(refresh);
        add(submitBox, 0, 2);

        HBox buttons = setUpTypeButtons();
        add(buttons, 0, 1);
    }

    @Override
    TableView<Result> setUpTable() {
        TableView<Result> table = new TableView<>();

        TableColumn<Result, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getWhenGiven());
            } else {
                return new SimpleStringProperty("<null>");
            }
        });

        TableColumn<Result, Number> valCol = new TableColumn<>("Value");
        valCol.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleDoubleProperty(p.getValue().getResultValue());
            } else {
                return new SimpleIntegerProperty(0);
            }
        });

        TableColumn<Result, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getResultType());
            } else {
                return new SimpleStringProperty("<null>");
            }
        });

        table.getColumns().add(dateCol);
        table.getColumns().add(valCol);
        table.getColumns().add(typeCol);

        return table;
    }

    @Override
    public void changeSubject(Subject subject, List<Result> results) {
        this.subject = subject;
        this.results = results;

        System.out.println("Changing subject...");

        ObservableList<Result> resultObservableList = FXCollections.observableArrayList(p ->
                new javafx.beans.Observable[] {p.whenGivenProperty(), p.resultValueProperty(), p.resultTypeProperty()}
        );

        resultObservableList.addAll(results);
        table.setItems(resultObservableList);
    }
}
