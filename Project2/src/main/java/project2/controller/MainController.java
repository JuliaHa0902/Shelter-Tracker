package project2.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import project2.domain.Animal;
import project2.domain.PetCompany;
import project2.domain.MyBuffer;
import project2.domain.Shelter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class MainController controls the logic in main-view
 * @author Dung Thi Thuy Ha
 */
public class MainController implements Initializable {
    public static final int FAILED = -1;
    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    @FXML
    private Label lbAnimalInfo;

    @FXML
    private TextField tfAnimalId;

    @FXML
    private Label lbResult;

    @FXML
    private ChoiceBox<String> cbShelterSelected;
    private PetCompany petCompany = new PetCompany();

    /**
     * Change to add-animal-view when button "Add Animal" is clicked
     */
    @FXML
    void onAddAnimalClick(ActionEvent event) {
        fxmlLoader = new FXMLLoader(getClass().getResource("/project2/ui/add-animal-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Change to shelter-info-view when clicked. This view will show the chosen shelter's information
     */
    @FXML
    void onGetInfoClick(ActionEvent event) {
        String choice = cbShelterSelected.getValue();
        if (choice != null) {
            MyBuffer.text = choice.substring(0, choice.indexOf(" - "));
            fxmlLoader = new FXMLLoader(getClass().getResource("/project2/ui/shelter-info-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.show();
        } else {
            lbResult.setText("Please pick a shelter!");
        }
    }

    /**
     * return the chosen animal information
     */
    @FXML
    void onSearchAnimalClick(ActionEvent event) {
        Animal animal = petCompany.getAnimalInfo(tfAnimalId.getText());
        if (animal == null) {
            lbAnimalInfo.setText("Invalid animal ID");
        } else {
            Shelter shelter = petCompany.getShelterInfo(animal.getShelterId());
            String shelterName;
            if (shelter == null) {
                shelterName = "Error Loading";
            } else shelterName = shelter.getName();
            String animalInfo = String.format("Animal name: %s (id: %s)\nShelter id: %s   Shelter name: %s\nType: %s   Weight:%.2f   Receipt Date: %s\n",
                    animal.getName(), animal.getAnimalId(), animal.getShelterId(), shelterName, animal.getAnimalType(), animal.getWeight(), animal.getReceiptDate());
            lbAnimalInfo.setText(animalInfo);
        }
    }

    /**
     * export all animal information to resources folder under name shelterExport.json
     */
    @FXML
    void onExportJSONClick(ActionEvent event) {
        int result = petCompany.exportAnimalList("JSON");
        if (result == FAILED) {
            lbResult.setText("Export JSON failed");
        } else {
            lbResult.setText("Export JSON success. Please look for your file under resource folder");
        }
    }

    /**
     * export all animal information to resources folder under name shelterExport.xml
     */
    @FXML
    void onExportXMLClick(ActionEvent event) {
        int result = petCompany.exportAnimalList("XML");
        if (result == FAILED) {
            lbResult.setText("Export XML failed");
        } else {
            lbResult.setText("Export XML success. Please look for your file under resource folder");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] shelterList = petCompany.getShelterList();
        cbShelterSelected.getItems().addAll(shelterList);
    }
}
