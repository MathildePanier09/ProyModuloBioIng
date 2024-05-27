package BBDD.create;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import users.Deportista;

public class Create {
	private static final String URL = "jdbc:mariadb://localhost:3306/ProyModuloBioIng";
	private static final String USER = "root";
	private static final String PASS = "";

	public Create() {
		createTablesIfNotExist();
		removeDuplicates();
	}

	//--------------------CREAR TABLAS DE USUARIOS Y RELACION ---------------------
	public static void createTablesIfNotExist() {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			// Crear tabla COACH si no existe
			String createCoachTableSql = "CREATE TABLE IF NOT EXISTS COACH (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), pass VARCHAR(255))";
			try (PreparedStatement pstmt = conn.prepareStatement(createCoachTableSql)) {
				pstmt.executeUpdate();
			}

			// Crear tabla DEPORTISTA si no existe
			String createDeportistaTableSql = "CREATE TABLE IF NOT EXISTS DEPORTISTA (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), pass VARCHAR(255))";
			try (PreparedStatement pstmt = conn.prepareStatement(createDeportistaTableSql)) {
				pstmt.executeUpdate();
			}
			String createSensorTableSql = "CREATE TABLE IF NOT EXISTS SENSOR (" +
					"id INT AUTO_INCREMENT PRIMARY KEY, " +
					"value INT, " +
					"time VARCHAR(255), " +
					"ownerID VARCHAR(255), " +
					"sensorFeatures VARCHAR(255))";
			try (PreparedStatement pstmt = conn.prepareStatement(createSensorTableSql)) {
				pstmt.executeUpdate();
			}
			// Crear tabla COACH_DEPORTISTA si no existe
			String createCoachDeportistaTableSql = "CREATE TABLE IF NOT EXISTS COACH_DEPORTISTA (coach_id INT, deportista_id INT, FOREIGN KEY (coach_id) REFERENCES COACH(id), FOREIGN KEY (deportista_id) REFERENCES DEPORTISTA(id))";
			try (PreparedStatement pstmt = conn.prepareStatement(createCoachDeportistaTableSql)) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Crear tabla SENSOR_DEP si no existe
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String createRelateSensorToDepTableSql = "CREATE TABLE IF NOT EXISTS SENSOR_DEP (" +
					"sensor_id INT, " +
					"deportista_id INT, " +
					"FOREIGN KEY (sensor_id) REFERENCES SENSOR(id), " +
					"FOREIGN KEY (deportista_id) REFERENCES DEPORTISTA(id))";
			try (PreparedStatement pstmt = conn.prepareStatement(createRelateSensorToDepTableSql)) {
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}
	public static void relateSensorToDep(int sensorId, int deportistaId) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String insertSql = "INSERT INTO SENSOR_DEP (sensor_id, deportista_id) VALUES (?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
				pstmt.setInt(1, sensorId);
				pstmt.setInt(2, deportistaId);

				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	 public static Deportista getDeportistaByName(String name) {
	        Deportista deportista = null;
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
	            String query = "SELECT id, name, pass FROM DEPORTISTA WHERE name = ?";
	            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	                pstmt.setString(1, name);
	                ResultSet rs = pstmt.executeQuery();
	                if (rs.next()) {
	                    String id = rs.getString("id");
	                    String deportistaName = rs.getString("name");
	                    String password = rs.getString("pass");
	                    deportista = new Deportista(id, deportistaName, password);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return deportista;
	    }
	public static List<String> getAllDeportistas() {
        List<String> deportistas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String querySql = "SELECT name FROM DEPORTISTA";
            try (PreparedStatement pstmt = conn.prepareStatement(querySql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    deportistas.add(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deportistas;
    }

	public static int createCoach(String name, String password) {
		int newCoachId = 0;
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String insertSql = "INSERT INTO COACH (name, pass) VALUES (?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, name);
				pstmt.setString(2, password);

				int rowsInserted = pstmt.executeUpdate();

				if (rowsInserted > 0) {
					ResultSet generatedKeys = pstmt.getGeneratedKeys();
					if (generatedKeys.next()) {
						newCoachId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newCoachId;
	}

	public static int createDeportista(String name, String password) {
		int newDeportistaId = 0;
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String insertSql = "INSERT INTO DEPORTISTA (name, pass) VALUES (?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, name);
				pstmt.setString(2, password);

				int rowsInserted = pstmt.executeUpdate();

				if (rowsInserted > 0) {
					ResultSet generatedKeys = pstmt.getGeneratedKeys();
					if (generatedKeys.next()) {
						newDeportistaId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newDeportistaId;
	}
	public static int createSensor(String deportista) {
		int newDeportistaId = 0;
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String insertSql = "INSERT INTO SENSOR (deportista_id) VALUES (?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, deportista);
			

				int rowsInserted = pstmt.executeUpdate();

				if (rowsInserted > 0) {
					ResultSet generatedKeys = pstmt.getGeneratedKeys();
					if (generatedKeys.next()) {
						newDeportistaId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newDeportistaId;
	}
	public static void relateCoachToDeportista(int coachId, int deportistaId) {
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String insertSql = "INSERT INTO COACH_DEPORTISTA (coach_id, deportista_id) VALUES (?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
				pstmt.setInt(1, coachId);
				pstmt.setInt(2, deportistaId);

				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	//--------------------SIGNUP : CREAR USUARIOS SEGUN EL TIPO DE USUARIO ---------------------
	// Esta funcion sirve para signUp : pide el nombre (name) y una contrasena. El id se genera
	// automaticamente y la funcion lo devuelve
	public static int signUpDeportista(String name, String password) {
		int newDeportistaId = 0;
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String insertSql = "INSERT INTO DEPORTISTA (name, pass) VALUES (?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, name);
				pstmt.setString(2, password);

				int rowsInserted = pstmt.executeUpdate();

				if (rowsInserted > 0) {
					ResultSet generatedKeys = pstmt.getGeneratedKeys();
					if (generatedKeys.next()) {
						newDeportistaId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(newDeportistaId);
		return newDeportistaId;
	}

	// Esta funcion sirve para signUp : pide el nombre (name) y una contrasena. El id se genera
	// automaticamente y la funcion lo devuelve
	public static int signUpCoach(String name, String password) {
		int newCoachId = 0;
		try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
			String insertSql = "INSERT INTO COACH (name, pass) VALUES (?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, name);
				pstmt.setString(2, password);

				int rowsInserted = pstmt.executeUpdate();

				if (rowsInserted > 0) {
					ResultSet generatedKeys = pstmt.getGeneratedKeys();
					if (generatedKeys.next()) {
						newCoachId = generatedKeys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(newCoachId);
		return newCoachId;
	}

	public static List<String> getDeportistasByCoach(int coachId) {
        List<String> deportistas = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            String querySql = "SELECT d.name FROM DEPORTISTA d INNER JOIN COACH_DEPORTISTA cd ON d.id = cd.deportista_id WHERE cd.coach_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(querySql)) {
                pstmt.setInt(1, coachId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        deportistas.add(rs.getString("name"));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deportistas;
    }
	public static void removeDuplicates() {
	    try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
	        // Eliminar duplicados en COACH
	        String deleteDuplicateCoachesSql = 
	            "DELETE c1 FROM COACH c1 " +
	            "INNER JOIN COACH c2 " +
	            "WHERE c1.id > c2.id AND c1.name = c2.name";
	        try (PreparedStatement pstmt = conn.prepareStatement(deleteDuplicateCoachesSql)) {
	            pstmt.executeUpdate();
	        }

	        // Eliminar duplicados en DEPORTISTA
	        String deleteDuplicateDeportistasSql = 
	            "DELETE d1 FROM DEPORTISTA d1 " +
	            "INNER JOIN DEPORTISTA d2 " +
	            "WHERE d1.id > d2.id AND d1.name = d2.name";
	        try (PreparedStatement pstmt = conn.prepareStatement(deleteDuplicateDeportistasSql)) {
	            pstmt.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}