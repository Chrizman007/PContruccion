/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemavinculacion.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sistemavinculacion.dao.DocumentoDAO;
import sistemavinculacion.dao.EstudianteDAO;
import sistemavinculacion.dao.ReportePPDAO;
import sistemavinculacion.pojo.Documento;
import sistemavinculacion.pojo.Estudiante;
import sistemavinculacion.pojo.ReportePP;
import sistemavinculacion.utilidades.Constantes;
import sistemavinculacion.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class FXMLExpedienteEstudianteController implements Initializable {

    @FXML
    private TextField tfNombreEstudiante;
    @FXML
    private TextField ftMatricula;
    @FXML
    private TextField tfSeguroFacultativo;
    @FXML
    private TextField tfCorreo;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfPromedio;
    @FXML
    private TextField tfCreditos;
    @FXML
    private TableView<ReportePP> tvReportes;
    @FXML
    private TableColumn<ReportePP, String> colFechaCreacion;
    @FXML
    private TableColumn<ReportePP, Integer> colNumeroReporte;
    @FXML
    private TableColumn<ReportePP, String> colNombreReporte;
    
    private ObservableList<ReportePP> reportes;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cargarDatosEstudiante(Constantes.MatriculaActual);
        configurarTabla();
        cargarInformacionTabla();
    }

    public void cargarDatosEstudiante(int matricula) {
        try {
            // Llamada al DAO para obtener el estudiante
            Estudiante estudiante = EstudianteDAO.obtenerEstudiantePorMatricula(matricula);
            if (estudiante != null) {
                // Asignar los valores a los campos de texto
                tfNombreEstudiante.setText(estudiante.getNombre());
                ftMatricula.setText(String.valueOf(estudiante.getMatricula()));
                tfCorreo.setText(estudiante.getCorreo());
                tfTelefono.setText(estudiante.getTelefono());
                tfSeguroFacultativo.setText(estudiante.getSeguroFacultativo());
                tfPromedio.setText(String.valueOf(estudiante.getPromedioGeneral()));
                tfCreditos.setText(String.valueOf(estudiante.getCreditos()));
            } else {
                System.out.println("Estudiante no encontrado con matrícula: " + matricula);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar los datos del estudiante: " + e.getMessage());
        }
    }
    
    private void configurarTabla() {
        colNumeroReporte.setCellValueFactory(new PropertyValueFactory<>("idReportePP"));
        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaSubida"));
        colNombreReporte.setCellValueFactory(new PropertyValueFactory<>("tipo"));
    }

    private void cargarInformacionTabla() {
        reportes = FXCollections.observableArrayList();
        try {
            List<ReportePP> reportesBD = ReportePPDAO.obtenerReportesDesempeño(Constantes.MatriculaActual);

            if (reportesBD != null && !reportesBD.isEmpty()) {
                reportes.addAll(reportesBD);
                tvReportes.setItems(reportes);
            } else {
                Utilidades.mostrarAlertaSimple("Información", 
                        "No se encontraron reportes en la base de datos.", 
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
    private void clicImprimirReporte() {
        ReportePP reporteSeleccionado = tvReportes.getSelectionModel().getSelectedItem();

        if (reporteSeleccionado == null) {
            Utilidades.mostrarAlertaSimple("Selección requerida", 
                    "Por favor, selecciona un reporte para descargar.", 
                    Alert.AlertType.WARNING);
            return;
        }

        // Comprobamos que el archivo del reporte no sea nulo
        if (reporteSeleccionado.getArchivoReporte() == null) {
            Utilidades.mostrarAlertaSimple("Archivo no disponible", 
                    "El archivo asociado al reporte no está disponible.", 
                    Alert.AlertType.ERROR);
            return;
        }

        // Crear un FileChooser para seleccionar la ubicación de guardado
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar reporte");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Proporcionar un nombre sugerido para el archivo
        fileChooser.setInitialFileName("Reporte_" + reporteSeleccionado.getIdReportePP() + ".pdf");

        // Mostrar el diálogo de guardado
        File archivoSeleccionado = fileChooser.showSaveDialog(tvReportes.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                // Guardar el archivo en la ubicación seleccionada
                Files.write(archivoSeleccionado.toPath(), reporteSeleccionado.getArchivoReporte());
                Utilidades.mostrarAlertaSimple("Éxito", 
                        "El archivo se guardó correctamente en " + archivoSeleccionado.getAbsolutePath(), 
                        Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", 
                        "Hubo un error al guardar el archivo: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicImprimirAsignacion() {

        // Obtener el documento desde la base de datos filtrando por matrícula y tipo
        Documento documento;
        try {
            documento = DocumentoDAO.obtenerDocumentoCartaAsignacion(Constantes.MatriculaActual);
            if (documento == null || documento.getArchivo() == null) {
                Utilidades.mostrarAlertaSimple("Documento no encontrado", 
                        "No se encontró un documento de tipo 'Carta Asignación' asociado a la matrícula: " + Constantes.MatriculaActual, 
                        Alert.AlertType.WARNING);
                return;
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", 
                    "Error al obtener el documento desde la base de datos. Intenta nuevamente más tarde.", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            return;
        }

        // Crear un FileChooser para seleccionar la ubicación de guardado
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar carta de asignación");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Sugerir un nombre de archivo
        fileChooser.setInitialFileName("Carta_Asignacion_" + Constantes.MatriculaActual + ".pdf");

        // Mostrar el diálogo de guardado
        File archivoSeleccionado = fileChooser.showSaveDialog(tvReportes.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                // Guardar el archivo en la ubicación seleccionada
                Files.write(archivoSeleccionado.toPath(), documento.getArchivo());
                Utilidades.mostrarAlertaSimple("Éxito", 
                        "El archivo se guardó correctamente en " + archivoSeleccionado.getAbsolutePath(), 
                        Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", 
                        "Hubo un error al guardar el archivo: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicImprimirLiberacion(ActionEvent event) {
        Documento documento;
        try {
            documento = DocumentoDAO.obtenerDocumentoCartaAsignacion(Constantes.MatriculaActual);
            if (documento == null || documento.getArchivo() == null) {
                Utilidades.mostrarAlertaSimple("Documento no encontrado", 
                        "No se encontró un documento de tipo 'Carta Liberacion' asociado a la matrícula: " + Constantes.MatriculaActual, 
                        Alert.AlertType.WARNING);
                return;
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", 
                    "Error al obtener el documento desde la base de datos. Intenta nuevamente más tarde.", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            return;
        }

        // Crear un FileChooser para seleccionar la ubicación de guardado
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar carta de liberacion");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Sugerir un nombre de archivo
        fileChooser.setInitialFileName("Carta_Liberacion_" + Constantes.MatriculaActual + ".pdf");

        // Mostrar el diálogo de guardado
        File archivoSeleccionado = fileChooser.showSaveDialog(tvReportes.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                // Guardar el archivo en la ubicación seleccionada
                Files.write(archivoSeleccionado.toPath(), documento.getArchivo());
                Utilidades.mostrarAlertaSimple("Éxito", 
                        "El archivo se guardó correctamente en " + archivoSeleccionado.getAbsolutePath(), 
                        Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", 
                        "Hubo un error al guardar el archivo: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicImprimirActividades(ActionEvent event) {
        
        Documento documento;
        try {
            documento = DocumentoDAO.obtenerDocumentoPlanActividades(Constantes.MatriculaActual);
            if (documento == null || documento.getArchivo() == null) {
                Utilidades.mostrarAlertaSimple("Documento no encontrado", 
                        "No se encontró un documento de tipo 'Plan Actividades' asociado a la matrícula: " + Constantes.MatriculaActual, 
                        Alert.AlertType.WARNING);
                return;
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", 
                    "Error al obtener el documento desde la base de datos. Intenta nuevamente más tarde.", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            return;
        }

        // Crear un FileChooser para seleccionar la ubicación de guardado
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Plan de Actividades");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Sugerir un nombre de archivo
        fileChooser.setInitialFileName("Plan_Actividades_" + Constantes.MatriculaActual + ".pdf");

        // Mostrar el diálogo de guardado
        File archivoSeleccionado = fileChooser.showSaveDialog(tvReportes.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                // Guardar el archivo en la ubicación seleccionada
                Files.write(archivoSeleccionado.toPath(), documento.getArchivo());
                Utilidades.mostrarAlertaSimple("Éxito", 
                        "El archivo se guardó correctamente en " + archivoSeleccionado.getAbsolutePath(), 
                        Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", 
                        "Hubo un error al guardar el archivo: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicImprimirEvaluacion(ActionEvent event) {
        
        Documento documento;
        try {
            documento = DocumentoDAO.obtenerDocumentoEvaluacion(Constantes.MatriculaActual);
            if (documento == null || documento.getArchivo() == null) {
                Utilidades.mostrarAlertaSimple("Documento no encontrado", 
                        "No se encontró un documento de tipo 'Evaluacion Organizacion' asociado a la matrícula: " + Constantes.MatriculaActual, 
                        Alert.AlertType.WARNING);
                return;
            }
        } catch (SQLException e) {
            Utilidades.mostrarAlertaSimple("Error", 
                    "Error al obtener el documento desde la base de datos. Intenta nuevamente más tarde.", 
                    Alert.AlertType.ERROR);
            e.printStackTrace();
            return;
        }

        // Crear un FileChooser para seleccionar la ubicación de guardado
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Evaluacion de Organizacion");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"),
            new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
        );

        // Sugerir un nombre de archivo
        fileChooser.setInitialFileName("Evaluacion_Organizacion_" + Constantes.MatriculaActual + ".pdf");

        // Mostrar el diálogo de guardado
        File archivoSeleccionado = fileChooser.showSaveDialog(tvReportes.getScene().getWindow());

        if (archivoSeleccionado != null) {
            try {
                // Guardar el archivo en la ubicación seleccionada
                Files.write(archivoSeleccionado.toPath(), documento.getArchivo());
                Utilidades.mostrarAlertaSimple("Éxito", 
                        "El archivo se guardó correctamente en " + archivoSeleccionado.getAbsolutePath(), 
                        Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error", 
                        "Hubo un error al guardar el archivo: " + e.getMessage(), 
                        Alert.AlertType.ERROR);
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clicFinalizar(ActionEvent event) {
        Utilidades.mostrarAlertaSimple("Fin de Consulta", "Consulta Exitosa", Alert.AlertType.INFORMATION);
        
        Stage stageActual = (Stage) tvReportes.getScene().getWindow();
        stageActual.close();
    }
    
}
