package main.networking;

import main.Main;
import main.model.Subject;
import main.model.WeekDay;
import main.model.WeekPattern;

import java.util.ArrayList;
import java.util.List;

public class Client {

    private Main main;
    private String address;
    private int port;

    public Client(Main main, String address, int port) {
        this.main = main;
        this.address = address;
        this.port = port;

        // TODO: Attempt to connect to the server
    }

    public List<Subject> getSubjects(boolean isLecturer) {
        // TODO: Get list from server
        System.err.println("Getting the subject list as a " + ((isLecturer) ? "lecturer" : "student") + "...");

        List<Subject> subjects = new ArrayList<>();

        if (isLecturer) {
            subjects.add(new Subject(0, "Majcher", "Algebra", WeekPattern.TN, "11:15", WeekDay.MONDAY));
            subjects.add(new Subject(1, "Majcher", "Algebra", WeekPattern.TP, "11:15", WeekDay.MONDAY));
            subjects.add(new Subject(2, "Majcher", "Algebra", WeekPattern.TN, "9:15", WeekDay.TUESDAY));
            subjects.add(new Subject(3, "Majcher", "Algebra", WeekPattern.TP, "9:15", WeekDay.TUESDAY));
        } else {
            subjects.add(new Subject(1, "K. Majcher", "Algebra", WeekPattern.TP, "11:15", WeekDay.MONDAY));
            subjects.add(new Subject(1, "P. Krupski", "Analiza", WeekPattern.EVERY, "11:15", WeekDay.TUESDAY));
            subjects.add(new Subject(1, "S. Żeberski", "Logika i struktury formalne", WeekPattern.EVERY, "7:30", WeekDay.FRIDAY));
        }

        return subjects;
    }

    public boolean login(String login, String password) {
        // TODO: Attempt to log in to the server
        System.err.println("Logging in as " + login);

        return true;
    }
}
