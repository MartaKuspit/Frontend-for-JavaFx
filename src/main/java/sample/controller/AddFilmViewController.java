package sample.controller;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.table.AutorTableModel;
import sample.dto.MovieDto;
import sample.rest.MovieRestClient;

import java.net.URL;
import java.util.ResourceBundle;

public class AddFilmViewController implements Initializable {
    private MovieRestClient movieRestClient;

    public AddFilmViewController() {
        movieRestClient = new MovieRestClient();
    }

    @FXML
    private BorderPane addFilmBorderPane;

    @FXML
    private TextField title;

    @FXML
    private TextField productionYear;

    @FXML
    private TextField duration;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private Long idAutor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeCancelButton();
        saveButtonAction();
    }

    public void loadAutorData(AutorTableModel autor) {
        this.idAutor = autor.getIdAutor();
    }

    public void saveButtonAction() {
        saveButton.setOnAction((x)->{

            MovieDto movieDto = createMovieDto();
            movieRestClient.saveNewFilm(movieDto,idAutor);
            getStage().close();
        });
    }

    private MovieDto createMovieDto() {
        String titleText = title.getText();
        String productionY = productionYear.getText();
        String dur = duration.getText();
        MovieDto movieDto = new MovieDto();
        movieDto.setTitle(titleText);
        movieDto.setProductionYear(productionY);
        movieDto.setDuration(dur);
        return movieDto;
    }

    private void initializeCancelButton() {
        cancelButton.setOnAction((x)->{
            getStage().close();

        });
    }
    private Stage getStage(){
        return (Stage) addFilmBorderPane.getScene().getWindow();
    }

}

