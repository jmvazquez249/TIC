package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.AvionDTO;

@Component
public class AvionRestService {
    private RestTemplate restTemplate;

    @Autowired
    private AvionRestService(RestTemplate restTemplate){this.restTemplate=restTemplate;}

    public ResponseEntity getAvion(String matricula){
        return restTemplate.postForEntity("http://localhost:8080/avion/getAvion",matricula, AvionDTO.class);
    }



}
