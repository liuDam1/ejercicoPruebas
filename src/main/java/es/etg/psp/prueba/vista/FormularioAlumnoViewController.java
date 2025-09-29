package es.etg.psp.prueba.vista;

import es.etg.psp.prueba.modelo.Alumno;
import es.etg.psp.prueba.modelo.AlumnoDaoMemoria;
import es.etg.psp.prueba.modelo.AlumnoServicio;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FormularioAlumnoViewController {

    @FXML
    private TextField textNombre;
    @FXML
    private TextField textApellido;
    @FXML
    private TextField textEdad;
    @FXML
    private Button btInsertar;
    @FXML
    private Button btListar;

    private AlumnoServicio servicio;

    private static final String TITULO_ALUMNO_INSERTADO = "Alumno insertado";
    private static final String TITULO_ERROR = "Error";
    private static final String MENSAJE_ERROR_EDAD_NUMERO = "La edad debe ser un número entero";
    private static final String TITULO_LISTADO_ALUMNOS = "Listado de alumnos";
    private static final String MENSAJE_NO_ALUMNOS_REGISTRADOS = "No hay alumnos registrados.";
    private static final String SALTO_LINEA = "\n";

    public FormularioAlumnoViewController() {
        this.servicio = new AlumnoServicio(new AlumnoDaoMemoria());
    }

    @FXML
    private void initialize() {
        btInsertar.setOnAction(e -> insertarAlumno());
        btListar.setOnAction(e -> listarAlumnos());
    }

    private void insertarAlumno() {
        try {
            String nombre = textNombre.getText();
            String apellido = textApellido.getText();
            int edad = Integer.parseInt(textEdad.getText());

            Alumno alumno = servicio.insertar(nombre, apellido, edad);

            mostrarAlerta(TITULO_ALUMNO_INSERTADO, alumno.toString(), Alert.AlertType.INFORMATION);

            textNombre.clear();
            textApellido.clear();
            textEdad.clear();
        } catch (NumberFormatException ex) {
            mostrarAlerta(TITULO_ERROR, MENSAJE_ERROR_EDAD_NUMERO, Alert.AlertType.ERROR);
        } catch (IllegalArgumentException ex) {
            mostrarAlerta(TITULO_ERROR, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void listarAlumnos() {
        StringBuilder sb = new StringBuilder();
        for (Alumno a : servicio.listar()) {
            sb.append(a).append(SALTO_LINEA);
        }
        if (sb.length() == 0)
            sb.append(MENSAJE_NO_ALUMNOS_REGISTRADOS);

        mostrarAlerta(TITULO_LISTADO_ALUMNOS, sb.toString(), Alert.AlertType.INFORMATION);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}