package sample.table;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class AutorTableView {
    private SimpleLongProperty idAutor;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;


    public AutorTableView(long idAutor, String firstName, String lastName) {
        this.idAutor = new SimpleLongProperty(idAutor);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);

    }

    public String getFirstName() {
        return firstName.get();
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

    public long getIdAutor() {
        return idAutor.get();
    }

    public SimpleLongProperty idAutorProperty() {
        return idAutor;
    }

    public void setIdAutor(long idAutor) {
        this.idAutor.set(idAutor);
    }
}
