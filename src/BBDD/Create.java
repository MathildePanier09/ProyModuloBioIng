package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Vector;

public class Create {
	private static final String URL = "jdbc:mariadb://localhost:3306/pilotech";
    private static final String USER = "root";
    private static final String PASS = "";

	public void createBasicTables() {
		Connection conn = null;
		Statement stmt = null;
		String sql;

		String tableUsers;
		String tableTeams;
		String tableCars;
		String tableSensors;
		String tableSugestions;

		tableUsers = "CREATE TABLE IF NOT EXISTS USERS ("
				+"id INT AUTO_INCREMENT,"
				+"pass VARCHAR(255),"
				+"name VARCHAR(255),"
				+"role VARCHAR(255),"
				+"mail VARCHAR(255),"
				+"PRIMARY KEY (id))";

		tableTeams = "CREATE TABLE IF NOT EXISTS TEAMS ("
				+"id INT AUTO_INCREMENT,"
				+"pass VARCHAR(255),"
				+"name VARCHAR(255),"
				+"PRIMARY KEY (id))";

		tableCars = "CREATE TABLE IF NOT EXISTS CARS ("
				+ "id INT AUTO_INCREMENT,"
				+ "PRIMARY KEY (id),"
				+ "carNumber INT, "
				+ "pilName VARCHAR(255))";
		

		tableSensors = "CREATE TABLE IF NOT EXISTS SENSORS ("
				+"id INT AUTO_INCREMENT,"
				+"type VARCHAR(255),"
				+"time LONG,"
				+"value INT,"
				+"PRIMARY KEY (id))";

		tableSugestions = "CREATE TABLE IF NOT EXISTS SUGESTIONS ("
				+"id INT AUTO_INCREMENT,"
				+"userId INT,"
				+"content VARCHAR(500),"
				+"time VARCHAR(255),"
				+"FOREIGN KEY (userId) REFERENCES USERS(id),"
				+"PRIMARY KEY (id))"; 


		Vector<String>tables=new Vector<String>();
		tables.add(tableUsers); tables.add(tableTeams); tables.add(tableCars); tables.add(tableSensors); tables.add(tableSugestions);

		try {
			//STEP 1: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");

			//STEP 2: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = DriverManager.getConnection(URL,USER, PASS);
			System.out.println("Connectado a la Base de Datos...");

			//STEP 3: Execute a query //consulta 
			for(String table:tables) {
				System.out.println("Creando la tabla si no existe...");
				sql=table;
				System.out.println("sql Create Table: "+sql);
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				stmt.close(); //MUY IMPORTANTE 
			}

			//STEP 6: Cerrando conexion.
			conn.close();

		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			//finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			}// do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}//end finally try
		}//end try

		System.out.println("QUERY TERMINADA!");
	}

	public void createRelationTables() {
		Connection conn = null;
		Statement stmt = null;
		String sql;

		String teamRelations;
		//String teamUsers;
		//String teamCars;

		//String userCars;
		String userSensors;
		String carSensors;

		//String medicPilots;
		//String pilotUsers;

		teamRelations = "CREATE TABLE IF NOT EXISTS teamRelations("
				+"id INT AUTO_INCREMENT PRIMARY KEY,"
				+"teamId INT,"
				+"engId INT NULL,"
				+"medId INT NULL,"
				+"pilId INT NULL,"
				+"carId INT NULL,"
				+ "FOREIGN KEY (teamId) 	REFERENCES TEAMS (id),"
				+ "FOREIGN KEY (engId)	 	REFERENCES USERS (id),"
				+ "FOREIGN KEY (medId)	 	REFERENCES USERS (id),"
				+ "FOREIGN KEY (pilId)	 	REFERENCES USERS (id),"
				+ "FOREIGN KEY (carId)	 	REFERENCES CARS  (id))";

		userSensors = "CREATE TABLE IF NOT EXISTS userSensors ("
				+"id INT AUTO_INCREMENT PRIMARY KEY,"
				+"userId INT,"
				+"sensorId INT,"
				+"FOREIGN KEY (userId) 		REFERENCES USERS    (id),"
				+"FOREIGN KEY (sensorId) 	REFERENCES SENSORS  (id))";

		carSensors = "CREATE TABLE IF NOT EXISTS carSensors("
				+"id INT AUTO_INCREMENT PRIMARY KEY,"
				+"carId INT,"
				+"sensorId INT,"
				+"FOREIGN KEY (carId) 		REFERENCES CARS		(id),"
				+"FOREIGN KEY (sensorId) 	REFERENCES SENSORS  (id))";
		
		/*teamUsers = "CREATE TABLE IF NOT EXISTS teamUsers("
		+"id INT AUTO_INCREMENT PRIMARY KEY,"
		+"teamId INT,"
		+"userId INT,"
		+ "FOREIGN KEY (teamId) 	REFERENCES TEAMS (id),"
		+ "FOREIGN KEY (userId) 	REFERENCES USERS (id))"; 
		
		teamCars = "CREATE TABLE IF NOT EXISTS teamCars("
				+"id INT AUTO_INCREMENT PRIMARY KEY,"
				+"teamId INT,"
				+"carId INT,"
				+"FOREIGN KEY (teamId) 		REFERENCES TEAMS (id),"
				+"FOREIGN KEY (carId) 		REFERENCES CARS  (id))"; 

		userCars = 	"CREATE TABLE IF NOT EXISTS userCars("
				+"id INT AUTO_INCREMENT PRIMARY KEY,"
				+"userId INT,"
				+"carId INT,"
				+"FOREIGN KEY (userId) 	REFERENCES USERS (id),"
				+"FOREIGN KEY (carId) 	REFERENCES CARS  (id))";

		medicPilots = "CREATE TABLE IF NOT EXISTS medicPilots("
				+"id INT AUTO_INCREMENT PRIMARY KEY,"
				+"medId INT,"
				+"pilId INT,"
				+"FOREIGN KEY (medId) 	REFERENCES USERS	(id),"
				+"FOREIGN KEY (pilId) 	REFERENCES USERS	(id))";

		pilotUsers = "CREATE TABLE IF NOT EXISTS pilotUsers("
				+"id INT AUTO_INCREMENT PRIMARY KEY,"
				+"pilId INT,"
				+"engId INT,"
				+"medId INT,"
				+"FOREIGN KEY (pilId)		REFERENCES USERS	(id),"
				+"FOREIGN KEY (engId)	 	REFERENCES USERS	(id),"
				+"FOREIGN KEY (medId) 		REFERENCES USERS	(id))";
		 */

		Vector<String>tables=new Vector<String>();
		tables.add(teamRelations);
		tables.add(userSensors);
		tables.add(carSensors);
		//tables.add(teamCars);
		//tables.add(userCars);
		//tables.add(medicPilots);
		//tables.add(pilotUsers);

		try {
			//STEP 1: Register JDBC driver
			Class.forName("org.mariadb.jdbc.Driver");

			//STEP 2: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = DriverManager.getConnection(URL,USER, PASS);

			System.out.println("Connectado a la Base de Datos...");

			//STEP 3: Execute a query //consulta 
			for(String table:tables) {
				System.out.println("Creando la tabla si no existe...");
				sql=table;
				System.out.println("sql Create Table: "+sql);
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
				stmt.close(); //MUY IMPORTANTE 
			}

			//STEP 6: Cerrando conexion.
			conn.close();

		} catch (SQLException se) {
			//Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			//Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			//finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			}// do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			}//end finally try
		}//end try

		System.out.println("QUERY TERMINADA!");
	}

	public int createTeam(String pass, String name) {
		String newStringId = null;
		Connection conn = null;
		PreparedStatement stmtInsert = null;
		PreparedStatement stmtSelect = null;
		ResultSet rs = null;
		int nuevasFilas = 0;
		int newIntId = 0;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			conn = DriverManager.getConnection(URL,USER, PASS);

															// SELECT VALUES ('hola', 'redbull')
			String sqlInsert = "INSERT INTO TEAMS (pass, name) SELECT ?, ? FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM TEAMS WHERE name = ?)";
			stmtInsert = conn.prepareStatement(sqlInsert);
			stmtInsert.setString(1, pass);
			stmtInsert.setString(2, name);
			stmtInsert.setString(3, name);
			nuevasFilas = stmtInsert.executeUpdate();

			String sqlSelect = "SELECT id FROM TEAMS WHERE name = ?";
			stmtSelect = conn.prepareStatement(sqlSelect);
			stmtSelect.setString(1, name);
			rs = stmtSelect.executeQuery();

			if (rs.next()) {
				newStringId = rs.getString("id");
			}

		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (stmtInsert != null) stmtInsert.close();
				if (stmtSelect != null) stmtSelect.close();
				if (conn != null) conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		if (nuevasFilas == 0) {
			newIntId = 0;
		} else if (newStringId != null) {
			newIntId = Integer.parseInt(newStringId);
		}

		return newIntId;
	}

	public int createUser(String pass, String name, String role, String mail) {
		int newUserId = 0; // Valor predeterminado en caso de que no se inserte ningún usuario nuevo
		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;

		try {
			// Paso 1: Registrar el controlador JDBC y establecer la conexión
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL,USER, PASS);


			// Paso 2: Preparar la consulta de inserción con parámetros
			String insertSql = "INSERT INTO USERS (pass, name, role, mail) " +
					"SELECT ?, ?, ?, ? FROM DUAL " +
					"WHERE NOT EXISTS (SELECT 1 FROM USERS WHERE mail = ?)";
			pstmtInsert = conn.prepareStatement(insertSql);
			pstmtInsert.setString(1, pass);
			pstmtInsert.setString(2, name);
			pstmtInsert.setString(3, role);
			pstmtInsert.setString(4, mail);
			pstmtInsert.setString(5, mail);

			// Ejecutar la consulta de inserción
			int rowsInserted = pstmtInsert.executeUpdate();

			// Paso 3: Si se insertaron filas, obtener el ID del nuevo usuario
			if (rowsInserted > 0) {
				String selectSql = "SELECT id FROM USERS WHERE mail = ?";
				pstmtSelect = conn.prepareStatement(selectSql);
				pstmtSelect.setString(1, mail);
				ResultSet rs = pstmtSelect.executeQuery();
				if (rs.next()) {
					newUserId = rs.getInt("id");
				}
				rs.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Cerrar recursos
			try {
				if (pstmtInsert != null) pstmtInsert.close();
				if (pstmtSelect != null) pstmtSelect.close();
				if (conn != null) conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return newUserId;
	}

	public int createCar(int carNumber) {
		boolean validNumber=true;
		if(carNumber<0) validNumber=false;

		int newCarId = 0; // Valor predeterminado en caso de que no se inserte ningún usuario nuevo
		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;

		if(carNumber==0) {
			try {
				// Paso 1: Registrar el controlador JDBC y establecer la conexión
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection(URL,USER, PASS);

				// Paso 2: Buscar el menor número y ajustar newCarId para ser uno inferior

				String selectSql = "SELECT MIN(carNumber) AS minCarNumber FROM CARS WHERE carNumber < 0";
				pstmtSelect = conn.prepareStatement(selectSql);
				ResultSet rs = pstmtSelect.executeQuery();

				if (rs.next()) {
					int minCarNumber = rs.getInt("minCarNumber");
					carNumber = minCarNumber - 1;
				}
				rs.close();

			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Cerrar recursos
				try {
					if (pstmtInsert != null) pstmtInsert.close();
					if (pstmtSelect != null) pstmtSelect.close();
					if (conn != null) conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}	
		}

		if(validNumber) {
			try {
				// Paso 1: Registrar el controlador JDBC y establecer la conexión
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection(URL,USER, PASS);

				// Paso 2: Preparar la consulta de inserción con parámetros
				String insertSql = "INSERT INTO CARS (carNumber	) " +
						"SELECT ? FROM DUAL " +
						"WHERE NOT EXISTS (SELECT 1 FROM CARS WHERE carNumber = ?)";
				pstmtInsert = conn.prepareStatement(insertSql);
				pstmtInsert.setInt(1, carNumber);
				pstmtInsert.setInt(2, carNumber);


				// Ejecutar la consulta de inserción
				int rowsInserted = pstmtInsert.executeUpdate();

				// Paso 3: Si se insertaron filas, obtener el ID del nuevo usuario
				if (rowsInserted > 0) {
					String selectSql = "SELECT id FROM CARS WHERE carNumber= ?";
					pstmtSelect = conn.prepareStatement(selectSql);
					pstmtSelect.setInt(1, carNumber);
					ResultSet rs = pstmtSelect.executeQuery();
					if (rs.next()) {
						newCarId = rs.getInt("id");
					}
					rs.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Cerrar recursos
				try {
					if (pstmtInsert != null) pstmtInsert.close();
					if (pstmtSelect != null) pstmtSelect.close();
					if (conn != null) conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}	
		}
		return newCarId;
	}

	public int createSensor(String type) {
		int newSensorId = 0; // Valor predeterminado en caso de que no se inserte ningún usuario nuevo
		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;
		if(type.equals("fc")||type.equals("pres")||type.equals("sat")||type.equals("temp")) {
			try {
				// Paso 1: Registrar el controlador JDBC y establecer la conexión
				Class.forName("org.mariadb.jdbc.Driver");
				conn = DriverManager.getConnection(URL,USER, PASS);

				// Paso 2: Preparar la consulta de inserción con parámetros
				String insertSql = "INSERT INTO SENSORS(type, time, value) VALUES (?, ?, ?)";
				pstmtInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
				pstmtInsert.setString(1, type);  // Establecer el valor para 'type'
				pstmtInsert.setNull(2, Types.BIGINT);  // Establecer el segundo valor como nulo (para 'time')
				pstmtInsert.setNull(3, Types.INTEGER);  // Establecer el tercer valor como nulo (para 'value')

				// Ejecutar la consulta de inserción
				int rowsInserted = pstmtInsert.executeUpdate();

				// Obtener las claves generadas
				ResultSet generatedKeys = pstmtInsert.getGeneratedKeys();
				if (generatedKeys.next()) {
					newSensorId = generatedKeys.getInt(1);
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Cerrar recursos
				try {
					if (pstmtInsert != null) pstmtInsert.close();
					if (pstmtSelect != null) pstmtSelect.close();
					if (conn != null) conn.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		}
		return newSensorId;
	}

	public String createSugestion(String pass, String name) {
		String newTeamId=null;
		return newTeamId;
	}

	public int createTeamWithRelations(String teamName, String engMail, String medMail, String pilMail) {
		createBasicTables();
		createRelationTables();

		System.out.println("------------FIN CREACIÓN TABLAS");
		System.out.println("------------");

		long startTime=System.currentTimeMillis();

		int teamId=createTeam("NULL", teamName);
		System.out.println("Id equipo: "+teamId);

		if(teamId!=0) {
			int engId=createUser("pass", "tempName", "E", engMail);
			System.out.println("Id eng: "+engId);

			int medId=createUser("pass", "tempName", "M", medMail);
			System.out.println("Id med: "+medId);

			int pilId=createUser("pass", "tempName", "P", pilMail);
			System.out.println("Id pil: "+pilId);

			int carId=createCar(0);
			System.out.println("Id car: "+carId);

			boolean basicTeamComponentes=false;

			if((teamId!=0)||(engId!=0)||(medId!=0)||(pilId!=0)||(carId!=0)) {
				basicTeamComponentes=true;
			}

			int sensorFcId=0;
			int sensorPresId=0;
			int sensorTempId=0;
			int sensorSatId=0;

			if(basicTeamComponentes) {
				System.out.println("	---------SENSORES------------");
				sensorFcId=createSensor("fc");
				System.out.println("Id sFc: "+sensorFcId);
				sensorPresId=createSensor("pres");
				System.out.println("Id sP: "+sensorPresId);
				sensorSatId=createSensor("sat");
				System.out.println("Id sS: "+sensorSatId);
				sensorTempId=createSensor("temp");
				System.out.println("Id sT: "+sensorTempId);	
			}
			System.out.println("------------FIN IDS---------------");

			System.out.println("------------RELACIONES------------");

			Relate relator=new Relate();
			int u1Team=relator.relateUserToTeam("E",engId,teamId);
			System.out.println("U1Team: "+u1Team);

			int u2Team=relator.relateUserToTeam("M",medId,teamId);
			System.out.println("U2Team: "+u2Team);

			int u3Team=relator.relateUserToTeam("P",pilId,teamId);
			System.out.println("U3Team: "+u3Team);

			int car1Team=relator.relateCarToTeam(carId,teamId);
			System.out.println("teamCar: "+car1Team);

			int pilM=relator.relateUser1ToUser2("medAndPil",teamId,medId,pilId);
			System.out.println("medPil: "+pilM);

			int carEng=relator.relateCarToUser("carToEng",teamId,carId,engId);
			System.out.println("engCar: "+carEng);

			int carPil=relator.relateCarToUser("carToPil",teamId,carId,pilId);
			System.out.println("pilCar: "+carPil);

			if(basicTeamComponentes) {
				int s1Pil=relator.relateSensorToUser(sensorFcId,pilId);
				System.out.println("S1PIL: "+s1Pil);
				int s1Car=relator.relateSensorToCar(sensorPresId,carId);
				System.out.println("S1CAR: "+s1Car);
				int s2Pil=relator.relateSensorToUser(sensorTempId,pilId);
				System.out.println("S2PIL: "+s2Pil);
				int s3Pil=relator.relateSensorToUser(sensorSatId,pilId);
				System.out.println("S3PIL: "+s3Pil);
			}
		}
		
		long endTime=System.currentTimeMillis();
		long qTime=endTime-startTime;
		System.out.println("TIEMPO CONSULTA: "+qTime);
		return teamId;
	}

	public static void main (String[]args) {
		Create creator=new Create();
		Relate relator=new Relate();
		creator.createBasicTables();
		creator.createRelationTables();
	}
}
