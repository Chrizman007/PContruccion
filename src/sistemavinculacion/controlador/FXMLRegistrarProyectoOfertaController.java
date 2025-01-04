/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;
/**
 * FXML Controller class
 *
 * @author chris
 */
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import sistemavinculacion.dao.ProyectoppDAO;
import sistemavinculacion.pojo.Proyectopp;
import sistemavinculacion.utilidades.Utilidades;

public class FXMLRegistrarProyectoOfertaController implements Initializable {

    @FXML
    private TableView<Proyectopp> tvProyectos;
    @FXML
    private TableColumn<Proyectopp, String> colNombre;
    @FXML
    private TableColumn<Proyectopp, String> colDescripcion;
    @FXML
    private TableColumn<Proyectopp, String> colOrganizacion;
    @FXML
    private TableColumn<Proyectopp, String> colRequisitos;
    @FXML
    private TableColumn<Proyectopp, String> colUbicacion;
    @FXML
    private TableColumn<Proyectopp, Integer> colCupos;
    
    private ObservableList<Proyectopp> proyectos;
    private ComboBox<String> comboBoxReferencia;
    private Set<String> proyectosOfertados = new HashSet<>();
    
    public void setComboBoxReferencia(ComboBox<String> comboBox) {
        this.comboBoxReferencia = comboBox;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionTabla();
    }
    
    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colOrganizacion.setCellValueFactory(new PropertyValueFactory<>("nombreOrganizacion"));
        colRequisitos.setCellValueFactory(new PropertyValueFactory<>("requisitos"));
        colUbicacion.setCellValueFactory(new PropertyValueFactory<>("ubicacion"));
        colCupos.setCellValueFactory(new PropertyValueFactory<>("cupos"));
    }

    private void cargarInformacionTabla() {
        proyectos = FXCollections.observableArrayList();
        try {
            List<Proyectopp> proyectosBD = ProyectoppDAO.obtenerProyectos();
            if (proyectosBD != null) {
                proyectos.addAll(proyectosBD);
                tvProyectos.setItems(proyectos);
            } else {
                Utilidades.mostrarAlertaSimple("Error",
                        "No se puede cargar la información de los proyectos en este momento.",
                        Alert.AlertType.ERROR);
            }
        } catch (SQLException ex) {
            Utilidades.mostrarAlertaSimple("Error",
                    "El sistema presentó fallas al recuperar la información. Inténtelo más tarde.",
                    Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void clicAdjuntarDocumento(ActionEvent event) {
        // Obtener el proyecto seleccionado en la tabla
        Proyectopp proyectoSeleccionado = tvProyectos.getSelectionModel().getSelectedItem();
        
        if (proyectoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Selecciona un proyecto", 
                    "Por favor selecciona un proyecto para adjuntar el documento.", 
                    Alert.AlertType.WARNING);
            return;
        }

        // Crear un diálogo para seleccionar el archivo
        FileChooser dialogoSeleccionArchivo = new FileChooser();
        dialogoSeleccionArchivo.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Documentos", "*.pdf", "*.docx", "*.txt"));
        File archivoSeleccionado = dialogoSeleccionArchivo.showOpenDialog(null);

        // Verificar si se seleccionó un archivo
        if (archivoSeleccionado != null) {
            try {
                // Leer el archivo seleccionado en bytes
                byte[] documentoBytes = Files.readAllBytes(archivoSeleccionado.toPath());

                // Llamar al DAO para actualizar el archivo en la base de datos
                ProyectoppDAO.subirDocumento(proyectoSeleccionado.getIdProyecto(), documentoBytes, archivoSeleccionado.getName());

                Utilidades.mostrarAlertaSimple("Documento adjuntado", 
                        "El documento se ha adjuntado correctamente al proyecto.", 
                        Alert.AlertType.INFORMATION);

            } catch (IOException ex) {
                Utilidades.mostrarAlertaSimple("Error", 
                        "Hubo un error al leer el archivo. Inténtalo nuevamente.", 
                        Alert.AlertType.ERROR);
            } catch (SQLException ex) {
                Utilidades.mostrarAlertaSimple("Error", 
                        "Hubo un error al actualizar la base de datos. Inténtalo nuevamente.", 
                        Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void clicAgregar(ActionEvent event) {
        Proyectopp proyectoSeleccionado = tvProyectos.getSelectionModel().getSelectedItem();

        if (proyectoSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Selecciona un proyecto", 
                    "Por favor selecciona un proyecto para agregar.", 
                    Alert.AlertType.WARNING);
            return;
        }

        try {
            int filasAfectadas = ProyectoppDAO.actualizarOfertado(proyectoSeleccionado.getIdProyecto(), 1); // Cambia 'ofertado' a 1
            if (filasAfectadas > 0) {
                
            proyectosOfertados.add(proyectoSeleccionado.getNombre());

            // Actualizar el ComboBox con los proyectos en el Set
            if (comboBoxReferencia != null) {
                comboBoxReferencia.getItems().setAll(proyectosOfertados);
            }
                
                Utilidades.mostrarAlertaSimple("Proyecto agregado", 
                        "El proyecto ha sido agregado satisfactoriamente.", 
                        Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                Utilidades.mostrarAlertaSimple("Sin cambios", 
                        "No se realizaron cambios en la base de datos.", 
                        Alert.AlertType.WARNING);
            }
        } catch (SQLException ex) {
            Utilidades.mostrarAlertaSimple("Error", 
                    "Error de conexión. Los datos no se han guardado: " + ex.getMessage(), 
                    Alert.AlertType.ERROR);
        }
    }

    private void cerrarVentana() {
        // Obtener el stage actual (ventana) y cerrarlo
        javafx.stage.Stage stage = (javafx.stage.Stage) tvProyectos.getScene().getWindow();
        stage.close();
    }

}
