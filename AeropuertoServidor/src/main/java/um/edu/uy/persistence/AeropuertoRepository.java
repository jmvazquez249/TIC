package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import um.edu.uy.business.entities.Aeropuerto;


public interface AeropuertoRepository extends JpaRepository<Aeropuerto,String> {


    Aeropuerto findAeropuertoByCodigoIATAAeropuerto(String codigoIATA);

    Aeropuerto findByIdAeropuerto(long id);


}
