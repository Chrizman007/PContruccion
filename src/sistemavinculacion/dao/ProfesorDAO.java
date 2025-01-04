/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.dao;

import sistemavinculacion.modelo.ConexionBD;
import sistemavinculacion.pojo.Profesor;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 *
 * @author chris
 */
public class ProfesorDAO {

    // Método para obtener todos los profesores (ya existente)
    public static List<Profesor> obtenerProfesores() throws SQLException {
        List<Profesor> profesores = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_profesor, id_usuario, usuario, contrasena, nombre, correo, tipo_profesor FROM profesores";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                profesores = new ArrayList<>();
                while (resultado.next()) {
                    profesores.add(serializarProfesor(resultado));
                }
            } catch (SQLException e) {
                profesores = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return profesores;
    }

    // Nuevo método para obtener profesor por usuario y contraseña
public static Profesor obtenerProfesorPorCredenciales(String usuario, String contrasena, String tipoProfesor) throws SQLException {
    Connection conexion = ConexionBD.abrirConexion();
    Profesor profesor = null;
    if (conexion != null) {
        String consulta = "SELECT * FROM profesores WHERE usuario = ? AND contrasena = ? AND id_usuario = ?";
        PreparedStatement prepararConsulta = conexion.prepareStatement(consulta);
        prepararConsulta.setString(1, usuario);
        prepararConsulta.setString(2, contrasena);
        prepararConsulta.setString(3, tipoProfesor);
        ResultSet resultado = prepararConsulta.executeQuery();
        if (resultado.next()) {
            profesor = new Profesor();
            profesor.setIdProfesor(resultado.getInt("id_usuario"));
            profesor.setNombre(resultado.getString("nombre"));
            profesor.setUsuario(resultado.getString("usuario"));
        }
        conexion.close();
    }
    return profesor;
}

    // Método de serialización (ya existente)
    private static Profesor serializarProfesor(ResultSet resultado) throws SQLException {
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(resultado.getInt("id_profesor"));
        profesor.setIdUsuario(resultado.getInt("id_usuario"));
        profesor.setUsuario(resultado.getString("usuario"));
        profesor.setContrasena(resultado.getString("contrasena"));
        profesor.setNombre(resultado.getString("nombre"));
        profesor.setCorreo(resultado.getString("correo"));
        profesor.setTipoProfesor(resultado.getString("tipo_profesor"));
        return profesor;
    }
}
