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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public static final int FAILED = -1;
    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    @FXML
    private Label lbAnimalInfo;

    @FXML
    private TextField tfAnimalId;

//    @FXML
//    private TextField tfShelterId;

    @FXML
    private Label lbResult;

    @FXML
    private ChoiceBox<String> cbShelterSelected;
    private PetCompany petCompany = new PetCompany();

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

    @FXML
    void onSearchAnimalClick(ActionEvent event) {
        Animal animal = petCompany.getAnimalInfo(tfAnimalId.getText());
        if (animal == null) {
            lbAnimalInfo.setText("Invalid animal ID");
        } else {
            String animalInfo = String.format("Animal name: %s (id: %s)\nShelter id: %s\nType: %s   Weight:%.2f   Receipt Date: %s\n",
                    animal.getName(), animal.getAnimalId(), animal.getShelterId(), animal.getAnimalType(), animal.getWeight(), animal.getReceiptDate());
            lbAnimalInfo.setText(animalInfo);
        }
    }

    @FXML
    void onExportJSONClick(ActionEvent event) {
        int result = petCompany.exportAnimalList("JSON");
        if (result == FAILED) {
            lbResult.setText("Export JSON failed");
        } else {
            lbResult.setText("Export JSON success. Please look for your file under resource folder");
        }
    }

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
