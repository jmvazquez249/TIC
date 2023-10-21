package um.edu.uy.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import um.edu.uy.business.entities.Aeropuerto;

import java.util.List;
import java.util.Optional;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto,String> {


    Aeropuerto findAeropuertoByCodigoIATAAeropuerto(String codigoIATA);

    Aeropuerto findByIdAeropuerto(long id);


}
