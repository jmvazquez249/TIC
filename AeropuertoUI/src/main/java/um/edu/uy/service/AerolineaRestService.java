package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.AerolineaDTO;
import um.edu.uy.AvionDTO;

@Component
public class AerolineaRestService {

    private RestTemplate restTemplate;

    @Autowired
    private AerolineaRestService(RestTemplate restTemplate){this.restTemplate=restTemplate;}

    public ResponseEntity agregarAerolinea(AerolineaDTO aerolineaDTO){
        return restTemplate.postForEntity("http://localhost:8080/aerolinea/agregar", aerolineaDTO,AerolineaDTO.class);
    }

    public ResponseEntity agregarAvionAerolinea(AvionDTO avionDTO){
        return restTemplate.postForEntity("http://localhost:8080/aerolinea/avion",avionDTO,AvionDTO.class);
    }

}
