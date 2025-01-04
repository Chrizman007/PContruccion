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
import sistemavinculacion.pojo.AsignacionSS;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AsignacionSSDAO {
    public static List<AsignacionSS> obtenerAsignacionesPorMatricula(int matricula) throws SQLException {
        List<AsignacionSS> asignaciones = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_asignacion_ss, id_proyecto_ss, matricula FROM asignaciones_ss WHERE matricula = ?";
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

    private static AsignacionSS serializarAsignacion(ResultSet resultado) throws SQLException {
        AsignacionSS asignacion = new AsignacionSS();
        asignacion.setIdAsignacionSS(resultado.getInt("id_asignacion_ss"));
        asignacion.setIdProyectoSS(resultado.getInt("id_proyecto_ss"));
        asignacion.setMatricula(resultado.getInt("matricula"));
        return asignacion;
    }
}
