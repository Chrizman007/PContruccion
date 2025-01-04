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
import sistemavinculacion.pojo.SeleccionEstudianteSS;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeleccionEstudianteSSDAO {
    public static List<SeleccionEstudianteSS> obtenerSeleccionSS() throws SQLException {
        List<SeleccionEstudianteSS> selecciones = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_seleccion, opcion1, opcion2, opcion3, matricula FROM seleccion_estudiante_ss";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                selecciones = new ArrayList<>();
                while (resultado.next()) {
                    selecciones.add(serializarSeleccionSS(resultado));
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

    private static SeleccionEstudianteSS serializarSeleccionSS(ResultSet resultado) throws SQLException {
        SeleccionEstudianteSS seleccion = new SeleccionEstudianteSS();
        seleccion.setIdSeleccion(resultado.getInt("id_seleccion"));
        seleccion.setOpcion1(resultado.getString("opcion1"));
        seleccion.setOpcion2(resultado.getString("opcion2"));
        seleccion.setOpcion3(resultado.getString("opcion3"));
        seleccion.setMatricula(resultado.getInt("matricula"));
        return seleccion;
    }
}

