package um.edu.uy.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import um.edu.uy.business.entities.UsuarioGeneral;

public interface UsuarioGeneralRepository extends JpaRepository<UsuarioGeneral,String> {
    UsuarioGeneral findOneByEmailAndContrasena(String email, String contrasena);
    UsuarioGeneral findOneByEmail(String email);
    UsuarioGeneral findOneByPasaporte(long pasaporte);
    UsuarioGeneral findOneByPasaporteAndTipo(long pasaporte, String tipo);




}
