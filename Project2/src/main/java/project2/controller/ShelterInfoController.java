package project2.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import project2.domain.Animal;
import project2.domain.MyBuffer;
import project2.domain.PetCompany;
import project2.domain.Shelter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Class ShelterInfoController controls the logic in shelter-info-view
 * @author Dung Thi Thuy Ha
 */
public class ShelterInfoController implements Initializable {
    @FXML
    private Label lbInTaking;
    @FXML
    private Label lbShelterIInfo;
    @FXML
    private TableView<Animal> tvAnimal;
    @FXML
    private TableColumn<Animal, String> animalId;
    @FXML
    private TableColumn<Animal, String> animalType;
    @FXML
    private TableColumn<Animal, String> name;
    @FXML
    private TableColumn<Animal, Date> receiptDate;
    @FXML
    private TableColumn<Animal, Float> weight;
    @FXML
    private ToggleButton tbInTaking;
    private Shelter shelter;
    private PetCompany petCompany = new PetCompany();


    /**
     * Toggle on/off in taking
     */
    @FXML
    void onInTakingButton(ActionEvent event) {
        if (tbInTaking.isSelected()) {
            lbInTaking.setText("In taking: Enabled");
            petCompany.enableReceivingAnimal(shelter.getId());
        } else {
            lbInTaking.setText("In taking: Disabled");
            petCompany.disableReceivingAnimal(shelter.getId());
        }
    }

    /**
     * return to main-view
     */
    @FXML
    void onBackClick(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/project2/ui/main-view.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shelter = petCompany.getShelterInfo(MyBuffer.text);

        if (shelter == null) {
            lbShelterIInfo.setText("Invalid Shelter ID");
            tbInTaking.setVisible(false);
            tvAnimal.setVisible(false);
        } else {
            initTable();
            initShelterInfo();
        }
    }

    private void initShelterInfo() {
        lbShelterIInfo.setText("Shelter " + shelter.getName() + " (id: " + shelter.getId() + ")");
        if (shelter.getInTaking()) {
            lbInTaking.setText("In Taking: Enable");
            tbInTaking.setSelected(true);
        } else {
            lbInTaking.setText("In Taking: Disable");
            tbInTaking.setSelected(false);
        }
        ArrayList <Animal> animalList = new ArrayList<>(shelter.getAnimalList().values());
        ObservableList <Animal> list = FXCollections.observableList(animalList);
        tvAnimal.setItems(list);
    }

    private void initTable() {
        name.setCellFactory(tc -> {
            TableCell<Animal, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(name.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell ;
        });
        name.setCellValueFactory(new PropertyValueFactory<Animal, String>("name"));
        animalId.setCellValueFactory(new PropertyValueFactory<Animal, String>("animalId"));
        animalType.setCellValueFactory(new PropertyValueFactory<Animal, String>("animalType"));
        receiptDate.setCellValueFactory(new PropertyValueFactory<Animal, Date>("receiptDate"));
        weight.setCellValueFactory(new PropertyValueFactory<Animal, Float>("weight"));
    }
}
