package sistemavinculacion.modelo;

import sistemavinculacion.utilidades.Constantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String NOMBRE_BD = "gestion_ss_pp";
    private static final String IP = "127.0.0.1";
    private static final String PUERTO = "3306";
    
    public static Connection abrirConexion() {
        Connection conexionBD = null;
        String urlConexion = String.format(
            "jdbc:mysql://%s:%s/%s?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
            IP, PUERTO, NOMBRE_BD
        );
        try {
            Class.forName(DRIVER);
            conexionBD = DriverManager.getConnection(urlConexion, 
                Constantes.USER_BD, Constantes.PASSWORD_BD);
        } catch (ClassNotFoundException ex) {
            System.err.println("Error: No se pudo cargar el driver de MySQL.");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("Error: No se pudo establecer la conexión con la base de datos.");
            ex.printStackTrace();
        }
        return conexionBD;
    }
}
