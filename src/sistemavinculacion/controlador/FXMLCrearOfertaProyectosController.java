/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import sistemavinculacion.dao.OfertaDAO;
import sistemavinculacion.pojo.Oferta;
import sistemavinculacion.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLCrearOfertaProyectosController implements Initializable {

    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFin;
    @FXML
    private ComboBox<String> cbVistaPrevia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicAgregarProyecto(ActionEvent event) {
        try {
            // Obtener la referencia del ComboBox
            ComboBox<String> comboBoxReferencia = cbVistaPrevia;

            // Crear la nueva ventana
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemavinculacion/vista/FXMLRegistrarProyectoOferta.fxml"));
            Parent vista = loader.load();

            // Obtener el controlador de la nueva ventana
            FXMLRegistrarProyectoOfertaController controlador = loader.getController();

            // Pasar el ComboBox al controlador de la nueva ventana
            controlador.setComboBoxReferencia(comboBoxReferencia);

            // Mostrar la nueva ventana
            Stage escenario = new Stage();
            Scene escenaProyectos = new Scene(vista);
            escenario.setScene(escenaProyectos);
            escenario.setTitle("Registrar Proyecto");
            escenario.show();

        } catch (IOException ex) {
            // En caso de error, mostrar una alerta
            Utilidades.mostrarAlertaSimple("ERROR", "No se puede mostrar la ventana de proyectos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicPublicar(ActionEvent event) {
        // Validar que los DatePicker tengan valores seleccionados
        if (dpInicio.getValue() == null || dpFin.getValue() == null) {
            Utilidades.mostrarAlertaSimple("Fechas requeridas", 
                "Complete todos los campos obligatorios.", Alert.AlertType.WARNING);
            return;
        }

        // Convertir los valores seleccionados a formato de cadena
        String fechaInicio = dpInicio.getValue().toString();
        String fechaFin = dpFin.getValue().toString();

        // Validar que la fecha de inicio sea anterior a la fecha de fin
        if (dpInicio.getValue().isAfter(dpFin.getValue())) {
            Utilidades.mostrarAlertaSimple("Error en las fechas", 
                "La fecha de limite no puede ser anterior a la fecha de inicio.", Alert.AlertType.ERROR);
            return;
        }

        // Crear el objeto Oferta
        Oferta oferta = new Oferta();
        oferta.setFechaInicio(fechaInicio);
        oferta.setFechaFin(fechaFin);

        // Llamar al DAO para registrar la oferta
        HashMap<String, Object> respuesta = OfertaDAO.registrarOferta(oferta);
        if (!(boolean) respuesta.get("error")) {
            Utilidades.mostrarAlertaSimple("Éxito", 
                (String) respuesta.get("mensaje"), Alert.AlertType.INFORMATION);

            // Cerrar la ventana actual
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } else {
            Utilidades.mostrarAlertaSimple("Error", 
                (String) respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
}
