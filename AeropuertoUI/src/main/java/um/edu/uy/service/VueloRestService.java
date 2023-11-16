package um.edu.uy.service;

import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.*;

import java.time.LocalDate;
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

    public ResponseEntity getPasaportes(CheckInDTO checkInDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getPasaportes",checkInDTO, List.class);
    }
    public ResponseEntity agregarMaletas(MaletasDTO maletasDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/agregarMaletas",maletasDTO,null);
    }
    public ResponseEntity getPasaportesBoarding(BoardingDTO boardingDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getPasaportesBoarding",boardingDTO, List.class);
    }
    public ResponseEntity Boarding(AgregarPasajeroDTO pasajeroDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/Boarding",pasajeroDTO,null);
    }

    public ResponseEntity getVueloReserva(ReservaDTO reservaDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getVueloReserva",reservaDTO,List.class);
    }
    public ResponseEntity getVueloMaletero(AgregarMaletasDTO agregarMaletasDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/getVueloMaletero",agregarMaletasDTO,List.class);
    }
    public ResponseEntity subirBajarMaleta(AgregarMaletasDTO agregarMaletasDTO){
        return restTemplate.postForEntity("http://localhost:8080/vuelo/subirBajarMaletas",agregarMaletasDTO,null);
    }

}
