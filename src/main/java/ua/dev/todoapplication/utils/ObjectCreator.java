package ua.dev.todoapplication.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ua.dev.todoapplication.entity.Task;

import java.util.List;

public class ObjectCreator {

    private final String STYLESHEET_NAME = "stylesheet.css";

    public Label createLabel(String text, String labelId){
        Label label = new Label(text);
        label.getStylesheets().add(STYLESHEET_NAME);
        label.setId(labelId);
        return label;
    }

    public VBox createVBox(int prefWidth, int prefHeight, int spacing, Pos alignment){
        VBox vbox = new VBox();
        vbox.setPrefWidth(prefWidth);
        vbox.setPrefHeight(prefHeight);
        vbox.setSpacing(spacing);
        vbox.setAlignment(alignment);
        return vbox;
    }

    public HBox createHBox(int prefWidth, int prefHeight, int spacing, Pos alignment){
        HBox hbox = new HBox();
        hbox.prefWidth(prefWidth);
        hbox.prefHeight(prefHeight);
        hbox.setSpacing(spacing);
        hbox.setAlignment(alignment);
        return hbox;
    }


    public HBox createAdvancedOptionHBox(ChoiceBox<TaskPriority> choiceBox, TextField timeStartTF, TextField timeEndTF) {
        Label priorityLabel = createLabel("Priority:", "priorityLabelId");

        Label timeLabel = createLabel("Time:", "timeLabelId");

        HBox hbox = createHBox(200, 50, 5, Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0, 0, 5, 5));
        hbox.getChildren().addAll(List.of(
                priorityLabel,
                choiceBox,
                timeLabel,
                timeStartTF,
                timeEndTF
        ));
        return hbox;
    }

    public ChoiceBox<TaskPriority> createAdvancedOptionChoiceBox(ChoiceBox<TaskPriority> choiceBox) {
        choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(List.of(TaskPriority.LOW, TaskPriority.MEDIUM, TaskPriority.HIGH));
        choiceBox.setValue(TaskPriority.NONE);
        return choiceBox;
    }

    public TextField createAdvancedOptionStartTimeTF(TextField startTime) {
        startTime = new TextField();
        startTime.setPromptText("Start");
        return startTime;
    }

    public TextField createAdvancedOptionEndTimeTF(TextField endTime) {
        endTime = new TextField();
        endTime.setPromptText("Start");
        return endTime;
    }

    public HBox createAdvancedOption(ChoiceBox<TaskPriority> taskCreationPriorityChoiceBox, TextField taskCreationStartTimeTextField, TextField taskCreationEndTimeTextField) {
        taskCreationPriorityChoiceBox = createAdvancedOptionChoiceBox(taskCreationPriorityChoiceBox);
        taskCreationStartTimeTextField = createAdvancedOptionStartTimeTF(taskCreationStartTimeTextField);
        taskCreationEndTimeTextField = createAdvancedOptionEndTimeTF(taskCreationEndTimeTextField);
        return createAdvancedOptionHBox(taskCreationPriorityChoiceBox, taskCreationStartTimeTextField, taskCreationEndTimeTextField);
    }

    public HBox createTaskHBox(Task task) {
        HBox hbox = createNewTaskBox(task);
        VBox textVBox = createTextVBox(task);
        VBox dataTimeVBox = createDateTimeVBox(task);
        HBox btnBox = createBtnHBox();

        hbox.getChildren().addAll(textVBox, dataTimeVBox, btnBox);
        return hbox;
    }

    private HBox createBtnHBox() {
        HBox btnBox = createHBox(200, 50, 5, Pos.CENTER_RIGHT);
        Button completeTaskBtn = new Button();
        completeTaskBtn.setId("completeTaskBtn");
        Button editTaskBtn = new Button();
        Button deleteTaskBtn = new Button();
        deleteTaskBtn.setId("deleteTaskBtn");
        btnBox.getChildren().addAll(List.of(completeTaskBtn, editTaskBtn, deleteTaskBtn));
        return btnBox;
    }

    private VBox createDateTimeVBox(Task task) {
        VBox dateTimeVBox = createVBox(100, 50, 0, Pos.CENTER);
        dateTimeVBox.getChildren().addAll(createDateTimeLabels(task));
        return dateTimeVBox;
    }

    private List<Label> createDateTimeLabels(Task task) {
        Label date = createLabel(task.getDate(), "taskDateLabel");

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
        timeLabel.getStylesheets().add(STYLESHEET_NAME);
        timeLabel.setId("taskTimeLabel");
        return timeLabel;
    }

    private VBox createTextVBox(Task task) {
        VBox textVBox = createVBox(200, 50, 0, Pos.CENTER_LEFT);
        textVBox.getChildren().addAll(createTaskText(task));
        return textVBox;
    }

    private List<Label> createTaskText(Task task) {
        Label taskTitle = createLabel(task.getTitle(), "taskTitle");
        Label taskDescription = createLabel(task.getDescription(), "taskDescription");
        return List.of(taskTitle, taskDescription);
    }

    private HBox createNewTaskBox(Task task){
        HBox hbox = createHBox(200, 50, 5, Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0, 0, 0, 5));
        hbox.getStylesheets().add(STYLESHEET_NAME);
        hbox.setId("taskHBox");
        if(task.getPriority() != null) {
            switch (task.getPriority()) {
                case "LOW":
                    hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: #40a02b");
                    break;
                case "MEDIUM":
                    hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color:  #df8e1d");
                    break;
                case "HIGH":
                    hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: #d20f39");
                    break;
                default:
                    break;
            }
        }
        return hbox;
    }
}
