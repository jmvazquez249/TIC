package um.edu.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.edu.uy.AeropuertoDTO;
import um.edu.uy.business.AeropuertoMapper;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.persistence.AeropuertoRepository;

import java.util.List;

@RestController
@RequestMapping("/aeropuerto")
public class AeropuertoRestService {

    private AeropuertoMapper aeropuertoMapper;

    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    public AeropuertoRestService(AeropuertoMapper aeroMapp , AeropuertoRepository aeroRepo){
        this.aeropuertoMapper=aeroMapp;
        this.aeropuertoRepository=aeroRepo;
    }

    @PostMapping("/agregar")
    public void crearAeropuerto(@RequestBody AeropuertoDTO aeropuertoDTO){
        Aeropuerto aeropuerto = aeropuertoMapper.toAeropuerto(aeropuertoDTO);
        aeropuertoRepository.save(aeropuerto);
    }
    @PostMapping("/getAeropuerto")
    public AeropuertoDTO getAeropuerto(@RequestBody String codigoIATAAeropuerto){
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoIATAAeropuerto);
        return aeropuertoMapper.toAeropuertoDTO(aeropuerto);
    }



}
