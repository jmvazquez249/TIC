package um.edu.uy.services;

import org.hibernate.annotations.ManyToAny;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.uy.AgregarPasajeroDTO;
import um.edu.uy.MaletasDTO;
import um.edu.uy.ReservaDTO;
import um.edu.uy.VueloDTO;
import um.edu.uy.business.VueloMapper;
import um.edu.uy.business.entities.*;
import um.edu.uy.persistence.*;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/vuelo")
public class VueloRestService {

    private VueloMapper vueloMapper;
    private VueloRepository vueloRepository;
    private AeropuertoRepository aeropuertoRepository;
    private AerolineaRepository aerolineaRepository;
    private PuertaRepository puertaRepository;
    private ReservaRepository reservaRepository;
    private AvionRepository avionRepository;
    private AsientoRepository asientoRepository;

    private MaletaRepository maletaRepository;
    @Autowired
    public VueloRestService(VueloMapper vueMapp, VueloRepository vueRepo, AeropuertoRepository aeroRepo, AerolineaRepository aerolRepo, PuertaRepository pueRepo, ReservaRepository resRepo, AvionRepository avionRepo, AsientoRepository asientoRepo, MaletaRepository malRepo){
        this.vueloMapper=vueMapp;
        this.vueloRepository=vueRepo;
        this.aeropuertoRepository=aeroRepo;
        this.aerolineaRepository=aerolRepo;
        this.puertaRepository=pueRepo;
        this.reservaRepository=resRepo;
        this.avionRepository=avionRepo;
        this.asientoRepository=asientoRepo;
        this.maletaRepository=malRepo;
    }

    @PostMapping("/getListaVuelosLlegada")
    public List<VueloDTO> getListaVuelosLlegada(@RequestBody String codigoAeroperto){
        Aeropuerto aeropuero = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeroperto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoDestinoAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aeropuero,true,true,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            if(vuelos.get(i).getETA().isAfter(LocalDateTime.now())){
                vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
            }
        }
        return vueloDTOs;
    }
    @PostMapping("/getListaVuelosSalida")
    public List<VueloDTO> getListaVuelosSalida(@RequestBody String codigoAeropuerto){
        Aeropuerto aeropuero = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeropuerto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aeropuero,true,true,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            if(vuelos.get(i).getEDT().isAfter(LocalDateTime.now())){
                vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
            }

        }
        return vueloDTOs;
    }
    @PostMapping("/getListaVuelosConfirmadosAero")
    public List<VueloDTO> getListaVuelosConfirmadosAero(@RequestBody String codigoAerolinea){
        Aerolinea aerolinea = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(codigoAerolinea);
        List<Vuelo> vuelos = vueloRepository.findAllByAerolineaAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aerolinea,true,true,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            //que solo aparezcan vuelos que su EDT es despues de hoy
            if(vuelos.get(i).getEDT().isAfter(LocalDateTime.now())){
                vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
            }
        }
        return vueloDTOs;
    }


    @PostMapping("/getListaVuelosSinConfirmarLlegada")
    public List<VueloDTO> getListaVuelosSinConfirmarLlegada(@RequestBody String codigoAeropuerto){
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeropuerto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoDestinoAndAceptadoDestinoAndRechadado(aeropuerto,false,false);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            if(vuelos.get(i).getEDT().isAfter(LocalDateTime.now())){
                vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
            }
        }
        return vueloDTOs;
    }

    @PostMapping("/getListaVuelosSinConfirmarSalida")
    public List<VueloDTO> getListaVuelosSinConfirmarSalida(@RequestBody String codigoAeropuerto){
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoAeropuerto);
        List<Vuelo> vuelos = vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndRechadado(aeropuerto,false,false);
        System.out.println(vuelos);
        List<VueloDTO> vueloDTOs = new ArrayList<>();
        for(int i=0; i<vuelos.size();i++){
            if(vuelos.get(i).getEDT().isAfter(LocalDateTime.now())){
                vueloDTOs.add(vueloMapper.toVueloDTO(vuelos.get(i)));
            }

        }
        return vueloDTOs;
    }
    @Transactional
    @PostMapping("/agregarPasajero")
    public ResponseEntity<String> agregarPasajero(@RequestBody AgregarPasajeroDTO pasajeroDTO){
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(pasajeroDTO.getCodigoVuelo());
        List<Asiento> asientos = vuelo.getAsientos();
        for(int i=0;i<asientos.size();i++){
            Asiento asiento = asientos.get(i);
            if (asiento.getPasaporte()==0){
                asiento.setPasaporte(pasajeroDTO.getPasaporte());
                asientoRepository.save(asiento);
                return new ResponseEntity<>("Pasajero agregado", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("No hay asientos disponibles", HttpStatus.BAD_REQUEST);
    }


    @Transactional
    @PostMapping("/agregar")
    public void agregarVuelo(@RequestBody VueloDTO vueloDTO) {
        Vuelo vuelo = vueloMapper.toVuelo(vueloDTO);
        Aerolinea aerol = aerolineaRepository.findAerolineaByCodigoIATAAerolinea(vueloDTO.getCodigoAerolinea());
        String avionAsignado = vuelo.getAvion();

        int capacidadAsientos = avionRepository.findByMatricula(avionAsignado).getCapacidad();
        // Crea una lista de asientos con la misma longitud que la capacidad de asientos del avión.
        List<Asiento> asientos = new ArrayList<>(capacidadAsientos);
        for (int i = 1; i <= capacidadAsientos; i++) {
            Asiento asiento = new Asiento(i);
            asientoRepository.save(asiento);
            asientos.add(asiento);

        }
        vuelo.setAsientos(asientos); // Asigna la lista de asientos al vuelo

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
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(reservaDTO.getCodigoVuelo());
        Aeropuerto aeropuerto;
        if (reservaDTO.isLlegada()){
            vuelo.setAceptadoDestino(true);
            aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(vuelo.getAeropuertoDestino());
            Puerta puerta = puertaRepository.findByIdPuerta(reservaDTO.getNumeroPuerta());
            Pista pista = aeropuerto.getPista();
            List<Reserva> reservasPista = pista.getReservasPista();
            List<Reserva> reservasPuerta = puerta.getReservasPuerta();

            LocalDate fechaLlegada = vuelo.getFechaETA();

            Reserva resPuerta = new Reserva(reservaDTO.getLocalTimeFinPista(),reservaDTO.getLocalTimeFinPuerta(),fechaLlegada,reservaDTO.getCodigoVuelo());
            Reserva resPista = new Reserva(vuelo.getHoraETA(),reservaDTO.getLocalTimeFinPista(),fechaLlegada ,reservaDTO.getCodigoVuelo());
            reservasPuerta.add(resPuerta);
            reservasPista.add(resPista);
            vueloRepository.save(vuelo);
            reservaRepository.save(resPista);
            reservaRepository.save(resPuerta);
            puertaRepository.save(puerta);


        }else{
            vuelo.setAceptadoOrigen(true);
            aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(vuelo.getAeropuertoOrigen());
            Puerta puerta = puertaRepository.findByIdPuerta(reservaDTO.getNumeroPuerta());
            Pista pista = aeropuerto.getPista();
            List<Reserva> reservasPista = pista.getReservasPista();
            List<Reserva> reservasPuerta = puerta.getReservasPuerta();

            LocalDate fechaSalida = vuelo.getFechaEDT();

            Reserva resPuerta = new Reserva(reservaDTO.getLocalTimeFinPuerta(),vuelo.getHoraEDT(),fechaSalida,reservaDTO.getCodigoVuelo());
            Reserva resPista = new Reserva(vuelo.getHoraEDT(),reservaDTO.getLocalTimeFinPista(),fechaSalida, reservaDTO.getCodigoVuelo());
            reservasPuerta.add(resPuerta);
            reservasPista.add(resPista);
            vueloRepository.save(vuelo);
            reservaRepository.save(resPista);
            reservaRepository.save(resPuerta);
            puertaRepository.save(puerta);


        }

    }

    @PostMapping("/getPasaportes")
    public List<Long> getVuelo(@RequestBody String codigoVuelo){
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(codigoVuelo);
        List<Asiento> pasajeros = vuelo.getAsientos();
        List<Long> pasaportes = new ArrayList<>();
        for (int i=0; i< pasajeros.size(); i++){
            Asiento pasajero = pasajeros.get(i);
            Long pas = pasajero.getPasaporte();
            boolean check = pasajero.isCheckIn();
            if(pas!=0 && check==false){
                pasaportes.add(pas);
            }
        }
        return pasaportes;
    }
    @PostMapping("/getPasaportesBoarding")
    public List<Long> getPasaportesBoarding(@RequestBody String codigoVuelo){
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(codigoVuelo);
        List<Asiento> pasajeros = vuelo.getAsientos();
        List<Long> pasaportes = new ArrayList<>();
        for (int i=0; i< pasajeros.size(); i++){
            Asiento pasajero = pasajeros.get(i);
            Long pas = pasajero.getPasaporte();
            boolean check = pasajero.isCheckIn();
            if(pas!=0 && check==true && pasajero.isBoarded()==false){
                pasaportes.add(pas);
            }
        }
        return pasaportes;
    }
    @Transactional
    @PostMapping("/Boarding")
    public void Boarding(@RequestBody AgregarPasajeroDTO pasajeroDTO) {
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(pasajeroDTO.getCodigoVuelo());
        List<Asiento> asiento = vuelo.getAsientos();
        for (int i = 0; i < asiento.size(); i++) {
            Asiento asientos = asiento.get(i);
            if (asientos.getPasaporte() == pasajeroDTO.getPasaporte()) {
                asientos.setBoarded(true);
                asientoRepository.save(asientos);
            }
        }
    }


    @Transactional
    @PostMapping("/agregarMaletas")
    public void agregarMaletas(@RequestBody MaletasDTO maletasDTO){
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(maletasDTO.getCodigoVuelo());
        List<Asiento> asientos = vuelo.getAsientos();
        List<Maleta> maletas =  new ArrayList<>();
        for(int j=0;j< maletasDTO.getCantidadMaletas();j++){
            Maleta maleta = new Maleta();
            maletaRepository.save(maleta);
            maletas.add(maleta);
        }

        for(int i=0;i<asientos.size();i++){
            Asiento asiento = asientos.get(i);
            if (asiento.getPasaporte()== maletasDTO.getPasaporte()){
                asiento.setCheckIn(true);
                asiento.setMaletas(maletas);
                asientoRepository.save(asiento);
            }
        }
    }







}

