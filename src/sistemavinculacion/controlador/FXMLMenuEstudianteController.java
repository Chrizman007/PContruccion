package sistemavinculacion.controlador;

import sistemavinculacion.pojo.Estudiante;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLMenuEstudianteController {

    @FXML
    private Label lbNombreEstudiante;
    @FXML
    private Label lbCorreoEstudiante;

    // Método para inicializar la información del estudiante
    public void inicializarValores(Estudiante estudiante) {
        lbNombreEstudiante.setText(estudiante.getNombre());
        lbCorreoEstudiante.setText(estudiante.getCorreo());
    }

    // Agregar aquí las acciones que el estudiante puede realizar
    @FXML
    private void clicVerActividades() {
        // Código para redirigir a la visualización de actividades
    }

    @FXML
    private void clicVerEvaluacion() {
        // Código para redirigir a la visualización de evaluación
    }
}
