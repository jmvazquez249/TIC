package um.edu.uy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import um.edu.uy.LoginDTO;
import um.edu.uy.UsuarioGeneralDTO;
import um.edu.uy.business.UsuarioGeneralMapper;
import um.edu.uy.business.entities.UsuarioGeneral;
import um.edu.uy.persistence.UsuarioGeneralRepository;

@RestController
@RequestMapping("/usuarioGeneral")
public class UsuarioGeneralRestService {
    private UsuarioGeneralMapper usuarioGeneralMapper;
    private UsuarioGeneralRepository usuarioGeneralRepository;

    @Autowired
    public UsuarioGeneralRestService(UsuarioGeneralMapper usuGeMap,  UsuarioGeneralRepository usuGeRepo){
        this.usuarioGeneralMapper=usuGeMap;
        this.usuarioGeneralRepository=usuGeRepo;
    }

    @PostMapping("/agregar")
    public void crearUsuarioGeneral(@RequestBody UsuarioGeneralDTO usuarioGeneralDTO){
        UsuarioGeneral usuarioGeneral=usuarioGeneralMapper.toUsuarioGeneral(usuarioGeneralDTO);
        usuarioGeneralRepository.save(usuarioGeneral);
    }

    @PostMapping("/get")
    public UsuarioGeneralDTO getUsuarioGeneral(@RequestBody LoginDTO loginDTO){
        UsuarioGeneral usuarioGeneral =  usuarioGeneralRepository.findOneByEmailAndContrasena(loginDTO.getEmail(), loginDTO.getPassword());
        return usuarioGeneralMapper.toUsuarioGeneralDTO(usuarioGeneral);

    }
    @PostMapping("/getPasaporte")
    public UsuarioGeneralDTO getUsuarioGeneralPasaporte(@RequestBody long pasaporte){
        UsuarioGeneral usuarioGeneral =  usuarioGeneralRepository.findOneByPasaporte(pasaporte);
        return usuarioGeneralMapper.toUsuarioGeneralDTO(usuarioGeneral);

    }
}
