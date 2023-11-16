package um.edu.uy.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import um.edu.uy.AerolineaDTO;
import um.edu.uy.Main;
import um.edu.uy.UsuarioGeneralDTO;
import um.edu.uy.VueloDTO;
import um.edu.uy.service.AeropuertoRestService;
import um.edu.uy.service.UsuarioGeneralRestService;


import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AgregarUsuarioAeropuertoController implements Initializable {

    @FXML
    private TextField contrasena;
    @FXML
    private TextField pasaporte;
    @FXML
    private TextField apellido;
    @FXML
    private TextField email;
    @FXML
    private TextField nombre;

    @FXML
    private ComboBox<String> registroUsuarioBox;

    @Autowired
    private UsuarioGeneralRestService usuarioGeneralRestService;

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


    @Transactional
    @FXML
    void agregarUsuarioAeropuerto(ActionEvent event){
        try {
            long pasaporteAdAero = Long.parseLong(pasaporte.getText());
            String nombreAdAero = nombre.getText();
            String apellidoAdAero = apellido.getText();
            String emailAdAero = email.getText();
            String contrasenaAdAero = contrasena.getText();
            String tipoUsuAerop = registroUsuarioBox.getValue().toUpperCase();



                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                String codigoAeropuero = (String) stage.getUserData();

                UsuarioGeneralDTO usuarioGeneralDTO = new UsuarioGeneralDTO();
                usuarioGeneralDTO.setEmail(emailAdAero);
                usuarioGeneralDTO.setNombre(nombreAdAero);
                usuarioGeneralDTO.setApellido(apellidoAdAero);
                usuarioGeneralDTO.setContrasena(contrasenaAdAero);
                usuarioGeneralDTO.setPasaporte(pasaporteAdAero);
                usuarioGeneralDTO.setTipo(tipoUsuAerop);
                usuarioGeneralDTO.setCodigoAeropuerto(codigoAeropuero);
                usuarioGeneralDTO.setCodigoAerolinea(null);

                ResponseEntity response = usuarioGeneralRestService.agregarUsuarioGeneral(usuarioGeneralDTO);

                if (response.getStatusCode() == HttpStatus.OK) {
                    showAlert("Usuario agregado", "Se agrego con exito el usuario!");
                }


        }catch (Exception e){
            showAlert("Datos incorrectos","Los datos ingresados no son correctos");
        }

    }

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

    @Autowired
    private AeropuertoRestService aeropuertoRestService;


    @FXML
    void backToAdminAeropuerto(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        String codigoAeropuerto = (String) stage.getUserData();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent inicioAeropuerto = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdministradorAeropuerto1.fxml"));
        Scene inicioAeropuertoScene = new Scene(inicioAeropuerto);
        Stage inicioAeropuertoStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        inicioAeropuertoStage.setScene(inicioAeropuertoScene);
        inicioAeropuertoStage.setUserData(codigoAeropuerto);

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
    void logOut(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("IniciarSesion.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
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







    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registroUsuarioBox.setItems(FXCollections.observableArrayList("Maleteria","Boarding","AdminVuelos"));

    }



}
