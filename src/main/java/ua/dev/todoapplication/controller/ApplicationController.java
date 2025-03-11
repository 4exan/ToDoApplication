package ua.dev.todoapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ua.dev.todoapplication.entity.Task;
import ua.dev.todoapplication.service.FileService;
import ua.dev.todoapplication.service.TaskService;
import ua.dev.todoapplication.utils.ObjectCreator;
import ua.dev.todoapplication.utils.TaskPriority;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationController implements Initializable {

    @FXML
    private Button addNewTaskBtn;

    @FXML
    private CheckBox taskCreationAdvancedOptionCheckBox;

    @FXML
    private DatePicker taskCreationDatePicker;

    @FXML
    private TextField taskCreationDescriptionTextField;

    @FXML
    private TextField taskCreationEndTimeTextField;

    @FXML
    private ChoiceBox<TaskPriority> taskCreationPriorityChoiceBox;

    @FXML
    private TextField taskCreationStartTimeTextField;

    @FXML
    private TextField taskCreationTitleTextField;

    @FXML
    private VBox taskVbox;

    @FXML
    private VBox taskListVBox;

    @FXML
    private VBox taskCreationVbox;

    @FXML
    private HBox taskCreationMainHbox;

    @FXML
    private ScrollPane taskScrollPane;

    @FXML
    private HBox taskCreationAditionalHbox;

    @FXML
    private Label taskCreationPriorityLabel;

    @FXML
    private Label taskCreationTimeLabel;

    // Projects
    @FXML
    private VBox projectsVBox;
    @FXML
    private Button addProjectBtn;

    private final String FILE_NAME = "taskStorage.txt";
    private final String STYLESHEET_NAME = "stylesheet.css";
    private List<Task> taskList = new ArrayList<>();

    private final ObjectCreator creator = new ObjectCreator();
    private final TaskService taskService = new TaskService();
    private final FileService fileService = new FileService();

    private HBox advancedSettingsHBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        advancedSettingsHBox = createAdvancedOptionHBox();
        taskList.addAll(fileService.readFile());
        if(!taskList.isEmpty()){
            refreshTaskHBox();
        }
    }

    private HBox createAdvancedOptionHBox() {
        Label priorityLabel = new Label("Priority:");

        ChoiceBox<TaskPriority> taskPriorityChoiceBox = new ChoiceBox<>();
        taskPriorityChoiceBox.getItems().addAll(List.of(TaskPriority.LOW, TaskPriority.MEDIUM, TaskPriority.HIGH));

        Label timeLabel = new Label("Time:");

        TextField timeStartTF = new TextField();
        timeStartTF.setPromptText("Start");
        TextField timeEndTF = new TextField();
        timeEndTF.setPromptText("End");

        HBox hbox = creator.createHBox(200, 50, 5, Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0, 0, 5, 5));
        hbox.getChildren().addAll(List.of(
                priorityLabel,
                taskPriorityChoiceBox,
                timeLabel,
                timeStartTF,
                timeEndTF
        ));
        return hbox;
    }

    private void refreshTaskHBox(){
        taskListVBox.getChildren().clear();
        List<HBox> tasks = new ArrayList<>();
        taskList.forEach(task -> {
            HBox hbox = createNewTaskBox(task);
            VBox textVBox = createTextVBox(task);
            VBox dataTimeVBox = createDateTimeVBox(task);
            HBox btnBox = createBtnHBox();

            hbox.getChildren().addAll(textVBox, dataTimeVBox, btnBox);
            tasks.add(hbox);
        });
        taskListVBox.getChildren().addAll(tasks);
    }

    private HBox createBtnHBox() {
        HBox btnBox = creator.createHBox(200, 50, 5, Pos.CENTER_RIGHT);
        Button completeTaskBtn = new Button();
        completeTaskBtn.setId("completeTaskBtn");
        Button editTaskBtn = new Button();
        Button deleteTaskBtn = new Button();
        deleteTaskBtn.setId("deleteTaskBtn");
        btnBox.getChildren().addAll(List.of(completeTaskBtn, editTaskBtn, deleteTaskBtn));
        return btnBox;
    }

    private VBox createDateTimeVBox(Task task) {
        VBox dateTimeVBox = creator.createVBox(200, 50, 0, Pos.CENTER);
        dateTimeVBox.getChildren().addAll(createDateTimeLabels(task));
        return dateTimeVBox;
    }

    private Collection<Label> createDateTimeLabels(Task task) {
        Label date = creator.createLabel(task.getDate(), "taskDateLabel");
        if(task.getTimeStart().equals("null") && task.getTimeEnd().equals("null")){
            return List.of(date);
        }else {
            Label time = createTimeLabel(task.getTimeStart(), task.getTimeEnd());
            return List.of(date, time);
        }
    }

    private Label createTimeLabel(String timeStart, String timeEnd) {
        Label timeLabel = new Label();
        StringBuilder dateTimeString = new StringBuilder();
        if(!timeStart.isBlank() && !timeStart.equals("null")){
            dateTimeString.append(timeStart);
            dateTimeString.append(" ");
        }else{
            dateTimeString.append("...");
        }
        dateTimeString.append(" - ");
        if(!timeEnd.isBlank() && !timeEnd.equals("null")){
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
        VBox textVBox = creator.createVBox(200, 50, 0, Pos.CENTER_LEFT);
        textVBox.getChildren().addAll(createTaskText(task));
        return textVBox;
    }

    private Collection<Label> createTaskText(Task task) {
        Label taskTitle = creator.createLabel(task.getTitle(), "taskTitle");
        Label taskDescription = creator.createLabel(task.getDescription(), "taskDescription");
        return List.of(taskTitle, taskDescription);
    }

    private HBox createNewTaskBox(Task task) {
        HBox hbox = creator.createHBox(200, 50, 5, Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0, 0, 0, 5));
        hbox.getStylesheets().add(STYLESHEET_NAME);
        hbox.setId("taskHBox");
        switch(task.getPriority()){
            case "LOW" : hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: #40a02b");break;
            case "MEDIUM" : hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color:  #df8e1d");break;
            case "HIGH" : hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: #d20f39");break;
            default: hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: #bcc0cc");break;
        }
        return hbox;
    }

    @FXML
    void addNewTask(ActionEvent event) {
        Task newTask = createNewTask();
        fileService.writeNewTask(newTask);
        taskList.clear();
        taskList = fileService.readFile();
        setValuesToDefault();
    }

    private void setValuesToDefault() {
        taskCreationPriorityChoiceBox.setValue(null);
        taskCreationTitleTextField.setText(null);
        taskCreationDatePicker.setValue(null);
        taskCreationDescriptionTextField.setText(null);
        taskCreationStartTimeTextField.setText(null);
        taskCreationEndTimeTextField.setText(null);
    }

    private Task createNewTask() {
        if(taskCreationAdvancedOptionCheckBox.isSelected()) {
            return new Task(
                    UUID.randomUUID().toString(),
                    taskCreationTitleTextField.getText(),
                    taskCreationDescriptionTextField.getText(),
                    new Date(System.currentTimeMillis()).toString(),
                    taskCreationDatePicker.getValue().toString(),
                    taskCreationPriorityChoiceBox.getValue().toString(),
                    taskCreationStartTimeTextField.getText(),
                    taskCreationEndTimeTextField.getText());
        }else{
            return new Task(
                    UUID.randomUUID().toString(),
                    taskCreationTitleTextField.getText(),
                    taskCreationDescriptionTextField.getText(),
                    new Date(System.currentTimeMillis()).toString(),
                    taskCreationDatePicker.getValue().toString());
        }
    }

    @FXML
    void toggleAdvancedTaskCreationOptions(ActionEvent event) {
        if(taskCreationAdvancedOptionCheckBox.isSelected()){
            taskCreationVbox.getChildren().add(advancedSettingsHBox);
        }else{
            taskCreationVbox.getChildren().remove(1);
        }
    }

    @FXML
    public void addNewProject(){

    }

}