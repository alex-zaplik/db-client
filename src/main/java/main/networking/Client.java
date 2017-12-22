package main.networking;

import main.Main;

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

    public List<String> getSubjects(boolean isLecturer) {
        // TODO: Get list from server

        List<String> subjects = new ArrayList<>();

        if (isLecturer) {
            subjects.add("Algebra [Pon. 11:15 TN]");
            subjects.add("Algebra [Pon. 11:15 TP]");
            subjects.add("Algebra [Wt. 9:15 TN]");
            subjects.add("Algebra [Wt. 9:15 TP]");
        } else {
            subjects.add("Algebra [Pon. 11:15 TP]");
            subjects.add("Analiza [Wt. 11:15]");
            subjects.add("Logika i struktury formalne [Pt. 7:30]");
        }

        return subjects;
    }

    public boolean login(String login, String password) {
        // TODO: Attempt to log in to the server

        return true;
    }
}
