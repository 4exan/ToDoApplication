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
    private VBox taskCreationVbox;

    @FXML
    private HBox taskCreationMainHbox;

    @FXML
    private HBox taskCreationAditionalHbox;

    private final String FILE_NAME = "taskStorage.txt";
    private final String STYLESHEET_NAME = "stylesheet.css";
    private List<Task> taskList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskCreationPriorityChoiceBox.getItems().addAll(List.of(TaskPriority.LOW, TaskPriority.MEDIUM, TaskPriority.HIGH));
        readFile();
        if(!taskList.isEmpty()){
            List<HBox> tasks = new ArrayList<>();
            taskList.forEach(task -> {
                HBox hbox = createNewTaskBox(task);
                VBox textVBox = createTextVBox(task);
                VBox dataTimeVBox = createDateTimeVBox(task);
                HBox btnBox = createBtnHBox();

                hbox.getChildren().addAll(textVBox, dataTimeVBox, btnBox);
                tasks.add(hbox);
            });
            taskVbox.getChildren().addAll(tasks);
        }
    }

    private HBox createBtnHBox() {
        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(5);
        Button completeTaskBtn = new Button("Complete");
        Button editTaskBtn = new Button("Edit");
        Button deleteTaskBtn = new Button("Delete");
        btnBox.getChildren().addAll(List.of(completeTaskBtn, editTaskBtn, deleteTaskBtn));
        return btnBox;
    }

    private VBox createDateTimeVBox(Task task) {
        VBox dateTimeVBox = new VBox();
        dateTimeVBox.setPrefWidth(200);
        dateTimeVBox.setAlignment(Pos.CENTER);
        dateTimeVBox.getChildren().addAll(createDateTimeLabels(task));
        return dateTimeVBox;
    }

    private Collection<Label> createDateTimeLabels(Task task) {
        Label date = createDateLabel(task.getDate());
        Label time = createTimeLabel(task.getTimeStart(), task.getTimeEnd());
        return List.of(date, time);
    }

    private Label createTimeLabel(String timeStart, String timeEnd) {
        Label timeLabel = new Label();
        StringBuilder dateTimeString = new StringBuilder();
        if(!timeStart.isBlank() && !timeStart.equals("null")){
            dateTimeString.append(timeStart);
        }else{
            dateTimeString.append("...");
        }
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

    private Label createDateLabel(String date) {
        Label dateLabel = new Label(date);
        dateLabel.getStylesheets().add(STYLESHEET_NAME);
        dateLabel.setId("taskDateLabel");
        return dateLabel;
    }

    private VBox createTextVBox(Task task) {
        VBox textVBox = new VBox();
        textVBox.setPrefWidth(200);
        textVBox.setAlignment(Pos.CENTER_LEFT);
        textVBox.getChildren().addAll(createTaskText(task));
        return textVBox;
    }

    private Collection<Label> createTaskText(Task task) {
        Label taskTitle = createTaskTitleLabel(task.getTitle());
        Label taskDescription = createTaskDescriptionLabel(task.getDescription());
        return List.of(taskTitle, taskDescription);
    }

    private Label createTaskDescriptionLabel(String description) {
        Label taskDescription = new Label(description);
        taskDescription.getStylesheets().add(STYLESHEET_NAME);
        taskDescription.setId("taskDescription");
        return taskDescription;
    }

    private Label createTaskTitleLabel(String title) {
        Label taskTitle = new Label(title);
        taskTitle.getStylesheets().add(STYLESHEET_NAME);
        taskTitle.setId("taskTitle");
        return taskTitle;
    }

    private HBox createNewTaskBox(Task task) {
        HBox hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setPadding(new Insets(0, 0, 0, 5));
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPrefHeight(50);
        hbox.getStylesheets().add(STYLESHEET_NAME);
        hbox.setId("taskHBox");
        switch(task.getPriority()){
            case "LOW" : hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: green");break;
            case "MEDIUM" : hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: yellow");break;
            case "HIGH" : hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: red");break;
            default: hbox.setStyle("-fx-border-width: 0 0 1 2; -fx-border-color: #bcc0cc");break;
        }
        return hbox;
    }

    private void readFile() {
        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))){
            String line;
            while((line = reader.readLine()) != null){
                Task task = new Task(
                        extractValue(line, "id"),
                        extractValue(line, "title"),
                        extractValue(line, "description"),
                        extractValue(line, "date"),
                        extractValue(line, "priority"),
                        extractValue(line, "timeStart"),
                        extractValue(line, "timeEnd"));
                System.out.println(task);
                taskList.add(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String extractValue(String line, String key) {
        String regex = key + "='([^']*)'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "Null";
    }

    @FXML
    void addNewTask(ActionEvent event) {
        checkRootFile();
        Task newTask = createNewTask();
        writeNewTask(newTask);
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

    private void writeNewTask(Task newTask) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.append(String.valueOf(newTask));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Task createNewTask() {
        if(taskCreationAdvancedOptionCheckBox.isSelected()) {
            return new Task(
                    assignTaskId(),
                    taskCreationTitleTextField.getText(),
                    taskCreationDescriptionTextField.getText(),
                    taskCreationDatePicker.getValue().toString(),
                    taskCreationPriorityChoiceBox.getValue().toString(),
                    taskCreationStartTimeTextField.getText(),
                    taskCreationEndTimeTextField.getText());
        }else{
            return new Task(
                    assignTaskId(),
                    taskCreationTitleTextField.getText(),
                    taskCreationDescriptionTextField.getText(),
                    taskCreationDatePicker.getValue().toString());
        }
    }

    private String assignTaskId() {
        return String.valueOf(UUID.randomUUID());
    }

    private void checkRootFile() {

    }

    @FXML
    void toggleAdvancedTaskCreationOptions(ActionEvent event) {
        if(taskCreationAdvancedOptionCheckBox.isSelected()){
            taskCreationAditionalHbox.setVisible(true);
        }else{
            taskCreationAditionalHbox.setVisible(false);
        }
    }
}

