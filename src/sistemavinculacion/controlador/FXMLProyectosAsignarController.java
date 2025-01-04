/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sistemavinculacion.dao.AsignacionPPDAO;
import sistemavinculacion.dao.ProyectoppDAO;
import sistemavinculacion.dao.SeleccionEstudiantePPDAO;
import sistemavinculacion.pojo.Proyectopp;
import sistemavinculacion.pojo.SeleccionEstudiantePP;
import sistemavinculacion.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLProyectosAsignarController implements Initializable {

    @FXML
    private TableView<Proyectopp> tvProyectosOferta;
    @FXML
    private TableColumn<Proyectopp, String> colNombre;
    @FXML
    private TableColumn<Proyectopp, String> colOrganizacion;
    @FXML
    private TableColumn<Proyectopp, String> colFechaInicio;
    @FXML
    private TableColumn<Proyectopp, String> colFechaTerimno;
    @FXML
    private TableColumn<Proyectopp, Integer> colCupos;
    @FXML
    private Label lbPrimera;
    @FXML
    private Label lbSegunda;
    @FXML
    private Label lbTercera;
    
    private Integer matriculaEstudiante;
    private ObservableList<Proyectopp> proyectos;
    private Set<String> proyectosOfertados = new HashSet<>();
    
    private Stage stageSeleccionEstudianteProyecto;
    private Stage stageDetallesEstudianteSeleccion;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    public void setStageSeleccionEstudianteProyecto(Stage stageSeleccionEstudianteProyecto) {
        this.stageSeleccionEstudianteProyecto = stageSeleccionEstudianteProyecto;
    }

    public void setStageDetallesEstudianteSeleccion(Stage stageDetallesEstudianteSeleccion) {
        this.stageDetallesEstudianteSeleccion = stageDetallesEstudianteSeleccion;
    }
    
    public void setMatriculaEstudiante(Integer matricula) {
        this.matriculaEstudiante = matricula;
         cargarOpcionesEstudiante();
    }
    
    public void cargarOpcionesEstudiante() {
        try {
            SeleccionEstudiantePP seleccion = SeleccionEstudiantePPDAO.obtenerSeleccionPorMatricula(matriculaEstudiante);
            if (seleccion != null) {
                lbPrimera.setText(seleccion.getOpcion1());
                lbSegunda.setText(seleccion.getOpcion2());
                lbTercera.setText(seleccion.getOpcion3());
            } else {
                Utilidades.mostrarAlertaSimple("Sin selección", "El estudiante no tiene opciones registradas.", Alert.AlertType.WARNING);
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", "Error al conectar con la base de datos. Intente nuevamente más tarde.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colOrganizacion.setCellValueFactory(new PropertyValueFactory<>("nombreOrganizacion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));
        colFechaTerimno.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
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
    private void clicAsignarProyecto(ActionEvent event) {
        // Obtener el proyecto seleccionado en la tabla
        Proyectopp proyectoSeleccionado = tvProyectosOferta.getSelectionModel().getSelectedItem();

        if (proyectoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Selecciona un proyecto", "Por favor selecciona un proyecto para asignar.", Alert.AlertType.WARNING);
            return;
        }

        // Verificar si hay cupos disponibles
        if (proyectoSeleccionado.getCupos() <= 0) {
            Utilidades.mostrarAlertaSimple("Sin cupos disponibles", "No hay cupos disponibles para este proyecto.", Alert.AlertType.WARNING);
            return;
        }

        // Realizar la asignación del proyecto al estudiante
        try {
            // Restar un cupo del proyecto
            int nuevoCupo = proyectoSeleccionado.getCupos() - 1;
            int filasAfectadas = ProyectoppDAO.actualizarCupo(proyectoSeleccionado.getIdProyecto(), nuevoCupo);

            if (filasAfectadas > 0) {
                // Crear la asignación
                AsignacionPPDAO.asignarProyectoAEstudiante(matriculaEstudiante, proyectoSeleccionado.getIdProyecto());

                // Mostrar mensaje de éxito
                Utilidades.mostrarAlertaSimple("Éxito", "Asignación completada exitosamente.", Alert.AlertType.INFORMATION);
                javafx.stage.Stage stage = (javafx.stage.Stage) tvProyectosOferta.getScene().getWindow();
                stage.close();

                // Cerrar las otras ventanas
                stageDetallesEstudianteSeleccion.close();
                stageSeleccionEstudianteProyecto.close();
                
                Stage stageActual = (Stage) tvProyectosOferta.getScene().getWindow();
                stageActual.close();
                
            } else {
                Utilidades.mostrarAlertaSimple("Error", "No se pudo actualizar el cupo del proyecto.", Alert.AlertType.ERROR);
            }
        } catch (Exception ex) {
            Utilidades.mostrarAlertaSimple("Error", "Hubo un error al procesar la asignación. Inténtalo nuevamente.", Alert.AlertType.ERROR);
        }
    }
    
}
    
    

