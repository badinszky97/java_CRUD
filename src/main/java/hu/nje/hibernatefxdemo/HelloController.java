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
    Integer mp = 0;
    Integer felmp = 0;
    static SessionFactory factory;
    List<Mertek> mertekek;
    List<Megnevezes> megnevezesek;
    List<Korlatozas> korlatozasok;
    @FXML
    private Label welcomeText;

    @FXML private Label mpLabel;
    @FXML private Label felmpLabel;

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



    @FXML private TextField adatbazisUtszamTf;
    @FXML private TextField adatbazisKezdetTf;
    @FXML private TextField adatbazisVegTf;
    @FXML private TextField adatbazisTelepulesTf;
    @FXML private TextField adatbazisMettolTf;
    @FXML private TextField adatbazisMeddigTf;
    @FXML private ComboBox adatbazisMegnevezesTf;
    @FXML private ComboBox adatbazisMertekTf;
    @FXML private TextField adatbazisSebessegTf;

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


    public void Init(){
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        factory = cfg.buildSessionFactory();
        Read();
        //Hozzaad();
        //factory.close();
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

    public void Read() {
        System.out.println("Read()........");
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        //System.out.println("--------------------------Mértékek");
        mertekek = session.createQuery("from Mertek").list();
        /*for(Mertek m : mertekek) {
            System.out.println(m.getNev());
        }*/
        //System.out.println("----------------------------Megnevezesek");
        megnevezesek = session.createQuery("from Megnevezes").list();
        /*for(Megnevezes m : megnevezesek) {
            System.out.println(m.getNev());
        }*/

        //System.out.println("----------------------------Korlatozasok");
        korlatozasok = session.createQuery("from Korlatozas").list();
        /*for(Korlatozas m : korlatozasok) {
            System.out.print(m.getUtszam() + " ");
            System.out.print(m.getTelepules() + " ");
            System.out.print(m.getMegnevezes().getNev() + " ");
            System.out.print(m.getMertek().getNev() + " ");
            System.out.println();
        }*/

        t.commit();
        session.close();

    }

    public void SetLabeltext(String text)
    {
        welcomeText.setText(text);
    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

        Integer szam = korlTable.getSelectionModel().getSelectedIndex();
        System.out.println(korlatozasok.get(szam).getTelepules());
    }

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

        System.out.println("keresett megnevezes: " + adatbazisMegnevezesTf.getValue().toString());
        int index = 0;
        for(int i = 0; i< megnevezesek.size();i++)
        {
            if(megnevezesek.get(i).getNev().equals(adatbazisMegnevezesTf.getValue().toString()))
            {
                System.out.println("Qnem egyenlod: " + megnevezesek.get(i).getNev() + " " + adatbazisMegnevezesTf.getValue().toString());
                System.out.println("TALÁLTAM MEGNEVEZEST");
                index=i;
                ujelem.setMegnevezes(megnevezesek.get(i));
            }
        }
        System.out.println("megnevezesid: " + index + " " + megnevezesek.size());
        //Megnevezes megn = session.load(Megnevezes.class, 3);
        //ujelem.setMegnevezes(megn);


        System.out.println("keresett mertek: " + adatbazisMertekTf.getValue().toString());
        index = 0;
        for(int i = 0; i< mertekek.size();i++)
        {
            if(mertekek.get(i).getNev().equals(adatbazisMertekTf.getValue().toString()))
            {
                System.out.println("nem egyenlod: " + mertekek.get(i).getNev() + " " + adatbazisMertekTf.getValue().toString());
                System.out.println("TALÁLTAM MÉRTÉKET");
                index=i;
                ujelem.setMertek(mertekek.get(i));
            }
        }
        System.out.println("mertekid: " + index + " " + mertekek.size());
        //Mertek merteke = session.load(Mertek.class, 4);
        //ujelem.setMertek(merteke);

        System.out.println("Elotte hossz: " + korlatozasok.size());

        session.save(ujelem);
        System.out.println(ujelem);
        t.commit();
        session.close();
        Read();
        tableFeltolt(korlatozasok);
        System.out.println("Utana hossz: " + korlatozasok.size());
        System.out.println("Lista hossz: " + korlTable.getItems().size());

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
        korlTable.getItems().clear();
        for(int i=0;i<lista.size();i++)
        {
            korlTable.getItems().add(lista.get(i));
        }
        System.out.println("Lista hossz: " + korlTable.getItems().size());
    }


}
