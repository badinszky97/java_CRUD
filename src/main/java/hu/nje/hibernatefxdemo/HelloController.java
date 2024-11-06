package hu.nje.hibernatefxdemo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class HelloController {



    /**
     * Párhuzamos programozás feladat változói
     */
    @FXML private Label mpLabel;
    @FXML private Label felmpLabel;
    Integer mp = 0;
    Integer felmp = 0;

    /**
     * Adatbázis feladat változói
     */
    static SessionFactory factory;
    List<Mertek> mertekek;
    List<Megnevezes> megnevezesek;
    List<Korlatozas> korlatozasok;
    @FXML private TableView korlTable;
    @FXML private TableColumn<Korlatozas, Integer> utszamCol;
    @FXML private TableColumn<Korlatozas, Float> kezdetCol;
    @FXML private TableColumn<Korlatozas, Float> vegCol;
    @FXML private TableColumn<Korlatozas, String> telepulesCol;
    @FXML private TableColumn<Korlatozas, String> mettolCol;
    @FXML private TableColumn<Korlatozas, String> meddigCol;
    @FXML private TableColumn<Korlatozas, String> megnevezesCol;
    @FXML private TableColumn<Korlatozas, String> mertekCol;
    @FXML private TableColumn<Korlatozas, Integer> sebessegCol;
    @FXML private TextField adatbazisUtszamTf;
    @FXML private TextField adatbazisKezdetTf;
    @FXML private TextField adatbazisVegTf;
    @FXML private TextField adatbazisTelepulesTf;
    @FXML private TextField adatbazisMettolTf;
    @FXML private TextField adatbazisMeddigTf;
    @FXML private ComboBox adatbazisMegnevezesTf;
    @FXML private ComboBox adatbazisMertekTf;
    @FXML private TextField adatbazisSebessegTf;


    /**
     * Párhuzamos programozás feladat objektumok
     */
    Timeline timelinemp = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        mpLabel.setText(mp.toString());
                        mp++;
                    }));

    Timeline timelinefelmp = new Timeline(
            new KeyFrame(Duration.seconds(0.5),
                    e -> {
                        felmpLabel.setText(felmp.toString());
                        felmp++;
                    }));

    /**
     * Inicializáló függvény, ami csak egyszer fut le a program legelején
     * Kapcsolódik az adatbázishoz és feltölti a helyi listákat a táblákból
     * Feltölti a comboboxok elemeit is
     */
    public void Init(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();
        Read();
        tableFeltolt(korlatozasok);

        timelinemp.setCycleCount(Timeline.INDEFINITE);
        timelinemp.play();

        timelinefelmp.setCycleCount(Timeline.INDEFINITE);
        timelinefelmp.play();

        adatbazisMegnevezesTf.getItems().clear();
        for(Megnevezes egy : megnevezesek)
        {
            adatbazisMegnevezesTf.getItems().add(egy.toString());
        }

        adatbazisMertekTf.getItems().clear();
        for(Mertek egy : mertekek)
        {
            adatbazisMertekTf.getItems().add(egy.toString());
        }
    }

    /**
     * Az adatbázis elemeit letölti és feltölti a helyi objektum listákat.
     */
    public void Read() {
        System.out.println("Read()........");
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        mertekek = session.createQuery("from Mertek").list();
        megnevezesek = session.createQuery("from Megnevezes").list();
        korlatozasok = session.createQuery("from Korlatozas").list();

        t.commit();
        session.close();

    }

    /**
     * Ez a funckió hívódik meg, ha a törlés gombra kattintunk. Törlődik a kijelölt elem.
     */
    public void AdatbazisElemTorlese()
    {

        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Korlatozas kijelolt = (Korlatozas)korlTable.getFocusModel().getFocusedItem();

        session.remove(kijelolt);
        System.out.println(kijelolt);
        t.commit();
        session.close();
        Read();
        tableFeltolt(korlatozasok);
        System.out.println("Utana hossz: " + korlatozasok.size());
        System.out.println("Lista hossz: " + korlTable.getItems().size());

    }

    /**
     * Ez a funkció hívódik meg a módosít gombra kattinta. A kijelölt elem módosul.
     */
    public void AdatbazisElemModositasa()
    {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Korlatozas kijelolt = (Korlatozas)korlTable.getFocusModel().getFocusedItem();
        kijelolt.setUtszam(Integer.parseInt(adatbazisUtszamTf.getText()));
        kijelolt.setKezdet(Float.parseFloat(adatbazisKezdetTf.getText()));
        kijelolt.setVeg(Float.parseFloat(adatbazisVegTf.getText()));
        kijelolt.setTelepules(adatbazisTelepulesTf.getText());
        kijelolt.setMettol(adatbazisMettolTf.getText());
        kijelolt.setMeddig(adatbazisMeddigTf.getText());
        kijelolt.setSebesseg(Integer.parseInt(adatbazisSebessegTf.getText()));


        System.out.println("keresett megnevezes: " + adatbazisMegnevezesTf.getValue().toString());
        for(int i = 0; i< megnevezesek.size();i++)
        {
            if(megnevezesek.get(i).getNev().equals(adatbazisMegnevezesTf.getValue().toString()))
            {
                kijelolt.setMegnevezes(megnevezesek.get(i));
            }
        }

        for(int i = 0; i< mertekek.size();i++)
        {
            if(mertekek.get(i).getNev().equals(adatbazisMertekTf.getValue().toString()))
            {
                kijelolt.setMertek(mertekek.get(i));
            }
        }

        session.update(kijelolt);
        System.out.println(kijelolt);
        t.commit();
        session.close();
        Read();
        tableFeltolt(korlatozasok);
        System.out.println("Utana hossz: " + korlatozasok.size());
    }

    /**
     * Ez a funkció hívódik meg ha a listában más elem jelölődik ki.
     */
    public void AdatbazisElemKijelol(){
        Korlatozas kijelolt = (Korlatozas)korlTable.getFocusModel().getFocusedItem();
        adatbazisUtszamTf.setText(kijelolt.getUtszam().toString());
        adatbazisKezdetTf.setText(kijelolt.getKezdet().toString());
        adatbazisVegTf.setText(kijelolt.getVeg().toString());
        adatbazisTelepulesTf.setText(kijelolt.getTelepules());
        adatbazisMettolTf.setText(kijelolt.getMettol());
        adatbazisMeddigTf.setText(kijelolt.getMeddig());
        adatbazisSebessegTf.setText(kijelolt.getSebesseg().toString());
        adatbazisMegnevezesTf.setValue(kijelolt.getMegnevezes().toString());
        adatbazisMertekTf.setValue(kijelolt.getMertek().toString());
    }

    /**
     * Ez a funckió hívódik meg a hozzáad gomb megnyomásakor. Egy új elem létre lesz hozva a korlatozasok táblában.
     */
    public void AdatbazisElemHozzaad()
    {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Korlatozas ujelem = new Korlatozas();

        ujelem.setUtszam(Integer.valueOf(adatbazisUtszamTf.getText()));
        ujelem.setKezdet(Float.valueOf(adatbazisKezdetTf.getText()));
        ujelem.setVeg(Float.valueOf(adatbazisVegTf.getText()));
        ujelem.setTelepules(adatbazisTelepulesTf.getText());
        ujelem.setMettol(adatbazisMettolTf.getText());
        ujelem.setMeddig(adatbazisMeddigTf.getText());
        ujelem.setSebesseg(Integer.valueOf(adatbazisSebessegTf.getText()));

        for(int i = 0; i< megnevezesek.size();i++)
        {
            if(megnevezesek.get(i).getNev().equals(adatbazisMegnevezesTf.getValue().toString()))
            {
                ujelem.setMegnevezes(megnevezesek.get(i));
            }
        }

        for(int i = 0; i< mertekek.size();i++)
        {
            if(mertekek.get(i).getNev().equals(adatbazisMertekTf.getValue().toString()))
            {
                ujelem.setMertek(mertekek.get(i));
            }
        }

        System.out.println("Elotte hossz: " + korlatozasok.size());

        session.save(ujelem);
        System.out.println(ujelem);
        t.commit();
        session.close();
        Read();
        tableFeltolt(korlatozasok);
        System.out.println("Utana hossz: " + korlatozasok.size());

    }

    /**
     * Ez a funkció feltölti a GUI-n a táblázatot a paraméterként átadott listával.
     * @param lista
     */
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
        korlTable.getItems().clear();
        for(int i=0;i<lista.size();i++)
        {
            korlTable.getItems().add(lista.get(i));
        }
        System.out.println("Lista hossz: " + korlTable.getItems().size());
    }
}
