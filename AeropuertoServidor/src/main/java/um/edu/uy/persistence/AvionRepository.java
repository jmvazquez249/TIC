package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.uy.business.entities.Avion;

import java.util.List;


public interface AvionRepository extends JpaRepository<Avion, Long> {
    Avion findByMatricula(String matricula);
    Avion findOneByMatricula(String matricula);

}
