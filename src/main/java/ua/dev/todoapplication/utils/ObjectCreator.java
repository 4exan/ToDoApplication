package ua.dev.todoapplication.utils;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
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


}
