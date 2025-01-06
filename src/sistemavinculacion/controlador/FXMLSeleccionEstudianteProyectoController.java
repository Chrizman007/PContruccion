/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemavinculacion.dao.EstudianteDAO;
import sistemavinculacion.pojo.Estudiante;
import sistemavinculacion.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLSeleccionEstudianteProyectoController implements Initializable {

    @FXML
    private TableView<Estudiante> tvEstudiantes;
    @FXML
    private TableColumn<Estudiante, String> colNombre;
    @FXML
    private TableColumn<Estudiante, Integer> colMatricula;
    @FXML
    private TableColumn<Estudiante, String> colCarrera;
    
    private ObservableList<Estudiante> estudiantes;

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
    colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
    colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    colCarrera.setCellValueFactory(new PropertyValueFactory<>("carrera"));
}

// Cargar la información desde la base de datos y mostrarla en la tabla
    private void cargarInformacionTabla() {
        
        estudiantes = FXCollections.observableArrayList();
        try {
            List<Estudiante> estudiantesBD = EstudianteDAO.obtenerNoAsignadosPP();

            if (estudiantesBD != null && !estudiantesBD.isEmpty()) {
                estudiantes.addAll(estudiantesBD);
                tvEstudiantes.setItems(estudiantes);
            } else {
                Utilidades.mostrarAlertaSimple("Información", 
                        "No se encontraron estudiantes no asignados.", 
                        Alert.AlertType.INFORMATION);
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", 
                    "Error al conectar con la base de datos. Intente nuevamente más tarde.", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void clicSeleccionar(ActionEvent event) throws SQLException {
        
        Estudiante estudianteSeleccionado = tvEstudiantes.getSelectionModel().getSelectedItem();

        if (estudianteSeleccionado == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Advertencia");
            alerta.setHeaderText("No se ha seleccionado ningún estudiante");
            alerta.setContentText("Por favor, selecciona un estudiante de la tabla.");
            alerta.showAndWait();
            return;
        }

        try {

            // Carga del archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemavinculacion/vista/FXMLDellatesEstdudianteSeleccion.fxml"));
            Parent vista = loader.load();

            // Obtener el controlador y pasar los datos
            FXMLDellatesEstdudianteSeleccionController controlador = loader.getController();
            controlador.setMatriculaEstudianteSeleccionada(estudianteSeleccionado.getMatricula());// Método en el controlador
            controlador.setStageSeleccionEstudianteProyecto((Stage) tvEstudiantes.getScene().getWindow());
            // Configurar el Stage
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(vista));
            stage.setTitle("Detalles del Estudiante");
            stage.showAndWait(); // Modal
        } catch (IOException e) {
            e.printStackTrace();
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Error");
            alerta.setHeaderText("Error al cargar la ventana de detalles");
            alerta.setContentText("Por favor, intente nuevamente.");
            alerta.showAndWait();
        }
    }
}
