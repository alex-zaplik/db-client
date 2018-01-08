package main.scenes.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Main;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Subject;
import main.scenes.SubmitScene;

import java.util.List;

public class LecturerView extends View {

    public LecturerView(Main main) {
        super(main);
    }

    void createView() {
        String loginType = "Login/Index";
        String nameType = "Last name";

        table = setUpTable();
        add(table, 0, 3);

        Button submit = new Button("Submit");
        submit.setMinWidth(minButtonWidth * 5 / 3);
        submit.setOnAction(event -> {
            if (subject == null)
                return;

            createSubmitWindow();
        });

        Button delete = new Button("Delete");
        delete.setMinWidth(minButtonWidth * 5 / 3);
        delete.setOnAction(event -> {
            if (subject == null || results == null)
                return;

            try {
                Result res = table.getSelectionModel().getSelectedItem();
                if (res != null) {
                    main.deleteResult(res, true);
                }
            } catch (AccessDeniedException e) {
                Main.displayMessage("Error", e.getMessage(), "Unable to delete the specified row", Alert.AlertType.ERROR);
            }

            try {
                changeSubject(subject, main.getResults(subject, true, false, null));
            } catch (AccessDeniedException e) {
                Main.displayMessage("Error", e.getMessage(), "Unable to retrieve new data", Alert.AlertType.ERROR);
            }
        });

        Button refresh = new Button("Refresh");
        refresh.setMinWidth(minButtonWidth * 5 / 3);
        refresh.setOnAction(event -> refreshTable());

        HBox submitBox = new HBox();
        submitBox.setAlignment(Pos.CENTER);
        submitBox.getChildren().addAll(submit, delete, refresh);
        add(submitBox, 0, 2);

        Label searchLabel = new Label("Search:");

        TextField searchField = new TextField();
        searchField.setMinWidth(200);

        ChoiceBox<String> searchType = new ChoiceBox<>();
        searchType.getItems().addAll(loginType, nameType);
        searchType.setValue(loginType);
        searchType.setMinWidth(80);

        Button searchButton = new Button("Search");
        searchButton.setMinWidth(minButtonWidth * 5 / 3);
        searchButton.setOnAction(event -> {
            if (subject == null || results == null || searchField.getText().length() == 0)
                return;

            try {
                List<Result> results = main.getResults(subject, true, searchType.getValue().equals(loginType), searchField.getText());
                changeSubject(subject, results);
            } catch (AccessDeniedException e) {
                Main.displayMessage("Error", e.getMessage(), "Unable to complete the search query", Alert.AlertType.ERROR);
            }
        });

        HBox searchBox = new HBox();
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(searchLabel, searchField, searchType, searchButton);
        searchBox.setSpacing(5);
        add(searchBox, 0, 4);

        HBox buttons = setUpTypeButtons();
        add(buttons, 0, 1);
    }

    private void createSubmitWindow() {
        final Stage stage = new Stage();
        Parent root = new SubmitScene(subject, main);
        stage.setScene(new Scene(root));
        stage.setTitle("Submit");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main.getStage());
        stage.show();
    }

    @Override
    TableView<Result> setUpTable() {
        TableView<Result> table = new TableView<>();

        TableColumn<Result, String> loginCol = new TableColumn<>("Login");
        loginCol.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getLogin());
            } else {
                return new SimpleStringProperty("<null>");
            }
        });

        TableColumn<Result, String> nameCol = new TableColumn<>("Names");
        nameCol.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getName());
            } else {
                return new SimpleStringProperty("<null>");
            }
        });

        TableColumn<Result, String> lastNameCol = new TableColumn<>("Last name");
        lastNameCol.setCellValueFactory(p -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getLastName());
            } else {
                return new SimpleStringProperty("<null>");
            }
        });

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

        table.getColumns().add(loginCol);
        table.getColumns().add(nameCol);
        table.getColumns().add(lastNameCol);
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
                new javafx.beans.Observable[] {p.IDProperty(), p.loginProperty(), p.nameProperty(), p.lastNameProperty(), p.whenGivenProperty(), p.resultValueProperty(), p.resultTypeProperty()}
        );

        resultObservableList.addAll(results);
        table.setItems(resultObservableList);
    }
}
