package um.edu.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import um.edu.uy.AerolineaDTO;
import um.edu.uy.AvionDTO;
import um.edu.uy.VueloDTO;
import um.edu.uy.business.AerolineaMapper;
import um.edu.uy.business.AeropuertoMapper;
import um.edu.uy.business.VueloMapper;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Avion;
import um.edu.uy.business.entities.Vuelo;
import um.edu.uy.persistence.AerolineaRepository;
import um.edu.uy.persistence.AeropuertoRepository;
import um.edu.uy.persistence.AvionRepository;
import um.edu.uy.persistence.VueloRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/aerolinea")

public class AerolineaRestService {

    private VueloMapper vueloMapper;
    private AerolineaMapper aerolineaMapper;

    private AerolineaRepository aerolineaRepository;

    private AvionRepository avionRepository;

    private VueloRepository vueloRepository;

    @Autowired
    public AerolineaRestService(AerolineaMapper aerMapp ,VueloMapper vueMapp, AerolineaRepository aerRepo, AvionRepository avRepo, VueloRepository vueRepo){
        this.aerolineaMapper=aerMapp;
        this.vueloMapper=vueMapp;
        this.aerolineaRepository=aerRepo;
        this.avionRepository=avRepo;
        this.vueloRepository=vueRepo;
    }

    @PostMapping("/agregar")
    public void crearAerlinea(@RequestBody AerolineaDTO aerolineaDTO){
        Aerolinea aerolinea = aerolineaMapper.toAerolinea(aerolineaDTO);
        aerolineaRepository.save(aerolinea);
    }

    @Transactional
    @PostMapping("/avion")
    public void agregarAvion(@RequestBody AvionDTO avionDTO){
        Aerolinea aerolinea = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(avionDTO.getCodigoAeroliena());
        List<Avion> aviones = aerolinea.getAviones();
        Avion avion = aerolineaMapper.toAvion(avionDTO);
        aviones.add(avion);

        avionRepository.save(avion);
        aerolineaRepository.save(aerolinea);
    }
    @PostMapping("/getAerolinea")
    public AerolineaDTO getAerolinea(@RequestBody String codigoIATAAerolinea){
        Aerolinea aerolinea = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(codigoIATAAerolinea);
        if (aerolinea==null){
            return null;
        }
        return aerolineaMapper.toAerolineaDTO(aerolinea);
    }
    @PostMapping("/getAerolineaICAO")
    public AerolineaDTO getAerolineaICAO(@RequestBody String codigoICAOAerolinea){
        Aerolinea aerolinea = aerolineaRepository.findAerolineaByCodigoICAO(codigoICAOAerolinea);
        if (aerolinea==null){
            return null;
        }
        return aerolineaMapper.toAerolineaDTO(aerolinea);
    }

    @PostMapping("/getVuelosAerolinea")
    public List<VueloDTO> getVuelosAerolinea(@RequestBody String codigoAerolinea){
        Aerolinea aerolinea = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(codigoAerolinea);
        List<Vuelo> vuelos = vueloRepository.findAllByAerolinea(aerolinea);
        List<VueloDTO> vueloDTOS = new ArrayList<>();
        for(int i=0;i< vuelos.size();i++){
            Vuelo vuelo = vuelos.get(i);
            VueloDTO vueloDTO = vueloMapper.toVueloDTO(vuelo);
            vueloDTOS.add(vueloDTO);
        }
        return vueloDTOS;
    }

}
