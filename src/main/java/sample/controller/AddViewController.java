package sample.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.dto.AutorDto;
import sample.rest.AutorRestClient;

import java.net.URL;

import java.util.ResourceBundle;



public class AddViewController implements Initializable {

    private AutorRestClient autorRestClient;

    public AddViewController() {
        autorRestClient = new  AutorRestClient();
    }

    @FXML
    private BorderPane addAutorBorderPane;
    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeSaveButton();

    }

    private void initializeSaveButton() {
        saveButton.setOnAction((x)->{
            AutorDto autorDto = createAutorDto();
            autorRestClient.saveNewAutor(autorDto);
            getStage().close();

        });
    }


    private AutorDto createAutorDto() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        AutorDto autorDto = new AutorDto();
        autorDto.setFirstName(firstName);
        autorDto.setLastName(lastName);
        return autorDto;
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction((x)->{
            getStage().close();

        });
    }
    private Stage getStage(){
        return (Stage) addAutorBorderPane.getScene().getWindow();
    }
}
