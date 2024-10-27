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
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        System.out.println("haha");
        //try{
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            factory = cfg.buildSessionFactory();
            Read();
            factory.close();
        //}
        //catch(Exception e){
       //     System.out.println("EXCEPTION VAN: " + e);
       // }


    }
    public static void Read() {
        System.out.println("Read()........");
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        System.out.println("--------------------------Mértékek");
        List<Mertek> mertekek = session.createQuery("from Mertek").list();
        for(Mertek m : mertekek) {
            System.out.println(m.getNev());
        }
        System.out.println("----------------------------Megnevezesek");
        List<Megnevezes> megnevezesek = session.createQuery("from Megnevezes").list();
        for(Megnevezes m : megnevezesek) {
            System.out.println(m.getNev());
        }


        /*List<Instructor> oktatóLista = session.createQuery("FROM Instructor").list();
        for (Instructor okt : oktatóLista) {
            System.out.print("ID: " + okt.getId());
            System.out.print(" Email: " + okt.getEmail());
            System.out.print(" First name:: " + okt.getFirstName());
            System.out.println(" Last name: " + okt.getLastName());
            System.out.println(" Kurzusok: " + okt.getCourses());
        }*/
        t.commit();
        session.close();
    }
    public static void main(String[] args) {
        launch();
    }
}