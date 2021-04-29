package sample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sample.dto.AutorDto;

import java.util.Arrays;
import java.util.List;

public class AutorRestClient {

    private static final String AUTORS_URL ="http://localhost:8080/list-of-autors";
    private RestTemplate restTemplate;

    public AutorRestClient() {
        restTemplate = new RestTemplate();
    }
    public List<AutorDto> getAllAutors(){
        ResponseEntity<AutorDto[]> autorsResponseEntity = restTemplate.getForEntity(AUTORS_URL, AutorDto[].class);
        return Arrays.asList(autorsResponseEntity.getBody());
    }
    public AutorDto getAutor(long idAutor){
        ResponseEntity<AutorDto> autorResponseEntity = restTemplate.getForEntity(AUTORS_URL + "/" + idAutor, AutorDto.class);
        return autorResponseEntity.getBody();
    }


    public void saveNewAutor(AutorDto autorDto) {
        restTemplate.postForEntity(AUTORS_URL, autorDto, AutorDto.class);
    }
    public void saveEditedAutor(AutorDto autorDto){
        restTemplate.postForEntity("http://localhost:8080/edit-autors", autorDto,AutorDto.class);
    }

    public void deleteAutor(Long idAutor) {
        restTemplate.delete(AUTORS_URL +"/"+ idAutor);

    }


    public List<AutorDto> seekingAutors(String text) {
        ResponseEntity<AutorDto[]> responseAutorEntity = restTemplate.getForEntity("http://localhost:8080/seeking/" + text, AutorDto[].class);
        return Arrays.asList(responseAutorEntity.getBody());
    }
}
