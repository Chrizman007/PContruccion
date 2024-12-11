/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemavinculacion.dao;

import sistemavinculacion.modelo.ConexionBD;
import sistemavinculacion.pojo.Estudiante;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * DAO para la gestión de estudiantes.
 * @author chris
 */
public class EstudianteDAO {

    public static List<Estudiante> obtenerEstudiantes() throws SQLException {
        List<Estudiante> estudiantes = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT matricula, nombre, correo, telefono, carrera, seguro_facultativo, promedio_general, creditos, "
                        + "carta_liberacion, carta_asignacion, plan_actividades, evaluacion_organizacion, idUsuario, usuario, contrasena "
                        + "FROM estudiante";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                estudiantes = new ArrayList<>();
                while (resultado.next()) {
                    estudiantes.add(serializarEstudiante(resultado));
                }
            } catch (SQLException e) {
                estudiantes = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return estudiantes;
    }

    public static HashMap<String, Object> registrarEstudiante(Estudiante estudiante) {
        HashMap<String, Object> respuesta = new HashMap<>();
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO estudiante (matricula, nombre, correo, telefono, carrera, seguro_facultativo, promedio_general, "
                        + "creditos, carta_liberacion, carta_asignacion, plan_actividades, evaluacion_organizacion, idUsuario, usuario, contrasena) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, estudiante.getMatricula());
                prepararSentencia.setString(2, estudiante.getNombre());
                prepararSentencia.setString(3, estudiante.getCorreo());
                prepararSentencia.setString(4, estudiante.getTelefono());
                prepararSentencia.setString(5, estudiante.getCarrera());
                prepararSentencia.setString(6, estudiante.getSeguroFacultativo());
                prepararSentencia.setFloat(7, estudiante.getPromedioGeneral());
                prepararSentencia.setInt(8, estudiante.getCreditos());
                prepararSentencia.setBytes(9,estudiante.getCartaLiberacion());
                prepararSentencia.setBytes(10,estudiante.getCartaAsignacion());
                prepararSentencia.setBytes(11, estudiante.getPlanActividades());
                prepararSentencia.setBytes(12, estudiante.getEvaluacionOrganizacion());
                prepararSentencia.setInt(13, estudiante.getIdUsuario());
                prepararSentencia.setString(14, estudiante.getUsuario());
                prepararSentencia.setString(15, estudiante.getContraseña());
                int filasAfectadas = prepararSentencia.executeUpdate();
                conexionBD.close();
                if (filasAfectadas > 0) {
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "El estudiante " + estudiante.getNombre() + " fue registrado con éxito");
                } else {
                    respuesta.put("error", true);
                    respuesta.put("mensaje", "No se pudo registrar el estudiante, por favor intente más tarde");
                }
            } catch (SQLException e) {
                respuesta.put("error", true);
                respuesta.put("mensaje", e.getMessage());
            }
        } else {
            respuesta.put("error", true);
            respuesta.put("mensaje", "No se pudo establecer conexión con la base de datos");
        }
        return respuesta;
    }

    private static Estudiante serializarEstudiante(ResultSet resultado) throws SQLException {
        Estudiante estudiante = new Estudiante();
        estudiante.setMatricula(resultado.getString("matricula"));
        estudiante.setNombre(resultado.getString("nombre"));
        estudiante.setCorreo(resultado.getString("correo"));
        estudiante.setTelefono(resultado.getString("telefono"));
        estudiante.setCarrera(resultado.getString("carrera"));
        estudiante.setSeguroFacultativo(resultado.getString("seguro_facultativo"));
        estudiante.setPromedioGeneral(resultado.getFloat("promedio_general"));
        estudiante.setCreditos(resultado.getInt("creditos"));
        estudiante.setCartaLiberacion(resultado.getBytes("carta_liberacion"));
        estudiante.setCartaAsignacion(resultado.getBytes("carta_asignacion"));
        estudiante.setPlanActividades(resultado.getBytes("plan_actividades"));
        estudiante.setEvaluacionOrganizacion(resultado.getBytes("evaluacion_organizacion"));
        estudiante.setIdUsuario(resultado.getInt("idUsuario"));
        estudiante.setUsuario(resultado.getString("usuario"));
        estudiante.setContraseña(resultado.getString("contrasena"));
        return estudiante;
    }
}
