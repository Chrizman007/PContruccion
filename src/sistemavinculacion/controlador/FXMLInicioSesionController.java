package sistemavinculacion.controlador;

import java.io.IOException;
import sistemavinculacion.dao.CoordinadorDAO;
import sistemavinculacion.dao.EstudianteDAO;
import sistemavinculacion.dao.ProfesorDAO;
import sistemavinculacion.pojo.Coordinador;
import sistemavinculacion.pojo.Estudiante;
import sistemavinculacion.pojo.Profesor;
import sistemavinculacion.utilidades.Utilidades;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLInicioSesionController {

    @FXML
    private TextField tfUsuario;
    @FXML
    private TextField tfContraseña;
    @FXML
    private ComboBox<String> cbTipo; // ComboBox para seleccionar el tipo de usuario

    @FXML
    private void initialize() {
        cbTipo.getItems().addAll("EstudianteSS", "EstudiantePP", "ProfesorSS", "ProfesorPP", "Coordinador");
        cbTipo.setValue("Coordinador");
    }

    @FXML
    private void clicIngrear(ActionEvent event) {
        String usuario = tfUsuario.getText();
        String contrasena = tfContraseña.getText();
        String tipoUsuario = cbTipo.getValue(); // Obtiene el valor seleccionado en el ComboBox

        if (validarFormulario(usuario, contrasena)) {
            verificarCredenciales(usuario, contrasena, tipoUsuario);
        } else {
            Utilidades.mostrarAlertaSimple("Campos vacíos",
                    "Por favor, complete todos los campos.", Alert.AlertType.WARNING);
        }
    }

    private boolean validarFormulario(String usuario, String contrasena) {
        return usuario != null && !usuario.isEmpty() && contrasena != null && !contrasena.isEmpty();
    }

    private void verificarCredenciales(String usuario, String contrasena, String tipoUsuario) {
        try {
            if ("Coordinador".equals(tipoUsuario)) {
                Coordinador coordinador = CoordinadorDAO.obtenerCoordinadorPorCredenciales(usuario, contrasena);
                if (coordinador != null) {
                    irPantallaPrincipal(coordinador, "coordinador");
                    return;
                }
            } else if ("EstudianteSS".equals(tipoUsuario) || "EstudiantePP".equals(tipoUsuario)) {
                Estudiante estudiante = EstudianteDAO.obtenerEstudiantePorCredenciales(usuario, contrasena, tipoUsuario);
                if (estudiante != null) {
                    irPantallaPrincipal(estudiante, "estudiante");
                    return;
                }
            } else if ("ProfesorSS".equals(tipoUsuario) || "ProfesorPP".equals(tipoUsuario)) {
                Profesor profesor = ProfesorDAO.obtenerProfesorPorCredenciales(usuario, contrasena, tipoUsuario);
                if (profesor != null) {
                    irPantallaPrincipal(profesor, "profesor");
                    return;
                }
            }
            Utilidades.mostrarAlertaSimple("Error de inicio de sesión",
                    "Usuario o contraseña incorrectos.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error en la conexión",
                    "No se pudo conectar con la base de datos.", Alert.AlertType.ERROR);
        }
    }

    private void irPantallaPrincipal(Object usuario, String tipoUsuario) {
        try {
            Stage escenario = (Stage) tfUsuario.getScene().getWindow();
            FXMLLoader loader = null;
            Parent vista = null;

            if ("coordinador".equals(tipoUsuario)) {
                loader = new FXMLLoader(getClass().getResource("/sistemavinculacion/vista/FXMLMenuCoordinador.fxml"));
                vista = loader.load();
                FXMLMenuCoordinadorController controlador = loader.getController();
                controlador.inicializarValores((Coordinador) usuario);
            } else if ("estudiante".equals(tipoUsuario)) {
                loader = new FXMLLoader(getClass().getResource("/sistemavinculacion/vista/FXMLMenuEstudiante.fxml"));
                vista = loader.load();
                FXMLMenuEstudianteController controlador = loader.getController();
                controlador.inicializarValores((Estudiante) usuario);
            } else if ("profesor".equals(tipoUsuario)) {
                loader = new FXMLLoader(getClass().getResource("/sistemavinculacion/vista/FXMLMenuProfesor.fxml"));
                vista = loader.load();
                FXMLMenuProfesorController controlador = loader.getController();
                controlador.inicializarValores((Profesor) usuario);
            }

            Scene escena = new Scene(vista);
            escenario.setScene(escena);
            escenario.show();
        } catch (IOException ex) {
            Utilidades.mostrarAlertaSimple("Error de carga",
                    "No se pudo cargar la pantalla principal.", Alert.AlertType.ERROR);
        }
    }
}