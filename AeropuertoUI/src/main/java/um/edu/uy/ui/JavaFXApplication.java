package um.edu.uy.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import um.edu.uy.Main;

public class JavaFXApplication extends Application  {
    private Parent root;

    @Override
    public void init() throws Exception {

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(Main.getContext()::getBean);
        root = fxmlLoader.load(IniciarSesionController.class.getResourceAsStream("IniciarSesion.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() {
        Main.getContext().close();
    }

}
