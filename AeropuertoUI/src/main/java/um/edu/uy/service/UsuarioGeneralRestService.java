package um.edu.uy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import um.edu.uy.LoginDTO;
import um.edu.uy.UsuarioGeneralDTO;

@Component
public class UsuarioGeneralRestService {

    private RestTemplate restTemplate;

    @Autowired
    private UsuarioGeneralRestService(RestTemplate restTemplate){this.restTemplate=restTemplate;}

    public ResponseEntity agregarUsuarioGeneral(UsuarioGeneralDTO usuarioGeneralDTO){
        return restTemplate.postForEntity("http://localhost:8080/usuarioGeneral/agregar",usuarioGeneralDTO,UsuarioGeneralDTO.class);
    }

    public ResponseEntity getUsuarioGeneralDTO(LoginDTO loginDTO){
        return restTemplate.postForEntity("http://localhost:8080/usuarioGeneral/get",loginDTO,UsuarioGeneralDTO.class);

    }


}
