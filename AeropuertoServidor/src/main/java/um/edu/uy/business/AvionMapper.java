package um.edu.uy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import um.edu.uy.AvionDTO;
import um.edu.uy.business.entities.Avion;

@Component
public class AvionMapper {

        public Avion toAvion(AvionDTO avionDTO){
            Avion avion = new Avion(avionDTO.getMatricula(), avionDTO.getModelo(), avionDTO.getCapacidad(), avionDTO.getCapacidadBulto());
            return avion;
        }
        public AvionDTO toAvionDTO(Avion avion){
            AvionDTO avionDTO = new AvionDTO();
            avionDTO.setMatricula(avion.getMatricula());
            avionDTO.setModelo(avion.getModelo());
            avionDTO.setCapacidad(avion.getCapacidad());
            avionDTO.setCapacidadBulto(avion.getCapacidadBulto());
            return avionDTO;
        }

}
