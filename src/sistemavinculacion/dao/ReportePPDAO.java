/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.dao;

/**
 *
 * @author chris
 */
import sistemavinculacion.modelo.ConexionBD;
import sistemavinculacion.pojo.ReportePP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportePPDAO {
    public static List<ReportePP> obtenerReportes() throws SQLException {
        List<ReportePP> reportes = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_reporte_pp, id_proyecto_pp, fecha_subida, archivo_reporte, " +
                                "comentarios, matricula, tipo FROM ReportesPP";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                reportes = new ArrayList<>();
                while (resultado.next()) {
                    reportes.add(serializarReporte(resultado));
                }
            } catch (SQLException e) {
                reportes = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return reportes;
    }

    public static List<ReportePP> obtenerReportesPorMatricula(int matricula) throws SQLException {
        List<ReportePP> reportes = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_reporte_pp, id_proyecto_pp, fecha_subida, archivo_reporte, " +
                                "comentarios, matricula, tipo FROM ReportesPP WHERE matricula = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, matricula);
                ResultSet resultado = prepararConsulta.executeQuery();
                reportes = new ArrayList<>();
                while (resultado.next()) {
                    reportes.add(serializarReporte(resultado));
                }
            } catch (SQLException e) {
                reportes = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return reportes;
    }
    
    public static HashMap<String, Object> registrarReporte(ReportePP reporte) {
    HashMap<String, Object> respuesta = new HashMap<>();
    Connection conexionBD = ConexionBD.abrirConexion();
    if (conexionBD != null) {
        try {
            String sentencia = "INSERT INTO reportes_pp (id_reporte_ss, id_proyecto_ss, fecha_subida, archivo_reporte, comentarios, matricula, tipo) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
            prepararSentencia.setInt(1, reporte.getIdReportePP());
            prepararSentencia.setInt(2, reporte.getIdProyectoPP());
            prepararSentencia.setString(3, reporte.getFechaSubida());
            prepararSentencia.setBytes(4, reporte.getArchivoReporte());
            prepararSentencia.setString(5, reporte.getComentarios());
            prepararSentencia.setInt(6, reporte.getMatricula());
            prepararSentencia.setString(7, reporte.getTipo());
            int filasAfectadas = prepararSentencia.executeUpdate();
            conexionBD.close();
            if (filasAfectadas > 0) {
                respuesta.put("error", false);
                respuesta.put("mensaje", "El reporte fue registrado con éxito.");
            } else {
                respuesta.put("error", true);
                respuesta.put("mensaje", "No se pudo registrar el reporte, por favor intente más tarde.");
            }
        } catch (SQLException e) {
            respuesta.put("error", true);
            respuesta.put("mensaje", e.getMessage());
        }
    } else {
        respuesta.put("error", true);
        respuesta.put("mensaje", "No se pudo establecer conexión con la base de datos.");
    }
    return respuesta;
}

    private static ReportePP serializarReporte(ResultSet resultado) throws SQLException {
        ReportePP reporte = new ReportePP();
        reporte.setIdReportePP(resultado.getInt("id_reporte_pp"));
        reporte.setIdProyectoPP(resultado.getInt("id_proyecto_pp"));
        reporte.setFechaSubida(resultado.getString("fecha_subida"));
        reporte.setArchivoReporte(resultado.getBytes("archivo_reporte"));
        reporte.setComentarios(resultado.getString("comentarios"));
        reporte.setMatricula(resultado.getInt("matricula"));
        reporte.setTipo(resultado.getString("tipo"));
        return reporte;
    }
}
