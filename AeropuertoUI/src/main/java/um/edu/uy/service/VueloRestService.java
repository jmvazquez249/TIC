package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.AgregarPasajeroDTO;
import um.edu.uy.MaletasDTO;
import um.edu.uy.ReservaDTO;
import um.edu.uy.VueloDTO;

import java.util.List;

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

    public ResponseEntity aceptarDestino(String codigoVuelo){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/aceptarDestino", codigoVuelo, null);
    }

    public ResponseEntity aceptarOrigen(String codigoVuelo){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/aceptarOrigen",codigoVuelo,null);
    }

    public ResponseEntity aceptarYReservar(ReservaDTO reservaDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/aceptarYReservar", reservaDTO,null);
    }
    public ResponseEntity agregarPasajero(AgregarPasajeroDTO pasajeroDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/agregarPasajero", pasajeroDTO,null);
    }

    public ResponseEntity getPasaportes(String codigoVuelo){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getPasaportes",codigoVuelo, List.class);
    }
    public ResponseEntity agregarMaletas(MaletasDTO maletasDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/agregarMaletas",maletasDTO,null);
    }
    public ResponseEntity getPasaportesBoarding(String codigoVuelo){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getPasaportesBoarding",codigoVuelo, List.class);
    }
    public ResponseEntity Boarding(AgregarPasajeroDTO pasajeroDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/Boarding",pasajeroDTO,null);
    }

}
