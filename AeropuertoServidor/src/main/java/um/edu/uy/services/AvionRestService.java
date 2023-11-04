package um.edu.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.edu.uy.AvionDTO;
import um.edu.uy.business.AvionMapper;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.Avion;
import um.edu.uy.persistence.AerolineaRepository;
import um.edu.uy.persistence.AeropuertoRepository;
import um.edu.uy.persistence.AvionRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/avion")
public class AvionRestService {
    private AvionMapper avionMapper;
    private AvionRepository avionRepository;
    private AerolineaRepository aerolineaRepository;

    @Autowired
    public AvionRestService(AvionMapper avionMapper, AvionRepository avionRepository, AerolineaRepository aeroREpo){
        this.avionMapper=avionMapper;
        this.avionRepository=avionRepository;
        this.aerolineaRepository=aeroREpo;
    }
    @PostMapping("/getAvion")
    public AvionDTO getAvion(@RequestBody String matricula){
        Avion avion = avionRepository.findByMatricula(matricula);
        if (avion==null){
            return null;
        }
        return avionMapper.toAvionDTO(avion);
    }

    @PostMapping("getAviones")
    public List<AvionDTO> getAviones(@RequestBody String codigoAerolinea){
        Aerolinea aerolinea = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(codigoAerolinea);
        List<Avion> aviones = aerolinea.getAviones();
        List<AvionDTO> avionDTOS = new ArrayList<>();
        for (int i=0;i< aviones.size();i++){
            avionDTOS.add(avionMapper.toAvionDTO(aviones.get(i)));
        }
        return avionDTOS;

    }
}
