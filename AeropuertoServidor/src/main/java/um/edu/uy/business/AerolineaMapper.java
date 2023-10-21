package um.edu.uy.business;

import org.springframework.stereotype.Component;
import um.edu.uy.AerolineaDTO;
import um.edu.uy.AvionDTO;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Avion;

@Component
public class AerolineaMapper {

    public Aerolinea toAerolinea(AerolineaDTO aerolineaDTO){
        Aerolinea aerolinea = new Aerolinea(aerolineaDTO.getCodigoIATAAerolinea(),aerolineaDTO.getNombre());
        return aerolinea;
    }

    public Avion toAvion(AvionDTO avionDTO){
        Avion avion = new Avion(avionDTO.getMatricula(), avionDTO.getModelo(), avionDTO.getCapacidad());
        return avion;
    }
}
