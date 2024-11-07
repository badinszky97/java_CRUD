package hu.nje.hibernatefxdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaElmeletiBeadando extends Application {

    GUIController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaElmeletiBeadando.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
        controller = fxmlLoader.getController();
        stage.setTitle("Java alkalmazások - elméleti beadandó");
        stage.setScene(scene);
        stage.show();
        controller.Init();
    }





    public static void main(String[] args) {
        launch();
    }
}