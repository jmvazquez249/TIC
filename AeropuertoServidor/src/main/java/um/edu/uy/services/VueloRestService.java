package um.edu.uy.services;

import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import um.edu.uy.ReservaDTO;
import um.edu.uy.VueloDTO;
import um.edu.uy.business.VueloMapper;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.Vuelo;
import um.edu.uy.persistence.AerolineaRepository;
import um.edu.uy.persistence.AeropuertoRepository;
import um.edu.uy.persistence.VueloRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vuelo")
public class VueloRestService {

    private VueloMapper vueloMapper;
    private VueloRepository vueloRepository;
    private AeropuertoRepository aeropuertoRepository;

    private AerolineaRepository aerolineaRepository;

    @Autowired
    public VueloRestService(VueloMapper vueMapp, VueloRepository vueRepo, AeropuertoRepository aeroRepo, AerolineaRepository aerolRepo){
        this.vueloMapper=vueMapp;
        this.vueloRepository=vueRepo;
        this.aeropuertoRepository=aeroRepo;
        this.aerolineaRepository=aerolRepo;
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
    public List<VueloDTO> getListaVuelosSalida(@RequestBody String codigoAeropuerto){
        Aeropuerto aeropuero = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeropuerto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aeropuero,true,true,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
        }
        return vueloDTOs;
    }

    @PostMapping("/getListaVuelosSinConfirmarLlegada")
    public List<VueloDTO> getListaVuelosSinConfirmarLlegada(@RequestBody String codigoAeropuerto){
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeropuerto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoDestinoAndAceptadoDestinoAndRechadado(aeropuerto,false,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
        }
        return vueloDTOs;
    }

    @PostMapping("/getListaVuelosSinConfirmarSalida")
    public List<VueloDTO> getListaVuelosSinConfirmarSalida(@RequestBody String codigoAeropuerto){
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeropuerto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndRechadado(aeropuerto,false,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
        }
        return vueloDTOs;
    }

    @Transactional
    @PostMapping("/agregar")
    public void agregarVuelo(@RequestBody VueloDTO vueloDTO){
        Vuelo vuelo = vueloMapper.toVuelo(vueloDTO);
        Aerolinea aerol = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(vueloDTO.getCodigoAerolinea());
        List<Vuelo> vuelosAero = aerol.getVuelos();
        vuelosAero.add(vuelo);

        vueloRepository.save(vuelo);
        aerolineaRepository.save(aerol);
    }

    @Transactional
    @PostMapping("/rechazar")
    public void rechazarVuelo(@RequestBody String codigoVuelo){
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(codigoVuelo);
        vuelo.setRechadado(true);
        vueloRepository.save(vuelo);
    }

    @Transactional
    @PostMapping("/aceptarDestino")
    public void aceptarDestino(@RequestBody String codigoVuelo){
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(codigoVuelo);
        vuelo.setAceptadoDestino(true);
        vueloRepository.save(vuelo);
    }

    @Transactional
    @PostMapping("/aceptarOrigen")
    public void aceptarOrigen(@RequestBody String codigoVuelo){
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(codigoVuelo);
        vuelo.setAceptadoOrigen(true);
        vueloRepository.save(vuelo);
    }

    @Transactional
    @PostMapping("/aceptarYReservar")
    public void aceptarYReservar(@RequestBody ReservaDTO reservaDTO){

    }







}

