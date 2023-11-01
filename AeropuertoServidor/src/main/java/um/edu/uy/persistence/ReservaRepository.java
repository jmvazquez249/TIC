package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.Reserva;

public interface ReservaRepository extends CrudRepository<Reserva,Long> {
}
