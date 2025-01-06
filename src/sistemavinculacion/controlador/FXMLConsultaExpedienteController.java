/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sistemavinculacion.dao.ExpedienteDAO;
import sistemavinculacion.pojo.Expediente;
import sistemavinculacion.utilidades.Constantes;
import sistemavinculacion.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLConsultaExpedienteController implements Initializable {

    @FXML
    private TextField tfMatricula;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicIngresar(ActionEvent event) {
        String matriculaTexto = tfMatricula.getText();
        if (matriculaTexto.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Error", "Debe ingresar una matrícula válida.",Alert.AlertType.ERROR);
            return;
        }

        try {
            int matricula = Integer.parseInt(matriculaTexto);
            List<Expediente> expedientes = ExpedienteDAO.obtenerExpedientesPorMatricula(matricula);

            if (!expedientes.isEmpty()) {
                Constantes.MatriculaActual = matricula;
                abrirNuevaVentana();
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Matrícula no encontrada", "No existe un expediente con la matrícula ingresada. Inténtelo de nuevo.",Alert.AlertType.ERROR);
            }
        } catch (NumberFormatException e) {
            Utilidades.mostrarAlertaSimple("Error", "La matrícula debe ser un número válido.",Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "Ocurrió un error al verificar la matrícula.",Alert.AlertType.ERROR);
        }
    }
    
    private void abrirNuevaVentana() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("vista/FXMLExpedienteEstudiante.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Expediente Estudiante");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            Utilidades.mostrarAlertaSimple("Error", "No se pudo abrir la nueva ventana.",Alert.AlertType.ERROR);
        }
    }
    
    private void cerrarVentana(){
        Stage stage = (Stage) tfMatricula.getScene().getWindow();
        stage.close();
    }
}
