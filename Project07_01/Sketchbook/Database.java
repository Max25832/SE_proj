package Sketchbook;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/*
 * Connects the Sketchbook to a local MySQL Database
 * Selects or loads the objects within.
 * 
 * Is called from GUI.java
 * 
 * @author Felipe Vasquez
 */

public class Database {
	/*
	ArrayList<Point> points = new ArrayList<Point>();
	ArrayList<Line> lines = new ArrayList<Line>();

	ArrayList<Line> selectedLine = new ArrayList<Line>();

	ArrayList<Line> triangles = new ArrayList<Line>();

	ArrayList<Line> selectedTriangle = new ArrayList<Line>();

	ArrayList<Integer> allIDs = new ArrayList<Integer>();
	
	ArrayList<Point[]> rects = new ArrayList<Point[]>();
	*/

	int lineId;
	
	public static Connection getConnection() throws Exception {
		/* The Connection method attempts to connect to a SQL database
		 * Standard database parameters url, username and password are hardcoded
		 *
		 * Will attempt to create a database called "shapes_db" if it does not currently exist
		 * 
		 * INPUTS: None
		 * 
		 * OUTPUTS: connection: Connection class - A connection to a local MySQL database.
		 */
		try {
			String driver = "com.mysql.cj.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/shapes_db";
			String username = "root";
			String password = "admin";
			String createQuery = "CREATE DATABASE IF NOT EXISTS shapes_db";
			Class.forName(driver);
			
			// Attempt to connect to database at url
			Connection connection = DriverManager.getConnection(url, username, password);
			// Create the SQL statement from string
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(createQuery);
			System.out.println("Connected to database!");
			return connection;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	public static void createTable() {
		/* The createTable method attempts to create a table in a SQL database
		 * Standard table parameters geometry and type are hardcoded
		 * 
		 * Parameters:
		 * geometry - VARCHAR - Max 300 Chars: Refers to the actual shape information being stored
		 * type - VARCHAR - Max 30 Chars: The type of shape being stored (eg. point, line, square)
		 *
		 * Will attempt to create a table called "polygons" if it does not currently exist
		 * 
		 * INPUTS: None
		 * 
		 * OUTPUTS: None
		 */
		try {
			Connection connection = getConnection();
			PreparedStatement createTable = connection.prepareStatement(
					"CREATE TABLE IF NOT EXISTS polygons (id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, geometry VARCHAR(300) NOT NULL, type VARCHAR(30) NOT NULL)");
			createTable.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("createTable() completed");
		}
	}
	
	public static void deleteTable() throws Exception {
		/* The deleteTable method attempts to delete a table in a SQL database
		 * Table name is hardcoded
		 * 
		 * Parameters:
		 * geometry - VARCHAR - Max 300 Chars: Refers to the actual shape information being stored
		 * type - VARCHAR - Max 30 Chars: The type of shape being stored (eg. point, line, square)
		 *
		 * Will attempt to create a table called "polygons" if it does not currently exist
		 * 
		 * INPUTS: None
		 * 
		 * OUTPUTS: None
		 */
		try {
			Connection connection = getConnection();
			PreparedStatement createTable = connection.prepareStatement(
					"DROP TABLE IF EXISTS polygons");
			
			createTable.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("deleteTable() completed");
		}
	}
	
	public void insertShapes(String geometry, String type) {
		/* The insertShapes method (complex variant) inserts the values of selected shapes into 
		 * the table "polygons"
		 * 
		 * INPUTS:
		 * geometry - String - The point values of the current shape
		 * type - String - The type of shape being stored (eg. point, line, square)
		 * 
		 * OUTPUTS: None
		 */
		System.out.println("Current geometry: " + type + " " + geometry);
		
		Statement stmt = null;
		
		try {
			Connection connection = getConnection();
			stmt = connection.createStatement();
			
			String query = "INSERT INTO polygons(geometry, type) VALUES ('" + geometry + "', " + type + ")";
			System.out.println(query);
			stmt.executeUpdate(query);
			System.out.println(query);
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("insertShapes() completed");
		}
	}
	
	public void getAllLinesFromDB() {
		/* The getAllLinesFromDB() method selects all line shapes from the database
		 * 
		 * INPUTS: None
		 * 
		 * OUTPUTS: None
		 */
		
		/*
		Statement stmt = null;
		lineId = 0;
		
		try {
			Connection connection = getConnection();
			stmt = connection.createStatement();
			
			String query = "SELECT * FROM polygons WHERE type = 'Line'";
			ResultSet results = stmt.executeQuery(query);
			
			while (results.next()) {
				selectedLine.removeAll(selectedLine);
				
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("getAllLinesFromDB() completed");
		}

		*/
				
	}
	
}
