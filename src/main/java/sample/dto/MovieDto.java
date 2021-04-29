package sample.dto;

import lombok.Data;

@Data
public class MovieDto {
    private Long idMovie;
    private String title;
    private String productionYear;
    private String duration;
}
