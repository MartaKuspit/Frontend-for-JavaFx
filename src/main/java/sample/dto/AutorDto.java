package sample.dto;


import lombok.Data;

import java.util.List;


@Data
public class AutorDto {
    private long idAutor;
    private String firstName;
    private String lastName;
    private List<MovieDto> movieDtoList;

}
