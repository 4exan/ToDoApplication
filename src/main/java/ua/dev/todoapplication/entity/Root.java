package ua.dev.todoapplication.entity;

public class Root {
    private Today today;

    public Today getToday() {
        return today;
    }

    public void setToday(Today today) {
        this.today = today;
    }

    @Override
    public String toString() {
        return "Root{" +
                today +
                '}';
    }
}
