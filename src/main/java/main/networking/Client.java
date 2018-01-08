package main.networking;

import com.google.common.hash.Hashing;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import main.Main;
import main.exceptions.AccessDeniedException;
import main.message.builder.IMessageBuilder;
import main.message.builder.JSONMessageBuilder;
import main.message.parser.IMessageParser;
import main.message.parser.JSONMessageParser;
import main.model.Result;
import main.model.Student;
import main.model.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Client {

    private Main main;
    private String address;
    private int port;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private IMessageParser parser = new JSONMessageParser();
    private IMessageBuilder builder = new JSONMessageBuilder();

    public Client(Main main, String address, int port) {
        this.main = main;
        this.address = address;
        this.port = port;
    }

    public boolean loginToServer(String login, String pass, String type) throws AccessDeniedException {
        try {
            socket = new Socket(address, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            return false;
        }

        out.println(builder
                .put("login", login)
                .put("pass", hashPass(pass))
                .put("type", type)
                .get()
        );

        String msg;
        try {
            msg = in.readLine();
        } catch (IOException e) {
            return false;
        }

        if (msg != null) {
            Map<String, Object> response = parser.parse(msg);

            if (response.containsKey("error"))
                throw new AccessDeniedException((String) response.get("error"));

            if (response.containsKey("msg")) {
                System.out.println("Logged in");
                return true;
            }
        }

        return false;
    }

    public void disconnect() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    public List<Subject> getSubjects(boolean isLecturer) throws AccessDeniedException {
        System.out.println("Getting the subject list as a " + ((isLecturer) ? "lecturer" : "student") + "...");

        List<Subject> subjects = new ArrayList<>();

        out.println(builder
                .put("action", 4)
                .get()
        );

        try {
            String msg = in.readLine();

            if (msg == null)
                throw new IOException();

            Map<String, Object> response = parser.parse(msg);

            if (response.containsKey("error"))
                throw new AccessDeniedException((String) response.get("error"));

            int size = (int) response.get("size");

            for (int i = 0; i < size; i++) {
                Map<String, Object> partMap = parser.parse((String) response.get("part" + i));

                String startDay;
                switch ((int) partMap.get("week_day")) {
                    case 1:
                        startDay = "Pn";
                        break;
                    case 2:
                        startDay = "Wt";
                        break;
                    case 3:
                        startDay = "Śr";
                        break;
                    case 4:
                        startDay = "Czw";
                        break;
                    case 5:
                        startDay = "Pt";
                        break;
                    case 6:
                        startDay = "Sob";
                        break;
                    case 7:
                        startDay = "Nd";
                        break;
                    default:
                        startDay = "Pn";
                        break;
                }

                if (isLecturer)
                    subjects.add(new Subject(
                            (int) partMap.get("ID"),
                            null,
                            (String) partMap.get("group_name"),
                            (String) partMap.get("week_patt"),
                            (String) partMap.get("start_time"),
                            startDay
                    ));
                else
                    subjects.add(new Subject(
                            (int) partMap.get("ID"),
                            partMap.get("degree") + " " + partMap.get("last_name"),
                            (String) partMap.get("group_name"),
                            (String) partMap.get("week_patt"),
                            (String) partMap.get("start_time"),
                            startDay
                    ));
            }

        } catch (IOException e) {
            System.err.println("Unable to retrieve data");
        }

        return subjects;
    }

    public List<Result> getResults(int groupID, boolean isLecturer, boolean useLogin, String input) throws AccessDeniedException {
        System.out.println("Getting the result list...");

        List<Result> results = new ArrayList<>();

        if (input != null) {
            out.println(builder
                    .put("action", 1)
                    .put("group", groupID)
                    .put((useLogin) ? "login" : "last_name", input)
                    .get()
            );
        } else {
            if (isLecturer) {
                out.println(builder
                        .put("action", 1)
                        .put("group", groupID)
                        .get()
                );
            } else {
                out.println(builder
                        .put("action", 2)
                        .put("group", groupID)
                        .get()
                );
            }
        }

        try {
            String msg = in.readLine();

            if (msg == null)
                throw new IOException();

            Map<String, Object> response = parser.parse(msg);

            if (response.containsKey("error"))
                throw new AccessDeniedException((String) response.get("error"));

            int size = (int) response.get("size");

            for (int i = 0; i < size; i++) {
                Map<String, Object> partMap = parser.parse((String) response.get("part" + i));

                Object resVal = partMap.get("res_value");
                if (isLecturer) {
                    results.add(new Result(
                            (int) partMap.get("ID"),
                            (String) partMap.get("login"),
                            (String) partMap.get("name_str"),
                            (String) partMap.get("last_name"),
                            (String) partMap.get("when_given"),
                            (resVal instanceof Integer) ? ((int) resVal) : ((double) resVal),
                            (String) partMap.get("res_type")
                    ));
                } else {
                    results.add(new Result(
                            -1,
                            null,
                            null,
                            null,
                            (String) partMap.get("when_given"),
                            (resVal instanceof Integer) ? ((int) resVal) : ((double) resVal),
                            (String) partMap.get("res_type")
                    ));
                }
            }

        } catch (IOException e) {
            System.err.println("Unable to retrieve data");
        }

        return results;
    }

    public List<Student> getStudents(int groupID, boolean isLecturer) throws AccessDeniedException {
        System.out.println("Getting the result list...");

        if (!isLecturer)
            throw new AccessDeniedException("Only a lecturer can add/delete results");

        List<Student> students = new ArrayList<>();

        out.println(builder
                .put("action", 0)
                .put("group", groupID)
                .get()
        );

        try {
            String msg = in.readLine();

            if (msg == null)
                throw new IOException();

            Map<String, Object> response = parser.parse(msg);

            if (response.containsKey("error"))
                throw new AccessDeniedException((String) response.get("error"));

            int size = (int) response.get("size");

            for (int i = 0; i < size; i++) {
                Map<String, Object> partMap = parser.parse((String) response.get("part" + i));

                students.add(new Student(
                        (String) partMap.get("login"),
                        (String) partMap.get("name_str"),
                        (String) partMap.get("last_name")
                ));
            }

        } catch (IOException e) {
            System.err.println("Unable to retrieve data");
        }

        return students;
    }

    public void deleteResult(int ID, boolean isLecturer) throws AccessDeniedException {
        if (!isLecturer)
            throw new AccessDeniedException("Only a lecturer can add/delete results");

        String resInfo = builder
                .put("ID", ID)
                .get();

        out.println(builder
                .put("action", 6)
                .put("size", 1)
                .put("part0", resInfo)
                .get()
        );

        awaitConfirmation();
    }

    public void newResults(List<Result> results, int groupID, boolean isLecturer) throws AccessDeniedException {
        if (!isLecturer)
            throw new AccessDeniedException("Only a lecturer can add/delete results");

        if (results.size() == 0)
            return;

        ArrayList<String> parts = new ArrayList<>();
        boolean add = results.get(0).getResultType().equals("activity");


        for (Result res : results) {
            parts.add(builder
                    .put("value", res.getResultValue())
                    .put("ID", res.getID())
                    .put("login", res.getLogin())
                    .put("group", groupID)
                    .put("type", res.getResultType())
                    .put("date", res.getWhenGiven())
                    .get()
            );
        }

        builder.put("size", parts.size());
        builder.put("activity", add);
        builder.put("add", add);
        builder.put("action", 5);

        for (int i = 0; i < parts.size(); i++)
            builder.put("part" + i, parts.get(i));

        out.println(builder.get());

        awaitConfirmation();
    }

    private String hashPass(String pass) {
        return Hashing.sha256().hashString(pass, StandardCharsets.UTF_8).toString();
    }

    private void awaitConfirmation() throws AccessDeniedException {
        try {
            String msg = in.readLine();

            if (msg == null)
                throw new IOException();

            Map<String, Object> response = parser.parse(msg);

            if (response.containsKey("error"))
                throw new AccessDeniedException((String) response.get("error"));
            else if (response.containsKey("msg"))
                Main.displayMessage("Success", (String) response.get("msg"), "", Alert.AlertType.CONFIRMATION);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
