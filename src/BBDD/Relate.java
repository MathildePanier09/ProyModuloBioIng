package BBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import BBDD.update.UpdateCar;

public class Relate {
	private static final String URL = "jdbc:mariadb://localhost:3306/pilotech";
    private static final String USER = "root";
    private static final String PASS = "";
	// AÑADIR COMPROBACIÓN A TODAS PARA EVITAR QUE LO QUE YA ESTÁ EN UN EQUIPO PUEDA SER AÑADIDO A OTRO
	
	public int relateUserToTeam(String userType, int userId, int teamId) {
		int newRelationId = 0; // Valor predeterminado en caso de que no se inserte ninguna relación nueva

		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;

		String engToTeamSql = "INSERT INTO teamRelations (teamId, engId) " +
				"SELECT ? AS teamId, ? AS engId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamId = ? AND engId= ?)";

		String medToTeamSql = "INSERT INTO teamRelations (teamId, medId) " +
				"SELECT ? AS teamId, ? AS medId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamId = ? AND medId= ?)";

		String pilToTeamSql = "INSERT INTO teamRelations (teamId, pilId) " +
				"SELECT ? AS teamId, ? AS pilId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamId = ? AND pilId= ?)";

		String insertSql=null;
		switch(userType) {
		case "E": insertSql=engToTeamSql; break;
		case "M": insertSql=medToTeamSql; break;
		case "P": insertSql=pilToTeamSql; break;
		}

		try {
			// Paso 1: Registrar el controlador JDBC y establecer la conexión
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);

			// Paso 2: Preparar la consulta de inserción con parámetros
			pstmtInsert = conn.prepareStatement(insertSql);
			pstmtInsert.setInt(1, teamId);
			pstmtInsert.setInt(2, userId);
			pstmtInsert.setInt(3, teamId);
			pstmtInsert.setInt(4, userId);

			// Ejecutar la consulta de inserción
			int rowsInserted = pstmtInsert.executeUpdate();

			// Paso 3: Si se insertaron filas, obtener el ID de la nueva relación
			if (rowsInserted > 0) {
				pstmtSelect = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = pstmtSelect.executeQuery();
				if (rs.next()) {
					newRelationId = rs.getInt(1);
				}
				rs.close();
			}
		} catch (SQLException | ClassNotFoundException e) {
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
		return newRelationId;
	}

	public int relateUser1ToUser2(String type,int teamId, int user1Id, int user2Id) {
		int newRelationId = 0; // Valor predeterminado en caso de que no se inserte ninguna relación nueva

		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;


		String engAndPil="INSERT INTO teamRelations (teamId,engId, pilId) " +
				"SELECT ? AS teamId, ? AS engId, ? AS pilId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamID = ? AND engId = ? AND pilId= ?)";

		String medAndPil="INSERT INTO teamRelations (teamId,medId, pilId) " +
				"SELECT ? AS teamId, ? AS medId, ? AS pilId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamID = ? AND medId = ? AND pilId= ?)";

		String insertSql=null;
		switch (type) {
		case "engAndPil":{
			insertSql=engAndPil; break;
		}
		case "medAndPil":{
			insertSql=medAndPil; break;
		}
		}
		try {
			// Paso 1: Registrar el controlador JDBC y establecer la conexión
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);

			// Paso 2: Preparar la consulta de inserción con parámetros
			pstmtInsert = conn.prepareStatement(insertSql);
			pstmtInsert.setInt(1, teamId);
			pstmtInsert.setInt(2, user1Id);
			pstmtInsert.setInt(3, user2Id);
			pstmtInsert.setInt(4, teamId);
			pstmtInsert.setInt(5, user1Id);
			pstmtInsert.setInt(6, user2Id);

			// Ejecutar la consulta de inserción
			int rowsInserted = pstmtInsert.executeUpdate();

			// Paso 3: Si se insertaron filas, obtener el ID de la nueva relación
			if (rowsInserted > 0) {
				pstmtSelect = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = pstmtSelect.executeQuery();
				if (rs.next()) {
					newRelationId = rs.getInt(1);
				}
				rs.close();
			}
		} catch (SQLException | ClassNotFoundException e) {
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
		return newRelationId;
	}

	public int relateCarToTeam(int carId, int teamId) {
		int newRelationId = 0; // Valor predeterminado en caso de que no se inserte ninguna relación nueva

		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;

		String insertSql="INSERT INTO teamRelations (teamId, carId) " +
				"SELECT ? AS teamId, ? AS carId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamId = ? AND carId= ?)";

		try {
			// Paso 1: Registrar el controlador JDBC y establecer la conexión
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);

			// Paso 2: Preparar la consulta de inserción con parámetros
			pstmtInsert = conn.prepareStatement(insertSql);
			pstmtInsert.setInt(1, teamId);
			pstmtInsert.setInt(2, carId);
			pstmtInsert.setInt(3, teamId);
			pstmtInsert.setInt(4, carId);

			// Ejecutar la consulta de inserción
			int rowsInserted = pstmtInsert.executeUpdate();

			// Paso 3: Si se insertaron filas, obtener el ID de la nueva relación
			if (rowsInserted > 0) {
				pstmtSelect = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = pstmtSelect.executeQuery();
				if (rs.next()) {
					newRelationId = rs.getInt(1);
				}
				rs.close();
			}
		} catch (SQLException | ClassNotFoundException e) {
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
		return newRelationId;
	}

	public int relateCarToUser(String type, int teamId, int carId, int userId) {
		/**
		 * El primer parámetro "carToEng" o "carToPil" según tipo de usuario
		 * Un ingeniero puede tener varios coches
		 * Un piloto puede tener varios coches
		 * UN COCHE NO PUEDE TENER VARIOS PILOTOS
		 */
		int newRelationId = 0; // Valor predeterminado en caso de que no se inserte ninguna relación nueva

		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;
		PreparedStatement pstmtSelectName=null;

		String carToEng="INSERT INTO teamRelations (teamId,engId, carId) " +
				"SELECT ? AS teamId, ? AS engId, ? AS carId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamID = ? AND engId = ? AND carId= ?)";

		String nuevaa = "INSERT INTO teamRelations (teamId, engId, carId) "+
				"SELECT ? AS teamId, ? as engId, ? AS carId FROM DUAL "+
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamId = ? AND carId = ? AND engId IS NOT NULL)";
				
		String carToPil="INSERT INTO teamRelations (teamId,pilId, carId) " +
				"SELECT ? AS teamId, ? AS pilId, ? AS carId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamID = ? AND pilId = ? AND carId= ?)";

		String nueva= "INSERT INTO teamRelations (teamId,pilId, carId) " +
				"SELECT ? AS teamId, ? AS pilId, ? AS carId FROM DUAL " +
				"WHERE NOT EXISTS (SELECT 1 FROM teamRelations WHERE teamID = ? AND carId= ? AND pilId IS NOT NULL )";
		
		String insertSql=null;
		
		boolean ctpBool=false;
		switch (type) {
		case "carToEng":{
			insertSql=nuevaa; break;
		}
		case "carToPil":{
			insertSql=nueva; 
			ctpBool=true;
			break;
		}
		}

		try {

			// Paso 1: Registrar el controlador JDBC y establecer la conexión
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);

			// Paso 2: Preparar la consulta de inserción con parámetros
			pstmtInsert = conn.prepareStatement(insertSql);
			pstmtInsert.setInt(1, teamId);
			pstmtInsert.setInt(2, userId);
			pstmtInsert.setInt(3, carId);
			pstmtInsert.setInt(4, teamId);
			pstmtInsert.setInt(5, carId);
			//pstmtInsert.setInt(6, carId);

			// Ejecutar la consulta de inserción
			int rowsInserted = pstmtInsert.executeUpdate();

			// Paso 3: Si se insertaron filas, obtener el ID de la nueva relación
			if (rowsInserted > 0) {
				pstmtSelect = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = pstmtSelect.executeQuery();
				if (rs.next()) {
					newRelationId = rs.getInt(1);
				}
				rs.close();
			}
			if(ctpBool) {
				String pilNameQuery = "SELECT name FROM USERS WHERE id = ?";
				pstmtSelectName = conn.prepareStatement(pilNameQuery);
				pstmtSelectName.setString(1, String.valueOf(userId));
				ResultSet result = pstmtSelectName.executeQuery();

				String pilName = null;

				if (result.next()) {
				    pilName = result.getString("name");
				}
				UpdateCar.updatePilName(carId, pilName);
			}
		} catch (SQLException | ClassNotFoundException e) {
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
		return newRelationId;
	}

	public int relateSensorToUser(int sensorId, int userId) {
		int newRelationId = 0; // Valor predeterminado en caso de que no se inserte ninguna relación nueva

		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;

		try {
			// Paso 1: Registrar el controlador JDBC y establecer la conexión
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);

			// Paso 2: Preparar la consulta de inserción con parámetros
			String insertSql = "INSERT INTO userSensors(userId, sensorId) " +
					"SELECT ?, ? FROM DUAL " +
					"WHERE NOT EXISTS (SELECT 1 FROM userSensors WHERE userId = ? AND sensorId = ?)";
			pstmtInsert = conn.prepareStatement(insertSql);
			pstmtInsert.setInt(1, userId);
			pstmtInsert.setInt(2, sensorId);
			pstmtInsert.setInt(3, userId);
			pstmtInsert.setInt(4, sensorId);

			// Ejecutar la consulta de inserción
			int rowsInserted = pstmtInsert.executeUpdate();

			// Paso 3: Si se insertaron filas, obtener el ID de la nueva relación
			if (rowsInserted > 0) {
				pstmtSelect = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = pstmtSelect.executeQuery();
				if (rs.next()) {
					newRelationId = rs.getInt(1);
				}
				rs.close();
			}
		} catch (SQLException | ClassNotFoundException e) {
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

		return newRelationId;
	}

	public int relateSensorToCar(int sensorId, int carId) {
		int newRelationId = 0; // Valor predeterminado en caso de que no se inserte ninguna relación nueva

		Connection conn = null;
		PreparedStatement pstmtInsert = null;
		PreparedStatement pstmtSelect = null;

		try {
			// Paso 1: Registrar el controlador JDBC y establecer la conexión
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection(URL, USER, PASS);

			// Paso 2: Preparar la consulta de inserción con parámetros
			String insertSql = "INSERT INTO carSensors(carId, sensorId) " +
					"SELECT ?, ? FROM DUAL " +
					"WHERE NOT EXISTS (SELECT 1 FROM carSensors WHERE carId = ? AND sensorId = ?)";
			pstmtInsert = conn.prepareStatement(insertSql);
			pstmtInsert.setInt(1, carId);
			pstmtInsert.setInt(2, sensorId);
			pstmtInsert.setInt(3, carId);
			pstmtInsert.setInt(4, sensorId);

			// Ejecutar la consulta de inserción
			int rowsInserted = pstmtInsert.executeUpdate();

			// Paso 3: Si se insertaron filas, obtener el ID de la nueva relación
			if (rowsInserted > 0) {
				pstmtSelect = conn.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = pstmtSelect.executeQuery();
				if (rs.next()) {
					newRelationId = rs.getInt(1);
				}
				rs.close();
			}
		} catch (SQLException | ClassNotFoundException e) {
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
		return newRelationId;
	}	
}