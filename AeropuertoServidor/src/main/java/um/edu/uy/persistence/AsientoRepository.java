package um.edu.uy.persistence;

import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.Asiento;

public interface AsientoRepository extends CrudRepository<Asiento, Long> {
    Asiento findByIdAsiento(long idAsiento);
    Asiento findByVuelo(String codigoVuelo);
}
