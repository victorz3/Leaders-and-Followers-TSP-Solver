package tsp.model;

/**
 * This class represents a city in the TSP problem.
 * @author Victor Zamora Gutierrez
 * @version 1.0 
 */
public class City{

    public final String country; // Name of the country the city belongs to. 
    public final String name; // Name of the city. 
    public final double latitude; // Latitude. 
    public final double longitude; // Longitude. 
    public final int id; // ID of city in database. 
    public final int population; // This city's population.
    
    /**
     * Standard constructor.
     * @param country Country the city belongs to.
     * @param name Name of the city.
     * @param latitude Latitude.
     * @param longitude Longitude.
     * @param id ID of city in the database.
     * @param population The city's population.
     */
    public City(String country, String name, double latitude, double longitude, int id, int population){
	this.country = country;
	this.name = name;
	this.latitude = latitude;
	this.longitude = longitude;
	this.id = id;
	this.population = population;
    }

    /**
     * Returns the id of the city in the database.
     * @return The city's id.
     */
    public int getId(){
	return this.id;
    }
    
    /**
     * Returns whether our city is equal to another city.
     * @param o The city to be compared to.
     * @return <code>true</code> if the cities are equal and <code>false</code> otherwise.
     */
    @Override
	public boolean equals(Object o){
	if(! (o instanceof City))
	    return false;
	City other = (City) o; // Cast to City. 
	return this.id == other.getId();
    }

    /**
     * Returns the city as a String.
     * @return String representing the city. 
     */
    @Override 
    public String toString(){
	return this.name + ", " + this.country;
    }
}
