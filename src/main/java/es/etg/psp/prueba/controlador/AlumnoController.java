package es.etg.psp.prueba.controlador;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AlumnoController extends Application {

    private static final String RUTA_VISTA_FXML = "/es/etg/psp/prueba/vista/formularioAlumnoView.fxml";
    private static final String TITULO_VENTANA = "Gestión de Alumnos";

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(RUTA_VISTA_FXML));
        Scene scene = new Scene(loader.load());
        stage.setTitle(TITULO_VENTANA);
        stage.setScene(scene);
        stage.show();
    }
}
