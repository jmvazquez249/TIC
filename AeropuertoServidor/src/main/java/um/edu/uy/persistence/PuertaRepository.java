package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.Puerta;

public interface PuertaRepository extends CrudRepository<Puerta, Long> {
    Puerta findByIdPuerta(long idPuerta);

}
