package sistemavinculacion.controlador;

import sistemavinculacion.pojo.Coordinador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class FXMLMenuCoordinadorController {

    @FXML
    private Label lbNombreCoordinador;
    @FXML
    private Label lbCorreoCoordinador;

    // Método para inicializar la información del coordinador
    public void inicializarValores(Coordinador coordinador) {
        lbNombreCoordinador.setText(coordinador.getNombre());
        lbCorreoCoordinador.setText(coordinador.getCorreo());
    }

    // Agregar aquí las acciones que el coordinador puede realizar
    @FXML
    private void clicGestionarEstudiantes() {
        // Código para redirigir a la gestión de estudiantes
    }

    @FXML
    private void clicGestionarProfesores() {
        // Código para redirigir a la gestión de profesores
    }
}
