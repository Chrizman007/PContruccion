package sistemavinculacion.controlador;

import sistemavinculacion.pojo.Profesor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLMenuProfesorController {

    @FXML
    private Label lbNombreProfesor;
    @FXML
    private Label lbCorreoProfesor;

    // Método para inicializar la información del profesor
    public void inicializarValores(Profesor profesor) {
        lbNombreProfesor.setText(profesor.getNombre());
        lbCorreoProfesor.setText(profesor.getCorreo());
    }

    // Agregar aquí las acciones que el profesor puede realizar
    @FXML
    private void clicVerAsignaciones() {
        // Código para redirigir a la visualización de asignaciones
    }

    @FXML
    private void clicVerEvaluacion() {
        // Código para redirigir a la visualización de evaluación
    }
}
