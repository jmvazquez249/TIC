package um.edu.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.edu.uy.AvionDTO;
import um.edu.uy.business.AvionMapper;
import um.edu.uy.business.entities.Avion;
import um.edu.uy.persistence.AvionRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/avion")
public class AvionRestService {
    private AvionMapper avionMapper;
    private AvionRepository avionRepository;

    @Autowired
    public AvionRestService(AvionMapper avionMapper, AvionRepository avionRepository){
        this.avionMapper=avionMapper;
        this.avionRepository=avionRepository;
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
    public List<AvionDTO> getAviones(){
        List<Avion> aviones = avionRepository.findAll();
        List<AvionDTO> avionDTOS = new ArrayList<>();
        for (int i=0;i< aviones.size();i++){
            avionDTOS.add(avionMapper.toAvionDTO(aviones.get(i)));
        }
        return avionDTOS;

    }
}
