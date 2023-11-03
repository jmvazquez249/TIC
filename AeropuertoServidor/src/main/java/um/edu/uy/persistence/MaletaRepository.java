package um.edu.uy.persistence;

import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.Maleta;

public interface MaletaRepository extends CrudRepository<Maleta, Long> {
    Maleta findByIdMaleta(long idMaleta);
}
