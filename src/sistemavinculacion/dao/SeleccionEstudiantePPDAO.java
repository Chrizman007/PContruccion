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
import sistemavinculacion.pojo.SeleccionEstudiantePP;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeleccionEstudiantePPDAO {
    public static List<SeleccionEstudiantePP> obtenerSeleccionPP() throws SQLException {
        List<SeleccionEstudiantePP> selecciones = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_seleccion, opcion1, opcion2, opcion3, matricula FROM seleccion_estudiante_pp";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                selecciones = new ArrayList<>();
                while (resultado.next()) {
                    selecciones.add(serializarSeleccionPP(resultado));
                }
            } catch (SQLException e) {
                selecciones = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return selecciones;
    }
    
    
    
    public static SeleccionEstudiantePP obtenerSeleccionPorMatricula(int matricula) throws SQLException {
    SeleccionEstudiantePP seleccion = null;
    Connection conexionBD = ConexionBD.abrirConexion();
    if (conexionBD != null) {
        try {
            String consulta = "SELECT * FROM seleccion_estudiante_pp WHERE matricula = ?";
            PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
            prepararConsulta.setInt(1, matricula);
            ResultSet resultado = prepararConsulta.executeQuery();
            if (resultado.next()) {
                seleccion = serializarSeleccionPP(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conexionBD.close();
        }
    }
    return seleccion;
}
    
    public static void RegistrarPrimeraOpcion(String nombreProyecto, int matricula) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "INSERT INTO seleccion_estudiante_pp (opcion1, matricula) VALUES (?, ?)";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, nombreProyecto);
                prepararConsulta.setInt(2, matricula);
                prepararConsulta.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
    }
    
    public static void RegistrarSegundaOpcion(String nombreProyecto, int matricula) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "UPDATE seleccion_estudiante_pp SET opcion2 = ? WHERE matricula = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, nombreProyecto);
                prepararConsulta.setInt(2, matricula);
                prepararConsulta.executeUpdate(); // Cambiar a executeUpdate
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
    }
    
    public static void RegistrarTerceraOpcion(String nombreProyecto, int matricula) throws SQLException {
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "UPDATE seleccion_estudiante_pp SET opcion3 = ? WHERE matricula = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setString(1, nombreProyecto);
                prepararConsulta.setInt(2, matricula);
                prepararConsulta.executeUpdate(); // Cambiar a executeUpdate
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
    }


    private static SeleccionEstudiantePP serializarSeleccionPP(ResultSet resultado) throws SQLException {
        SeleccionEstudiantePP seleccion = new SeleccionEstudiantePP();
        seleccion.setIdSeleccion(resultado.getInt("id_seleccion"));
        seleccion.setOpcion1(resultado.getString("opcion1"));
        seleccion.setOpcion2(resultado.getString("opcion2"));
        seleccion.setOpcion3(resultado.getString("opcion3"));
        seleccion.setMatricula(resultado.getInt("matricula"));
        return seleccion;
    }
}

