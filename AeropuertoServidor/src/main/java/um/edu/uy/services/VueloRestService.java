package um.edu.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import um.edu.uy.VueloDTO;
import um.edu.uy.business.VueloMapper;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.Vuelo;
import um.edu.uy.persistence.AeropuertoRepository;
import um.edu.uy.persistence.VueloRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vuelo")
public class VueloRestService {

    private VueloMapper vueloMapper;
    private VueloRepository vueloRepository;

    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    public VueloRestService(VueloMapper vueMapp, VueloRepository vueRepo, AeropuertoRepository aeroRepo){
        this.vueloMapper=vueMapp;
        this.vueloRepository=vueRepo;
        this.aeropuertoRepository=aeroRepo;
    }

    @PostMapping("/getListaVuelosLlegada")
    public List<VueloDTO> getListaVuelosLlegada(@RequestBody String codigoAeroperto){
        Aeropuerto aeropuero = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeroperto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoDestinoAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aeropuero,true,true,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
        }
        return vueloDTOs;
    }
    @PostMapping("/getListaVuelosSalida")
    public List<VueloDTO> getListaVuelosSalida(@RequestBody String codigoAeroperto){
        Aeropuerto aeropuero = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeroperto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aeropuero,true,true,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
        }
        return vueloDTOs;
    }



}

