module ua.dev.todoapplication {
    requires javafx.controls;
    requires javafx.fxml;
    //requires javafx.base;

    exports ua.dev.todoapplication;
    exports ua.dev.todoapplication.controller;
    exports ua.dev.todoapplication.entity;
    exports ua.dev.todoapplication.utils;
    opens ua.dev.todoapplication.controller to javafx.fxml;
}