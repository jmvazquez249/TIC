package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Aeropuerto;

import java.util.List;

public interface AerolineaRepository extends JpaRepository<Aerolinea,Long> {
    Aerolinea findAerolineaByCodigoIATAAerolinea(String codigoIATA);

    Aerolinea findByIdAerolinea(long id);


}
