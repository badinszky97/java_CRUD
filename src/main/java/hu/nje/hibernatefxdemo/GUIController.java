package hu.nje.hibernatefxdemo;

import MNB_soap.MNBArfolyamServiceSoap;
import MNB_soap.MNBArfolyamServiceSoapImpl;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.account.AccountSummary;
import com.oanda.v20.primitives.DateTime;
import com.oanda.v20.trade.Trade;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUIController {


    private final OandaConfig oandaConfig = new OandaConfig();
    /**
     * Párhuzamos programozás feladat változói
     */
    @FXML private Label mpLabel;
    @FXML private Label duplampLabel;
    Integer mp = 0;
    Integer duplamp = 0;

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
    @FXML private Button AdatbazisHozzaadButton;
    @FXML private Button AdatbazisModositButton;
    @FXML private Button AdatbazisTorlesButton;

    @FXML private Button AdatbazisTelepulesSzuresButton;
    @FXML private Button AdatbazisMegnevezesSzuresButton;
    @FXML private Button AdatbazisSzamjegySzuresButton;
    @FXML private Button AdatbazisSebessegSzuresButton;

    @FXML private TextField AdatbazisTelepulesSzuresTextField;
    @FXML private ComboBox AdatbazisMegnevezesSzuresComboBox;
    @FXML private RadioButton AdatbazisSzamjegy1;
    @FXML private RadioButton AdatbazisSzamjegy2;
    @FXML private RadioButton AdatbazisSzamjegy3;
    @FXML private RadioButton AdatbazisSzamjegy4;
    @FXML private RadioButton AdatbazisSzamjegy5;
    @FXML private CheckBox AdatbazisSebesseg;

//  Main_layout a menüsornak minden oldalon, a contentArea-ba tölti be a kiválasztott fxml-t.
    @FXML private VBox contentArea;

//  Forex menu változók
    List<oandaAccount> oandaAccountsresult;
    @FXML private TableView<Item> accountDetailTbl;
    @FXML private TableColumn<Item, String> KeyColumn, ValueColumn;
    @FXML private ComboBox<String> devizaParCombo;
    @FXML private TableView<Trade> openPositionTbl;
    @FXML private ComboBox<String> baseCurrencyComboBox, targetCurrencyComboBox, directionComboBox;
    @FXML private TextField amountTextField, closePositionTxt;
    /**
     * Párhuzamos programozás feladat objektumok
     */
    Timeline timelinemp = new Timeline(
            new KeyFrame(Duration.seconds(1),
                    e -> {
                        mpLabel.setText(mp.toString());
                        mp++;
                    }));

    Timeline timelineduplamp = new Timeline(
            new KeyFrame(Duration.seconds(2),
                    e -> {
                        duplampLabel.setText(duplamp.toString());
                        duplamp++;
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

        timelineduplamp.setCycleCount(Timeline.INDEFINITE);
        timelineduplamp.play();

        adatbazisMegnevezesTf.getItems().clear();
        for(Megnevezes egy : megnevezesek)
        {
            adatbazisMegnevezesTf.getItems().add(egy.toString());
            AdatbazisMegnevezesSzuresComboBox.getItems().add(egy.toString());
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
        AdatbazisTorlesButton.setDisable(false);
        AdatbazisModositButton.setDisable(false);
        AdatbazisHozzaadButton.setDisable(false);
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
        AdatbazisTorlesButton.setDisable(true);
        AdatbazisModositButton.setDisable(true);

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
        AdatbazisTorlesButton.setDisable(true);
        AdatbazisModositButton.setDisable(true);
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
        AdatbazisHozzaadButton.setDisable(false);
        AdatbazisTorlesButton.setDisable(false);
        AdatbazisModositButton.setDisable(false);
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
        AdatbazisTorlesButton.setDisable(true);
        AdatbazisModositButton.setDisable(true);
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

    @FXML
    public void AdatbazisSzuresTelepules()
    {
        if(AdatbazisTelepulesSzuresTextField.getText().equals(""))
        {
            tableFeltolt(korlatozasok);
            return;
        }
        List<Korlatozas> ujlista = new ArrayList<>();
        for(int i=0;i<korlatozasok.size();i++)
        {
            if(korlatozasok.get(i).getTelepules().equals(AdatbazisTelepulesSzuresTextField.getText()))
            {
                ujlista.add(korlatozasok.get(i));
            }
        }
        tableFeltolt(ujlista);
    }
    @FXML
    public void AdatbazisSzuresMegnevezes()
    {

        List<Korlatozas> ujlista = new ArrayList<>();
        for(int i=0;i<korlatozasok.size();i++)
        {
            if(korlatozasok.get(i).getMegnevezes().getNev().equals(AdatbazisMegnevezesSzuresComboBox.getValue()))
            {
                ujlista.add(korlatozasok.get(i));
            }
        }
        tableFeltolt(ujlista);
    }
    @FXML
    public void AdatbazisSzuresSzamjegy()
    {
        List<Korlatozas> ujlista = new ArrayList<>();
        if(AdatbazisSzamjegy1.isSelected())
        {
            for(int i=0;i<korlatozasok.size();i++)
            {
                if(Integer.valueOf(korlatozasok.get(i).getUtszam()) > 0 && Integer.valueOf(korlatozasok.get(i).getUtszam()) < 10)
                {
                    ujlista.add(korlatozasok.get(i));
                }
            }
            tableFeltolt(ujlista);
            return;
        }

        if(AdatbazisSzamjegy2.isSelected())
        {
            for(int i=0;i<korlatozasok.size();i++)
            {
                if(Integer.valueOf(korlatozasok.get(i).getUtszam()) > 9 && Integer.valueOf(korlatozasok.get(i).getUtszam()) < 100)
                {
                    ujlista.add(korlatozasok.get(i));
                }
            }
            tableFeltolt(ujlista);
            return;
        }

        if(AdatbazisSzamjegy3.isSelected())
        {
            for(int i=0;i<korlatozasok.size();i++)
            {
                if(Integer.valueOf(korlatozasok.get(i).getUtszam()) > 99 && Integer.valueOf(korlatozasok.get(i).getUtszam()) < 1000)
                {
                    ujlista.add(korlatozasok.get(i));
                }
            }
            tableFeltolt(ujlista);
            return;
        }

        if(AdatbazisSzamjegy4.isSelected())
        {
            for(int i=0;i<korlatozasok.size();i++)
            {
                if(Integer.valueOf(korlatozasok.get(i).getUtszam()) > 999 && Integer.valueOf(korlatozasok.get(i).getUtszam()) < 10000)
                {
                    ujlista.add(korlatozasok.get(i));
                }
            }
            tableFeltolt(ujlista);
            return;
        }

        if(AdatbazisSzamjegy5.isSelected())
        {
            for(int i=0;i<korlatozasok.size();i++)
            {
                if(Integer.valueOf(korlatozasok.get(i).getUtszam()) > 9999 && Integer.valueOf(korlatozasok.get(i).getUtszam()) < 100000)
                {
                    ujlista.add(korlatozasok.get(i));
                }
            }
            tableFeltolt(ujlista);
            return;
        }
    }
    @FXML
    public void AdatbazisSzuresSebesseg()
    {
        List<Korlatozas> ujlista = new ArrayList<>();
        for(int i=0;i<korlatozasok.size();i++)
        {
            if(AdatbazisSebesseg.isSelected())
            {
                if(korlatozasok.get(i).getSebesseg() == 0)
                {
                    ujlista.add(korlatozasok.get(i));
                }
            }
            else
            {
                if(korlatozasok.get(i).getSebesseg() != 0)
                {
                    ujlista.add(korlatozasok.get(i));
                }
            }

        }
        tableFeltolt(ujlista);
    }

    @FXML
    public void soapLetoltesAll() {
        new Thread(() -> {
            System.out.println("soapLetoltesAll");
            MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
            MNBArfolyamServiceSoap service = impl.getCustomBindingMNBArfolyamServiceSoap();
            try {
                System.out.println(service.getInfo());
                System.out.println(service.getCurrentExchangeRates());
                System.out.println(service.getExchangeRates("2022-08-14", "2022-09-14", "EUR"));
            } catch (Exception e) {
                System.err.print(e.toString());
            }
        }).start();
    }

//    *************** Forex menu Actions *****************
    //    Lekéri az account adatokat és betölti a táblába
    @FXML
    public void forexGetSzamlaInfo() {
        try {
            KeyColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            ValueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
            accountDetailTbl.setItems(FXCollections.observableArrayList(oandaConfig.accountInformation()));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    public void forexGetActualPrice()   {
        if (devizaParCombo.getValue() != null) {
            try {
                List<String> instruments = new ArrayList<>(Arrays.asList(devizaParCombo.getValue().toString()));
                KeyColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
                ValueColumn.setCellValueFactory(cellData -> cellData.getValue().valueProperty());
                accountDetailTbl.setItems(FXCollections.observableArrayList(oandaConfig.getActualPrice(instruments)));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else    {
            showErrorDialog("Nincs kiválasztva devizapár!");
        }
    }
    @FXML
    private void oandaOpenPos()    {
        final String baseCurrency = baseCurrencyComboBox.getValue();
        final String targetCurrency = targetCurrencyComboBox.getValue();
        final String amount = amountTextField.getText();
        final String direction = directionComboBox.getValue();
        if (baseCurrency == null) {
            showErrorDialog("Nem választottál ki alapdevizát!");
            return;
        }
        if (targetCurrency == null) {
            showErrorDialog("Nem választottál ki céldevizát!");
            return;
        }
        if (amount == null || !amount.matches("\\d+(\\.\\d+)?")) {
            showErrorDialog("A mennyiségnek csak számokat adj meg!");
            return;
        }
        if (direction == null) {
            showErrorDialog("Kötelező directiont megadni!");
            return;
        }

        final String instrument = baseCurrency + "_" + targetCurrency;
        oandaConfig.setOpenPosition(instrument, Double.parseDouble(amount));
        showInfoDialog("Pozició nyitás sikeres!");
    }

    @FXML
    private void oandaClosePos(ActionEvent actionEvent) {
        final String positionId = closePositionTxt.getText();
        if (positionId == null) {
            showErrorDialog("Nem adtál meg ID-t!");
            return;
        }
        showErrorDialog("Pozició zárva!");
        oandaConfig.setClosePosition(positionId);
        closePositionTxt.setText("");
    }
    @FXML
    private void oandaOpenPositions() {
        openPositionTbl.setItems(FXCollections.observableArrayList(oandaConfig.getOpenPositions()));
    }

    ///// *************** View váltások *****************
    @FXML
    public void loadHelloView() throws IOException {
        loadView("views/adatbazis/hello-view.fxml");
        Init();
    }
    @FXML
    public void loadSoapLetoltesView() throws IOException { loadView("views/soap/soap-letoltes.fxml"); }
    @FXML
    public void loadForexView() throws IOException { loadView("views/forex/forex-szamlainfo.fxml"); }
    @FXML
    public void loadForexActualPriceView() throws IOException {

        loadView("views/forex/forex-aktualisarak.fxml");
        String[] devizaParok = {"EUR_USD", "USD_JPY", "GBP_USD", "USD_CHF"};
        devizaParCombo.getItems().clear();
        for (String devizaPar : devizaParok) {
            devizaParCombo.getItems().add(devizaPar);
        }
    }
    @FXML
    public void loadForexNyitottPozView() throws IOException { loadView("views/forex/forex-nyitottpoz.fxml"); }
    @FXML
    public void loadForexPozNyitasView() throws IOException {
        loadView("views/forex/forex-poznyitas.fxml");
        String[] directions = {"Eladás", "Vétel"};
        String[] CurrencyCodes = {"EUR", "USD", "JPY", "GBP", "CHF", "HUF"};
        directionComboBox.getItems().clear();
        for (String direction : directions) {
            directionComboBox.getItems().add(direction);
        }
        baseCurrencyComboBox.getItems().clear();
        targetCurrencyComboBox.getItems().clear();
        for (String currency : CurrencyCodes) {
            baseCurrencyComboBox.getItems().add(currency);
            targetCurrencyComboBox.getItems().add(currency);
        }

    }
    @FXML
    public void loadForexPozZarasView() throws IOException {

        loadView("views/forex/forex-pozzaras.fxml");
    }


//    Betölti a paraméterként átadott fxml fájlt a main_layout fájlba.
    public void loadView(String fxmlFile) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));

//      Ugyanazt a GUIControllert használja mindegyik subView, szebb lenne a külön oldalakat külön controllerbe tenni de most igy maradt.
        loader.setController(this);
        Node view = loader.load();
        contentArea.getChildren().setAll(view);
    }

    ///// *************** View váltások VÉGE *****************
    @FXML
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void showInfoDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("OK");
        alert.setHeaderText("");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
