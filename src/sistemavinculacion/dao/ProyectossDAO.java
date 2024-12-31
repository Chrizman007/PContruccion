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
import sistemavinculacion.pojo.Proyectoss;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProyectossDAO {
    public static List<Proyectoss> obtenerProyectos() throws SQLException {
        List<Proyectoss> proyectos = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_proyecto, nombre, descripcion, cupos, requisitos, fecha_inicio, fecha_fin, " +
                                "nombre_organizacion, ubicacion, ofertado, id_responsable FROM Proyectoss";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                proyectos = new ArrayList<>();
                while (resultado.next()) {
                    proyectos.add(serializarProyecto(resultado));
                }
            } catch (SQLException e) {
                proyectos = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return proyectos;
    }

    private static Proyectoss serializarProyecto(ResultSet resultado) throws SQLException {
        Proyectoss proyecto = new Proyectoss();
        proyecto.setIdProyecto(resultado.getInt("id_proyecto"));
        proyecto.setNombre(resultado.getString("nombre"));
        proyecto.setDescripcion(resultado.getString("descripcion"));
        proyecto.setCupos(resultado.getInt("cupos"));
        proyecto.setRequisitos(resultado.getString("requisitos"));
        proyecto.setFechaInicio(resultado.getString("fecha_inicio"));
        proyecto.setFechaFin(resultado.getString("fecha_fin"));
        proyecto.setNombreOrganizacion(resultado.getString("nombre_organizacion"));
        proyecto.setUbicacion(resultado.getString("ubicacion"));
        proyecto.setOfertado(resultado.getBoolean("ofertado"));
        proyecto.setIdResponsable(resultado.getInt("id_responsable"));
        return proyecto;
    }

    public static int insertarProyecto(Proyectoss proyecto) throws SQLException {
        int resultado = 0;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "INSERT INTO Proyectoss (nombre, descripcion, cupos, requisitos, fecha_inicio, fecha_fin, " +
                                "nombre_organizacion, ubicacion, ofertado, id_responsable) VALUES (?, ?, ?, ?, ?, ?, ?, ?, FALSE, NULL)";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, proyecto.getNombre());
                prepararConsulta.setString(2, proyecto.getDescripcion());
                prepararConsulta.setInt(3, proyecto.getCupos());
                prepararConsulta.setString(4, proyecto.getRequisitos());
                prepararConsulta.setString(5, proyecto.getFechaInicio());
                prepararConsulta.setString(6, proyecto.getFechaFin());
                prepararConsulta.setString(7, proyecto.getNombreOrganizacion());
                prepararConsulta.setString(8, proyecto.getUbicacion());
                
                resultado = prepararConsulta.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return resultado;
    }
}