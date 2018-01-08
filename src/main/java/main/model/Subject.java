package main.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Subject {

    private IntegerProperty ID;
    private StringProperty lecturer;
    private StringProperty name;
    private StringProperty pattern;
    private StringProperty startTime;
    private StringProperty startDay;

    public Subject(int ID, String lecturer, String name, String pattern, String startTime, String startDay) {
        this.ID = new SimpleIntegerProperty(ID);
        this.lecturer = new SimpleStringProperty(lecturer);
        this.name = new SimpleStringProperty(name);
        this.pattern = new SimpleStringProperty(pattern);
        this.startTime = new SimpleStringProperty(startTime);
        this.startDay = new SimpleStringProperty(startDay);
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

    public String getLecturer() {
        return lecturer.get();
    }

    public StringProperty lecturerProperty() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer.set(lecturer);
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

    public String getPattern() {
        return pattern.get();
    }

    public StringProperty patternProperty() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern.set(pattern);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public String getStartDay() {
        return startDay.get();
    }

    public StringProperty startDayProperty() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay.set(startDay);
    }

    @Override
    public String toString() {
        String patt = (getPattern().equals("EVERY")) ? "" : " " + getPattern();
        return getName() + " [" + getStartDay() + " " + getStartTime() + patt + "]";
    }
}
