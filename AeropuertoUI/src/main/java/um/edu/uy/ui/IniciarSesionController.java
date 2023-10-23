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
import um.edu.uy.service.AerolineaRestService;
import um.edu.uy.service.AeropuertoRestService;
import um.edu.uy.service.AvionRestService;
import um.edu.uy.service.UsuarioGeneralRestService;
import um.edu.uy.service.VueloRestService;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.*;


@Component
public class IniciarSesionController  {

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
        if (email.getText() == null || email.getText().equals("") ||
                contrasena.getText() == null || contrasena.getText().equals("")){

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        }else{
            String emailUsu = email.getText();
            String contrasenaUsu = contrasena.getText();

            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(emailUsu);
            loginDTO.setPassword(contrasenaUsu);

            ResponseEntity usuResponse = usuarioGeneralRestService.getUsuarioGeneralDTO(loginDTO);
            UsuarioGeneralDTO usu = (UsuarioGeneralDTO) usuResponse.getBody();


            if(usu!=null){
                String tipoUsuario = usu.getTipo();
                if (tipoUsuario.equals("DIOS")){
                    redireccion(event,"InicioAdministradorDios.fxml",null);
                } else if (tipoUsuario.equals("ADMINAEROPUERTO")) {
                    cargarAdministradorAeropuerto(event,  usu.getCodigoAeropuerto());
                }
                else if (tipoUsuario.equals("CLIENTE")) {
                    redireccion(event,"Cliente.fxml",null);
                }
                else if (tipoUsuario.equals("ADMINAEROLINEA")) {
                    redireccion(event,"AdminAerolinea.fxml",usu.getCodigoAerolinea());
                }
                else if (tipoUsuario.equals("CHECK IN")) {
                    redireccion(event,"CheckIn.fxml",null);
                }
                else if (tipoUsuario.equals("OFICINA")){
                    cargarAgregarVuelo(event,"UsuarioAerolinea.fxml",usu.getCodigoAerolinea());
                }
                else if (tipoUsuario.equals("MALETERIA")){
                    redireccion(event, "Maletero.fxml", null);
                }
                else if (tipoUsuario.equals("BOARDING")){
                    redireccion(event, "Boarding.fxml", null);
                }else if (tipoUsuario.equals("ADMINVUELOS")){
                    cargarAdministradorVuelos(event, usu.getCodigoAeropuerto());
                }
            } else{
                showAlert("No Existe Usuario","El usuario no esta registrado");
            }
        }
    }


    @Transactional
    @FXML
    void registrarAdministradorAeropuerto(ActionEvent event) {
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




        if(aeropuertoDTO==null){
            showAlert(
                    "Aeropuerto No existe",
                    "El aeropuerto ingresado no existe");

        }
        else if(usuarioGeneralDTOEmail!=null){
            showAlert(
                    "Email ya registrado",
                    "El email ingresado ya esta registrado");
        }
        else if(usuarioGeneralDTOPasaporte!=null){
            showAlert(
                    "Pasaporte ya registrado",
                    "El pasaporte ingresado ya esta registrado");
        }

        //funcion para controlar que exista el aeropuerto dentro de la base de datos comparandolo con el codigo del aeropuerto
        else if (pasaporte.getText() == null ||pasaporte.getText().equals("") ||
                    nombreAdAero == null || nombreAdAero.equals("") ||
                    apellidoAdAero == null || apellidoAdAero.equals("")||
                    contrasenaAdAero == null || contrasenaAdAero.equals("")||
                    codigoAeropuertoAdAero == null || codigoAeropuertoAdAero.equals("")||
                    emailAdAero == null || emailAdAero.equals("")) {

                showAlert(
                        "Datos faltantes!",
                        "No se ingresaron los datos necesarios para completar el ingreso.");



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

    }


    @Transactional
    @FXML
    void registrarCliente(ActionEvent event){
        long pasaporteUsu = Long.parseLong(pasaporte.getText());
        String nombreUsu = nombre.getText();
        String apellidoUsu = apellido.getText();
        String contrasenaUsu = contrasena.getText();
        String emailUsu = email.getText();

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

        else if(pasaporte.getText() == null ||pasaporte.getText().equals("") ||
                    nombreUsu == null || nombreUsu.equals("") ||
                    apellidoUsu == null || apellidoUsu.equals("")||
                    contrasenaUsu == null || contrasenaUsu.equals("")||
                    emailUsu == null || emailUsu.equals("")){

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");
        }else{
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
    }


    @Transactional
    @FXML
    void registrarAeropuerto(ActionEvent event){
        String nombreAero = nombreAeropuerto.getText();
        String codigoIATAAero = codigoIATAAeropuerto.getText();
        String ciudadAero = ciudad.getText();
        String paisAero = pais.getText();

        ResponseEntity response1 = aeropuertoRestService.getAeropuerto(codigoIATAAero);
        AeropuertoDTO aeropuertoDTOCodigo = (AeropuertoDTO) response1.getBody();

        if(aeropuertoDTOCodigo!=null){
            showAlert(
                    "Aeropuerto ya registrado",
                    "El aeropuerto ingresado ya esta registrado");
        }

        else if (nombreAero == null ||nombreAero.equals("") ||
                codigoIATAAero == null || codigoIATAAero.equals("") || ciudadAero == null  || paisAero == null ) {
            showAlert("Datos faltantes!", "No se ingresaron los datos necesarios para completar el ingreso.");


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
    }

    @Autowired
    private AvionRestService avionRestService;
    @Transactional
    @FXML
    void registrarAvion(ActionEvent event){
        String modeloAv = modelo.getText();
        String matriculaAv = matricula.getText();
        int capacidadAv = Integer.parseInt(capacidad.getText());

        ResponseEntity response1 = avionRestService.getAvion(matriculaAv);
        AvionDTO avionDTOMatricula = (AvionDTO) response1.getBody();


        if(avionDTOMatricula!=null){
            showAlert(
                    "Avion ya registrado",
                    "El avion ingresado ya esta registrado");
        }
        else if (modeloAv == null ||modeloAv.equals("") ||
                matriculaAv == null || matriculaAv.equals("") || capacidadAv == 0) {
            showAlert("Datos faltantes!", "No se ingresaron los datos necesarios para completar el ingreso.");

        } else {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
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
    }

    @Transactional
    @FXML
    void agregarAerolinea(ActionEvent event) {
        String nombreAero = nombreAerolinea.getText();
        String codigoIATAAerol = codigoIATAAerolinea.getText();
        String nombreUsu = nombre.getText();
        String emailUsu = email.getText();
        Long pasaporteUsu = Long.parseLong(pasaporte.getText());
        String contrasenaUsu = contrasena.getText();
        String apellidoUsu = apellido.getText();

        if (nombreAero == null || nombreAero.equals("") ||
                codigoIATAAerol == null || codigoIATAAerol.equals("") ||
                nombreUsu == null || nombreUsu.equals("")||
                apellidoUsu == null || apellidoUsu.equals("")||
                pasaporte.getText() == null || pasaporte.getText().equals("")||
                contrasenaUsu == null || contrasenaUsu.equals("")||
                emailUsu == null || emailUsu.equals("")) {

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        //} else if (aerolineaRepository.findAerolineaByCodigoIATAAerolinea(codigoIATAAerol)!=null) {
        //   showAlert("Aerolinea Ya Existe","Aerolinea ya esta registrada");
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

            if (response1.getStatusCode() == HttpStatus.OK  & response2.getStatusCode() == HttpStatus.OK) {
                showAlert("Aerolinea agregado", "Se agrego con exito la aerolinea!");
            }







        }


    }

    @Transactional
    @FXML
    void agregarAdministradorAerolineaExistente(ActionEvent event) {
        String codigoIATAAerol = codigoIATAAerolinea.getText();

        String nombreUsu = nombre.getText();
        String emailUsu = email.getText();
        Long pasaporteUsu = Long.parseLong(pasaporte.getText());
        String contrasenaUsu = contrasena.getText();
        String apellidoUsu = apellido.getText();


        if (codigoIATAAerol == null || codigoIATAAerol.equals("") ||
                nombreUsu == null || nombreUsu.equals("")||
                apellidoUsu == null || apellidoUsu.equals("")||
                pasaporte.getText() == null || pasaporte.getText().equals("")||
                contrasenaUsu == null || contrasenaUsu.equals("")||
                emailUsu == null || emailUsu.equals("")) {

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        } else {

            //asegurar que exista aerolinea
            //asegurar que no se repita email
            //asegurar que no se repita pasaporte
            UsuarioGeneralDTO usuarioGeneralDTO =  new UsuarioGeneralDTO();
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
            vuelosLle.add(vueloDTO);
        }

        for (int j=0;j<vueloDTOS2.size();j++){
            VueloDTO vueloDTO2 = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS2.get(j);
            vueloDTO2.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO2.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO2.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO2.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vuelosSal.add(vueloDTO2);
        }



        ObservableList<VueloDTO> vuelosLlegada = FXCollections.observableArrayList(vuelosLle);
        ObservableList<VueloDTO> vuelosSalida = FXCollections.observableArrayList(vuelosSal);



        codigoVueloAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigenAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
        aeropuertoDestinoAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
        matriculaAvionAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
        tablaVuelosAceptadosLlegada.setItems(vuelosLlegada);


        codigoVueloAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigenAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
        aeropuertoDestinoAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoDestino"));
        matriculaAvionAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
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

        List vueloDTOS = (List) response1.getBody();
        List<VueloDTO> vuelosLle = new ArrayList<>();

        for (int i=0;i<vueloDTOS.size();i++){
            VueloDTO vueloDTO = new VueloDTO();
            LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
            vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
            vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
            vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
            vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
            vuelosLle.add(vueloDTO);
        }

        System.out.println(vuelosLle);

        //chquear funcion destinio origen
        //ObservableList<Vuelo> vuelosLlegada = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoDestinoAndAceptadoDestinoAndRechadado(aeropuerto,false,false));
        //ObservableList<Vuelo> vuelosSalida = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndRechadado(aeropuerto,false,false));


        //codigoVueloLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        //aeropuertoOrigen.setCellValueFactory(new PropertyValueFactory<>("aeropuertoOrigen"));
        //matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("avion"));
        //tablaLlegada.setItems(vuelosLlegada);
        //addButtonToTableAceptar(tablaLlegada, true, aeropuerto);
        //addButtonToTableRechazar(tablaLlegada,aeropuerto);


        //codigoVueloSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        //aeropuertoDestino.setCellValueFactory(new PropertyValueFactory<>("aeropuertoDestino"));
        //matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("avion"));
        //tablaSalida.setItems(vuelosSalida);
        //addButtonToTableAceptar(tablaSalida, false, aeropuerto);
        //addButtonToTableRechazar(tablaSalida,aeropuerto);

        stage.show();



    }
    /*
    private void addButtonToTableAceptar(TableView t, boolean llegada, Aeropuerto aeropuerto) {
        TableColumn<Data, Void> colBtn = new TableColumn("Aceptar");

        Callback<TableColumn<Data, Void>, TableCell<Data, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Data, Void> call(final TableColumn<Data, Void> param) {
                final TableCell<Data, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Aceptar");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Vuelo vuelo = (Vuelo) getTableView().getItems().get(getIndex());
                            if (llegada==true){
                                vuelo.setAceptadoDestino(true);
                                vueloRepository.save(vuelo);

                                ObservableList<Vuelo> vuelosLlegada = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoDestinoAndAceptadoDestinoAndRechadado(aeropuerto,false,false));

                                codigoVueloLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                                aeropuertoOrigen.setCellValueFactory(new PropertyValueFactory<>("aeropuertoOrigen"));
                                matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("avion"));
                                tablaLlegada.setItems(vuelosLlegada);


                            }else if(llegada==false){
                                vuelo.setAceptadoOrigen(true);
                                vueloRepository.save(vuelo);

                                ObservableList<Vuelo> vuelosSalida = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndRechadado(aeropuerto,false,false));

                                codigoVueloSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                                aeropuertoDestino.setCellValueFactory(new PropertyValueFactory<>("aeropuertoDestino"));
                                matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("avion"));
                                tablaSalida.setItems(vuelosSalida);

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



     */
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



                            //ObservableList<VueloDTO> vuelosLlegada = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoDestinoAndAceptadoDestinoAndRechadado(aeropuerto,false,false));
                            //ObservableList<VueloDTO> vuelosSalida = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndRechadado(aeropuerto,false,false));




                            ResponseEntity response1 = aeropuertoRestService.getListaVuelosSinConfirmarLlegada(codigoAeropuerto);

                            List vueloDTOS = (List) response1.getBody();
                            List<VueloDTO> vuelosLle = new ArrayList<>();

                            for (int i=0;i<vueloDTOS.size();i++){
                                VueloDTO vueloDTO = new VueloDTO();
                                LinkedHashMap hashMap = (LinkedHashMap) vueloDTOS.get(i);
                                vueloDTO.setCodigoVuelo((String) hashMap.get("codigoVuelo"));
                                vueloDTO.setCodigoAeropuertoOrigen((String) hashMap.get("codigoAeropuertoOrigen"));
                                vueloDTO.setCodigoAeropuertoDestino((String) hashMap.get("codigoAeropuertoDestino"));
                                vueloDTO.setMatriculaAvion((String) hashMap.get("matriculaAvion"));
                                vuelosLle.add(vueloDTO);
                            }

                            ObservableList<VueloDTO> vuelosLlegada = FXCollections.observableArrayList(vuelosLle);

                            matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                            matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoAeropuertoOrigen"));
                            matriculaAvionLlegada.setCellValueFactory(new PropertyValueFactory<>("matriculaAvion"));
                            tablaLlegada.setItems(vuelosLlegada);

                            /*
                            codigoVueloSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
                            aeropuertoDestino.setCellValueFactory(new PropertyValueFactory<>("aeropuertoDestino"));
                            matriculaAvionSalida.setCellValueFactory(new PropertyValueFactory<>("avion"));
                            tablaSalida.setItems(vuelosSalida);

                             */



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
        AerolineaDTO aero = (AerolineaDTO) stage.getUserData();

        stage.setUserData(aero);
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

        ResponseEntity response1 = aeropuertoRestService.getAeropuertos();
        List listaAeropuertos = (List) response1.getBody();
        //List<Avion> aviones = avionRepository.findAll();




        for (int i=0; i<listaAeropuertos.size();i++) {
            LinkedHashMap aeropuerto = (LinkedHashMap) listaAeropuertos.get(i);
            codigoIATAeropuertoDestino.getItems().addAll((String) aeropuerto.get("codigoIATAAeropuerto"));
            codigoIATAeropuertoOrigen.getItems().addAll((String) aeropuerto.get("codigoIATAAeropuerto"));
        }
        /*
        for (int j=0;j<aviones.size();j++){
            String avion = aviones.get(j).getMatricula();
            matriculaBox.getItems().addAll(avion);
        }

         */

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
    @Transactional
    @FXML
    void registrarVuelo(ActionEvent event){
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

            ResponseEntity response = vueloRestService.agregarVuelo(vueloDTO);

            if (response.getStatusCode() == HttpStatus.OK) {
                showAlert("Vuelo agregado", "Se agrego con exito el vuelo!");
            }

        }
    }









}