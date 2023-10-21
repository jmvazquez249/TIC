package um.edu.uy.ui;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Text;
import um.edu.uy.Main;


import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AgregarVueloController implements Initializable {

    @FXML
    private ComboBox<String> matricula;
    @FXML
    private ComboBox<String> codigoIATAeropuertoDestino;
    @FXML
    private ComboBox<String> codigoIATAeropuertoOrigen;
    @FXML
    private TextField codigoIATAAvuelo;

    /*
    @Transactional
    @FXML
    void registrarVuelo(ActionEvent event){
        String matriculaAvion = matricula.getValue();
        String codigoIATAAeropDest = codigoIATAeropuertoDestino.getValue();
        String codigoIATAAeropOri = codigoIATAeropuertoOrigen.getValue();
        Long codigoIATAVue = Long.parseLong(codigoIATAAvuelo.getText());

        Aeropuerto aeropuertoDestino = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoIATAAeropDest);
        Aeropuerto aeropuertoOrigen = aeropuertoRepository.findAeropuertoByCodigoIATAAeropuerto(codigoIATAAeropOri);
        Avion avion = avionRepository.findByMatricula(matriculaAvion);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        Aerolinea aero = (Aerolinea) stage.getUserData();
        String codigoIATAAerol = aero.getCodigoIATAAerolinea();



        if (matriculaAvion == null || matriculaAvion.equals("") ||
                codigoIATAAeropDest == null || codigoIATAAeropDest.equals("")||
                codigoIATAAeropOri == null || codigoIATAAeropOri.equals("") ||
                codigoIATAVue == null || codigoIATAVue.equals("") ) {
            showAlert("Datos faltantes!", "No se ingresaron los datos necesarios para completar el ingreso.");
        } else {

            List<Vuelo> vuelosAero = aero.getVuelos();
            Vuelo vuelo = new Vuelo(codigoIATAAerol+codigoIATAVue,aeropuertoDestino,aeropuertoOrigen,avion,aero,false,false);
            vuelosAero.add(vuelo);

            vueloRepository.save(vuelo);
            aerolineaRepository.save(aero);
            showAlert("Vuelo agregado", "Se agrego con exito el vuelo!");


        }
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


     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
        List<Aeropuerto> aeropuertos = aeropuertoRepository.findAll();
        List<Avion> aviones = avionRepository.findAll();
        for (int i=0; i<aeropuertos.size();i++) {
            String aeropuerto = aeropuertos.get(i).getCodigoIATAAeropuerto();
            codigoIATAeropuertoDestino.getItems().addAll(aeropuerto);
            codigoIATAeropuertoOrigen.getItems().addAll(aeropuerto);
        }

        for (int j=0;j<aviones.size();j++){
            String avion = aviones.get(j).getMatricula();
            matricula.getItems().addAll(avion);
        }

         */
    }


}
