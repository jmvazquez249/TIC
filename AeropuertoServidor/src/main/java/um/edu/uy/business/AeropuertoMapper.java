package um.edu.uy.business;


import org.springframework.stereotype.Component;
import um.edu.uy.AeropuertoDTO;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.Pista;
import um.edu.uy.business.entities.Puerta;

import java.util.ArrayList;
import java.util.List;

@Component
public class AeropuertoMapper {

    public Aeropuerto toAeropuerto(AeropuertoDTO aeropuertoDTO){
        Aeropuerto aeropuerto = new Aeropuerto(aeropuertoDTO.getCodigoIATAAeropuerto(), aeropuertoDTO.getNombre(), aeropuertoDTO.getCiudad(), aeropuertoDTO.getPais());
        aeropuerto.setPista(new Pista());
        long cantidadPuertas = aeropuertoDTO.getCantidadPuertas();
        List<Puerta> puertaList = new ArrayList<>();
        for (int i=0; i<cantidadPuertas;i++){
            puertaList.add(new Puerta());
        }
        aeropuerto.setPuertas(puertaList);
        return aeropuerto;
    }
    public AeropuertoDTO toAeropuertoDTO(Aeropuerto aeropuerto){
        AeropuertoDTO aeropuertoDTO = new AeropuertoDTO();
        aeropuertoDTO.setCodigoIATAAeropuerto(aeropuerto.getCodigoIATAAeropuerto());
        aeropuertoDTO.setNombre(aeropuerto.getNombre());
        aeropuertoDTO.setCiudad(aeropuerto.getCiudad());
        aeropuertoDTO.setPais(aeropuerto.getPais());
        List<Puerta> puertaList = aeropuerto.getPuertas();
        List<Long> codigoPuertas = new ArrayList<>();
        for (int i=0;i<puertaList.size();i++){
            codigoPuertas.add(puertaList.get(i).getIdPuerta());
        }
        aeropuertoDTO.setPuertas(codigoPuertas);
        return aeropuertoDTO;
    }




}
