package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.MovieDto;

import java.util.Arrays;
import java.util.List;

public class MovieRestClient {

    private static final String GET_MOVIES_URL ="http://localhost:8080/list-of-movies";
    private RestTemplate restTemplate;

    public MovieRestClient() {
        restTemplate = new RestTemplate();
    }

    public List<MovieDto> getAllAutorMovies(long idAutor){
        ResponseEntity<MovieDto[]> response = restTemplate.getForEntity(GET_MOVIES_URL + "/" + idAutor, MovieDto[].class);
        return Arrays.asList(response.getBody());

    }

    public void saveNewFilm(MovieDto movieDto, long idAutor) {
        restTemplate.postForEntity(GET_MOVIES_URL + "/"+ idAutor, movieDto, MovieDto.class);

    }

}
