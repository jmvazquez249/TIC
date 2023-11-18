package um.edu.uy.ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import um.edu.uy.AerolineaDTO;
import um.edu.uy.Main;
import um.edu.uy.UsuarioGeneralDTO;
import um.edu.uy.service.UsuarioGeneralRestService;


import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;




@Component
public class AgregarUsuarioAerolineaController implements Initializable {

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


    @Transactional
    @FXML
    void registrarUsuarioAerolinea(ActionEvent event) {
        try{
        long pasaporteUsuAero = Long.parseLong(pasaporte.getText());
        String nombreUsuAero = nombre.getText();
        String apellidoUsuAero = apellido.getText();
        String emailUsuAero = email.getText();
        String contrasenaUsuAero = contrasena.getText();
        String tipoUsuAero = registroUsuarioBox.getValue().toUpperCase();

                if (pasaporteUsuAero == 0 || nombreUsuAero.equals("") || apellidoUsuAero.equals("") || emailUsuAero.equals("") || contrasenaUsuAero.equals("") || tipoUsuAero.equals("")) {
                    showAlert("Datos incorrectos", "Los datos ingresados no son correctos");
                }


                else {
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    String codigoAerolinea = (String) stage.getUserData();


                    UsuarioGeneralDTO usuarioGeneralDTO = new UsuarioGeneralDTO();
                    usuarioGeneralDTO.setPasaporte(pasaporteUsuAero);
                    usuarioGeneralDTO.setEmail(emailUsuAero);
                    usuarioGeneralDTO.setContrasena(contrasenaUsuAero);
                    usuarioGeneralDTO.setNombre(nombreUsuAero);
                    usuarioGeneralDTO.setApellido(apellidoUsuAero);
                    usuarioGeneralDTO.setTipo(tipoUsuAero);
                    usuarioGeneralDTO.setCodigoAerolinea(codigoAerolinea);
                    usuarioGeneralDTO.setCodigoAeropuerto(null);


                    ResponseEntity response = usuarioGeneralRestService.agregarUsuarioGeneral(usuarioGeneralDTO);

                    if (response.getStatusCode() == HttpStatus.OK) {

                        showAlert("Usuario agregado", "Se agrego con exito el usuario!");

                    }
                }


        }catch (Exception e){
            showAlert("Datos incorrectos", "Los datos ingresados no son correctos");
        }

    }

    @FXML
    void backToAdminAerolinea(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        String codigoAerolinea = (String) stage.getUserData();


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);

        Parent root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("AdminAerolinea.fxml"));
        Scene scene = new Scene(root);

        Stage stage2 = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage2.setUserData(codigoAerolinea);
        stage2.setScene(scene);
        stage2.show();
    }
    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        registroUsuarioBox.setItems(FXCollections.observableArrayList("Check In","Oficina"));

    }


}
