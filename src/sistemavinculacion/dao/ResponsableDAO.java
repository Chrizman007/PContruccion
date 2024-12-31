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
import sistemavinculacion.pojo.Responsable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponsableDAO {
    public static Responsable obtenerResponsablePorId(int idResponsable) throws SQLException {
        Responsable responsable = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_responsable, nombre, correo, telefono FROM ResponsableProyecto WHERE id_responsable = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, idResponsable);
                ResultSet resultado = prepararConsulta.executeQuery();
                if (resultado.next()) {
                    responsable = serializarResponsable(resultado);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return responsable;
    }

    private static Responsable serializarResponsable(ResultSet resultado) throws SQLException {
        Responsable responsable = new Responsable();
        responsable.setIdResponsable(resultado.getInt("id_responsable"));
        responsable.setNombre(resultado.getString("nombre"));
        responsable.setCorreo(resultado.getString("correo"));
        responsable.setTelefono(resultado.getString("telefono"));
        return responsable;
    }
}
