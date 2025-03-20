package ua.dev.todoapplication.entity;

public class Task {
    private String id;
    private String title;
    private String description;
    private String creationDate;
    private String date;
    private String priority;
    private String timeStart;
    private String timeEnd;

    public Task() {
    }

    public Task(String id, String title, String description, String creationDate, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.date = date;
    }

    public Task(String id, String title, String description, String creationDate, String date, String priority, String timeStart, String timeEnd) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.date = date;
        this.priority = priority;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return  "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", date='" + date + '\'' +
                ", priority='" + priority + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                '}';
    }
}
