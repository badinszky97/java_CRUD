package hu.nje.hibernatefxdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class HelloController {
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


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
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