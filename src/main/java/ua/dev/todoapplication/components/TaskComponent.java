package ua.dev.todoapplication.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ua.dev.todoapplication.entity.Task;

import java.util.List;

public class TaskComponent {

    private final Task task;
    private HBox hbox;

    public TaskComponent(Task task) {
        this.task = task;
        createTaskComponent();
    }

    public HBox getHbox() {
        return hbox;
    }

    private void createTaskComponent(){
       hbox = createTaskBox(task);
       VBox textVBox = createTextVBox(task);
       VBox dataTimeVBox = createDateTimeVBox(task);
       HBox btnHBox = createBtnHBox();

       hbox.getChildren().addAll(List.of(textVBox, dataTimeVBox, btnHBox));
    }

    private HBox createTaskBox(Task task){
        HBox hbox = createHBox(200, 50, 5, Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0, 0, 0, 5));
        hbox.getStylesheets().add("stylesheet.css");
        hbox.setId("taskHBox");
        hbox.setStyle(priorityStyle(task.getPriority()));
        return hbox;
    }

    private VBox createTextVBox(Task task) {
        VBox vBox = createVBox(200, 50, 0, Pos.CENTER_LEFT);
        vBox.getChildren().addAll(createTaskText(task));
        return vBox;
    }


    private HBox createBtnHBox() {
        HBox btnBox = createHBox(200, 50, 5, Pos.CENTER_RIGHT);
        Button completeTaskBtn = new Button();
        completeTaskBtn.setId("completeTaskBtn");
        completeTaskBtn.setOnAction(actionEvent -> {
            System.out.println("Task with id: " + task.getId() + " is completed!");
        });
        Button editTaskBtn = new Button();
        Button deleteTaskBtn = new Button();
        deleteTaskBtn.setId("deleteTaskBtn");
        btnBox.getChildren().addAll(List.of(completeTaskBtn, editTaskBtn, deleteTaskBtn));
        return btnBox;
    }

    private List<Label> createTaskText(Task task) {
        Label title = new Label(task.getTitle());
        title.setId("taskTitle");
        Label description = new Label(task.getDescription());
        description.setId("taskDescription");
        return List.of(title, description);
    }


    private VBox createDateTimeVBox(Task task) {
        VBox dateTimeVBox = createVBox(100, 50, 0, Pos.CENTER);
        dateTimeVBox.getChildren().addAll(createDateTimeLabels(task));
        return dateTimeVBox;
    }

    private List<Label> createDateTimeLabels(Task task) {
        Label date = new Label(task.getDate());
        date.setId("taskDateLabel");

        if(task.getTimeStart().isEmpty() && task.getTimeEnd().isEmpty()){
            return List.of(date);
        }else {
            Label time = createTimeLabel(task.getTimeStart(), task.getTimeEnd());
            return List.of(date, time);
        }
    }

    private Label createTimeLabel(String timeStart, String timeEnd) {
        Label timeLabel = new Label();
        StringBuilder dateTimeString = new StringBuilder();
        if(!timeStart.isBlank()){
            dateTimeString.append(timeStart);
            dateTimeString.append(" ");
        }else{
            dateTimeString.append("...");
        }
        dateTimeString.append(" - ");
        if(!timeEnd.isBlank()){
            dateTimeString.append(timeEnd);
        }else{
            dateTimeString.append("...");
        }
        timeLabel.setText(String.valueOf(dateTimeString));
        timeLabel.getStylesheets().add("stylesheet.css");
        timeLabel.setId("taskTimeLabel");
        return timeLabel;
    }

    private VBox createVBox(int prefWidth, int prefHeight, int spacing, Pos alignment){
        VBox vbox = new VBox();
        vbox.setPrefWidth(prefWidth);
        vbox.setPrefHeight(prefHeight);
        vbox.setSpacing(spacing);
        vbox.setAlignment(alignment);
        return vbox;
    }

    private HBox createHBox(int prefWidth, int prefHeight, int spacing, Pos alignment){
        HBox hbox = new HBox(spacing);
        hbox.setPrefWidth(prefWidth);
        hbox.setPrefHeight(prefHeight);
        hbox.setAlignment(alignment);
        return hbox;
    }

    private String priorityStyle(String priority) {
        return switch (priority) {
            case "LOW" -> "-fx-border-width: 0 0 1 2; -fx-border-color: #40a02b";
            case "MEDIUM" -> "-fx-border-width: 0 0 1 2; -fx-border-color:  #df8e1d";
            case "HIGH" -> "-fx-border-width: 0 0 1 2; -fx-border-color: #d20f39";
            default -> "-fx-border-width: 0 0 1 2; -fx-border-color: #bcc0cc";
        };
    }

}
