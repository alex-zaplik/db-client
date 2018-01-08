package main.model;

import javafx.beans.property.*;

public class Result {

	private IntegerProperty ID;
	private StringProperty login;
	private StringProperty name;
	private StringProperty lastName;
	private StringProperty whenGiven;
	private DoubleProperty resultValue;
	private StringProperty resultType;

	public Result(int ID, String login, String name, String lastName, String whenGiven, double resultValue, String resultType) {
		this.ID = new SimpleIntegerProperty(ID);
		this.login = new SimpleStringProperty(login);
		this.name = new SimpleStringProperty(name);
		this.lastName = new SimpleStringProperty(lastName);
		this.whenGiven = new SimpleStringProperty(whenGiven);
		this.resultValue = new SimpleDoubleProperty(resultValue);
		this.resultType = new SimpleStringProperty(resultType);
	}

	public int getID() {
		return ID.get();
	}

	public IntegerProperty IDProperty() {
		return ID;
	}

	public void setID(int ID) {
		this.ID.set(ID);
	}

	public String getLogin() {
		return login.get();
	}

	public StringProperty loginProperty() {
		return login;
	}

	public void setLogin(String login) {
		this.login.set(login);
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public String getLastName() {
		return lastName.get();
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public String getWhenGiven() {
		return whenGiven.get();
	}

	public StringProperty whenGivenProperty() {
		return whenGiven;
	}

	public void setWhenGiven(String whenGiven) {
		this.whenGiven.set(whenGiven);
	}

	public double getResultValue() {
		return resultValue.get();
	}

	public DoubleProperty resultValueProperty() {
		return resultValue;
	}

	public void setResultValue(double resultValue) {
		this.resultValue.set(resultValue);
	}

	public String getResultType() {
		return resultType.get();
	}

	public StringProperty resultTypeProperty() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType.set(resultType);
	}
}
