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
import sistemavinculacion.pojo.Expediente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistemavinculacion.pojo.Estudiante;

public class ExpedienteDAO {
    public static List<Expediente> obtenerExpedientes() throws SQLException {
        List<Expediente> expedientes = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_expediente, matricula FROM expedientes";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                expedientes = new ArrayList<>();
                while (resultado.next()) {
                    expedientes.add(serializarExpediente(resultado));
                }
            } catch (SQLException e) {
                expedientes = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return expedientes;
    }
    
    public static List<Expediente> obtenerExpedientesPorMatricula(int matricula) throws SQLException {
        List<Expediente> expedientes = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_expediente, matricula FROM expedientes WHERE matricula = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, matricula);
                ResultSet resultado = prepararConsulta.executeQuery();
                expedientes = new ArrayList<>();
                while (resultado.next()) {
                    expedientes.add(serializarExpediente(resultado));
                }
            } catch (SQLException e) {
                expedientes = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return expedientes;
    }
    
    
    private static Expediente serializarExpediente(ResultSet resultado) throws SQLException {
        Expediente expediente = new Expediente();
        expediente.setIdExpediente(resultado.getInt("id_expediente"));
        expediente.setMatricula(resultado.getInt("matricula"));
        return expediente;
    }
}
