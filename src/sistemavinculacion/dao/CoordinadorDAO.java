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

    // Método para obtener todos los coordinadores
    public static List<Coordinador> obtenerCoordinadores() throws SQLException {
        List<Coordinador> coordinadores = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_coordinador, id_usuario, usuario, contrasena, nombre, correo FROM coordinadores";
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

    // Nuevo método para obtener coordinador por usuario y contraseña
public static Coordinador obtenerCoordinadorPorCredenciales(String usuario, String contrasena) throws SQLException {
    Connection conexion = ConexionBD.abrirConexion();
    Coordinador coordinador = null;
    if (conexion != null) {
        String consulta = "SELECT * FROM coordinadores WHERE usuario = ? AND contrasena = ?";
        PreparedStatement prepararConsulta = conexion.prepareStatement(consulta);
        prepararConsulta.setString(1, usuario);
        prepararConsulta.setString(2, contrasena);
        ResultSet resultado = prepararConsulta.executeQuery();
        if (resultado.next()) {
            coordinador = new Coordinador();
            coordinador.setIdCoordinador(resultado.getInt("id_coordinador"));
            coordinador.setNombre(resultado.getString("nombre"));
            coordinador.setUsuario(resultado.getString("usuario"));
        }
        conexion.close();
    }
    return coordinador;
}

    private static Coordinador serializarCoordinador(ResultSet resultado) throws SQLException {
        Coordinador coordinador = new Coordinador();
        coordinador.setIdCoordinador(resultado.getInt("id_coordinador"));
        coordinador.setIdUsuario(resultado.getInt("id_usuario"));
        coordinador.setUsuario(resultado.getString("usuario"));
        coordinador.setContraseña(resultado.getString("contrasena"));
        coordinador.setNombre(resultado.getString("nombre"));
        coordinador.setCorreo(resultado.getString("correo"));
        return coordinador;
    }
}