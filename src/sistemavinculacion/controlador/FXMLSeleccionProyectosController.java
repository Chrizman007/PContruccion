/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemavinculacion.dao.ProyectoppDAO;
import sistemavinculacion.dao.SeleccionEstudiantePPDAO;
import sistemavinculacion.pojo.Proyectopp;
import sistemavinculacion.utilidades.Constantes;
import sistemavinculacion.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLSeleccionProyectosController implements Initializable {

    @FXML
    private TableView<Proyectopp> tvProyectosOferta;
    @FXML
    private TableColumn<Proyectopp, String> colNombreProyecto;
    @FXML
    private TableColumn<Proyectopp, String> colNombreOrganizacion;
    @FXML
    private TableColumn<Proyectopp, Integer> colCupos;
    
    private ObservableList<Proyectopp> proyectos;
    private Set<String> proyectosOfertados = new HashSet<>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla() {
        colNombreProyecto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombreOrganizacion.setCellValueFactory(new PropertyValueFactory<>("nombreOrganizacion"));
        colCupos.setCellValueFactory(new PropertyValueFactory<>("cupos"));
    }

    private void cargarInformacionTabla() {
        proyectos = FXCollections.observableArrayList();
        try {
            proyectos.addAll(ProyectoppDAO.obtenerProyectosOfertados());  // Asegúrate que este método devuelva la lista de proyectos
            tvProyectosOferta.setItems(proyectos);
        } catch (Exception e) {
            Utilidades.mostrarAlertaSimple("Error", "Error al conectar con la base de datos. Intente nuevamente más tarde.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicTercera(ActionEvent event) {
        Proyectopp proyectoSeleccionado = tvProyectosOferta.getSelectionModel().getSelectedItem();

        if (proyectoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Selecciona un proyecto", "Por favor selecciona un proyecto para asignar.", Alert.AlertType.WARNING);
            return;
        }
        
        try{
            SeleccionEstudiantePPDAO.RegistrarTerceraOpcion(proyectoSeleccionado.getNombre(), Constantes.MatriculaActual);
            Utilidades.mostrarAlertaSimple("Primera Opcion", "Se establecio tu tercera opcion", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            Utilidades.mostrarAlertaSimple("Error", "Hubo un error al establecer la opcion de proyecto. Inténtalo nuevamente.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicSegunda(ActionEvent event) {
        Proyectopp proyectoSeleccionado = tvProyectosOferta.getSelectionModel().getSelectedItem();

        if (proyectoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Selecciona un proyecto", "Por favor selecciona un proyecto para asignar.", Alert.AlertType.WARNING);
            return;
        }
        
        try{
            SeleccionEstudiantePPDAO.RegistrarSegundaOpcion(proyectoSeleccionado.getNombre(), Constantes.MatriculaActual);
            Utilidades.mostrarAlertaSimple("Primera Opcion", "Se establecio tu segunda opcion", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            Utilidades.mostrarAlertaSimple("Error", "Hubo un error al establecer la opcion de proyecto. Inténtalo nuevamente.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicPrimera(ActionEvent event) {
        Proyectopp proyectoSeleccionado = tvProyectosOferta.getSelectionModel().getSelectedItem();

        if (proyectoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Selecciona un proyecto", "Por favor selecciona un proyecto para asignar.", Alert.AlertType.WARNING);
            return;
        }
        
        try{
            SeleccionEstudiantePPDAO.RegistrarPrimeraOpcion(proyectoSeleccionado.getNombre(), Constantes.MatriculaActual);
            Utilidades.mostrarAlertaSimple("Primera Opcion", "Se establecio tu primera opcion", Alert.AlertType.INFORMATION);
        } catch (Exception ex) {
            Utilidades.mostrarAlertaSimple("Error", "Hubo un error al establecer la opcion de proyecto. Inténtalo nuevamente.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicGuardar(ActionEvent event) {
        Boolean confirmacion = Utilidades.mostrarAlertaConfirmacion("Confirmar Seleccion", "¿Estas Seguro de tu Seleccion de Proyectos?");
        
        if (confirmacion) {
        // Mostrar un mensaje de éxito
            Utilidades.mostrarAlertaSimple("Selección Guardada","Tu selección ha sido guardada exitosamente.",Alert.AlertType.INFORMATION);
            // Cerrar la ventana actual
            Stage stage = (Stage) tvProyectosOferta.getScene().getWindow();
            stage.close();
        } 
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        Stage stage = (Stage) tvProyectosOferta.getScene().getWindow();
        stage.close();
    }
    
}
