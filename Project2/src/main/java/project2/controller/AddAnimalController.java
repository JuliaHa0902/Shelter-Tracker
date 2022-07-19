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
import project2.domain.PetCompany;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import org.json.simple.parser.ParseException;
import java.util.ResourceBundle;

/**
 * Class AddAnimalController controls the logic in add-animal-view
 * @author Dung Thi Thuy Ha
 */
public class AddAnimalController implements Initializable {
    private Stage stage;
    private Scene scene;
    private FXMLLoader fxmlLoader;

    @FXML
    private ChoiceBox<String> cbType;

    @FXML
    private TextField tfFileName;

    @FXML
    private Label lbResult;

    private String[] typeList = {"XML", "JSON"};


    /**
     * add animal to database
     */
    @FXML
    void onImportClick(ActionEvent event) {
        String type = cbType.getValue();
        if (type != null) {
            PetCompany petCompany = new PetCompany();
            try {
                String errorNote = petCompany.addAnimal(tfFileName.getText(), type);
                lbResult.setText("Import Success!\n" + errorNote);
            } catch (FileNotFoundException e) {
                lbResult.setText("Error Importing: Please put your input file under resources folder with the correct name");
            } catch (IOException e) {
                lbResult.setText("Error Importing: Please put your input file under resources folder with the correct name");
            } catch (ParseException e) {
                lbResult.setText("Please make sure that the file contains correct data type");
            } catch (ClassCastException e) {
                lbResult.setText("Please make sure that the format of the file is similar to the format of the example input file");
            }
        } else {
            lbResult.setText("Please pick the type of file");
        }
    }

    /**
     * return to main-view
     */
    @FXML
    void onBackClick(ActionEvent event) {
        fxmlLoader = new FXMLLoader(getClass().getResource("/project2/ui/main-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
        cbType.getItems().addAll(typeList);
    }
}
