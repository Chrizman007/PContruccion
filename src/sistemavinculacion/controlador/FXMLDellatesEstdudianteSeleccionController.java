/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistemavinculacion.pojo.Estudiante;
import sistemavinculacion.dao.EstudianteDAO;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLDellatesEstdudianteSeleccionController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfMatricula;
    @FXML
    private TextField tfCarrera;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfPromedio;
    @FXML
    private TextField tfCreditos;
    
    private Stage stageSeleccionEstudianteProyecto;
    private Integer matriculaEstudianteSeleccionada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }
    
    public void setMatriculaEstudianteSeleccionada(Integer matricula) throws SQLException {
        this.matriculaEstudianteSeleccionada = matricula;
        cargarDatos(); // Llama al método para cargar los datos
    }
    
    
    public void setStageSeleccionEstudianteProyecto(Stage stage) {
        this.stageSeleccionEstudianteProyecto = stage;
    }
    
    public void cargarDatos() throws SQLException {
        
        Estudiante estudiante = EstudianteDAO.obtenerEstudiantePorMatricula(matriculaEstudianteSeleccionada);
        
        if (estudiante != null) {
            tfNombre.setText(estudiante.getNombre());
            tfMatricula.setText(estudiante.getMatricula().toString());
            tfCarrera.setText(estudiante.getCarrera());
            tfCorreo.setText(estudiante.getCorreo());
            tfTelefono.setText(estudiante.getTelefono());
            tfPromedio.setText(estudiante.getPromedioGeneral().toString());
            tfCreditos.setText(estudiante.getCreditos().toString());
        }
    }

    @FXML
    private void clicVolver(ActionEvent event) {
        
        Stage stageActual = (Stage) tfNombre.getScene().getWindow();
        stageActual.close();
    }

    @FXML
    private void clicSeleccionarProyecto(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sistemavinculacion/vista/FXMLProyectosAsignar.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la nueva ventana
            FXMLProyectosAsignarController controller = loader.getController();

            // Pasar las referencias de las ventanas actuales
            controller.setStageSeleccionEstudianteProyecto(stageSeleccionEstudianteProyecto);
            controller.setStageDetallesEstudianteSeleccion((Stage) tfNombre.getScene().getWindow());

            // Pasar la matrícula del estudiante al controlador de la nueva ventana
            controller.setMatriculaEstudiante(matriculaEstudianteSeleccionada);

            // Configurar y mostrar la nueva ventana
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Asignar Proyecto");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
