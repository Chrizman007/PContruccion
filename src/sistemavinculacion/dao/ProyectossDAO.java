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
                String consulta = "SELECT id_proyecto, nombre, descripcion, id_organizacion, cupos, requisitos, fecha_inicio, fecha_fin FROM Proyectoss";
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
        proyecto.setIdOrganizacion(resultado.getInt("id_organizacion"));
        proyecto.setCupos(resultado.getInt("cupos"));
        proyecto.setRequisitos(resultado.getString("requisitos"));
        proyecto.setFechaInicio(resultado.getString("fecha_inicio"));
        proyecto.setFechaFin(resultado.getString("fecha_fin"));
        return proyecto;
    }
   
}
