package ua.dev.todoapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TodoApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(TodoApplication.class.getResource("todoApplication.fxml"));
        Scene scene = new Scene(fxmlloader.load());
        scene.getStylesheets().add(getClass().getResource("stylesheets/late.css").toExternalForm());
        stage.setTitle("Todo Application!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
