package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.Vuelo;

import java.util.List;

public interface VueloRepository extends JpaRepository<Vuelo,Long> {
    Vuelo findByCodigoVuelo(long codigoVuelo);
    List<Vuelo> findAllByAeropuertoDestino(Aeropuerto aeropuerto);
    List<Vuelo> findAllByAeropuertoDestinoAndAceptadoDestinoAndRechadado(Aeropuerto aeropuerto, boolean aceptado, boolean rechazado);
    List<Vuelo> findAllByAeropuertoOrigenAndAceptadoOrigenAndRechadado(Aeropuerto aeropuerto, boolean aceptado, boolean rechazado);
    List<Vuelo> findAllByAeropuertoDestinoAndAceptadoOrigenAndAceptadoDestinoAndRechadado(Aeropuerto aeropuerto, boolean aceptadoorigen, boolean aceptadodestino, boolean rechazado);
    List<Vuelo> findAllByAeropuertoOrigenAndAceptadoOrigenAndAceptadoDestinoAndRechadado(Aeropuerto aeropuerto, boolean aceptadoorigen, boolean aceptadodestino, boolean rechazado);

}