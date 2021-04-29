package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.dto.AutorDto;
import sample.dto.MovieDto;
import sample.rest.AutorRestClient;
import sample.rest.MovieRestClient;
import sample.table.AutorTableModel;
import sample.table.AutorTableView;
import sample.table.MovieTableModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TableViewController implements Initializable {

    private MovieRestClient movieRestClient;
    private AutorRestClient autorRestClient;
    ObservableList<AutorTableModel> autorData;
    ObservableList<MovieTableModel> movieData;

    public TableViewController() {
        autorData = FXCollections.observableArrayList();
        movieData = FXCollections.observableArrayList();
        movieRestClient = new MovieRestClient();
        autorRestClient = new AutorRestClient();
    }

    @FXML
    private Pane tableViewPane;

    @FXML
    private Button addingButton;

    @FXML
    private Button addingFilm;

    @FXML
    private Button editButton;

    @FXML
    private Button deletingButton;

    @FXML
    private Button refreshButton;

    @FXML
    private TableView<AutorTableModel> autorTable;
    @FXML
    private TableColumn<AutorTableView, Long> idAutor;

    @FXML
    private TableColumn<AutorTableView, String> firstName;

    @FXML
    private TableColumn<AutorTableView, String> lastName;

    @FXML
    private TableView<MovieTableModel> movieTable;

    @FXML
    private TableColumn<MovieTableModel, String> title;

    @FXML
    private TableColumn<MovieTableModel, String> productionYear;

    @FXML
    private TableColumn<MovieTableModel, String> duration;


    @FXML
    private TextField textField;


    public void initialize(URL location, ResourceBundle resources) {
        initializeAddingButton();
        initializeRefreshButton();
        initializeAutorTableView();
        initializeMovieTableView();
        onClick();
        initializeAddingFilmButton();
        initializeDeletingButton();
        initializeTextField();
        initializeEditButton();

    }

    private void initializeEditButton() {
        editButton.setOnAction((x)->{
            AutorTableModel selectedAutor = autorTable.getSelectionModel().getSelectedItem();
            if(selectedAutor != null){

                try {
                    Stage editAutorStage = createAutorCrudStage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/edit-autor.fxml"));
                    Scene scene = new Scene(loader.load(),600,400);
                    editAutorStage.setScene(scene);
                    EditAutorController controller = loader.getController();
                    controller.loadAutorData(selectedAutor.getIdAutor());
                    editAutorStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }

    private void initializeTextField() {

        textField.textProperty().addListener((observable, oldValue, newValue)->{
            if(newValue== null || newValue.isEmpty()){
                loadAutorData();
                autorTable.setItems(autorData);

            }else {
                filterAutorlist(newValue);
            }

        });
//
//        FilteredList<AutorTableModel> filteredList = new FilteredList<>(autorData, b->true);
//        textField.textProperty().addListener((observable, oldValue, newValue)->{
//            filteredList.setPredicate(autor ->{
//                if(newValue == null || newValue.isEmpty()){
//                    return true;
//                }
//                String lowerCaseFilter = newValue.toLowerCase();
//                if(autor.getFirstName().toLowerCase().indexOf(lowerCaseFilter) != -1){
//                    return true;
//                } else if(autor.getLastName().toLowerCase().indexOf(lowerCaseFilter) != -1){
//                    return true;
//                } else {
//                    return false;
//                }
//
//            });
//        });
//        SortedList<AutorTableModel> sortedList = new SortedList<>(filteredList);
//        sortedList.comparatorProperty().bind(autorTable.comparatorProperty());
//        autorTable.setItems(sortedList);

    }

    private void filterAutorlist(String text) {
        List<AutorDto> list = autorRestClient.seekingAutors(text);
        autorData.clear();
        autorData.addAll(list.stream().map(AutorTableModel::of).collect(Collectors.toList()));
        autorTable.setItems(autorData);
    }

    private void initializeDeletingButton() {
        deletingButton.setOnAction((x)->{
            AutorTableModel selectedAutor = autorTable.getSelectionModel().getSelectedItem();
            if(selectedAutor != null){

                try {
                    Stage deleteAutorStage = createAutorCrudStage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/delete-autor.fxml"));
                    Scene scene = new Scene(loader.load(),400,200);
                    deleteAutorStage.setScene(scene);
                    DeleteAutorController controller = loader.getController();
                    controller.loadAutorData(selectedAutor);
                    deleteAutorStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private Stage createAutorCrudStage(){
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;

    }

    private void initializeRefreshButton() {
        refreshButton.setOnAction((x) -> {
            movieData.clear();
            autorData.clear();
            loadAutorData();
        });
    }

    private void initializeMovieTableView() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        productionYear.setCellValueFactory(new PropertyValueFactory<>("productionYear"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
    }

    private void initializeAutorTableView() {
        idAutor.setCellValueFactory(new PropertyValueFactory<>("idAutor"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        loadAutorData();
        autorTable.setItems(autorData);

    }

    private void onClick() {
        autorTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showMovieList(newValue));
    }

    private void showMovieList(AutorTableModel autor) {
        movieData.clear();
        Thread thread = new Thread(() -> {
            long idAutor = autor.getIdAutor();
            List<MovieDto> allAutorMovies = movieRestClient.getAllAutorMovies(idAutor);
            movieData.addAll(allAutorMovies.stream().map(MovieTableModel::of).collect(Collectors.toList()));
            movieTable.setItems(movieData);
        });
        thread.start();

    }
    public void loadAutorData() {
        Thread thread = new Thread(() -> {
            List<AutorDto> autors = autorRestClient.getAllAutors();
            autorData.clear();
            autorData.addAll(autors.stream().map(AutorTableModel::of).collect(Collectors.toList()));
        });
        thread.start();
    }

    private void initializeAddingButton() {

        addingButton.setOnAction((x) -> {
            Stage addAutorStage = new Stage();
            addAutorStage.initStyle(StageStyle.UNDECORATED);
            addAutorStage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent addAutorParent = FXMLLoader.load(getClass().getResource("/fxml/addView.fxml"));
                Scene scene = new Scene(addAutorParent);
                addAutorStage.setScene(scene);
                addAutorStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    private void initializeAddingFilmButton() {
        addingFilm.setOnAction((x) -> {
            AutorTableModel selectedAutor = autorTable.getSelectionModel().getSelectedItem();
            if (selectedAutor != null) {

                try {
                    Stage addingFilm = createAutorCrudStage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addFilmView.fxml"));
                    Scene scene = new Scene(loader.load());
                    addingFilm.setScene(scene);
                    AddFilmViewController controller = loader.getController();
                    controller.loadAutorData(selectedAutor);
                    addingFilm.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }



}
