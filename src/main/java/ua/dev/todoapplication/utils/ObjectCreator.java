package ua.dev.todoapplication.utils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
        endTime.setPromptText("End");
        return endTime;
    }

    public HBox createAdvancedOption(ChoiceBox<TaskPriority> taskCreationPriorityChoiceBox, TextField taskCreationStartTimeTextField, TextField taskCreationEndTimeTextField) {
        taskCreationPriorityChoiceBox = createAdvancedOptionChoiceBox(taskCreationPriorityChoiceBox);
        taskCreationStartTimeTextField = createAdvancedOptionStartTimeTF(taskCreationStartTimeTextField);
        taskCreationEndTimeTextField = createAdvancedOptionEndTimeTF(taskCreationEndTimeTextField);
        return createAdvancedOptionHBox(taskCreationPriorityChoiceBox, taskCreationStartTimeTextField, taskCreationEndTimeTextField);
    }
}
