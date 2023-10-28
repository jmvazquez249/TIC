package um.edu.uy.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.jsf.FacesContextUtils;
import um.edu.uy.*;
import um.edu.uy.service.AerolineaRestService;
import um.edu.uy.service.AeropuertoRestService;
import um.edu.uy.service.AvionRestService;
import um.edu.uy.service.UsuarioGeneralRestService;
import um.edu.uy.service.VueloRestService;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


@Component
public class IniciarSesionController {

    @Autowired
    private UsuarioGeneralRestService usuarioGeneralRestService;

    @Autowired
    private AeropuertoRestService aeropuertoRestService;

    @Autowired
    private AerolineaRestService aerolineaRestService;
    @FXML
    private TextField email;
    @FXML
    private TextField contrasena;
    @FXML
    private TextField pasaporte;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;
    @FXML
    private TextField codigoaeropuerto;
    @FXML
    private TextField codigoIATAAeropuerto;
    @FXML
    private TextField nombreAeropuerto;
    @FXML
    private TextField nombreAerolinea;
    @FXML
    private TextField codigoIATAAerolinea;
    @FXML
    private TextField modelo;
    @FXML
    private TextField matricula;
    @FXML
    private TextField capacidad;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField pais;


    @FXML
    void loginUsuario(ActionEvent event) throws IOException {


            String emailUsu = email.getText();
            String contrasenaUsu = contrasena.getText();

            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(emailUsu);
            loginDTO.setPassword(contrasenaUsu);

            ResponseEntity usuResponse = usuarioGeneralRestService.getUsuarioGeneralDTO(loginDTO);
            UsuarioGeneralDTO usu = (UsuarioGeneralDTO) usuResponse.getBody();


            if (usu != null) {
                String tipoUsuario = usu.getTipo();
                if (tipoUsuario.equals("DIOS")) {
                    redireccion(event, "InicioAdministradorDios.fxml", null);
                } else if (tipoUsuario.equals("ADMINAEROPUERTO")) {
                    cargarAdministradorAeropuerto(event, usu.getCodigoAeropuerto());
                } else if (tipoUsuario.equals("CLIENTE")) {
                    redireccion(event, "Cliente.fxml", null);
                } else if (tipoUsuario.equals("ADMINAEROLINEA")) {
                    redireccion(event, "AdminAerolinea.fxml", usu.getCodigoAerolinea());
                } else if (tipoUsuario.equals("CHECK IN")) {
                    redireccion(event, "CheckIn.fxml", null);
                } else if (tipoUsuario.equals("OFICINA")) {
                    cargarAgregarVuelo(event, "UsuarioAerolinea.fxml", usu.getCodigoAerolinea());
                } else if (tipoUsuario.equals("MALETERIA")) {
                    redireccion(event, "Maletero.fxml", null);
                } else if (tipoUsuario.equals("BOARDING")) {
                    redireccion(event, "Boarding.fxml", null);
                } else if (tipoUsuario.equals("ADMINVUELOS")) {
                    cargarAdministradorVuelos(event, usu.getCodigoAeropuerto());
                }
            } else {
                showAlert("No Existe Usuario", "El usuario no esta registrado");
            }

    }

    @Transactional
    @FXML
    void registrarAdministradorAeropuerto(ActionEvent event) {
        try {
            long pasaporteAdAero = Long.parseLong(pasaporte.getText());
            String nombreAdAero = nombre.getText();
            String apellidoAdAero = apellido.getText();
            String emailAdAero = email.getText();
            String contrasenaAdAero = contrasena.getText();
            String codigoAeropuertoAdAero = codigoaeropuerto.getText();

            ResponseEntity response1 = aeropuertoRestService.getAeropuerto(codigoAeropuertoAdAero);
            AeropuertoDTO aeropuertoDTO = (AeropuertoDTO) response1.getBody();

            ResponseEntity response2 = usuarioGeneralRestService.getUsuarioGeneralPasaporte(pasaporteAdAero);
            UsuarioGeneralDTO usuarioGeneralDTOPasaporte = (UsuarioGeneralDTO) response2.getBody();

            ResponseEntity response3 = usuarioGeneralRestService.getUsuarioGeneralEmail(emailAdAero);
            UsuarioGeneralDTO usuarioGeneralDTOEmail = (UsuarioGeneralDTO) response3.getBody();

            if (aeropuertoDTO == null) {
                showAlert(
                        "Aeropuerto No existe",
                        "El aeropuerto ingresado no existe");

            } else if (usuarioGeneralDTOEmail != null) {
                showAlert(
                        "Email ya registrado",
                        "El email ingresado ya esta registrado");
            } else if (usuarioGeneralDTOPasaporte != null) {
                showAlert(
                        "Pasaporte ya registrado",
                        "El pasaporte ingresado ya esta registrado");
            }

            else {
                UsuarioGeneralDTO usuarioGeneralDTO = new UsuarioGeneralDTO();
                usuarioGeneralDTO.setPasaporte(pasaporteAdAero);
                usuarioGeneralDTO.setEmail(emailAdAero);
                usuarioGeneralDTO.setNombre(nombreAdAero);
                usuarioGeneralDTO.setApellido(apellidoAdAero);
                usuarioGeneralDTO.setContrasena(contrasenaAdAero);
                usuarioGeneralDTO.setCodigoAeropuerto(codigoAeropuertoAdAero);
                usuarioGeneralDTO.setTipo("ADMINAEROPUERTO");
                usuarioGeneralDTO.setCodigoAerolinea(null);

                ResponseEntity response = usuarioGeneralRestService.agregarUsuarioGeneral(usuarioGeneralDTO);

                if (response.getStatusCode() == HttpStatus.OK) {

                    showAlert("Administrador agregado", "Se agrego con exito el Administrador!");

                }
            }
        }
        catch (Exception e){
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }
    }

    @Transactional
    @FXML
    void registrarCliente(ActionEvent event){
        try {
        String nombreUsu = nombre.getText();
        String apellidoUsu = apellido.getText();
        String contrasenaUsu = contrasena.getText();
        String emailUsu = email.getText();
        long pasaporteUsu = Long.parseLong(pasaporte.getText());

        ResponseEntity response1 = usuarioGeneralRestService.getUsuarioGeneralPasaporte(pasaporteUsu);
        UsuarioGeneralDTO usuarioGeneralDTOPasaporte = (UsuarioGeneralDTO) response1.getBody();

        ResponseEntity response2 = usuarioGeneralRestService.getUsuarioGeneralEmail(emailUsu);
        UsuarioGeneralDTO usuarioGeneralDTOEmail = (UsuarioGeneralDTO) response2.getBody();

        if(usuarioGeneralDTOPasaporte!=null){
            showAlert(
                    "Pasaporte ya registrado",
                    "El pasaporte ingresado ya esta registrado");
        }
        else if(usuarioGeneralDTOEmail!=null){
            showAlert(
                    "Email ya registrado",
                    "El email ingresado ya esta registrado");
        }
        else{
                UsuarioGeneralDTO usuarioGeneralDTO = new UsuarioGeneralDTO();
                usuarioGeneralDTO.setPasaporte(pasaporteUsu);
                usuarioGeneralDTO.setEmail(emailUsu);
                usuarioGeneralDTO.setNombre(nombreUsu);
                usuarioGeneralDTO.setApellido(apellidoUsu);
                usuarioGeneralDTO.setContrasena(contrasenaUsu);
                usuarioGeneralDTO.setTipo("CLIENTE");
                usuarioGeneralDTO.setCodigoAerolinea(null);
                usuarioGeneralDTO.setCodigoAeropuerto(null);
                try {

                    ResponseEntity response = usuarioGeneralRestService.agregarUsuarioGeneral(usuarioGeneralDTO);

                    if (response.getStatusCode() == HttpStatus.OK) {

                        showAlert("Cliente agregado", "Se agrego con exito el cliente!");

                    }

                } catch (HttpClientErrorException error) {
                    if (error.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        showAlert(
                                "Informacion invalida !",
                                "Se encontro un error en los datos ingresados.");
                    } else if (error.getStatusCode() == HttpStatus.CONFLICT) {
                        showAlert(
                                "Documento ya registrado !",
                                "El documento indicado ya ha sido registrado en el sistema.");
                    } else {
                        showAlert(
                                "Error Generico",
                                "Se recibio el siguiente codigo de error: " + error.getStatusCode());
                    }
                }
        }

        }catch (Exception e){
            System.out.println(e);
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }
    }


    @Transactional
    @FXML
    void registrarAeropuerto(ActionEvent event) {
        try {
            String nombreAero = nombreAeropuerto.getText();
            String codigoIATAAero = codigoIATAAeropuerto.getText();
            String ciudadAero = ciudad.getText();
            String paisAero = pais.getText();

            ResponseEntity response1 = aeropuertoRestService.getAeropuerto(codigoIATAAero);
            AeropuertoDTO aeropuertoDTOCodigo = (AeropuertoDTO) response1.getBody();

            if (aeropuertoDTOCodigo != null) {
                showAlert(
                        "Aeropuerto ya registrado",
                        "El aeropuerto ingresado ya esta registrado");
            } else {

                AeropuertoDTO aeropuertoDTO = new AeropuertoDTO();
                aeropuertoDTO.setCodigoIATAAeropuerto(codigoIATAAero);
                aeropuertoDTO.setNombre(nombreAero);
                aeropuertoDTO.setCiudad(ciudadAero);
                aeropuertoDTO.setPais(paisAero);

                ResponseEntity response = aeropuertoRestService.agregarAeropuerto(aeropuertoDTO);

                if (response.getStatusCode() == HttpStatus.OK) {
                    showAlert("Aeropuerto agregado", "Se agrego con exito el aeropuerto!");

                }

            }
        }catch (Exception e){
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }
    }

    @Autowired
    private AvionRestService avionRestService;
    @Transactional
    @FXML
    void registrarAvion(ActionEvent event) {
        try {
            String modeloAv = modelo.getText();
            String matriculaAv = matricula.getText();
            int capacidadAv = Integer.parseInt(capacidad.getText());

            ResponseEntity response1 = avionRestService.getAvion(matriculaAv);
            AvionDTO avionDTOMatricula = (AvionDTO) response1.getBody();


            if (avionDTOMatricula != null) {
                showAlert(
                        "Avion ya registrado",
                        "El avion ingresado ya esta registrado");
            } else {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                String codigoAerolinea = (String) stage.getUserData();

                AvionDTO avionDTO = new AvionDTO();
                avionDTO.setCapacidad(capacidadAv);
                avionDTO.setMatricula(matriculaAv);
                avionDTO.setModelo(modeloAv);
                avionDTO.setCodigoAeroliena(codigoAerolinea);

                ResponseEntity response = aerolineaRestService.agregarAvionAerolinea(avionDTO);
                if (response.getStatusCode() == HttpStatus.OK) {
                    showAlert("Avion agregado", "Se agrego con exito el avion!");
                }

            }
        }catch (Exception e){
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }
    }

    @Transactional
    @FXML
    void agregarAerolinea(ActionEvent event) {
        try {
            String nombreAero = nombreAerolinea.getText();
            String codigoIATAAerol = codigoIATAAerolinea.getText();
            String nombreUsu = nombre.getText();
            String emailUsu = email.getText();
            Long pasaporteUsu = Long.parseLong(pasaporte.getText());
            String contrasenaUsu = contrasena.getText();
            String apellidoUsu = apellido.getText();

            ResponseEntity response3 = aerolineaRestService.getAerolinea(codigoIATAAerol);
            AerolineaDTO aerolineaDTOCodigo = (AerolineaDTO) response3.getBody();

            ResponseEntity response4 = usuarioGeneralRestService.getUsuarioGeneralPasaporte(pasaporteUsu);
            UsuarioGeneralDTO usuarioGeneralDTOPasaporte = (UsuarioGeneralDTO) response4.getBody();

            ResponseEntity response5 = usuarioGeneralRestService.getUsuarioGeneralEmail(emailUsu);
            UsuarioGeneralDTO usuarioGeneralDTOEmail = (UsuarioGeneralDTO) response5.getBody();


            if (aerolineaDTOCodigo != null) {
                showAlert(
                        "Aerolinea ya registrada",
                        "La aerolinea ingresada ya esta registrada");
            } else if (usuarioGeneralDTOPasaporte != null) {
                showAlert(
                        "Pasaporte ya registrado",
                        "El pasaporte ingresado ya esta registrado");
            } else if (usuarioGeneralDTOEmail != null) {
                showAlert(
                        "Email ya registrado",
                        "El email ingresado ya esta registrado");
            } else {
                AerolineaDTO aerolineaDTO = new AerolineaDTO();
                aerolineaDTO.setCodigoIATAAerolinea(codigoIATAAerol);
                aerolineaDTO.setNombre(nombreAero);

                UsuarioGeneralDTO usuarioGeneralDTO = new UsuarioGeneralDTO();
                usuarioGeneralDTO.setCodigoAerolinea(codigoIATAAerol);
                usuarioGeneralDTO.setNombre(nombreUsu);
                usuarioGeneralDTO.setPasaporte(pasaporteUsu);
                usuarioGeneralDTO.setApellido(apellidoUsu);
                usuarioGeneralDTO.setEmail(emailUsu);
                usuarioGeneralDTO.setContrasena(contrasenaUsu);
                usuarioGeneralDTO.setTipo("ADMINAEROLINEA");
                usuarioGeneralDTO.setCodigoAeropuerto(null);

                //hacer que sea atomica la transaccion
                ResponseEntity response1 = aerolineaRestService.agregarAerolinea(aerolineaDTO);
                ResponseEntity response2 = usuarioGeneralRestService.agregarUsuarioGeneral(usuarioGeneralDTO);

                if (response1.getStatusCode() == HttpStatus.OK & response2.getStatusCode() == HttpStatus.OK) {
                    showAlert("Aerolinea agregado", "Se agrego con exito la aerolinea!");
                }

            }
        }catch (Exception e){
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }

    }

    @Transactional
    @FXML
    void agregarAdministradorAerolineaExistente(ActionEvent event) {
        try {
            String codigoIATAAerol = codigoIATAAerolinea.getText();
            String nombreUsu = nombre.getText();
            String emailUsu = email.getText();
            Long pasaporteUsu = Long.parseLong(pasaporte.getText());
            String contrasenaUsu = contrasena.getText();
            String apellidoUsu = apellido.getText();

            ResponseEntity response3 = aerolineaRestService.getAerolinea(codigoIATAAerol);
            AerolineaDTO aerolineaDTOCodigo = (AerolineaDTO) response3.getBody();

            ResponseEntity response4 = usuarioGeneralRestService.getUsuarioGeneralPasaporte(pasaporteUsu);
            UsuarioGeneralDTO usuarioGeneralDTOPasaporte = (UsuarioGeneralDTO) response4.getBody();

            ResponseEntity response5 = usuarioGeneralRestService.getUsuarioGeneralEmail(emailUsu);
            UsuarioGeneralDTO usuarioGeneralDTOEmail = (UsuarioGeneralDTO) response5.getBody();

            if (aerolineaDTOCodigo == null) {
                showAlert(
                        "Aerolinea no registrada",
                        "La aerolinea ingresada no esta registrada");
            } else if (usuarioGeneralDTOPasaporte != null) {
                showAlert(
                        "Pasaporte ya registrado",
                        "El pasaporte ingresado ya esta registrado");
            } else if (usuarioGeneralDTOEmail != null) {
                showAlert(
                        "Email ya registrado",
                        "El email ingresado ya esta registrado");
            }

            else{


            UsuarioGeneralDTO usuarioGeneralDTO = new UsuarioGeneralDTO();
            usuarioGeneralDTO.setPasaporte(pasaporteUsu);
            usuarioGeneralDTO.setNombre(nombreUsu);
            usuarioGeneralDTO.setApellido(apellidoUsu);
            usuarioGeneralDTO.setEmail(emailUsu);
            usuarioGeneralDTO.setContrasena(contrasenaUsu);
            usuarioGeneralDTO.setCodigoAeropuerto(null);
            usuarioGeneralDTO.setTipo("ADMINAEROLINEA");
            usuarioGeneralDTO.setCodigoAerolinea(codigoIATAAerol);

            ResponseEntity response = usuarioGeneralRestService.agregarUsuarioGeneral(usuarioGeneralDTO);

            if (response.getStatusCode() == HttpStatus.OK) {
                showAlert("Administrador agregado", "Se agrego con exito el administrador de la aerolinea!");
            }
        }
    }catch (Exception e) {
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }
    }


    @FXML
    void cargarRegistrarAdministradorAerolinaExistente(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAdministradorAerolineaExistente.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void cargarRegistrarAdministradorAeropuerto(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAdministradorAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void cargarAgregarAeropuerto(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void cargarRegistroCliente(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("RegistroCliente.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void cargarAgregarAerolinea(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAerolinea.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void cargarAgregarAvion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAvion.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarAgregarUsuarioAerolinea(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(AgregarUsuarioAerolineaController.class.getResourceAsStream("AgregarUsuarioAerolinea.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void cargarAgregarUsuarioAeropuerto(ActionEvent event) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(AgregarUsuarioAeropuertoController.class.getResourceAsStream("agregarUsuarioAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);

        stage.show();
    }
    @FXML
    public TableView<VueloDTO> tablaVuelosAceptadosLlegada;
    @FXML
    public TableColumn<VueloDTO, String> codigoVueloAceptadoLlegada;
    @FXML
    public TableColumn<VueloDTO, String> aeropuertoOrigenAceptadoLlegada;
    @FXML
    public TableColumn<VueloDTO, String> aeropuertoDestinoAceptadoLlegada;
    @FXML
    public TableColumn<VueloDTO, String> matriculaAvionAceptadoLlegada;
    @FXML
    public TableColumn<VueloDTO,String> EDTllegada;
    @FXML
    public TableColumn<VueloDTO,String> ETAllegada;
    @FXML
    public TableColumn<VueloDTO,String> EDTsalida;
    @FXML
    public TableColumn<VueloDTO,String> ETAsalida;
    @FXML
    public TableView<VueloDTO> tablaVuelosAceptadosSalida;
    @FXML
    public TableColumn<VueloDTO, String> codigoVueloAceptadoSalida;
    @FXML
    public TableColumn<VueloDTO, String> aeropuertoOrigenAceptadoSalida;
    @FXML
    public TableColumn<VueloDTO, String> aeropuertoDestinoAceptadoSalida;
    @FXML
    public TableColumn<VueloDTO, String> matriculaAvionAceptadoSalida;

    @FXML
    void cargarAdministradorAeropuerto(ActionEvent event, String codigoAeropuerto) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdministradorAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(codigoAeropuerto != null){
            stage.setUserData(codigoAeropuerto);
        }
        stage.setScene(scene);

        ResponseEntity response1 = aeropuertoRestService.getListaAeropuertosLlegada(codigoAeropuerto);
        ResponseEntity response2 = aeropuertoRestService.getListaAeropuertosSalida(codigoAeropuerto);

        List vueloDTOS = (List) response1.getBody();
        List vueloDTOS2 = (List) response2.getBody();

        List<VueloDTO> vuelosLle = new ArrayList<>();
        List<VueloDTO> vuelosSal = new ArrayList<>();

        for (int i=0;i<vueloDTOS.size();i++){
            VueloDTO vueloDTO = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
            vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
            vuelosLle.add(vueloDTO);
        }

        for (int j=0;j<vueloDTOS2.size();j++){
            VueloDTO vueloDTO2 = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS2.get(j);
            vueloDTO2.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO2.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO2.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO2.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vueloDTO2.setETA((LocalDateTime) hashMap.get("eta"));
            vueloDTO2.setEDT((LocalDateTime) hashMap.get("edt"));
            vuelosSal.add(vueloDTO2);
        }

        ObservableList<VueloDTO> vuelosLlegada = FXCollections.observableArrayList(vuelosLle);
        ObservableList<VueloDTO> vuelosSalida = FXCollections.observableArrayList(vuelosSal);

        codigoVueloAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigenAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
        aeropuertoDestinoAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
        matriculaAvionAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
        ETAllegada.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        EDTllegada.setCellValueFactory(new PropertyValueFactory<>("EDT"));
        tablaVuelosAceptadosLlegada.setItems(vuelosLlegada);

        codigoVueloAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigenAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
        aeropuertoDestinoAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
        matriculaAvionAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
        ETAsalida.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        EDTsalida.setCellValueFactory(new PropertyValueFactory<>("EDT"));
        tablaVuelosAceptadosSalida.setItems(vuelosSalida);

        stage.show();
    }

    @FXML
    private TableView<VueloDTO> tablaLlegada;
    @FXML
    private TableColumn<VueloDTO,String> aeropuertoOrigen;
    @FXML
    private TableColumn<VueloDTO, String> codigoVueloLlegada;
    @FXML
    private TableColumn<VueloDTO,String> matriculaAvionLlegada;
    @FXML
    private TableView<VueloDTO> tablaSalida;
    @FXML
    private TableColumn<VueloDTO,String> aeropuertoDestino;
    @FXML
    private TableColumn<VueloDTO, String> codigoVueloSalida;
    @FXML
    private TableColumn<VueloDTO,String> matriculaAvionSalida;

    @FXML
    void cargarAdministradorVuelos(ActionEvent event, String codigoAeropuerto) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdministradorVuelos.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        ResponseEntity response1 = aeropuertoRestService.getListaVuelosSinConfirmarLlegada(codigoAeropuerto);
        ResponseEntity response2 = aeropuertoRestService.getListaVuelosSinConfirmarSalida(codigoAeropuerto);

        List vueloDTOS = (List) response1.getBody();
        List<VueloDTO> vuelosLle = new ArrayList<>();

        List vueloDTOS2 = (List) response2.getBody();
        List<VueloDTO> vuelosSal = new ArrayList<>();

        for (int i=0;i<vueloDTOS.size();i++){
            VueloDTO vueloDTO = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
            vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
            vuelosLle.add(vueloDTO);
        }

        for (int j=0;j<vueloDTOS2.size();j++){
            VueloDTO vueloDTO = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS2.get(j);
            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
            vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
            vuelosSal.add(vueloDTO);
        }

        ObservableList<VueloDTO> vuelosLlegada = FXCollections.observableArrayList(vuelosLle);
        ObservableList<VueloDTO> vuelosSalida = FXCollections.observableArrayList(vuelosSal);

        codigoVueloLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigen.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
        matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
        ETAllegada.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        EDTllegada.setCellValueFactory(new PropertyValueFactory<>("EDT"));
        tablaLlegada.setItems(vuelosLlegada);
        addButtonToTableAceptar(tablaLlegada,true,codigoAeropuerto);
        addButtonToTableRechazar(tablaLlegada,codigoAeropuerto);

        codigoVueloSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoDestino.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
        matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
        ETAsalida.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        EDTsalida.setCellValueFactory(new PropertyValueFactory<>("EDT"));
        tablaSalida.setItems(vuelosSalida);
        addButtonToTableAceptar(tablaSalida,false,codigoAeropuerto);
        addButtonToTableRechazar(tablaSalida,codigoAeropuerto);

        stage.show();
    }

        private void addButtonToTableAceptar(TableView t, boolean llegada, String codigoAeropuerto) {
            TableColumn<Data, Void> colBtn = new TableColumn("Aceptar");

            Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<>() {
                @Override
                public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                    final TableCell<Data, Void> cell = new TableCell<>() {

                        private final Button btn = new Button("Aceptar");

                        {
                            btn.setOnAction((ActionEvent event) -> {
                                VueloDTO vuelo = (VueloDTO) getTableView().getItems().get(getIndex());
                                if (llegada==true){

                                    //controlar que se acepte correctamente

                                    ResponseEntity response = vueloRestService.aceptarDestino(vuelo.getCodigoVuelo());

                                    if (response.getStatusCode() == HttpStatus.OK ) {
                                        ResponseEntity response1 = aeropuertoRestService.getListaVuelosSinConfirmarLlegada(codigoAeropuerto);
                                        List vueloDTOS = (List) response1.getBody();

                                        List<VueloDTO> vuelosLle = new ArrayList<>();

                                        for (int i = 0; i < vueloDTOS.size(); i++) {
                                            VueloDTO vueloDTO = new VueloDTO();
                                            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
                                            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
                                            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
                                            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
                                            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
                                            vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
                                            vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
                                            vuelosLle.add(vueloDTO);
                                        }

                                        ObservableList<VueloDTO> vuelosLlegada = FXCollections.observableArrayList(vuelosLle);

                                        codigoVueloLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                                        aeropuertoOrigen.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
                                        matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
                                        ETAllegada.setCellValueFactory(new PropertyValueFactory<>("ETA"));
                                        EDTllegada.setCellValueFactory(new PropertyValueFactory<>("EDT"));
                                        tablaLlegada.setItems(vuelosLlegada);
                                    }

                                }else if(llegada==false){
                                    ResponseEntity response = vueloRestService.aceptarOrigen(vuelo.getCodigoVuelo());

                                    if (response.getStatusCode() == HttpStatus.OK ) {
                                        ResponseEntity response1 = aeropuertoRestService.getListaVuelosSinConfirmarSalida(codigoAeropuerto);

                                        List vueloDTOS = (List) response1.getBody();

                                        List<VueloDTO> vuelosSal = new ArrayList<>();

                                        for (int i = 0; i < vueloDTOS.size(); i++) {
                                            VueloDTO vueloDTO = new VueloDTO();
                                            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
                                            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
                                            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
                                            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
                                            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
                                            vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
                                            vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
                                            vuelosSal.add(vueloDTO);
                                        }

                                        ObservableList<VueloDTO> vuelosSalida = FXCollections.observableArrayList(vuelosSal);

                                        codigoVueloSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                                        aeropuertoDestino.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
                                        matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
                                        ETAsalida.setCellValueFactory(new PropertyValueFactory<>("ETA"));
                                        EDTsalida.setCellValueFactory(new PropertyValueFactory<>("EDT"));
                                        tablaSalida.setItems(vuelosSalida);

                                    }
                                }
                            });
                        }

                        @Override
                        public void updateItem(Void item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty) {
                                setGraphic(null);
                            } else {
                                setGraphic(btn);
                            }
                        }
                    };
                    return cell;
                }
            };

            colBtn.setCellFactory(cellFactory);

            t.getColumns().add(colBtn);

        }




    private void addButtonToTableRechazar(TableView t,String codigoAeropuerto) {
        TableColumn<Data, Void> colBtn = new TableColumn("Rechazar");

        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Rechazar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            VueloDTO vuelo = (VueloDTO) getTableView().getItems().get(getIndex());
                            String codigoVuelo = vuelo.getCodigoVuelo();
                            ResponseEntity response = vueloRestService.rechazarVuelo(codigoVuelo);


                            ResponseEntity response1 = aeropuertoRestService.getListaVuelosSinConfirmarLlegada(codigoAeropuerto);
                            ResponseEntity response2 = aeropuertoRestService.getListaVuelosSinConfirmarSalida(codigoAeropuerto);

                            List vueloDTOS = (List) response1.getBody();
                            List<VueloDTO> vuelosLle = new ArrayList<>();

                            List vueloDTOS2 = (List) response2.getBody();
                            List<VueloDTO> vuelosSal = new ArrayList<>();


                            for (int i=0;i<vueloDTOS.size();i++){
                                VueloDTO vueloDTO = new VueloDTO();
                                LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
                                vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
                                vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
                                vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
                                vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
                                vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
                                vuelosLle.add(vueloDTO);
                            }

                            for (int i=0;i<vueloDTOS2.size();i++){
                                VueloDTO vueloDTO = new VueloDTO();
                                LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS2.get(i);
                                vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
                                vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoOrigen"));
                                vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
                                vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
                                vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
                                vuelosSal.add(vueloDTO);
                            }

                            ObservableList<VueloDTO> vuelosLlegada = FXCollections.observableArrayList(vuelosLle);
                            ObservableList<VueloDTO> vuelosSalida = FXCollections.observableArrayList(vuelosSal);


                            matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                            matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
                            matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
                            ETAllegada.setCellValueFactory(new PropertyValueFactory<>("ETA"));
                            EDTllegada.setCellValueFactory(new PropertyValueFactory<>("EDT"));
                            tablaLlegada.setItems(vuelosLlegada);

                            matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                            matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
                            matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
                            ETAsalida.setCellValueFactory(new PropertyValueFactory<>("ETA"));
                            EDTsalida.setCellValueFactory(new PropertyValueFactory<>("EDT"));
                            tablaSalida.setItems(vuelosSalida);





                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        t.getColumns().add(colBtn);

    }




    @FXML
    void backToAdminAerolinea(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdminAerolinea.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void backToInicioDios(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("InicioAdministradorDios.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void logOut(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("IniciarSesion.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void redireccion(ActionEvent event, String fxml, String codigoAerolinea) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream(fxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(codigoAerolinea != null){
            stage.setUserData(codigoAerolinea);
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarAgregarVuelo(ActionEvent event, String fxml, String codigoAerolinea) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream(fxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        if(codigoAerolinea != null){
            stage.setUserData(codigoAerolinea);
        }

        ResponseEntity response1 = aeropuertoRestService.getAeropuertos();
        List listaAeropuertos = (List) response1.getBody();

        ResponseEntity response2 = avionRestService.getAviones();
        List listaAviones = (List) response2.getBody();




        for (int i=0; i<listaAeropuertos.size();i++) {
            LinkedHashMap aeropuerto = (LinkedHashMap) listaAeropuertos.get(i);
            codigoIATAeropuertoDestino.getItems().addAll((String) aeropuerto.get("codigoIATAAeropuerto"));
            codigoIATAeropuertoOrigen.getItems().addAll((String) aeropuerto.get("codigoIATAAeropuerto"));
        }
        //Hacer que se vean los aviones de cada aerolinea
        for (int j=0;j<listaAviones.size();j++){
            LinkedHashMap avion = (LinkedHashMap) listaAviones.get(j);
            matriculaBox.getItems().addAll((String) avion.get("matricula"));
        }



        stage.setScene(scene);
        stage.show();
    }




    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }


    @FXML
    private ComboBox<String> matriculaBox;
    @FXML
    private ComboBox<String> codigoIATAeropuertoDestino;
    @FXML
    private ComboBox<String> codigoIATAeropuertoOrigen;
    @FXML
    private TextField codigoIATAAvuelo;

    @Autowired
    private VueloRestService vueloRestService;


    @FXML
    private TextField yyyyETA;

    @FXML
    private TextField MMETA;

    @FXML
    private TextField ddETA;

    @FXML
    private TextField HHETA;

    @FXML
    private TextField mmETA;

    @FXML
    private TextField yyyyEDT;

    @FXML
    private TextField MMEDT;

    @FXML
    private TextField ddEDT;

    @FXML
    private TextField HHEDT;

    @FXML
    private TextField mmEDT;
    @Transactional
    @FXML
    void registrarVuelo(ActionEvent event){

        //poner los controles de las fechas
        //ETA>EDT cambiar en FXML
        String anoETA = yyyyETA.getText();
        String mesETA = MMETA.getText();
        String diaETA = ddETA.getText();
        String horaETA = HHETA.getText();
        String minutoETA = mmETA.getText();

        int anoIntETA = Integer.parseInt(anoETA);
        int mesIntETA = Integer.parseInt(mesETA);
        int diaIntETA = Integer.parseInt(diaETA);
        int horaIntETA = Integer.parseInt(horaETA);
        int minutoIntETA = Integer.parseInt(minutoETA);

        LocalDateTime localDateTimeETA = LocalDateTime.of(anoIntETA,mesIntETA,diaIntETA,horaIntETA,minutoIntETA);
        System.out.println(localDateTimeETA);

        String anoEDT = yyyyEDT.getText();
        String mesEDT = MMEDT.getText();
        String diaEDT = ddEDT.getText();
        String horaEDT = HHEDT.getText();
        String minutoEDT = mmEDT.getText();

        int anoIntEDT = Integer.parseInt(anoEDT);
        int mesIntEDT = Integer.parseInt(mesEDT);
        int diaIntEDT = Integer.parseInt(diaEDT);
        int horaIntEDT = Integer.parseInt(horaEDT);
        int minutoIntEDT = Integer.parseInt(minutoEDT);

        LocalDateTime localDateTimeEDT = LocalDateTime.of(anoIntEDT,mesIntEDT,diaIntEDT,horaIntEDT,minutoIntEDT);
        System.out.println(localDateTimeEDT);


        String matriculaAvion = matriculaBox.getValue();
        String codigoIATAAeropDest = codigoIATAeropuertoDestino.getValue();
        String codigoIATAAeropOri = codigoIATAeropuertoOrigen.getValue();
        Long codigoIATAVue = Long.parseLong(codigoIATAAvuelo.getText());


        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        String codAerol = (String) stage.getUserData();

        if (matriculaAvion == null || matriculaAvion.equals("") ||
                codigoIATAAeropDest == null || codigoIATAAeropDest.equals("")||
                codigoIATAAeropOri == null || codigoIATAAeropOri.equals("") ||
                codigoIATAVue == null || codigoIATAVue.equals("") ) {
            showAlert("Datos faltantes!", "No se ingresaron los datos necesarios para completar el ingreso.");
        } else {

            VueloDTO vueloDTO = new VueloDTO();
            vueloDTO.setCodigoVuelo(codAerol+codigoIATAVue);
            vueloDTO.setCodigoAeropuertoDestino(codigoIATAAeropDest);
            vueloDTO.setCodigoAeropuertoOrigen(codigoIATAAeropOri);
            vueloDTO.setMatriculaAvion(matriculaAvion);
            vueloDTO.setCodigoAerolinea(codAerol);
            vueloDTO.setAceptadoOrigen(false);
            vueloDTO.setAcepradoDestino(false);
            vueloDTO.setRechadado(false);
            vueloDTO.setEDT(localDateTimeEDT);
            vueloDTO.setETA(localDateTimeETA);

            ResponseEntity response = vueloRestService.agregarVuelo(vueloDTO);

            if (response.getStatusCode() == HttpStatus.OK) {
                showAlert("Vuelo agregado", "Se agrego con exito el vuelo!");
            }

        }
    }

}