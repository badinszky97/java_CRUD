package hu.nje.hibernatefxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HelloController {
    static SessionFactory factory;
    List<Mertek> mertekek;
    List<Megnevezes> megnevezesek;
    List<Korlatozas> korlatozasok;
    @FXML
    private Label welcomeText;

    @FXML
    private TableView korlTable;
    @FXML private TableColumn<Korlatozas, Integer> utszamCol;
    @FXML private TableColumn<Korlatozas, Float> kezdetCol;
    @FXML private TableColumn<Korlatozas, Float> vegCol;
    @FXML private TableColumn<Korlatozas, String> telepulesCol;
    @FXML private TableColumn<Korlatozas, String> mettolCol;
    @FXML private TableColumn<Korlatozas, String> meddigCol;
    @FXML private TableColumn<Korlatozas, String> megnevezesCol;
    @FXML private TableColumn<Korlatozas, String> mertekCol;
    @FXML private TableColumn<Korlatozas, Integer> sebessegCol;

    public void Init(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();
        Read();
        //Hozzaad();
        //factory.close();
        tableFeltolt(korlatozasok);
    }
    public void Hozzaad(){
        Korlatozas korlatozas = new Korlatozas();
        korlatozas.setUtszam(1234);
        korlatozas.setKezdet(1.0f);
        korlatozas.setVeg(2.0f);
        korlatozas.setTelepules("uj telepulesnev");
        korlatozas.setMettol("2020");
        korlatozas.setMeddig("2121");
        korlatozas.setMegnevezes(megnevezesek.get(1));
        korlatozas.setMertek(mertekek.get(1));
        korlatozas.setSebesseg(66);
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        session.save(korlatozas);
        t.commit();
        session.close();
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


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        Integer szam = korlTable.getSelectionModel().getSelectedIndex();
        System.out.println(korlatozasok.get(szam).getTelepules());
    }

    public void tableFeltolt(List<Korlatozas> lista){
        utszamCol.setCellValueFactory(new PropertyValueFactory<>("utszam"));
        kezdetCol.setCellValueFactory(new PropertyValueFactory<>("kezdet"));
        vegCol.setCellValueFactory(new PropertyValueFactory<>("veg"));
        telepulesCol.setCellValueFactory(new PropertyValueFactory<>("telepules"));
        mettolCol.setCellValueFactory(new PropertyValueFactory<>("mettol"));
        meddigCol.setCellValueFactory(new PropertyValueFactory<>("meddig"));
        megnevezesCol.setCellValueFactory(new PropertyValueFactory<>("megnevezes"));
        mertekCol.setCellValueFactory(new PropertyValueFactory<>("mertek"));
        sebessegCol.setCellValueFactory(new PropertyValueFactory<>("sebesseg"));

        for(int i=0;i<lista.size();i++)
        {
            korlTable.getItems().add(lista.get(i));
        }
    }


}