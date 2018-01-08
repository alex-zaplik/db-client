package main.scenes;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import main.Main;
import main.exceptions.AccessDeniedException;
import main.model.Result;
import main.model.Student;
import main.model.Subject;

import java.util.*;
import java.util.regex.Pattern;

public class SubmitScene extends GridPane {

	private Subject subject;
	private Main main;

	int minButtonWidth = 70;

	private TableView<Student> table;

	public SubmitScene(Subject subject, Main main) {
		this.subject = subject;
		this.main = main;

		setAlignment(Pos.CENTER);
		setHgap(10);
		setVgap(10);
		setPadding(new Insets(25, 25, 25, 25));

		createScene();
	}

	private void createScene() {
		String initialType = "Activity";
		Map<String, String> niceToServer = new HashMap<>();
		niceToServer.put("Exam", "exam");
		niceToServer.put("Midterm", "mid_term");
		niceToServer.put("Problem set", "problem_set");
		niceToServer.put(initialType, "activity");

		table = setUpTable();
		add(table, 0, 2);

		Label day = new Label("DD: ");
		TextField dayText = new TextField();
		dayText.appendText("01");
		dayText.setMaxWidth(30);
		dayText.textProperty().addListener((observable, oldValue, newValue) -> {
			if (Pattern.compile("[0-9]{0,2}").matcher(newValue).matches() && newValue.length() <= 5) ((StringProperty) observable).setValue(newValue);
			else ((StringProperty) observable).setValue(oldValue);
		});

		Label month = new Label("MM: ");
		TextField monthText = new TextField();
		monthText.appendText("01");
		monthText.setMaxWidth(30);
		monthText.textProperty().addListener((observable, oldValue, newValue) -> {
			if (Pattern.compile("[0-9]{0,2}").matcher(newValue).matches()) ((StringProperty) observable).setValue(newValue);
			else ((StringProperty) observable).setValue(oldValue);
		});

		Label year = new Label("YYYY: ");
		TextField yearText = new TextField();
		yearText.appendText("1900");
		yearText.setMaxWidth(50);
		yearText.textProperty().addListener((observable, oldValue, newValue) -> {
			if (Pattern.compile("[0-9]{0,4}").matcher(newValue).matches()) ((StringProperty) observable).setValue(newValue);
			else ((StringProperty) observable).setValue(oldValue);
		});

		ChoiceBox<String> resultType = new ChoiceBox<>();
		resultType.getItems().addAll(niceToServer.keySet());
		resultType.setValue(initialType);
		resultType.setMinWidth(80);

		HBox valueBox = new HBox();
		valueBox.setAlignment(Pos.CENTER);
		valueBox.getChildren().addAll(day, dayText, month, monthText, year, yearText, resultType);
		valueBox.setSpacing(10);
		add(valueBox, 0, 1);

		Button submit = new Button("Submit");
		submit.setMinWidth(minButtonWidth * 5);
		submit.setOnAction(event -> {
			try {
				List<Result> results = parseResults(dateString(yearText.getText(), monthText.getText(), dayText.getText()), niceToServer.get(resultType.getValue()));
				main.newResults(results, subject.getID(), true);
			} catch (NumberFormatException e) {
				Main.displayMessage("Error", "Invalid data given", "Make sure that the values you are trying to submit are correct", Alert.AlertType.WARNING);
			} catch (AccessDeniedException e) {
				Main.displayMessage("Error", e.getMessage(), "Make sure that the values you are trying to submit are correct", Alert.AlertType.ERROR);
			}

			// TODO: Close this window
		});

		HBox submitBox = new HBox();
		submitBox.setAlignment(Pos.CENTER);
		submitBox.getChildren().addAll(submit);
		add(submitBox, 0, 0);
	}

	private String dateString(String year, String month, String day) throws NumberFormatException {
		int iYear = Integer.parseInt(year);
		int iMonth = Integer.parseInt(month);
		int iDay = Integer.parseInt(day);

		if (iYear < 1000)
			throw new NumberFormatException();

		return iYear + "-" + ((iMonth < 10) ? "0" + iMonth : iMonth) + "-" + ((iDay < 10) ? "0" + iDay : iDay);
	}

	private List<Result> parseResults(String date, String type) throws NumberFormatException {
		List<Result> results = new ArrayList<>();

		for (Student s : table.getItems()) {
			String valueString = s.getValue();
			if (valueString.length() == 0)
				continue;

			double value = Double.parseDouble(valueString);
			results.add(new Result(0, s.getLogin(), s.getName(), s.getLastName(), date, value, type));
		}

		return results;
	}

	private TableView<Student> setUpTable() {
		TableView<Student> table = new TableView<>();
		table.setEditable(true);

		TableColumn<Student, String> loginCol = new TableColumn<>("Login");
		loginCol.setCellValueFactory(p -> {
			if (p.getValue() != null) {
				return new SimpleStringProperty(p.getValue().getLogin());
			} else {
				return new SimpleStringProperty("<null>");
			}
		});

		TableColumn<Student, String> nameCol = new TableColumn<>("Names");
		nameCol.setCellValueFactory(p -> {
			if (p.getValue() != null) {
				return new SimpleStringProperty(p.getValue().getName());
			} else {
				return new SimpleStringProperty("<null>");
			}
		});

		TableColumn<Student, String> lastNameCol = new TableColumn<>("Last name");
		lastNameCol.setCellValueFactory(p -> {
			if (p.getValue() != null) {
				return new SimpleStringProperty(p.getValue().getLastName());
			} else {
				return new SimpleStringProperty("<null>");
			}
		});

		TableColumn<Student, String> valCol = new TableColumn<>("Value");
		valCol.setEditable(true);
		valCol.setCellFactory(TextFieldTableCell.forTableColumn());
		valCol.setCellValueFactory(p -> {
			if (p.getValue() != null) {
				return new SimpleStringProperty(p.getValue().getValue());
			} else {
				return new SimpleStringProperty("");
			}
		});
		valCol.setOnEditCommit(p -> p.getTableView().getItems().get(p.getTablePosition().getRow()).setValue(p.getNewValue()));

		table.getColumns().add(loginCol);
		table.getColumns().add(nameCol);
		table.getColumns().add(lastNameCol);
		table.getColumns().add(valCol);


		try {
			ObservableList<Student> observableList = FXCollections.observableArrayList(p ->
					new javafx.beans.Observable[] {p.loginProperty(), p.nameProperty(), p.lastNameProperty(), p.valueProperty()}
			);

			observableList.addAll(main.getStudents(subject, true));
			table.setItems(observableList);
		} catch (AccessDeniedException e) {
			Main.displayMessage("Error", e.getMessage(), "Unable to retrieve data. Please try again", Alert.AlertType.ERROR);
		}

		return table;
	}
}
