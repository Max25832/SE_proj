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
		String driver = "com.mysql.cj.jdbc.Driver";
		String username = "root";
		String password = "admin";
		
		
		try {
			
			String url = "jdbc:mysql://localhost:3306/shapes_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin";					
			String createQuery = "CREATE DATABASE IF NOT EXISTS shapes_db";
			Class.forName(driver);
			
			// Attempt to connect to database at url
			Connection connection = DriverManager.getConnection(url, username, password);
			// Create the SQL statement from string
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(createQuery);
			System.out.println("Connected to database!");
			return connection;
			// First time setup causes this error
		} catch (java.sql.SQLSyntaxErrorException e) {
			String url = "jdbc:mysql://localhost:3306";					
			String createQuery = "CREATE DATABASE IF NOT EXISTS shapes_db";
			Class.forName(driver);
			
			// Attempt to connect to database at url
			Connection connection = DriverManager.getConnection(url, username, password);
			// Create the SQL statement from string
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(createQuery);
			stmt.executeUpdate("USE shapes_db");
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
	
	public String[] getAllFromDB() {
		/* The getAllLinesFromDB() method gets all data from the database
		 * 
		 * INPUTS: None
		 * 
		 * OUTPUTS: dataList - A string Array with shape data
		 */
		Statement stmt = null;
		List<String> dataList = new LinkedList<String>();
		
		try {
			Connection connection = getConnection();
			stmt = connection.createStatement();
			
			String query = "SELECT * FROM polygons";
			ResultSet results = stmt.executeQuery(query);
			
			while (results.next()) {

			        
		        int id = results.getInt("id");
		        String geometry = results.getString("geometry");
		        String type = results.getString("type");
		        
		        dataList.add(type + ";" + geometry);
		        System.out.format("%s, %s, %s\n", id, geometry, type);
			        
			    
			    System.out.println("");
			}
				
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("getAllFromDB() completed");
		}
		return dataList.toArray(new String[0]);
	}
	
	public Integer[] selectFeatures(int inputX1, int inputY1, int inputX2, int inputY2) {
		/* The selectFeatures() method returns the features of the database which are inside the Selection Rectangle
		 * 
		 * INPUTS: the Coordinates of the Select-Rectangle
		 * 
		 * OUTPUTS: List of the IDs to manipulate them afterwards
		 * 
		 * @author Ida Hausmann
		 */
		Statement stmt = null;
		boolean isInSelection = true;
		List<Integer> idList = new LinkedList<Integer>();
		
		try {
			Connection connection = getConnection();
			stmt = connection.createStatement();
			
			//request to the database to get all features and saving them
			String query = "SELECT * FROM polygons";
			ResultSet results = stmt.executeQuery(query);
			
			//while loop to iterate through all features
			while (results.next()) {
				
				//saving the different columns of the database in variables
		        int id = results.getInt("id");
		        String geometry = results.getString("geometry");
		        String type = results.getString("type");
		        
		        //checking if the current feature is of the type point or line/rectangle
		        if(type == "Point") {
		        	
		        	//splitting the geometry String to get a list of the different values
		        	String[] split = geometry.split(", ");
		        	
		        	//converting the string values to integer to make them comparable to the input parameters
		        	int compareX = Integer.parseInt(split[0]);
		        	int compareY = Integer.parseInt(split[1]);
		        	
		        	//Query if the current point is inside the selection-Rectangle
		        	if(compareX <= inputX2 && compareX >= inputX1 && compareY <= inputY2 && compareY >= inputY1) {
        			} else {
        				//if the point is not inside it, setting a variable to false so that this feature is not used for the final list
        				isInSelection = false;
        			}
		        	
		        } else {
		        	//splitting the geometry String to get a list of the different values
		        	String[] split = geometry.split(", ");
		        	
		        	//converting the string values to integer to make them comparable to the input parameters
		        	for(int i = 0; i < 4; i++) {
		        		int comparePoint = Integer.parseInt(split[i]);
		        		
		        		if(i%2==0) {
		        			//if i%2 == 0, the list element represents an X-Coordinate
		        			if(comparePoint <= inputX2 && comparePoint >= inputX1) {
		        			} else {
		        				isInSelection = false;
		        			}
		        		} else {
		        			//if i%2 != 0, the list element represents an Y-Coordinate
		        			if(comparePoint <= inputY2 && comparePoint >= inputY1) {
		        			} else {
		        				isInSelection = false;
		        			}
		        		}
		        	}
		        }
		        
		        //if the isInSelection variable is true, the id of the current feature is added to the final list
		        if(isInSelection) {
	        		System.out.println(id);
	        		idList.add(id);
	        	}
		        
		        //reset the variable to check the next feature
		        isInSelection=true;
			}	
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("selectFeatures() completed");
		}
		return idList.toArray(new Integer[0]);
		
		
	}
	
	public void exportCSV() {
		getAllFromDB();
	}
}
