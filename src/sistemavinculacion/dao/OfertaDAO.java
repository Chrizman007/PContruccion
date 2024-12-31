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
import sistemavinculacion.pojo.Oferta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class OfertaDAO {
    public static HashMap<String, Object> registrarOferta(Oferta oferta) {
        HashMap<String, Object> respuesta = new HashMap<>();
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String sentencia = "INSERT INTO ofertas (fecha_inicio, fecha_fin) VALUES (?, ?)";
                PreparedStatement prepararSentencia = conexionBD.prepareStatement(sentencia);
                prepararSentencia.setString(1, oferta.getFechaInicio());
                prepararSentencia.setString(2, oferta.getFechaFin());
                int filasAfectadas = prepararSentencia.executeUpdate();
                conexionBD.close();
                if (filasAfectadas > 0) {
                    respuesta.put("error", false);
                    respuesta.put("mensaje", "La oferta fue registrada con éxito.");
                } else {
                    respuesta.put("error", true);
                    respuesta.put("mensaje", "No se pudo registrar la oferta, por favor intente más tarde.");
                }
            } catch (SQLException e) {
                respuesta.put("error", true);
                respuesta.put("mensaje", e.getMessage());
            }
        } else {
            respuesta.put("error", true);
            respuesta.put("mensaje", "No se pudo establecer conexión con la base de datos.");
        }
        return respuesta;
    }
}
