package cu.edu.cujae.backend.core.util;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionImp {

	
	private static String url = "jdbc:postgresql://localhost:5432/postgres";
	private static String usuario = "postgres";
	private static String contraseña = "postgres";
	 
	 
	public ConnectionImp() {
	 
	}
	
	public static java.sql.Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, usuario, contraseña);
	}
	
	
}
