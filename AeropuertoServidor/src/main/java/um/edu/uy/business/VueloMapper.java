package um.edu.uy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import um.edu.uy.VueloDTO;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.Avion;
import um.edu.uy.business.entities.Vuelo;
import um.edu.uy.persistence.AerolineaRepository;
import um.edu.uy.persistence.AeropuertoRepository;
import um.edu.uy.persistence.AvionRepository;
import um.edu.uy.persistence.VueloRepository;


@Component
public class VueloMapper {

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    private AvionRepository avionRepository;

    @Autowired
    private AerolineaRepository aerolineaRepository;



    public Vuelo toVuelo(VueloDTO vueloDTO){
        Aeropuerto aeropuertoDestino = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(vueloDTO.getCodigoAeropuertoDestino());
        Aeropuerto aeropuertoOrigen = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(vueloDTO.getCodigoAeropuertoOrigen());
        Avion avion = avionRepository.findByMatricula(vueloDTO.getMatriculaAvion());
        Aerolinea aerolinea = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(vueloDTO.getCodigoAerolinea());
        Vuelo vuelo = new Vuelo(vueloDTO.getCodigoVuelo(),aeropuertoDestino,aeropuertoOrigen,avion,aerolinea, vueloDTO.isAceptadoOrigen(), vueloDTO.isAcepradoDestino(),vueloDTO.getEDT(),vueloDTO.getETA());
        return vuelo;
    }

    public VueloDTO toVueloDTO(Vuelo vuelo){
        VueloDTO vueloDTO = new VueloDTO();
        vueloDTO.setCodigoVuelo(vuelo.getCodigoVuelo());
        vueloDTO.setCodigoAeropuertoDestino(vuelo.getAeropuertoDestino());
        vueloDTO.setCodigoAeropuertoOrigen(vuelo.getAeropuertoOrigen());
        vueloDTO.setMatriculaAvion(vuelo.getAvion());
        vueloDTO.setCodigoAerolinea(vuelo.getAerolinea());
        vueloDTO.setAcepradoDestino(vuelo.isAceptadoDestino());
        vueloDTO.setAceptadoOrigen(vuelo.isAceptadoOrigen());
        vueloDTO.setEDT(vuelo.getEDT());
        vueloDTO.setETA(vuelo.getETA());
        return vueloDTO;
    }

}
