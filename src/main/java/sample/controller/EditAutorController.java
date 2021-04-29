package sample.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.dto.AutorDto;
import sample.rest.AutorRestClient;
import sample.dto.MovieDto;
import sample.rest.MovieRestClient;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditAutorController implements Initializable {
    private AutorRestClient autorRestClient;
    private MovieRestClient movieRestClient;
    private Long idAutor;
    private List<MovieDto> movieList;

    public EditAutorController() {
        autorRestClient = new AutorRestClient();
        movieRestClient = new MovieRestClient();
    }

    @FXML
    private BorderPane editAutorBorderPane;

    @FXML
    private Button editButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        initializeEditButton();

    }

    private void initializeEditButton() {
        editButton.setOnAction((x)->{

            AutorDto autorDto = createAutorDto();
            autorRestClient.saveEditedAutor(autorDto);
            getStage().close();

        });
    }

    private AutorDto createAutorDto() {
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        AutorDto autorDto = new AutorDto();
        autorDto.setIdAutor(idAutor);
        autorDto.setFirstName(firstName);
        autorDto.setLastName(lastName);
        autorDto.setMovieDtoList(movieList);
        return autorDto;
    }
    public void loadAutorData(Long idAutor) {
        Thread thread = new Thread(()->{
            AutorDto autorDto = autorRestClient.getAutor(idAutor);
            this.movieList = movieRestClient.getAllAutorMovies(autorDto.getIdAutor());
            Platform.runLater(()->{
                this.idAutor = idAutor;
                firstNameTextField.setText(autorDto.getFirstName());
                lastNameTextField.setText(autorDto.getLastName());
            });

        });
        thread.start();

    }

    private void initializeCancelButton() {
        cancelButton.setOnAction((x)->{
            getStage().close();

        });
    }
    private Stage getStage(){
        return (Stage) editAutorBorderPane.getScene().getWindow();
    }
}
