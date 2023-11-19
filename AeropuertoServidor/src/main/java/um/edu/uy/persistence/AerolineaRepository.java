package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.uy.business.entities.Aerolinea;


public interface AerolineaRepository extends JpaRepository<Aerolinea,Long> {
    Aerolinea findAerolineaByCodigoIATAAerolinea(String codigoIATA);

    Aerolinea findByIdAerolinea(long id);
    Aerolinea findAerolineaByCodigoICAO(String codigoICAO);


}
