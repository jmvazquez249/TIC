package um.edu.uy.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import um.edu.uy.UsuarioGeneralDTO;
import um.edu.uy.business.entities.Aerolinea;
import um.edu.uy.business.entities.Aeropuerto;
import um.edu.uy.business.entities.UsuarioGeneral;
import um.edu.uy.persistence.AerolineaRepository;
import um.edu.uy.persistence.AeropuertoRepository;

@Component
public class UsuarioGeneralMapper {

    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Autowired
    private AerolineaRepository aerolineaRepository;


    public UsuarioGeneral toUsuarioGeneral(UsuarioGeneralDTO usuarioGeneralDTO){
        UsuarioGeneral usuarioGeneral = new UsuarioGeneral(usuarioGeneralDTO.getPasaporte(), usuarioGeneralDTO.getNombre(), usuarioGeneralDTO.getApellido(), usuarioGeneralDTO.getContrasena(), usuarioGeneralDTO.getEmail(),"CLIENTE" );
        return usuarioGeneral;
    }

    public UsuarioGeneral toUsuarioGeneralAdminAeropuerto(UsuarioGeneralDTO usuarioGeneralDTO){
        Aeropuerto aeropuerto = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(usuarioGeneralDTO.getCodigoAeropuerto());
        UsuarioGeneral usuarioGeneral = new UsuarioGeneral(usuarioGeneralDTO.getPasaporte(), usuarioGeneralDTO.getNombre(), usuarioGeneralDTO.getApellido(), usuarioGeneralDTO.getContrasena(), usuarioGeneralDTO.getEmail(),aeropuerto,"ADMINAEROPUERTO" );
        return usuarioGeneral;
    }

    public UsuarioGeneral toUsuarioGeneralAerolinea(UsuarioGeneralDTO usuarioGeneralDTO){
        Aerolinea aerolinea =  aerolineaRepository.findAerolineaByCodigoIATAAerolinea(usuarioGeneralDTO.getCodigoAerolinea());
        UsuarioGeneral usuarioGeneral = new UsuarioGeneral(usuarioGeneralDTO.getPasaporte(), usuarioGeneralDTO.getNombre(), usuarioGeneralDTO.getApellido(), usuarioGeneralDTO.getContrasena(), usuarioGeneralDTO.getEmail(),aerolinea,"ADMINAEROLINEA" );
        return usuarioGeneral;
    }

    public UsuarioGeneralDTO toUsuarioGeneralDTO(UsuarioGeneral usuarioGeneral){
        UsuarioGeneralDTO usuarioGeneralDTO = new UsuarioGeneralDTO();

        usuarioGeneralDTO.setPasaporte(usuarioGeneral.getPasaporte());
        usuarioGeneralDTO.setNombre(usuarioGeneral.getNombre());
        usuarioGeneralDTO.setApellido(usuarioGeneral.getApellido());
        usuarioGeneralDTO.setContrasena(usuarioGeneral.getContrasena());
        usuarioGeneralDTO.setEmail(usuarioGeneral.getEmail());
        usuarioGeneralDTO.setTipo(usuarioGeneral.getTipo());
        if (null != usuarioGeneral.getCodigoAeropuerto()) {
            usuarioGeneralDTO.setCodigoAeropuerto(usuarioGeneral.getCodigoAeropuerto());
        }
        if (null != usuarioGeneral.getCodigoAerolinea()) {
            usuarioGeneralDTO.setCodigoAerolinea(usuarioGeneral.getCodigoAerolinea());
        }
        return usuarioGeneralDTO;
    }





    /*
    * public ClientDTO toDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setDocument(client.getDocument());
        clientDTO.setAddress(client.getAddress());
        clientDTO.setName(client.getName());
        clientDTO.setId(client.getId());

        return clientDTO;
    }*/


}
