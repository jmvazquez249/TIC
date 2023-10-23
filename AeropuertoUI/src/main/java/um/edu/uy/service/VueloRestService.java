package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.VueloDTO;

@Component
public class VueloRestService {
    private RestTemplate restTemplate;

    @Autowired
    private VueloRestService(RestTemplate restTemplate){this.restTemplate=restTemplate;}

    public ResponseEntity agregarVuelo(VueloDTO vueloDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/agregar",vueloDTO,VueloDTO.class);
    }

    public ResponseEntity rechazarVuelo(String codigoVuelo){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/rechazar", codigoVuelo,null);
    }

}
