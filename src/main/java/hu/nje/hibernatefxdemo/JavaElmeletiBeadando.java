package hu.nje.hibernatefxdemo;

import MNB_soap.MNBArfolyamServiceSoap;
import MNB_soap.GetCurrenciesRequestBody;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.module.Configuration;
import java.sql.SQLException;

public class JavaElmeletiBeadando extends Application {

    GUIController controller;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaElmeletiBeadando.class.getResource("views/main_layout.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 768);
        controller = fxmlLoader.getController();
        stage.setTitle("Java alkalmazások - elméleti beadandó");
        stage.setScene(scene);
        stage.show();
        controller.loadHelloView(); //Ez a default screen ezért rögtön betöltöm init előtt.
        controller.Init();
    }
    public static void start(String[] args) throws SQLException {

        launch();
    }
}