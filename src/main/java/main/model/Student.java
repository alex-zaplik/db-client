package main.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
	private StringProperty login;
	private StringProperty name;
	private StringProperty lastName;
	private StringProperty value;

	public Student(String login, String name, String lastName) {
		this.login = new SimpleStringProperty(login);
		this.name = new SimpleStringProperty(name);
		this.lastName = new SimpleStringProperty(lastName);
		this.value = new SimpleStringProperty("");
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

	public String getValue() {
		return value.get();
	}

	public StringProperty valueProperty() {
		return value;
	}

	public void setValue(String value) {
		this.value.set(value);
	}
}
