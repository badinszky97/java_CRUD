package hu.nje.hibernatefxdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    HelloController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

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