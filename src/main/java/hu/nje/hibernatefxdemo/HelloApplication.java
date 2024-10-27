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
    static SessionFactory factory;
    HelloController controller;
    List<Mertek> mertekek;
    List<Megnevezes> megnevezesek;
    List<Korlatozas> korlatozasok;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        controller = fxmlLoader.getController();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        System.out.println("haha");
        //try{
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            factory = cfg.buildSessionFactory();
            Read();
            factory.close();
            controller.tableFeltolt(korlatozasok);
        //}
        //catch(Exception e){
       //     System.out.println("EXCEPTION VAN: " + e);
       // }


    }
    public void Read() {
        System.out.println("Read()........");
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        System.out.println("--------------------------Mértékek");
        mertekek = session.createQuery("from Mertek").list();
        for(Mertek m : mertekek) {
            System.out.println(m.getNev());
        }
        System.out.println("----------------------------Megnevezesek");
        megnevezesek = session.createQuery("from Megnevezes").list();
        for(Megnevezes m : megnevezesek) {
            System.out.println(m.getNev());
        }

        System.out.println("----------------------------Korlatozasok");
        korlatozasok = session.createQuery("from Korlatozas").list();
        for(Korlatozas m : korlatozasok) {
            System.out.print(m.getUtszam() + " ");
            System.out.print(m.getTelepules() + " ");
            System.out.print(m.getMegnevezes().getNev() + " ");
            System.out.print(m.getMertek().getNev() + " ");
            System.out.println();
        }

        t.commit();
        session.close();

    }
    public static void main(String[] args) {
        launch();
    }
}