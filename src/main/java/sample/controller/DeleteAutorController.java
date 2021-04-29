package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.rest.AutorRestClient;
import sample.table.AutorTableModel;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAutorController implements Initializable {

    private AutorRestClient autorRestClient;

    public DeleteAutorController() {
        autorRestClient = new AutorRestClient();
    }


    @FXML
    private BorderPane deleteAutorBorder;

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    private Long idAutor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeDeleteButton();


    }

    private void initializeDeleteButton() {
        deleteButton.setOnAction((x)->{
            autorRestClient.deleteAutor(idAutor);
            getStage().close();


        });
    }

    public void loadAutorData(AutorTableModel autor) {
        this.idAutor = autor.getIdAutor();
        firstNameLabel.setText(autor.getFirstName());
        lastNameLabel.setText(autor.getLastName());
    }
    private void initializeCancelButton() {
        cancelButton.setOnAction((x)->{
            getStage().close();

        });
    }
    private Stage getStage(){
        return (Stage) deleteAutorBorder.getScene().getWindow();
    }
}


