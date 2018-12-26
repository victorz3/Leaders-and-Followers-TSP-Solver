package tsp.controller;

import tsp.model.City;
import java.sql.SQLException;
import java.sql.ResultSet; 


/**
 * This class represents an instance of our TSP problem.
 * It contains a list of cities and an array of distances between them.
 * @author Victor Zamora
 * @version 1.0
 */
public class Instance{

    public static final City[] cities = fillCityList(); // List of cities.
    public static final double[][] distances = fillDistances(); // Array of distances between the cities.
    public static final double DEFAULT_DISTANCE = getDefaultDistance(); // Default distance between two cities.

    /**
     * Initializes this instance's data.
     */
    public static void initialize(){
	// For now, just close the connection since most initialization is done above.
	DBConnection.closeConnection();
    }

    
    /**
     * Returns the distance between two cities.
     * @param a The first city's id.
     * @param b The second city's id.
     * @return Distance between a and b if they're connected or DEFAULT_DISTANCE if they are not connected.
     */
    public static double getDistance(int a, int b){
	if(distances[a][b] > 0)
	    return distances[a][b];
	else
	    return DEFAULT_DISTANCE;
    }

    // Fills distance array.
    private static double[][] fillDistances(){
	DBConnection c = DBConnection.getConnection(); // Connection to the dtabase.
	ResultSet rs = null;
	try{ 
	    int size = cities != null ? cities.length : 0; // This method should not be called if the city array hasn't been initialized,
	    // but if that's the case, we just set the distance array size to 0.
	    double[][] distances = new double[size][size]; // Not doing any space optimizations.
	    rs = c.query("SELECT * FROM connections"); // Connection table.
	    while(rs.next()){
		int index1, index2; // Indices of connected cities.
		index1 = rs.getInt("id_city_1");
		index2 = rs.getInt("id_city_2");
		distances[index1][index2] = rs.getDouble("distance");
		distances[index2][index1] = distances[index1][index2]; // Fill distance in both orders to save time later.
		return distances;
	    }
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	}
	return null; // Something went wrong.
    }

    // Calculates the default distance between two cities.
    private static double getDefaultDistance(){
	DBConnection c = DBConnection.getConnection();
	double max = 0; // Maximum distance found so far.
	ResultSet rs = null;
	try{
	    rs = c.query("SELECT MAX(distance) as maxDistance from connections"); // Get maximum distance through SQL.
	    max = rs.getDouble("maxDistance"); // Obtain maximum distance from result set.
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	}
    	return max*2;
    }

    // Returns the size of the city array.
    private static int getSize(){
	DBConnection c = DBConnection.getConnection(); // Connection to the database.
	int size = -1; // Size of city table.
	ResultSet count = c.query("SELECT count(*) FROM cities"); // We count the cities.
       	try{
	    size = count.getInt(1);
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (count != null) count.close(); } catch (Exception e) {};
	}
	return size;
    }
    
    // Fills the list of cities from the database.
    private static City[] fillCityList(){
	DBConnection c = DBConnection.getConnection(); // Connection to the database.
	ResultSet rs = null; // Result set with list of cities.
	City[] list = null; // List of cities.
	try{
	    int size = getSize(); // Size of city array.
	    list = new City[size + 1]; 
	    rs = c.query("SELECT * FROM cities"); // City set.
	    while(rs.next()){
		City current = new City(rs.getString("country"), rs.getString("name"), rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getInt("id"), rs.getInt("population"));
		list[current.getId()] = current;
	    }
	}catch(SQLException e){
	    System.err.println(e.getMessage());
	}finally{
	    try { if (rs != null) rs.close(); } catch (Exception e) {};
	}
	return list;
    }
    

    
}
