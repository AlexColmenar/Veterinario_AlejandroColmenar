package proyecto_final_alejandrocolmenar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*@author Alejandro*/
public class CONEXION {

    private static final String url = "jdbc:mysql://iasanz.synology.me:3306/acolmenara01_proyecto?serverTimezone=UTC";
    private static final String user = "alumno";
    private static final String password = "AlumnoSanz$1";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

}
