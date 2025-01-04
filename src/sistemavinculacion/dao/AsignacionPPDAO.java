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
import sistemavinculacion.pojo.AsignacionPP;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionPPDAO {
    public static List<AsignacionPP> obtenerAsignacionesPorMatricula(int matricula) throws SQLException {
        List<AsignacionPP> asignaciones = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_asignacion_pp, id_proyecto_pp, matricula FROM asignaciones_pp WHERE matricula = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, matricula);
                ResultSet resultado = prepararConsulta.executeQuery();
                asignaciones = new ArrayList<>();
                while (resultado.next()) {
                    asignaciones.add(serializarAsignacion(resultado));
                }
            } catch (SQLException e) {
                asignaciones = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return asignaciones;
    }
    
    
   public static void asignarProyectoAEstudiante(int matriculaEstudiante, int idProyecto) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        
        if (conexionBD != null) {
            try {
                String consulta = "INSERT INTO asignaciones_pp (matricula, id_proyecto_pp) VALUES (?, ?)";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, matriculaEstudiante);  
                prepararConsulta.setInt(2, idProyecto);  
                
                prepararConsulta.executeUpdate();  
            } catch (SQLException e) {
                e.printStackTrace();  
            } finally {
                // Cerramos la conexión
                conexionBD.close();
            }
        }
    }


    private static AsignacionPP serializarAsignacion(ResultSet resultado) throws SQLException {
        AsignacionPP asignacion = new AsignacionPP();
        asignacion.setIdAsignacionPP(resultado.getInt("id_asignacion_pp"));
        asignacion.setIdProyectoPP(resultado.getInt("id_proyecto_pp"));
        asignacion.setMatricula(resultado.getInt("matricula"));
        return asignacion;
    }
}
