package sample.table;

import javafx.beans.property.SimpleStringProperty;
import sample.dto.MovieDto;

public class MovieTableModel {

    private SimpleStringProperty title;
    private SimpleStringProperty productionYear;
    private SimpleStringProperty duration;

    public MovieTableModel(String title, String productionYear, String duration) {
        this.title = new SimpleStringProperty(title);
        this.productionYear = new SimpleStringProperty(productionYear);
        this.duration = new SimpleStringProperty(duration);
    }

    public static MovieTableModel of(MovieDto dto){
        return new MovieTableModel(dto.getTitle(),dto.getProductionYear(),dto.getDuration());
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getProductionYear() {
        return productionYear.get();
    }

    public SimpleStringProperty productionYearProperty() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear.set(productionYear);
    }

    public String getDuration() {
        return duration.get();
    }

    public SimpleStringProperty durationProperty() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration.set(duration);
    }
}
