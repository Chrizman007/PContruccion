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
import sistemavinculacion.pojo.Documento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAO {
    public static List<Documento> obtenerDocumentos() throws SQLException {
        List<Documento> documentos = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_documento, matricula, nombre_documento, tipo, archivo FROM Documentos";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                ResultSet resultado = prepararConsulta.executeQuery();
                documentos = new ArrayList<>();
                while (resultado.next()) {
                    documentos.add(serializarDocumento(resultado));
                }
            } catch (SQLException e) {
                documentos = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return documentos;
    }

    public static List<Documento> obtenerDocumentosPorMatricula(int matricula) throws SQLException {
        List<Documento> documentos = null;
        Connection conexionBD = ConexionBD.abrirConexion();
        if (conexionBD != null) {
            try {
                String consulta = "SELECT id_documento, matricula, nombre_documento, tipo, archivo FROM Documentos WHERE matricula = ?";
                PreparedStatement prepararConsulta = conexionBD.prepareStatement(consulta);
                prepararConsulta.setInt(1, matricula);
                ResultSet resultado = prepararConsulta.executeQuery();
                documentos = new ArrayList<>();
                while (resultado.next()) {
                    documentos.add(serializarDocumento(resultado));
                }
            } catch (SQLException e) {
                documentos = null;
                e.printStackTrace();
            } finally {
                conexionBD.close();
            }
        }
        return documentos;
    }

    private static Documento serializarDocumento(ResultSet resultado) throws SQLException {
        Documento documento = new Documento();
        documento.setIdDocumento(resultado.getInt("id_documento"));
        documento.setMatricula(resultado.getInt("matricula"));
        documento.setNombreDocumento(resultado.getString("nombre_documento"));
        documento.setTipo(resultado.getString("tipo"));
        documento.setArchivo(resultado.getBytes("archivo"));
        return documento;
    }
}
