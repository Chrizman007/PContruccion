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
import java.util.List;
/**
 *
 * @author chris
 */
public class ProfesorDAO {

    public static List<Profesor> obtenerProfesores() throws SQLException {
        List<Profesor> profesores = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT idProfesor, nombre, matricula, correo, idUsuario, usuario, contrasena FROM profesor";
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


    private static Profesor serializarProfesor(ResultSet resultado) throws SQLException {
        Profesor profesor = new Profesor();
        profesor.setIdProfesor(resultado.getInt("idProfesor"));
        profesor.setNombre(resultado.getString("nombre"));
        profesor.setMatricula(resultado.getString("matricula"));
        profesor.setCorreo(resultado.getString("correo"));
        profesor.setIdUsuario(resultado.getInt("idUsuario"));
        profesor.setUsuario(resultado.getString("usuario"));
        profesor.setContraseña(resultado.getString("contrasena"));
        return profesor;
    }
}
