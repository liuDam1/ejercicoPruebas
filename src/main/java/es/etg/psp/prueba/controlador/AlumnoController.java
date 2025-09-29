package es.etg.psp.prueba.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlumnoController extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("vista/FormularioAlumnoView.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Gestión de Alumnos");
        stage.setScene(scene);
        stage.show();
    }
}
