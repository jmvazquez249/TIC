package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.AvionDTO;

import java.util.List;

@Component
public class AvionRestService {
    private RestTemplate restTemplate;

    @Autowired
    private AvionRestService(RestTemplate restTemplate){this.restTemplate=restTemplate;}

    public ResponseEntity getAvion(String matricula){
        return restTemplate.postForEntity("http://localhost:8080/avion/getAvion",matricula, AvionDTO.class);
    }

    public ResponseEntity getAviones(){
        return restTemplate.postForEntity("http://localhost:8080/avion/getAviones",null, List.class);
    }



}
