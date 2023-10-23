package um.edu.uy.business;


import org.springframework.stereotype.Component;
import um.edu.uy.AeropuertoDTO;
import um.edu.uy.business.entities.Aeropuerto;

@Component
public class AeropuertoMapper {

    public Aeropuerto toAeropuerto(AeropuertoDTO aeropuertoDTO){
        Aeropuerto aeropuerto = new Aeropuerto(aeropuertoDTO.getCodigoIATAAeropuerto(), aeropuertoDTO.getNombre(), aeropuertoDTO.getCiudad(), aeropuertoDTO.getPais());
        return aeropuerto;
    }
    public AeropuertoDTO toAeropuertoDTO(Aeropuerto aeropuerto){
        AeropuertoDTO aeropuertoDTO = new AeropuertoDTO();
        aeropuertoDTO.setCodigoIATAAeropuerto(aeropuerto.getCodigoIATAAeropuerto());
        aeropuertoDTO.setNombre(aeropuerto.getNombre());
        aeropuertoDTO.setCiudad(aeropuerto.getCiudad());
        aeropuertoDTO.setPais(aeropuerto.getPais());
        return aeropuertoDTO;
    }




}
