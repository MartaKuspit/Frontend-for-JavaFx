package sample.table;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.dto.AutorDto;
import sample.dto.MovieDto;


import java.util.List;

public class AutorTableModel {
    private SimpleLongProperty idAutor;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private List<MovieDto> movieList;


    public AutorTableModel(long idAutor, String firstName, String lastName, List<MovieDto> movieList) {
        this.idAutor = new SimpleLongProperty(idAutor);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.movieList = movieList;
    }

    public static AutorTableModel of(AutorDto dto){
        return new AutorTableModel(dto.getIdAutor(),dto.getFirstName(),dto.getLastName(), dto.getMovieDtoList());
    }

    public long getIdAutor() {
        return idAutor.get();
    }

    public SimpleLongProperty idAutorProperty() {
        return idAutor;
    }

    public void setIdAutor(long idAutor) {
        this.idAutor.set(idAutor);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public List<MovieDto> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<MovieDto> movieList) {
        this.movieList = movieList;
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
}
