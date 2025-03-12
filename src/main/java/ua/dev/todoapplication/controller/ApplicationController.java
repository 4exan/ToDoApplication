package ua.dev.todoapplication.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ua.dev.todoapplication.components.TaskComponent;
import ua.dev.todoapplication.entity.Task;
import ua.dev.todoapplication.service.FileService;
import ua.dev.todoapplication.service.TaskService;
import ua.dev.todoapplication.utils.ObjectCreator;
import ua.dev.todoapplication.utils.TaskPriority;

import java.net.URL;
import java.util.*;

public class ApplicationController implements Initializable {

    @FXML
    private CheckBox taskCreationAdvancedOptionCheckBox;

    @FXML
    private DatePicker taskCreationDatePicker;

    @FXML
    private TextField taskCreationDescriptionTextField;

    @FXML
    private TextField taskCreationTitleTextField;

    @FXML
    private VBox taskListVBox;

    @FXML
    private VBox taskCreationVbox;


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
        defineAdvancedOptions();
        fileService.readJsonFile();
        taskList.addAll(taskService.getUnsortedUncompletedTasks());
        if (!taskList.isEmpty()) {
            refreshTaskHBox();
        }
    }

    @FXML
    public void refresh() {
        fileService.readJsonFile();
        refreshTaskHBox();
    }

    @FXML
    void addNewTask(ActionEvent event) {
        Task newTask = createNewTask();
        fileService.writeNewTask(newTask);
        taskList.clear();
        taskList = taskService.getUnsortedUncompletedTasks();
        refreshTaskHBox();
    }

    @FXML
    void toggleAdvancedTaskCreationOptions(ActionEvent event) {
        if (taskCreationAdvancedOptionCheckBox.isSelected()) {
            taskCreationVbox.getChildren().add(advancedSettingsHBox);
        } else {
            taskCreationVbox.getChildren().remove(1);
        }
    }

    @FXML
    public void addNewProject() {

    }

    private void refreshTaskHBox() {
        taskListVBox.getChildren().clear();
        List<HBox> tasks = new ArrayList<>();
        taskList.forEach(task -> {
            tasks.add(new TaskComponent(task).getHbox());
        });
        taskListVBox.getChildren().addAll(tasks);
    }

    private void defineAdvancedOptions() {
        taskCreationPriorityChoiceBox = creator.createAdvancedOptionChoiceBox(taskCreationPriorityChoiceBox);
        taskCreationStartTimeTextField = creator.createAdvancedOptionStartTimeTF(taskCreationStartTimeTextField);
        taskCreationEndTimeTextField = creator.createAdvancedOptionEndTimeTF(taskCreationEndTimeTextField);
        advancedSettingsHBox = creator.createAdvancedOptionHBox(taskCreationPriorityChoiceBox, taskCreationStartTimeTextField, taskCreationEndTimeTextField);
    }

    private Task createNewTask() {
        return new Task(
                UUID.randomUUID().toString(),
                taskCreationTitleTextField.getText(),
                taskCreationDescriptionTextField.getText(),
                new Date(System.currentTimeMillis()).toString(),
                taskCreationDatePicker.getValue().toString(),
                taskCreationPriorityChoiceBox.getValue().toString() == null ? TaskPriority.NONE.toString() : taskCreationPriorityChoiceBox.getValue().toString(),
                taskCreationStartTimeTextField.getText() == null ? "" : taskCreationStartTimeTextField.getText(),
                taskCreationEndTimeTextField.getText() == null ? "" : taskCreationEndTimeTextField.getText());
    }
}