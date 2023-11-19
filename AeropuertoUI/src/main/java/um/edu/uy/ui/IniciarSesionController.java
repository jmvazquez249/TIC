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
import um.edu.uy.*;
import um.edu.uy.Objects.Maleta;
import um.edu.uy.Objects.Oficina;
import um.edu.uy.Objects.Pasaporte;
import um.edu.uy.service.AerolineaRestService;
import um.edu.uy.service.AeropuertoRestService;
import um.edu.uy.service.AvionRestService;
import um.edu.uy.service.UsuarioGeneralRestService;
import um.edu.uy.service.VueloRestService;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    private TextField capacidadBulto;
    @FXML
    private TextField ciudad;
    @FXML
    private TextField pais;
    @FXML
    private TextField codigoICAO;
    @FXML
    private TextField paisAero;


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
                redireccion(event, "BuscarVuelo.fxml", usu.getCodigoAerolinea());
            } else if (tipoUsuario.equals("OFICINA")) {
                Oficina oficina = new Oficina();
                oficina.setCodigoAerolinea(usu.getCodigoAerolinea());
                cargarAgregarVuelo(event, "Oficinista.fxml", oficina);
            } else if (tipoUsuario.equals("MALETERIA")) {
                redireccion(event, "Maletero.fxml", usu.getCodigoAeropuerto());
            } else if (tipoUsuario.equals("BOARDING")) {
                redireccion(event, "Boarding.fxml", usu.getCodigoAeropuerto());
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

            if (aeropuertoDTO == null) {
                showAlert(
                        "Aeropuerto No existe",
                        "El aeropuerto ingresado no existe");
            }

            else if(pasaporteAdAero == 0 || nombreAdAero.equals("") || apellidoAdAero.equals("") || emailAdAero.equals("") || contrasenaAdAero.equals("") || codigoAeropuertoAdAero.equals("")){
                showAlert("Datos incorrectos", "Los datos ingresados no son correctos");


            } else {
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
        } catch (Exception e) {
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }
    }

    @Transactional
    @FXML
    void registrarCliente(ActionEvent event) {
        try {
            String nombreUsu = nombre.getText();
            String apellidoUsu = apellido.getText();
            String contrasenaUsu = contrasena.getText();
            String emailUsu = email.getText();
            long pasaporteUsu = Long.parseLong(pasaporte.getText());
            if (pasaporteUsu == 0 || nombreUsu.equals("") || apellidoUsu.equals("") || emailUsu.equals("") || contrasenaUsu.equals("")) {
                showAlert("Datos incorrectos", "Los datos ingresados no son correctos");
            }
            else {


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


        } catch (Exception e) {
            System.out.println(e);
            showAlert(
                    "ERROR!",
                    "No se ingresaron los datos necesarios para completar el ingreso o algun dato es incorrecto");
        }

    }

    @FXML
    private TextField cantidadPuertas;

    @Transactional
    @FXML
    void registrarAeropuerto(ActionEvent event) {
        try {
            String nombreAero = nombreAeropuerto.getText();
            String codigoIATAAero = codigoIATAAeropuerto.getText();
            String ciudadAero = ciudad.getText();
            String paisAero = pais.getText();
            String cantPuertas = cantidadPuertas.getText();
            Long cantidadPuertas = Long.parseLong(cantPuertas);

            if (!codigoIATAAero.matches("^[A-Za-z]{2}$")) {
                showAlert(
                        "Error en el codigo IATA",
                        "El codigo IATA ingresado no es valido");
            } else if (cantidadPuertas == 0 || nombreAero.equals("") || codigoIATAAero.equals("") || ciudadAero.equals("") || paisAero.equals("")) {
                showAlert("Datos incorrectos", "Los datos ingresados no son correctos");
            } else {

                AeropuertoDTO aeropuertoDTO = new AeropuertoDTO();
                aeropuertoDTO.setCodigoIATAAeropuerto(codigoIATAAero);
                aeropuertoDTO.setNombre(nombreAero);
                aeropuertoDTO.setCiudad(ciudadAero);
                aeropuertoDTO.setPais(paisAero);
                aeropuertoDTO.setCantidadPuertas(cantidadPuertas);

                ResponseEntity response = aeropuertoRestService.agregarAeropuerto(aeropuertoDTO);

                if (response.getStatusCode() == HttpStatus.OK) {
                    showAlert("Aeropuerto agregado", "Se agrego con exito el aeropuerto!");

                }

            }
        } catch (Exception e) {
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
            int capacidadBultoAv = Integer.parseInt(capacidadBulto.getText());
            //la capacidad de asintos no puede ser mayor a 1000 y cantidad de bulto mayor a 10000 pero que se rompa y no lo agregue
            if (capacidadAv > 1000 || capacidadBultoAv > 10000) {
                showAlert(
                        "Error en la capacidad",
                        "La capacidad de asientos no puede ser mayor a 1000 y la capacidad de bultos no puede ser mayor a 10000");
            } else if(modeloAv.equals("") || matriculaAv.equals("") || capacidadAv == 0 || capacidadBultoAv == 0){
                showAlert("Datos incorrectos", "Los datos ingresados no son correctos");
            }

            else {


                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                String codigoAerolinea = (String) stage.getUserData();

                AvionDTO avionDTO = new AvionDTO();
                avionDTO.setCapacidad(capacidadAv);
                avionDTO.setMatricula(matriculaAv);
                avionDTO.setModelo(modeloAv);
                avionDTO.setCodigoAeroliena(codigoAerolinea);
                avionDTO.setCapacidadBulto(capacidadBultoAv);

                ResponseEntity response = aerolineaRestService.agregarAvionAerolinea(avionDTO);
                if (response.getStatusCode() == HttpStatus.OK) {
                    showAlert("Avion agregado", "Se agrego con exito el avion!");
                }

            }
        } catch (Exception e) {
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
            String paisAerolinea = paisAero.getText();
            String codigoICAOAero = codigoICAO.getText();

            ResponseEntity response3 = aerolineaRestService.getAerolinea(codigoIATAAerol);
            AerolineaDTO aerolineaDTOCodigo = (AerolineaDTO) response3.getBody();

            ResponseEntity response6 = aerolineaRestService.getAerolineaICAO(codigoICAOAero);
            AerolineaDTO aerolineaDTOICAO = (AerolineaDTO) response6.getBody();


            if (!codigoICAOAero.matches("^[A-Za-z]{3}$") || !codigoIATAAerol.matches("^[A-Za-z]{2}$")) {
                showAlert(
                        "Error en el codigo IATA o ICAO",
                        "El codigo IATA o ICAO ingresado no es valido");

            } else if (nombreAero.equals("") || codigoIATAAerol.equals("") || paisAerolinea.equals("") || codigoICAOAero.equals("")) {
                showAlert("Datos incorrectos", "Los datos ingresados no son correctos");
            }
            else {
                AerolineaDTO aerolineaDTO = new AerolineaDTO();
                aerolineaDTO.setCodigoIATAAerolinea(codigoIATAAerol);
                aerolineaDTO.setNombre(nombreAero);
                aerolineaDTO.setPaisAero(paisAerolinea);
                aerolineaDTO.setCodigoICAO(codigoICAOAero);

                ResponseEntity response1 = aerolineaRestService.agregarAerolinea(aerolineaDTO);


                if (response1.getStatusCode() == HttpStatus.OK) {
                    showAlert("Aerolinea agregado", "Se agrego con exito la aerolinea!");
                }

            }
        } catch (Exception e) {
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


            //tienen que estar en el servidor
            ResponseEntity response3 = aerolineaRestService.getAerolinea(codigoIATAAerol);
            AerolineaDTO aerolineaDTOCodigo = (AerolineaDTO) response3.getBody();


            if (aerolineaDTOCodigo == null) {
                showAlert(
                        "Aerolinea no registrada",
                        "La aerolinea ingresada no esta registrada");

            }else if(pasaporteUsu == 0 || nombreUsu.equals("") || apellidoUsu.equals("") || emailUsu.equals("") || contrasenaUsu.equals("") || codigoIATAAerol.equals("")){
                showAlert("Datos incorrectos", "Los datos ingresados no son correctos");
            }
            else {

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
        } catch (Exception e) {
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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarRegistrarAdministradorAeropuerto(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAdministradorAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarAgregarAeropuerto(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarRegistroCliente(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("RegistroCliente.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void cargarAgregarAerolinea(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAerolinea.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarAgregarAvion(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AgregarAvion.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarAgregarUsuarioAerolinea(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(AgregarUsuarioAerolineaController.class.getResourceAsStream("AgregarUsuarioAerolinea.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void cargarAgregarUsuarioAeropuerto(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(AgregarUsuarioAeropuertoController.class.getResourceAsStream("agregarUsuarioAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
    public TableColumn<VueloDTO, String> EDTllegada;
    @FXML
    public TableColumn<VueloDTO, String> ETAllegada;
    @FXML
    public TableColumn<VueloDTO, String> EDTsalida;
    @FXML
    public TableColumn<VueloDTO, String> ETAsalida;
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

        Parent root = fxmlLoader.load(AgregarUsuarioAeropuertoController.class.getResourceAsStream("AdministradorAeropuerto.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (codigoAeropuerto != null) {
            stage.setUserData(codigoAeropuerto);
        }
        stage.setScene(scene);

        ResponseEntity response1 = aeropuertoRestService.getListaAeropuertosLlegada(codigoAeropuerto);
        ResponseEntity response2 = aeropuertoRestService.getListaAeropuertosSalida(codigoAeropuerto);

        List vueloDTOS = (List) response1.getBody();
        List vueloDTOS2 = (List) response2.getBody();

        List<VueloDTO> vuelosLle = new ArrayList<>();
        List<VueloDTO> vuelosSal = new ArrayList<>();

        for (int i = 0; i < vueloDTOS.size(); i++) {
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

        for (int j = 0; j < vueloDTOS2.size(); j++) {
            VueloDTO vueloDTO2 = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS2.get(j);
            vueloDTO2.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO2.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO2.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO2.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vueloDTO2.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
            vueloDTO2.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
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
    private TableColumn<VueloDTO, String> aeropuertoOrigen;
    @FXML
    private TableColumn<VueloDTO, String> codigoVueloLlegada;
    @FXML
    private TableColumn<VueloDTO, String> matriculaAvionLlegada;
    @FXML
    private TableView<VueloDTO> tablaSalida;
    @FXML
    private TableColumn<VueloDTO, String> aeropuertoDestino;
    @FXML
    private TableColumn<VueloDTO, String> codigoVueloSalida;
    @FXML
    private TableColumn<VueloDTO, String> matriculaAvionSalida;

    @FXML
    void cargarAdministradorVuelos(ActionEvent event, String codigoAeropuerto) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdministradorVuelos.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);

        ResponseEntity response1 = aeropuertoRestService.getListaVuelosSinConfirmarLlegada(codigoAeropuerto);
        ResponseEntity response2 = aeropuertoRestService.getListaVuelosSinConfirmarSalida(codigoAeropuerto);

        List vueloDTOS = (List) response1.getBody();
        List<VueloDTO> vuelosLle = new ArrayList<>();

        List vueloDTOS2 = (List) response2.getBody();
        List<VueloDTO> vuelosSal = new ArrayList<>();


        for (int i = 0; i < vueloDTOS.size(); i++) {
            VueloDTO vueloDTO = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
            vueloDTO.setFechaETA(LocalDate.parse((String) hashMap.get("fechaETA")));
            vueloDTO.setETA(LocalDateTime.parse((String) hashMap.get("eta")));
            vuelosLle.add(vueloDTO);
        }

        for (int j = 0; j < vueloDTOS2.size(); j++) {
            VueloDTO vueloDTO = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS2.get(j);
            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vueloDTO.setEDT(LocalDateTime.parse((String) hashMap.get("edt")));
            vueloDTO.setFechaEDT(LocalDate.parse((String) hashMap.get("fechaEDT")));
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
        addButtonToTable(tablaLlegada, true);

        codigoVueloSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoDestino.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
        matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
        ETAsalida.setCellValueFactory(new PropertyValueFactory<>("ETA"));
        EDTsalida.setCellValueFactory(new PropertyValueFactory<>("EDT"));
        tablaSalida.setItems(vuelosSalida);
        addButtonToTable(tablaSalida, false);

        stage.show();
    }

    @FXML
    private ComboBox<Long> puerta;
    @FXML
    private Label labelHora;

    @FXML
    private TableView<VueloReservaDTO> tablaVuelosReserva;
    @FXML
    private TableColumn<VueloReservaDTO, String> codVuelo;
    @FXML
    private TableColumn<VueloReservaDTO, String> numeroPuerta;
    @FXML
    private TableColumn<VueloReservaDTO, String> horaIniPuerta;
    @FXML
    private TableColumn<VueloReservaDTO, String> horaFinPuerta;
    @FXML
    private TableColumn<VueloReservaDTO, String> horaIniPista;
    @FXML
    private TableColumn<VueloReservaDTO, String> horaFinPista;


    private void addButtonToTable(TableView t, boolean llegada) {
        TableColumn<Data, Void> colBtn = new TableColumn("");

        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Seleccionar");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            VueloDTO vuelo = (VueloDTO) getTableView().getItems().get(getIndex());

                            String hora;
                            String codigoAeropuerto;
                            String fxml;
                            ReservaDTO reservaDTO = new ReservaDTO();
                            if (llegada == true) {
                                codigoAeropuerto = vuelo.getCodigoAeropuertoDestino();
                                fxml = "ReservaPuertaPistaLlegada.fxml";
                                hora = String.valueOf(vuelo.getETA());
                                reservaDTO.setFecha(vuelo.getFechaETA());
                                reservaDTO.setCodigoAeropuerto(codigoAeropuerto);
                            } else {
                                codigoAeropuerto = vuelo.getCodigoAeropuertoOrigen();
                                fxml = "ReservaPuertaPistaSalida.fxml";
                                hora = String.valueOf(vuelo.getEDT());
                                reservaDTO.setFecha(vuelo.getFechaEDT());
                                reservaDTO.setCodigoAeropuerto(codigoAeropuerto);
                            }
                            ResponseEntity response = aeropuertoRestService.getAeropuerto(codigoAeropuerto);


                            AeropuertoDTO aeroOrigen = (AeropuertoDTO) response.getBody();
                            List<Long> codigoPuertas = aeroOrigen.getPuertas();


                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setControllerFactory(Main.getContext()::getBean);

                            Parent root;
                            try {
                                root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream(fxml));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Scene scene = new Scene(root);

                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);

                            ArrayList arrayList = new ArrayList();
                            arrayList.add(vuelo.getCodigoVuelo());
                            arrayList.add(llegada);
                            arrayList.add(codigoAeropuerto);
                            stage.setUserData(arrayList);

                            puerta.getItems().addAll(codigoPuertas);

                            labelHora.setText(hora);

                            ResponseEntity response1 = vueloRestService.getVueloReserva(reservaDTO);

                            List reservaVuelos = (List) response1.getBody();
                            List<VueloReservaDTO> resVuelos = new ArrayList<>();

                            for (int i = 0; i < reservaVuelos.size(); i++) {
                                VueloReservaDTO resVuelo = new VueloReservaDTO();
                                LinkedHashMap hashMap = (LinkedHashMap) reservaVuelos.get(i);
                                resVuelo.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
                                resVuelo.setNumeroPuerta(Long.valueOf(String.valueOf(hashMap.get("numeroPuerta"))));
                                resVuelo.setHoraFinPuerta(LocalTime.parse((String) hashMap.get("horaFinPuerta")));
                                resVuelo.setHoraInicioPuerta(LocalTime.parse((String) hashMap.get("horaInicioPuerta")));
                                resVuelo.setHoraInicioPista(LocalTime.parse((String) hashMap.get("horaInicioPista")));
                                resVuelo.setHoraFinPista(LocalTime.parse((String) hashMap.get("horaFinPista")));
                                resVuelos.add(resVuelo);
                            }

                            ObservableList<VueloReservaDTO> vuelosRes = FXCollections.observableArrayList(resVuelos);


                            codVuelo.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                            numeroPuerta.setCellValueFactory(new PropertyValueFactory<>("numeroPuerta"));
                            horaIniPuerta.setCellValueFactory(new PropertyValueFactory<>("horaInicioPuerta"));
                            horaFinPuerta.setCellValueFactory(new PropertyValueFactory<>("horaFinPuerta"));
                            horaIniPista.setCellValueFactory(new PropertyValueFactory<>("horaInicioPista"));
                            horaFinPista.setCellValueFactory(new PropertyValueFactory<>("horaFinPista"));
                            tablaVuelosReserva.setItems(vuelosRes);

                            stage.show();

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
    void backAdminVuelos(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        List list = (List) stage.getUserData();
        cargarAdministradorVuelos(event, (String) list.get(2));
    }

    @FXML
    private TextField horaFinalizacionPuerta;
    @FXML
    private TextField minutoFinalizacionPuerta;
    @FXML
    private TextField horaFinalizacionPista;
    @FXML
    private TextField minutoFinalizacionPista;

    @FXML
    private void confirmarReservasLlegada(ActionEvent event) throws IOException {

        Long numeroPuerta = puerta.getValue();

        int horaFinPuerta = Integer.parseInt(horaFinalizacionPuerta.getText());
        int minFinPuerta = Integer.parseInt(minutoFinalizacionPuerta.getText());
        LocalTime timeFinPuerta = LocalTime.of(horaFinPuerta, minFinPuerta);

        int horaFinPista = Integer.parseInt(horaFinalizacionPista.getText());
        int minFinPista = Integer.parseInt(minutoFinalizacionPista.getText());
        LocalTime timeFinPista = LocalTime.of(horaFinPista, minFinPista);


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        List list = (List) stage.getUserData();

        ReservaDTO reservaDTO = new ReservaDTO();
        reservaDTO.setNumeroPuerta(numeroPuerta);
        reservaDTO.setLocalTimeFinPista(timeFinPista);
        reservaDTO.setLocalTimeFinPuerta(timeFinPuerta);
        reservaDTO.setLlegada((boolean) list.get(1));
        reservaDTO.setCodigoVuelo((String) list.get(0));

        try {
            ResponseEntity response = vueloRestService.aceptarYReservar(reservaDTO);
            if (response.getStatusCode() == HttpStatus.OK) {
                stage.setUserData(list.get(2));
                cargarAdministradorVuelos(event, (String) list.get(2));
                showAlert("Confirmacion Reserva", "La reserva fue exitosa");
            }
        }catch (HttpClientErrorException error){
            if(error.getStatusCode()==HttpStatus.CONFLICT){
                showAlert("Error","No es posible realizar reserva");
            }
        }




    }

    @FXML
    private void rechazarVuelo(ActionEvent event) throws IOException {


        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        List list = (List) stage.getUserData();

        ResponseEntity response = vueloRestService.rechazarVuelo((String) list.get(0));
        if (response.getStatusCode() == HttpStatus.OK) {
            showAlert("Vuelo Rechazado", "El vuelo fue rechazado con exito");
        }

        stage.setUserData(list.get(2));
        cargarAdministradorVuelos(event, (String) list.get(2));

    }

    @FXML
    private TableView<VueloDTO> tablaVuelosConfirmadosAero;
    @FXML
    private TableColumn<VueloDTO, String> codigoVueloConfirmadoAero;
    @FXML
    private TableColumn<VueloDTO, String> aeropuertoOrigenConfirmadoAero;
    @FXML
    private TableColumn<VueloDTO, String> aeropuertoDestinoConfirmadoAero;
    @FXML
    private TableColumn<VueloDTO, String> matriculaAvionConfirmadoAero;

    @FXML
    void backToVuelosConfirmados(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Oficina oficina = (Oficina) stage.getUserData();
        cargarAgregarVuelo(event, "Oficinista.fxml", oficina);
    }

    @FXML
    void backToUsuarioAerolinea(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Oficina oficina = (Oficina) stage.getUserData();
        cargarAgregarVuelo(event, "UsuarioAerolinea.fxml", oficina);
    }

    private void addButtonToTablePasajeros(TableView t) {
        TableColumn<Data, Void> colBtn = new TableColumn("Agregar Pasajero");

        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Agregar Pasajero");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            VueloDTO vuelo = (VueloDTO) getTableView().getItems().get(getIndex());
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Oficina oficina = (Oficina) stage.getUserData();
                            String codigoVuelo = vuelo.getCodigoVuelo();
                            oficina.setCodigoVuelo(codigoVuelo);
                            stage.setUserData(oficina);
                            try {
                                redireccion2(event, "AgregarPasajero.fxml", oficina);
                            } catch (IOException e) {
                                e.printStackTrace();
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

    @FXML
    private TextField pasaportePasajero;

    //boton que agrega un pasajero al vuelo y le determian automaticamente un numero de asiento
    @FXML
    void agregarPasajero(ActionEvent event) {
        try {
            Oficina oficina = (Oficina) ((Stage) ((Node) event.getSource()).getScene().getWindow()).getUserData();
            AgregarPasajeroDTO agregarPasajeroDTO = new AgregarPasajeroDTO();
            agregarPasajeroDTO.setCodigoVuelo(oficina.getCodigoVuelo());
            agregarPasajeroDTO.setPasaporte(Long.parseLong(pasaportePasajero.getText()));
            ResponseEntity response = vueloRestService.agregarPasajero(agregarPasajeroDTO);
            if (response.getStatusCode() == HttpStatus.OK) {
                showAlert("Exito!", "Se agrego el pasajero al vuelo");
            }
        } catch (Exception e) {
            showAlert("Error", "No se pudo agregar pasajero");
        }


    }


    @FXML
    void backToAdminAerolinea(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdminAerolinea.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void backToInicioDios(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("InicioAdministradorDios.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logOut(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("IniciarSesion.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setUserData(null);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void redireccion(ActionEvent event, String fxml, String codigoAerolinea) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream(fxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (codigoAerolinea != null) {
            stage.setUserData(codigoAerolinea);
        }
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void redireccion2(ActionEvent event, String fxml, Oficina oficina) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream(fxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (oficina != null) {
            stage.setUserData(oficina);
        }
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void cargarAgregarVuelo(ActionEvent event, String fxml, Oficina oficina) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream(fxml));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (oficina != null) {
            stage.setUserData(oficina);
        }

        ResponseEntity response1 = aeropuertoRestService.getAeropuertos();
        List listaAeropuertos = (List) response1.getBody();

        ResponseEntity response2 = avionRestService.getAviones(oficina.getCodigoAerolinea());
        List listaAviones = (List) response2.getBody();


        for (int i = 0; i < listaAeropuertos.size(); i++) {
            LinkedHashMap aeropuerto = (LinkedHashMap) listaAeropuertos.get(i);
            codigoIATAeropuertoDestino.getItems().addAll((String) aeropuerto.get("codigoIATAAeropuerto"));
            codigoIATAeropuertoOrigen.getItems().addAll((String) aeropuerto.get("codigoIATAAeropuerto"));
        }
        //Hacer que se vean los aviones de cada aerolinea
        for (int j = 0; j < listaAviones.size(); j++) {
            LinkedHashMap avion = (LinkedHashMap) listaAviones.get(j);
            matriculaBox.getItems().addAll((String) avion.get("matricula"));
        }

        ResponseEntity response = aerolineaRestService.getListaVuelosConfirmadosAero(oficina.getCodigoAerolinea());

        List vueloDTOS = (List) response.getBody();
        List<VueloDTO> vuelos = new ArrayList<>();

        for (int i = 0; i < vueloDTOS.size(); i++) {
            VueloDTO vueloDTO = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vuelos.add(vueloDTO);
        }
        ObservableList<VueloDTO> vuelosConfirmados = FXCollections.observableArrayList(vuelos);

        codigoVueloConfirmadoAero.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigenConfirmadoAero.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
        aeropuertoDestinoConfirmadoAero.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
        matriculaAvionConfirmadoAero.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
        tablaVuelosConfirmadosAero.setItems(vuelosConfirmados);
        addButtonToTablePasajeros(tablaVuelosConfirmadosAero);

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
    private TextField HHETA;

    @FXML
    private TextField mmETA;

    @FXML
    private TextField HHEDT;

    @FXML
    private TextField mmEDT;

    @FXML
    private DatePicker fechaETA;

    @FXML
    private DatePicker fechaEDT;

    @Transactional
    @FXML
    void registrarVuelo(ActionEvent event) {
        try {
            String horaETA = HHETA.getText();
            String minutoETA = mmETA.getText();
            int horaIntETA = Integer.parseInt(horaETA);
            int minutoIntETA = Integer.parseInt(minutoETA);
            LocalTime timeETA = LocalTime.of(horaIntETA, minutoIntETA);
            LocalDate fecETA = fechaETA.getValue();

            LocalDateTime localDateTimeETA = LocalDateTime.of(fecETA, timeETA);

            String horaEDT = HHEDT.getText();
            String minutoEDT = mmEDT.getText();
            int horaIntEDT = Integer.parseInt(horaEDT);
            int minutoIntEDT = Integer.parseInt(minutoEDT);
            LocalTime timeEDT = LocalTime.of(horaIntEDT, minutoIntEDT);
            LocalDate fecEDT = fechaEDT.getValue();

            LocalDateTime localDateTimeEDT = LocalDateTime.of(fecEDT, timeEDT);

            String matriculaAvion = matriculaBox.getValue();
            String codigoIATAAeropDest = codigoIATAeropuertoDestino.getValue();
            String codigoIATAAeropOri = codigoIATAeropuertoOrigen.getValue();
            Long codigoIATAVue = Long.parseLong(codigoIATAAvuelo.getText());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Oficina oficina = (Oficina) stage.getUserData();

            if (localDateTimeEDT.isAfter(localDateTimeETA)) {
                showAlert("Datos incorrectos!", "La fecha de llegada no puede ser anterior a la de salida.");
            } else {
                VueloDTO vueloDTO = new VueloDTO();
                vueloDTO.setCodigoVuelo(oficina.getCodigoAerolinea() + codigoIATAVue);
                vueloDTO.setCodigoAeropuertoDestino(codigoIATAAeropDest);
                vueloDTO.setCodigoAeropuertoOrigen(codigoIATAAeropOri);
                vueloDTO.setMatriculaAvion(matriculaAvion);
                vueloDTO.setCodigoAerolinea(oficina.getCodigoAerolinea());
                vueloDTO.setAceptadoOrigen(false);
                vueloDTO.setAcepradoDestino(false);
                vueloDTO.setRechadado(false);
                vueloDTO.setFechaEDT(fecEDT);
                vueloDTO.setHoraEDT(timeEDT);
                vueloDTO.setHoraETA(timeETA);
                vueloDTO.setFechaETA(fecETA);

                ResponseEntity response = vueloRestService.agregarVuelo(vueloDTO);

                if (response.getStatusCode() == HttpStatus.OK) {
                    showAlert("Vuelo agregado", "Se agrego con exito el vuelo!");
                }

            }
        } catch (Exception e) {
            showAlert("Datos incorrectos!", "Algun dato ingresado es incorrecto.");
        }
    }

    @FXML
    private TableView<Pasaporte> tablaCheckIn;

    @FXML
    private TableColumn<Pasaporte, Long> columnaPasaporte;

    @FXML
    private TextField codigoVuelo;

    @FXML
    void buscarVuelo(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String codigoAerolinea = (String) stage.getUserData();
        String codVuelo = codigoVuelo.getText();

        if (codVuelo.equals("") || codVuelo == null) {

            showAlert("Error", "Error en los datos ingresados");

        } else {
            CheckInDTO checkInDTO = new CheckInDTO();
            checkInDTO.setCodigoVuelo(codVuelo);
            checkInDTO.setCodigoAerolinea(codigoAerolinea);
            try {
                ResponseEntity response = vueloRestService.getPasaportes(checkInDTO);
                if (response.getStatusCode() == HttpStatus.OK) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setControllerFactory(Main.getContext()::getBean);

                    Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("CheckInVuelo.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    MaletasDTO maletasDTO = new MaletasDTO();
                    maletasDTO.setCodigoVuelo(codVuelo);
                    maletasDTO.setCodigoAerolinea(codigoAerolinea);
                    stage.setUserData(maletasDTO);

                    List<Long> list = (List<Long>) response.getBody();
                    List<Pasaporte> listaPasaportes = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Pasaporte pas = new Pasaporte(Long.parseLong(String.valueOf(list.get(i))));
                        listaPasaportes.add(pas);
                    }
                    ObservableList<Pasaporte> pasaportes = FXCollections.observableArrayList(listaPasaportes);
                    columnaPasaporte.setCellValueFactory(new PropertyValueFactory<>("valor"));
                    tablaCheckIn.setItems(pasaportes);
                    addButtonToTablePasaportes(tablaCheckIn);
                    stage.show();
                }
            } catch (HttpClientErrorException error) {
                if (error.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    showAlert("Error", "El vuelo no fue creado por tu aerolinea");
                } else if (error.getStatusCode() == HttpStatus.CONFLICT) {
                    showAlert("Error", "No existe vuelo");

                }
            }
        }


    }

    @FXML
    private TextField cantidadMaletas;

    private void addButtonToTablePasaportes(TableView t) {
        TableColumn<Data, Void> colBtn = new TableColumn("");
        colBtn.setMinWidth(127);

        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Check In");


                    {

                        btn.setOnAction((ActionEvent event) -> {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setControllerFactory(Main.getContext()::getBean);

                            Parent root = null;
                            try {
                                root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("MaletasCheckIn.fxml"));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Scene scene = new Scene(root);
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);

                            Pasaporte pas = (Pasaporte) getTableView().getItems().get(getIndex());


                            MaletasDTO maletasDTO = (MaletasDTO) stage.getUserData();
                            maletasDTO.setPasaporte(pas.getValor());
                            stage.setUserData(maletasDTO);

                            stage.show();

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
    private TableView<Pasaporte> tablaBoarding;
    @FXML
    private TableColumn<Pasaporte, Long> columnaPasaporteBoarding;
    @FXML
    private TextField codigoVueloBoarding;

    @FXML
    void buscarVueloBoarding(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        String codVuelo = codigoVueloBoarding.getText();
        String codigoAeropuerto = stage.getUserData().toString();

        if (codVuelo.equals("") || codVuelo == null) {
            showAlert("Error", "Error en los datos ingresados");
        } else {

            BoardingDTO boardingDTO = new BoardingDTO();
            boardingDTO.setCodigoVuelo(codVuelo);
            boardingDTO.setCodigoAeropuerto(codigoAeropuerto);

            try {
                ResponseEntity response = vueloRestService.getPasaportesBoarding(boardingDTO);
                if (response.getStatusCode() == HttpStatus.OK) {

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setControllerFactory(Main.getContext()::getBean);

                    Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("BoardingVuelo.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                    List<Long> list = (List<Long>) response.getBody();
                    List<Pasaporte> listaPasaportes = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        Pasaporte pas = new Pasaporte(Long.parseLong(String.valueOf(list.get(i))));
                        listaPasaportes.add(pas);
                    }
                    stage.setUserData(codigoAeropuerto);
                    ObservableList<Pasaporte> pasaportes = FXCollections.observableArrayList(listaPasaportes);
                    columnaPasaporteBoarding.setCellValueFactory(new PropertyValueFactory<>("valor"));
                    tablaBoarding.setItems(pasaportes);
                    stage.setUserData(boardingDTO);
                    addButtonToTableBoarding(tablaBoarding);
                    stage.show();
                }
            } catch (HttpClientErrorException error) {
                if (error.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    showAlert("Error", "El vuelo no sale del aeropuerto");
                } else if (error.getStatusCode() == HttpStatus.CONFLICT) {
                    showAlert("Error", "No existe vuelo");

                }
            }
        }

    }

    private void addButtonToTableBoarding(TableView<Pasaporte> t) {
        TableColumn<Pasaporte, Void> colBtn = new TableColumn("");
        colBtn.setMinWidth(127);

        Callback<TableColumn<Pasaporte, Void>, TableCell<Pasaporte, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Pasaporte, Void> call(final TableColumn<Pasaporte, Void> param) {
                final TableCell<Pasaporte, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Boarding");
                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            BoardingDTO boardingDTO = (BoardingDTO) stage.getUserData();
                            Pasaporte pas = getTableView().getItems().get(getIndex());
                            AgregarPasajeroDTO agregarPasajeroDTO = new AgregarPasajeroDTO();
                            agregarPasajeroDTO.setCodigoVuelo(boardingDTO.getCodigoVuelo());
                            agregarPasajeroDTO.setPasaporte(pas.getValor());
                            ResponseEntity response = vueloRestService.Boarding(agregarPasajeroDTO);
                            if (response.getStatusCode() == HttpStatus.OK) {
                                showAlert("Boarding", "Pasaporte procesado");
                            }
                            try {
                                buscarVueloBoarding(event);
                            } catch (IOException e) {
                                e.printStackTrace();
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
    @FXML
    void backToBoarding(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);
        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("Boarding.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        BoardingDTO boardingDTO = (BoardingDTO) stage.getUserData();
        stage.setUserData(boardingDTO.getCodigoAeropuerto());
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void agregarMeletas(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MaletasDTO maletasDTO = (MaletasDTO) stage.getUserData();
        long cantMaletas = Long.parseLong(cantidadMaletas.getText());
        //no puedeen haber mas de 5 maletas por persona
        if (cantMaletas > 5) {
            showAlert("Error", "No puede haber mas de 5 maletas por persona");
            return;
        } else {
            maletasDTO.setCantidadMaletas(cantMaletas);

            ResponseEntity response = vueloRestService.agregarMaletas(maletasDTO);

            if (response.getStatusCode() == HttpStatus.OK) {
                showAlert("Check In", "Pasaporte procesado");
            }

            buscarVuelo(event);
        }
    }

    @FXML
    public void backBuscarVuelo(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MaletasDTO maletasDTO = (MaletasDTO) stage.getUserData();
        stage.setUserData(maletasDTO.getCodigoAerolinea());
        redireccion(event, "BuscarVuelo.fxml", null);
    }


    @FXML
    private TableView<Maleta> tablaMaletas;
    @FXML
    private TableColumn<Maleta, Long> columnaPasaporteMaletas;
    @FXML
    private TextField codigoVueloMaletas;

    @FXML
    void buscarVueloMaletero(ActionEvent event) throws IOException {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();


            String codigoAeropuerto = (String) stage.getUserData();
            String codVuelo = codigoVueloMaletas.getText();
            if (codVuelo.equals("") || codVuelo == null) {
                showAlert("Error", "Error en los datos ingresados");
            }
            else {

                try{
                        AgregarMaletasDTO agregarMaletasDTO = new AgregarMaletasDTO();
                        agregarMaletasDTO.setCodigoVueloMaletero(codVuelo);
                        agregarMaletasDTO.setCodigoAeropuertoMaletero(codigoAeropuerto);

                        ResponseEntity response = vueloRestService.getVueloMaletero(agregarMaletasDTO);

                        if(response.getStatusCode()==HttpStatus.OK) {
                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setControllerFactory(Main.getContext()::getBean);
                            Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("SubirBajarMaletas.fxml"));
                            Scene scene = new Scene(root);
                            stage.setScene(scene);


                            List list = (List) response.getBody();
                            List<Maleta> listaMaletas = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                Maleta maleta = new Maleta();
                                LinkedHashMap hashMap = (LinkedHashMap) list.get(i);
                                maleta.setIdMaleta(Long.parseLong(hashMap.get("idMaleta").toString()));
                                listaMaletas.add(maleta);
                            }
                            ObservableList<Maleta> maletas = FXCollections.observableArrayList(listaMaletas);
                            columnaPasaporteMaletas.setCellValueFactory(new PropertyValueFactory<>("idMaleta"));
                            tablaMaletas.setItems(maletas);

                            addButtonToTableMaletas(tablaMaletas);


                            stage.show();
                        }
            }catch (HttpClientErrorException error) {
                    if (error.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        showAlert("Error", "Error con el vuelo ingesado");
                    } else if (error.getStatusCode() == HttpStatus.CONFLICT) {
                        showAlert("Error", "No existe vuelo");

                    }
                }

        }
    }

    @FXML
    private void backToMaletero(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String codigoAeropuerto = (String) stage.getUserData();
        redireccion(event, "Maletero.fxml",codigoAeropuerto );
    }
    private void addButtonToTableMaletas(TableView<Maleta> t) {
        TableColumn<Maleta, Void> colBtn = new TableColumn("");
        colBtn.setMinWidth(127);

        Callback<TableColumn<Maleta, Void>, TableCell<Maleta, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Maleta, Void> call(final TableColumn<Maleta, Void> param) {
                final TableCell<Maleta, Void> cell = new TableCell<>() {
                    private final Button btn = new Button("Subir/Bajar");


                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            Maleta maleta = getTableView().getItems().get(getIndex());
                            AgregarMaletasDTO agregarMaletasDTO = new AgregarMaletasDTO();
                            agregarMaletasDTO.setCodigoVueloMaletero(codigoVueloMaletas.getText());
                            agregarMaletasDTO.setCodigoAeropuertoMaletero((String) stage.getUserData());
                            agregarMaletasDTO.setIdMaleta(maleta.getIdMaleta());
                            ResponseEntity response = vueloRestService.subirBajarMaleta(agregarMaletasDTO);
                            if (response.getStatusCode() == HttpStatus.OK) {
                                showAlert("Maleta", "Maleta procesada");
                            }
                            try {
                                buscarVueloMaletero(event);
                            } catch (IOException e) {
                                e.printStackTrace();
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
}