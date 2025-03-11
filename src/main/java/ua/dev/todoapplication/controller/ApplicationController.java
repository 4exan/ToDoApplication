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

import java.net.URL;
import java.util.*;

public class ApplicationController implements Initializable {

    @FXML
    private Button addNewTaskBtn;

    @FXML
    private CheckBox taskCreationAdvancedOptionCheckBox;

    @FXML
    private DatePicker taskCreationDatePicker;

    @FXML
    private TextField taskCreationDescriptionTextField;

//    @FXML
//    private TextField taskCreationEndTimeTextField;

//    @FXML
//    private ChoiceBox<TaskPriority> taskCreationPriorityChoiceBox;

//    @FXML
//    private TextField taskCreationStartTimeTextField;

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

    @FXML
    private Button refreshBtn;

    // Projects
    @FXML
    private VBox projectsVBox;
    @FXML
    private Button addProjectBtn;

    private final String FILE_NAME = "taskStorage.txt";
    private final String STYLESHEET_NAME = "stylesheet.css";
    private List<Task> taskList = new ArrayList<>();
    //Advanced option
    private ChoiceBox<TaskPriority> taskCreationPriorityChoiceBox;
    private TextField taskCreationStartTimeTextField;
    private TextField taskCreationEndTimeTextField;

    private final ObjectCreator creator = new ObjectCreator();
    private final FileService fileService = new FileService();
    private final TaskService taskService = new TaskService(fileService);

    private HBox advancedSettingsHBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        advancedSettingsHBox = createAdvancedOptionHBox();
        fileService.readJsonFile();
        taskList.addAll(taskService.getTodayUncompletedTasks());
        if(!taskList.isEmpty()){
            refreshTaskHBox();
        }
    }

    @FXML
    public void refresh(){
        fileService.readJsonFile();
        refreshTaskHBox();
    }

    private HBox createAdvancedOptionHBox() {
        Label priorityLabel = new Label("Priority:");

        taskCreationPriorityChoiceBox = new ChoiceBox<>();
        taskCreationPriorityChoiceBox.getItems().addAll(List.of(TaskPriority.LOW, TaskPriority.MEDIUM, TaskPriority.HIGH));

        Label timeLabel = new Label("Time:");

        taskCreationStartTimeTextField = new TextField();
        taskCreationStartTimeTextField.setPromptText("Start");
        taskCreationEndTimeTextField = new TextField();
        taskCreationEndTimeTextField.setPromptText("End");

        HBox hbox = creator.createHBox(200, 50, 5, Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(0, 0, 5, 5));
        hbox.getChildren().addAll(List.of(
                priorityLabel,
                taskCreationPriorityChoiceBox,
                timeLabel,
                taskCreationStartTimeTextField,
                taskCreationEndTimeTextField
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

    @FXML
    void addNewTask(ActionEvent event) {
        Task newTask = createNewTask();
        fileService.writeNewTask(newTask);
        taskList.clear();
        taskList = taskService.getTodayUncompletedTasks();
        setValuesToDefault();
    }

    private void setValuesToDefault() {
        taskCreationPriorityChoiceBox.setValue(TaskPriority.NONE);
        taskCreationTitleTextField.setText("");
        taskCreationDatePicker.setValue(null);
        taskCreationDescriptionTextField.setText("");
        taskCreationStartTimeTextField.setText("");
        taskCreationEndTimeTextField.setText("");
    }

    private Task createNewTask() {
        if(taskCreationAdvancedOptionCheckBox.isSelected()) {
            return new Task(
                    UUID.randomUUID().toString(),
                    taskCreationTitleTextField.getText(),
                    taskCreationDescriptionTextField.getText(),
                    new Date(System.currentTimeMillis()).toString(),
                    taskCreationDatePicker.getValue().toString(),
                    taskCreationPriorityChoiceBox.getValue().toString() == null ? TaskPriority.NONE.toString() : taskCreationPriorityChoiceBox.getValue().toString(),
                    taskCreationStartTimeTextField.getText() == null ? "" : taskCreationStartTimeTextField.getText(),
                    taskCreationEndTimeTextField.getText() == null ? "" : taskCreationEndTimeTextField.getText());
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