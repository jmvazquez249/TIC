package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.Vuelo;

import java.time.LocalDate;
import java.util.List;

public interface VueloRepository extends JpaRepository<Vuelo,Long> {
    Vuelo findByCodigoVuelo(String codigoVuelo);
    List<Vuelo> findAllByAeropuertoDestino(Aeropuerto aeropuerto);
    List<Vuelo> findAllByAeropuertoDestinoAndAceptadoDestinoAndRechadado(Aeropuerto aeropuerto, boolean aceptado, boolean rechazado);
    List<Vuelo> findAllByAeropuertoOrigenAndAceptadoOrigenAndRechadado(Aeropuerto aeropuerto, boolean aceptado, boolean rechazado);
    List<Vuelo> findAllByAeropuertoDestinoAndAceptadoOrigenAndAceptadoDestinoAndRechadado(Aeropuerto aeropuerto, boolean aceptadoorigen, boolean aceptadodestino, boolean rechazado);
    List<Vuelo> findAllByAeropuertoOrigenAndAceptadoOrigenAndAceptadoDestinoAndRechadado(Aeropuerto aeropuerto, boolean aceptadoorigen, boolean aceptadodestino, boolean rechazado);
    List<Vuelo> findAllByAerolineaAndAceptadoOrigenAndAceptadoDestinoAndRechadado(Aerolinea aerolinea, boolean aceptadoorigen, boolean aceptadodestino, boolean rechazado);
    List<Vuelo> findAllByFechaETAAndAeropuertoDestinoAndAceptadoDestinoAndAceptadoOrigen(LocalDate fechaLlegada, Aeropuerto aeropuerto, boolean aceptadoorigen, boolean aceptadodestino);
    List<Vuelo> findAllByFechaEDTAndAeropuertoOrigenAndAceptadoDestinoAndAceptadoOrigen(LocalDate fechaLlegada, Aeropuerto aeropuerto, boolean aceptadoorigen, boolean aceptadodestino);
    List<Vuelo> findAllByAerolinea(Aerolinea aerolinea);
}
