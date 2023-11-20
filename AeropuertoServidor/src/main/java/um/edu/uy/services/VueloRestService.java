package um.edu.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import um.edu.uy.*;
import um.edu.uy.business.Exceptions.ConflictoCodgoVuelo;
import um.edu.uy.business.Exceptions.ErrorMaleteria;
import um.edu.uy.business.Exceptions.ErrorReservaVuelo;
import um.edu.uy.business.Exceptions.NoExisteVuelo;
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
    public void aceptarYReservar(@RequestBody ReservaDTO reservaDTO) throws ErrorReservaVuelo {
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(reservaDTO.getCodigoVuelo());
        Aeropuerto aeropuerto;
        if (reservaDTO.isLlegada()){
            if(reservaDTO.getLocalTimeFinPista().isAfter(reservaDTO.getLocalTimeFinPuerta())||vuelo.getHoraETA().isAfter(reservaDTO.getLocalTimeFinPista())){
                throw new ErrorReservaVuelo();
            }

            aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(vuelo.getAeropuertoDestino());
            Pista pista = aeropuerto.getPista();
            Puerta puerta = puertaRepository.findByIdPuerta(reservaDTO.getNumeroPuerta());


            LocalDate fechaLlegada = vuelo.getFechaETA();
            List<Vuelo> vuelosLlegada = vueloRepository.findAllByFechaETAAndAeropuertoDestinoAndAceptadoDestinoAndAceptadoOrigen(fechaLlegada,aeropuerto,true,true);
            for (int i = 0; i < vuelosLlegada.size(); i++) {
                Vuelo vue = vuelosLlegada.get(i);
                if(vue.getReservaLlegada()!=null){
                    Reserva resLle = vue.getReservaLlegada();
                    if (resLle.getPuerta().getIdPuerta() == reservaDTO.getNumeroPuerta()) {
                        if (reservaDTO.getLocalTimeFinPista().isBefore(resLle.getHoraFinalizacionPuerta()) && reservaDTO.getLocalTimeFinPuerta().isAfter(resLle.getHoraFinalizacionPuerta())) {
                            throw new ErrorReservaVuelo();
                        } else if (reservaDTO.getLocalTimeFinPuerta().isBefore(resLle.getHoraFinalizacionPuerta()) && reservaDTO.getLocalTimeFinPista().isAfter(resLle.getHoraInicioPuerta())) {
                            throw new ErrorReservaVuelo();
                        } else if (reservaDTO.getLocalTimeFinPuerta().isAfter(resLle.getHoraInicioPuerta()) && reservaDTO.getLocalTimeFinPista().isBefore(resLle.getHoraInicioPuerta())) {
                            throw new ErrorReservaVuelo();
                        } else if (reservaDTO.getLocalTimeFinPuerta().isAfter(resLle.getHoraFinalizacionPuerta()) && reservaDTO.getLocalTimeFinPista().isBefore(resLle.getHoraInicioPuerta())) {
                            throw new ErrorReservaVuelo();
                        }
                    } else if (reservaDTO.getLocalTimeFinPista().isAfter(resLle.getHoraFinalizacionPista()) && vuelo.getHoraETA().isBefore(resLle.getHoraFinalizacionPista())) {
                        throw new ErrorReservaVuelo();
                    } else if (reservaDTO.getLocalTimeFinPista().isBefore(resLle.getHoraFinalizacionPista()) && vuelo.getHoraETA().isAfter(resLle.getHoraInicioPista())) {
                        throw new ErrorReservaVuelo();
                    } else if (reservaDTO.getLocalTimeFinPista().isAfter(resLle.getHoraInicioPista()) && vuelo.getHoraETA().isBefore(resLle.getHoraInicioPista())) {
                        throw new ErrorReservaVuelo();
                    } else if (reservaDTO.getLocalTimeFinPista().isAfter(resLle.getHoraFinalizacionPista()) && vuelo.getHoraETA().isBefore(resLle.getHoraInicioPista())) {
                        throw new ErrorReservaVuelo();
                    }
                }
            }

            Reserva reservaLlegada = new Reserva(pista,puerta,fechaLlegada,reservaDTO.getLocalTimeFinPista(),reservaDTO.getLocalTimeFinPuerta(),vuelo.getHoraETA(),reservaDTO.getLocalTimeFinPista());
            vuelo.setAceptadoDestino(true);
            if(vuelo.isAceptadoOrigen()){
                String avionAsignado = vuelo.getAvion();

                int capacidadAsientos = avionRepository.findByMatricula(avionAsignado).getCapacidad();
                List<Asiento> asientos = new ArrayList<>(capacidadAsientos);
                for (int i = 1; i <= capacidadAsientos; i++) {
                    Asiento asiento = new Asiento(i);
                    asientoRepository.save(asiento);
                    asientos.add(asiento);

                }
                vuelo.setAsientos(asientos);
            }

            vuelo.setReservaLlegada(reservaLlegada);
            reservaRepository.save(reservaLlegada);
            vueloRepository.save(vuelo);


        }else{
            if(reservaDTO.getLocalTimeFinPuerta().isAfter(vuelo.getHoraEDT())||vuelo.getHoraEDT().isAfter(reservaDTO.getLocalTimeFinPista())){
                throw new ErrorReservaVuelo();
            }
            aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(vuelo.getAeropuertoOrigen());
            Puerta puerta = puertaRepository.findByIdPuerta(reservaDTO.getNumeroPuerta());
            Pista pista = aeropuerto.getPista();

            LocalDate fechaSalida = vuelo.getFechaEDT();
            List<Vuelo> vuelosSalida = vueloRepository.findAllByFechaEDTAndAeropuertoOrigenAndAceptadoDestinoAndAceptadoOrigen(fechaSalida,aeropuerto,true,true);
            for (int i=0;i< vuelosSalida.size();i++){
                Vuelo vue = vuelosSalida.get(i);
                if(vue.getReservaSalida()!=null) {
                    Reserva resSal = vue.getReservaSalida();
                    if (resSal.getPuerta().getIdPuerta() == reservaDTO.getNumeroPuerta()) {
                        if (vuelo.getHoraEDT().isAfter(resSal.getHoraFinalizacionPuerta()) && reservaDTO.getLocalTimeFinPuerta().isBefore(resSal.getHoraFinalizacionPuerta())) {
                            throw new ErrorReservaVuelo();
                        } else if (vuelo.getHoraEDT().isBefore(resSal.getHoraFinalizacionPuerta()) && reservaDTO.getLocalTimeFinPuerta().isAfter(resSal.getHoraInicioPuerta())) {
                            throw new ErrorReservaVuelo();
                        } else if (vuelo.getHoraEDT().isAfter(resSal.getHoraInicioPuerta()) && reservaDTO.getLocalTimeFinPuerta().isBefore(resSal.getHoraInicioPuerta())) {
                            throw new ErrorReservaVuelo();
                        } else if (vuelo.getHoraEDT().isAfter(resSal.getHoraFinalizacionPuerta()) && reservaDTO.getLocalTimeFinPuerta().isBefore(resSal.getHoraInicioPuerta())) {
                            throw new ErrorReservaVuelo();
                        }
                    } else if (reservaDTO.getLocalTimeFinPista().isAfter(resSal.getHoraFinalizacionPista()) && vuelo.getHoraEDT().isBefore(resSal.getHoraFinalizacionPista())) {
                        throw new ErrorReservaVuelo();
                    } else if (reservaDTO.getLocalTimeFinPista().isBefore(resSal.getHoraFinalizacionPista()) && vuelo.getHoraEDT().isAfter(resSal.getHoraInicioPista())) {
                        throw new ErrorReservaVuelo();
                    } else if (reservaDTO.getLocalTimeFinPista().isAfter(resSal.getHoraInicioPista()) && vuelo.getHoraEDT().isBefore(resSal.getHoraInicioPista())) {
                        throw new ErrorReservaVuelo();
                    } else if (reservaDTO.getLocalTimeFinPista().isAfter(resSal.getHoraFinalizacionPista()) && vuelo.getHoraEDT().isBefore(resSal.getHoraInicioPista())) {
                        throw new ErrorReservaVuelo();
                    }
                }
            }

            vuelo.setAceptadoOrigen(true);
            if(vuelo.isAceptadoDestino()){
                String avionAsignado = vuelo.getAvion();

                int capacidadAsientos = avionRepository.findByMatricula(avionAsignado).getCapacidad();
                List<Asiento> asientos = new ArrayList<>(capacidadAsientos);
                for (int i = 1; i <= capacidadAsientos; i++) {
                    Asiento asiento = new Asiento(i);
                    asientoRepository.save(asiento);
                    asientos.add(asiento);

                }
                vuelo.setAsientos(asientos);
            }

            Reserva reservaSalida = new Reserva(pista,puerta,fechaSalida,reservaDTO.getLocalTimeFinPuerta(),vuelo.getHoraEDT(),vuelo.getHoraEDT(),reservaDTO.getLocalTimeFinPista());

            vuelo.setReservaSalida(reservaSalida);
            reservaRepository.save(reservaSalida);
            vueloRepository.save(vuelo);
        }

    }

    @PostMapping("/getPasaportes")
    public List<Long> getVuelo(@RequestBody CheckInDTO checkInDTO) throws ConflictoCodgoVuelo, NoExisteVuelo {

        Vuelo vuelo = vueloRepository.findByCodigoVuelo(checkInDTO.getCodigoVuelo());
        if(vuelo==null){
            throw new NoExisteVuelo();
        }
        if(!vuelo.getAerolinea().equals(checkInDTO.getCodigoAerolinea())){
            throw new ConflictoCodgoVuelo();
        }
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
    public List<Long> getPasaportesBoarding(@RequestBody BoardingDTO boardingDTO) throws NoExisteVuelo, ConflictoCodgoVuelo {
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(boardingDTO.getCodigoVuelo());
        if(vuelo==null){
            throw new NoExisteVuelo();
        }
        if(!vuelo.getAeropuertoOrigen().equals(boardingDTO.getCodigoAeropuerto())){
            throw new ConflictoCodgoVuelo();
        }
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
    @PostMapping("/getVueloMaletero")
    public List<MaletasDTO> getVueloMaletero(@RequestBody AgregarMaletasDTO agregarMaletasDTO) throws ConflictoCodgoVuelo, ErrorMaleteria {
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(agregarMaletasDTO.getCodigoVueloMaletero());
        if(vuelo==null){
            throw new ErrorMaleteria();
        }
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(agregarMaletasDTO.getCodigoAeropuertoMaletero());




        List<Asiento> asientos = vuelo.getAsientos();
        List<MaletasDTO> maletasDTOs = new ArrayList<>();
        if (vuelo.getAeropuertoDestino().equals(aeropuerto.getCodigoIATAAeropuerto())) {
            for (int i = 0; i < asientos.size(); i++) {
                Asiento asiento = asientos.get(i);
                if (asiento.isCheckIn() == true) {
                    List<Maleta> maletas = asiento.getMaletas();
                    for (int j = 0; j < maletas.size(); j++) {
                        Maleta maleta = maletas.get(j);
                        if (maleta.isSubidoAvion() && !maleta.isEntregadoCliente()) {
                            MaletasDTO maletasDTO = new MaletasDTO();
                            maletasDTO.setIdMaleta(maleta.getIdMaleta());
                            maletasDTOs.add(maletasDTO);
                        }
                    }
                }
            }
        }
        else if(vuelo.getAeropuertoOrigen().equals(aeropuerto.getCodigoIATAAeropuerto())){
            for (int i = 0; i < asientos.size(); i++) {
                Asiento asiento = asientos.get(i);
                if (asiento.isCheckIn() == true) {
                    List<Maleta> maletas = asiento.getMaletas();
                    for (int j = 0; j < maletas.size(); j++) {
                        Maleta maleta = maletas.get(j);
                        if(!maleta.isSubidoAvion()) {
                            MaletasDTO maletasDTO = new MaletasDTO();
                            maletasDTO.setIdMaleta(maleta.getIdMaleta());
                            maletasDTOs.add(maletasDTO);
                        }
                        }
                    }
                }
            }
        else{
            throw new ConflictoCodgoVuelo();
        }

        return maletasDTOs;
    }
    @Transactional
    @PostMapping("/subirBajarMaletas")
    public void subirBajarMaletas(@RequestBody AgregarMaletasDTO agregarMaletasDTO) {
        Vuelo vuelo = vueloRepository.findByCodigoVuelo(agregarMaletasDTO.getCodigoVueloMaletero());
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(agregarMaletasDTO.getCodigoAeropuertoMaletero());
        List<Asiento> asientos = vuelo.getAsientos();
        for (int i = 0; i < asientos.size(); i++) {
            Asiento asiento = asientos.get(i);
            if (vuelo.getAeropuertoDestino().equals(aeropuerto.getCodigoIATAAeropuerto())) {
                if (asiento.isCheckIn() == true) {
                    List<Maleta> maletas = asiento.getMaletas();
                    for (int j = 0; j < maletas.size(); j++) {
                        Maleta maleta = maletas.get(j);
                        if (maleta.isSubidoAvion() == true) {
                            if (maleta.getIdMaleta() == agregarMaletasDTO.getIdMaleta()) {
                                maleta.setEntregadoCliente(true);
                                maletaRepository.save(maleta);
                            }
                        }
                    }
                }
            } else if (vuelo.getAeropuertoOrigen().equals(aeropuerto.getCodigoIATAAeropuerto())) {
                if (asiento.isCheckIn() == true) {
                    List<Maleta> maletas = asiento.getMaletas();
                    for (int j = 0; j < maletas.size(); j++) {
                        Maleta maleta = maletas.get(j);
                        if (maleta.getIdMaleta() == agregarMaletasDTO.getIdMaleta()) {
                            maleta.setSubidoAvion(true);
                            maletaRepository.save(maleta);
                        }
                    }
                }
            }
        }
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


    @PostMapping("/getVueloReserva")
    public List<VueloReservaDTO> getVueloReserva(@RequestBody ReservaDTO reservaDTO){
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(reservaDTO.getCodigoAeropuerto());
        List<Vuelo> vuelosLlegada = vueloRepository.findAllByFechaETAAndAeropuertoDestinoAndAceptadoDestinoAndAceptadoOrigen(reservaDTO.getFecha(),aeropuerto,true,true);
        List<Vuelo> vuelosSalida = vueloRepository.findAllByFechaEDTAndAeropuertoOrigenAndAceptadoDestinoAndAceptadoOrigen(reservaDTO.getFecha(),aeropuerto,true,true);
        List<VueloReservaDTO> reservaDTOS = new ArrayList<>();
        for (int i=0; i< vuelosLlegada.size(); i++){
            VueloReservaDTO vueloReservaDTO = new VueloReservaDTO();
            Vuelo vuelo = vuelosLlegada.get(i);
            Reserva reservaLlegada = vuelo.getReservaLlegada();

            vueloReservaDTO.setCodigoVuelo(vuelo.getCodigoVuelo());
            vueloReservaDTO.setNumeroPuerta(reservaLlegada.getPuerta().getIdPuerta());
            vueloReservaDTO.setHoraFinPista(reservaLlegada.getHoraFinalizacionPista());
            vueloReservaDTO.setHoraInicioPista(reservaLlegada.getHoraInicioPista());
            vueloReservaDTO.setHoraFinPuerta(reservaLlegada.getHoraFinalizacionPuerta());
            vueloReservaDTO.setHoraInicioPuerta(reservaLlegada.getHoraInicioPuerta());
            reservaDTOS.add(vueloReservaDTO);
        }
        for (int j=0; j< vuelosSalida.size(); j++){
            VueloReservaDTO vueloReservaDTO = new VueloReservaDTO();
            Vuelo vuelo = vuelosSalida.get(j);
            Reserva reservaSalida = vuelo.getReservaSalida();

            vueloReservaDTO.setCodigoVuelo(vuelo.getCodigoVuelo());
            vueloReservaDTO.setNumeroPuerta(reservaSalida.getPuerta().getIdPuerta());
            vueloReservaDTO.setHoraFinPista(reservaSalida.getHoraFinalizacionPista());
            vueloReservaDTO.setHoraInicioPista(reservaSalida.getHoraInicioPista());
            vueloReservaDTO.setHoraFinPuerta(reservaSalida.getHoraFinalizacionPuerta());
            vueloReservaDTO.setHoraInicioPuerta(reservaSalida.getHoraInicioPuerta());
            reservaDTOS.add(vueloReservaDTO);
        }
        return reservaDTOS;
    }

    //funcion que me devuelva una lista con todos los vuelos confirmados de todas las aerolineas
    @GetMapping("/getVuelosConfirmadoCliente")
    public List<VueloDTO> getVuelosConfirmados(){
    	List<Vuelo> vuelos = vueloRepository.findAllByAceptadoOrigenAndAceptadoDestinoAndRechadado(true, true, false);
    	List<VueloDTO> vuelosDTO = new ArrayList<>();
    	for(int i=0; i<vuelos.size();i++){
            if(vuelos.get(i).getEDT().isAfter(LocalDateTime.now())){
                vuelosDTO.add(vueloMapper.toVueloDTO(vuelos.get(i)));
            }
        }
    	return vuelosDTO;
    }










}

