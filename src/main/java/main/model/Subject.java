package main.model;

public class Subject {

    private int ID;
    private String lecturer;
    private String name;
    private WeekPattern pattern;
    private String startTime;
    private WeekDay startDay;

    public Subject(int ID, String lecturer, String name, WeekPattern pattern, String startTime, WeekDay startDay) {
        this.ID = ID;
        this.lecturer = lecturer;
        this.name = name;
        this.pattern = pattern;
        this.startTime = startTime;
        this.startDay = startDay;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WeekPattern getPattern() {
        return pattern;
    }

    public void setPattern(WeekPattern pattern) {
        this.pattern = pattern;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public WeekDay getStartDay() {
        return startDay;
    }

    public void setStartDay(WeekDay startDay) {
        this.startDay = startDay;
    }

    @Override
    public String toString() {
        String day;
        String patt;

        switch (startDay) {
            case MONDAY:
                day = "Mon. ";
                break;
            case TUESDAY:
                day = "Tue. ";
                break;
            case WEDNESDAY:
                day = "Wed. ";
                break;
            case THURSDAY:
                day = "Thu. ";
                break;
            case FRIDAY:
                day = "Fri. ";
                break;
            case SATURDAY:
                day = "Sat. ";
                break;
            case SUNDAY:
                day = "Sun. ";
                break;
            default:
                day = "";
        }

        switch (pattern) {
            case TN:
                patt = " TN";
                break;
            case TP:
                patt = " TP";
                break;
            default:
                patt = "";
        }

        return name + " [" + day + startTime + patt + "]";
    }
}
