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
import sistemavinculacion.pojo.Coordinador;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CoordinadorDAO {

    public static List<Coordinador> obtenerCoordinadores() throws SQLException {
        List<Coordinador> coordinadores = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idCoordinador, nombre, matricula, correo, idUsuario, usuario, contrasena "
                        + "FROM coordinador";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                coordinadores = new ArrayList<>();
                while (resultado.next()) {
                    coordinadores.add(serializarCoordinador(resultado));
                }
            } catch (SQLException e) {
                coordinadores = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return coordinadores;
    }


    private static Coordinador serializarCoordinador(ResultSet resultado) throws SQLException {
        Coordinador coordinador = new Coordinador();
        coordinador.setIdCoordinador(resultado.getInt("idCoordinador"));
        coordinador.setNombre(resultado.getString("nombre"));
        coordinador.setMatricula(resultado.getString("matricula"));
        coordinador.setCorreo(resultado.getString("correo"));
        coordinador.setIdUsuario(resultado.getInt("idUsuario"));
        coordinador.setUsuario(resultado.getString("usuario"));
        coordinador.setContraseña(resultado.getString("contrasena"));
        return coordinador;
    }
}
