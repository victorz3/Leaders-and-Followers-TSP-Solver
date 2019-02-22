package tsp.controller;

/**
 * Database connection. 
 * @author Victor Zamora
 * @version 1.0 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection{

    private Connection c = null; // Database connection. 
    private Statement stmt = null; 
    private static DBConnection currentConnection; // Object currently connected to the database, if it exists. 
    
    /**
     * Creates the connection to the database. 
     */
    public DBConnection(String dbName){
	/* Attempt to create the connection... */
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:db/tsp.db"); // Initialize the connection. 
	    stmt = c.createStatement();
	    // Connection successful. 
	}catch ( Exception e ) {
		System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		System.exit(0);
	}
    }

    /**
     * Returns a connection to the database if it exists, or creates it otherwise.
     * @return A connection to the database.
     */
    public static DBConnection getConnection(){
	if(currentConnection == null || !currentConnection.isValid())
	    currentConnection = new DBConnection();
	return currentConnection;
    }

    /**
     * Closes the current connection.
     */
    public static void closeConnection(){
	if(currentConnection == null || !currentConnection.isValid())
	    return;
	currentConnection.close();
    }
    
    
    /**
     * Queries the database.
     * @param query An SQL query.
     * @return A ResultSet with the results from the query.
     */
    public ResultSet query(String query){
	ResultSet rs = null;
	try{
	    rs = stmt.executeQuery(query);
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}
	return rs;
    }

    /**
     * Tells us whether the connection is valid.
     * @return <code>true</code> if the connection is valid an <code>false</code> otherwise.
     */
    public boolean isValid(){
	try{
	    return this.c.isValid(0);
	}catch(SQLException e){
	    return false;
	}
    }

    /**
     * Closes the connection.
     */
    public void close(){
	try { if (stmt != null) stmt.close(); } catch (Exception e) {};
	try { if (c != null) c.close(); } catch (Exception e) {};
    }
}
