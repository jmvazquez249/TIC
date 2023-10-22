package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.AeropuertoDTO;
import um.edu.uy.VueloDTO;

import java.util.List;

@Component
public class AeropuertoRestService {

    private RestTemplate restTemplate;

    @Autowired
    private AeropuertoRestService(RestTemplate restTemplate){this.restTemplate=restTemplate;}

    public ResponseEntity agregarAeropuerto(AeropuertoDTO aeropuertoDTO){
        return restTemplate.postForEntity("http://localhost:8080/aeropuerto/agregar",aeropuertoDTO,AeropuertoDTO.class);
    }

    public ResponseEntity getListaAeropuertosLlegada(String codigoAeropuerto){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getListaVuelosLlegada",codigoAeropuerto, List.class);
    }
    public ResponseEntity getListaAeropuertosSalida(String codigoAeropuerto){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getListaVuelosSalida",codigoAeropuerto, List.class);
    }
}
