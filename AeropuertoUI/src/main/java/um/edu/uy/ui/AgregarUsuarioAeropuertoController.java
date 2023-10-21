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
import org.springframework.stereotype.Component;
import um.edu.uy.Main;


import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
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

    /*
    @Transactional
    @FXML
    void agregarUsuarioAeropuerto(ActionEvent event){
        long pasaporteAdAero = Long.parseLong(pasaporte.getText());
        String nombreAdAero = nombre.getText();
        String apellidoAdAero = apellido.getText();
        String emailAdAero = email.getText();
        String contrasenaAdAero = contrasena.getText();
        String tipoUsuAerop = registroUsuarioBox.getValue().toUpperCase();



        if (pasaporte.getText() == null ||pasaporte.getText().equals("") ||
                nombreAdAero == null || nombreAdAero.equals("") ||
                apellidoAdAero == null || apellidoAdAero.equals("")||
                contrasenaAdAero == null || contrasenaAdAero.equals("")||
                emailAdAero == null || emailAdAero.equals("")) {

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        } else if (usuarioGeneralRepository.findOneByPasaporte(pasaporteAdAero)!=null) {
            showAlert("Usuario Ya Existe","El usuario ya esta resgistrado");
        } else {
            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            Aeropuerto aeropuerto = (Aeropuerto) stage.getUserData();

            UsuarioGeneral usuarioGeneral = new UsuarioGeneral(pasaporteAdAero,nombreAdAero,apellidoAdAero,contrasenaAdAero,emailAdAero,aeropuerto,tipoUsuAerop);
            usuarioGeneralRepository.save(usuarioGeneral);

            showAlert("Usuario agregado", "Se agrego con exito el usuario!");


        }

    }

    @FXML
    public TableView<Vuelo> tablaVuelosAceptadosSalida;
    @FXML
    public TableColumn<Vuelo, String> codigoVueloAceptadoSalida;
    @FXML
    public TableColumn<Vuelo, String> aeropuertoOrigenAceptadoSalida;
    @FXML
    public TableColumn<Vuelo, String> aeropuertoDestinoAceptadoSalida;
    @FXML
    public TableColumn<Vuelo, String> matriculaAvionAceptadoSalida;
    @FXML
    public TableView<Vuelo> tablaVuelosAceptadosLlegada;
    @FXML
    public TableColumn<Vuelo, String> codigoVueloAceptadoLlegada;
    @FXML
    public TableColumn<Vuelo, String> aeropuertoOrigenAceptadoLlegada;
    @FXML
    public TableColumn<Vuelo, String> aeropuertoDestinoAceptadoLlegada;
    @FXML
    public TableColumn<Vuelo, String> matriculaAvionAceptadoLlegada;
    @Autowired
    private VueloRepository vueloRepository;


    @FXML
    void backToAdminAeropuerto(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Aeropuerto aeropuerto = (Aeropuerto) stage.getUserData();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent inicioAeropuerto = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdministradorAeropuerto1.fxml"));
        Scene inicioAeropuertoScene = new Scene(inicioAeropuerto);
        Stage inicioAeropuertoStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        inicioAeropuertoStage.setScene(inicioAeropuertoScene);
        inicioAeropuertoStage.setUserData(aeropuerto);

        ObservableList<Vuelo> vuelosLlegada = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoDestinoAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aeropuerto,true,true, false));
        ObservableList<Vuelo> vuelosSalida = FXCollections.observableArrayList(vueloRepository.findAllByAeropuertoOrigenAndAceptadoOrigenAndAceptadoDestinoAndRechadado(aeropuerto,true,true, false));


        codigoVueloAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigenAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("aeropuertoOrigen"));
        aeropuertoDestinoAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("aeropuertoDestino"));
        matriculaAvionAceptadoLlegada.setCellValueFactory(new PropertyValueFactory<>("avion"));
        tablaVuelosAceptadosLlegada.setItems(vuelosLlegada);

        codigoVueloAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("codigoVuelo"));
        aeropuertoOrigenAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("aeropuertoOrigen"));
        aeropuertoDestinoAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("aeropuertoDestino"));
        matriculaAvionAceptadoSalida.setCellValueFactory(new PropertyValueFactory<>("avion"));
        tablaVuelosAceptadosSalida.setItems(vuelosSalida);

        inicioAeropuertoStage.show();
    }
    */
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
