package tsp.algorithm;

import tsp.controller.Instance;
import java.util.Random;

/**
 * This class represents a solution for the TSP problem. 
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */
public class Solution{
    
    private int[] solution; // City id's ordered in an array.
    private double cost; // Cost function value.
    public static final double C = 2; // Constant value for calculating distance between two non-connected cities.
    private static double max; // Maximum distance between two cities.
    private static double avg; // Average distance.
    
    /**
     * Constructor that only recieves an array of ids. 
     * @param solution Array of ids with the solution's ordering.
     */
    public Solution(int[] solution){
	this.solution = solution;
	max = maxP();
	avg = avgP();
	cost = cost();
    }

    /**
     * Constructs an instance of this class with its array of ids and a calculated cost.
     * This constructor is used to avoid repeatedly calculating a solution's cost.
     * @param solution Array of ids for this solution.
     * @param cost Cost value of this solution.
     */
    public Solution(int[] solution, double cost){
	this.solution = solution;
	this.cost = cost;
    }
    /**
     * Returns the solution's cost. 
     * @return This solution's cost.
     */
    public double getCost(){
	return this.cost;
    }

    /** 
     * Returns this solution's array of ids.
     * @return Array with this solution's ordering of ids.
     */
    public int[] getSolution(){
	return this.solution;
    }

    /**
     * Evaluates the cost function for this solution.
     * @return The solution's calculated cost.
     */
    public double cost(){
	double sum = 0.0; // Sum of distances.
	for(int i = 1; i < solution.length; ++i)
	    sum += dPrime(solution[i-1], solution[i]);
	return (sum / (avg*(solution.length-1)));
    }

    /** 
     * Tells us whether a solution is feasible.
     * @return <code>true</code> if the solution is feasible, <code>false</code> otherwise.
     */
    public boolean isFeasible(){
	for(int i = 0; i < solution.length-1; ++i)
	    if(Instance.getDistance(solution[i], solution[i+1]) == Instance.DEFAULT_DISTANCE)
		return false;
	return true;
    }

    /**
     * Returns the maximum distance between elements of the solution.
     * @return Maximum distance between two elements of the solution.
     */
    public double maxP(){
	double max = 0; // Maximum distance seen so far.
	for(int i = 0; i < solution.length; i++)
	    for(int j = i; j < solution.length; j++)
		if(Instance.getDistance(solution[i], solution[j]) > max)
		    max = Instance.getDistance(solution[i], solution[j]);
	return max;	
    }

    /**
     * Average distance in our graph.
     * @return The average of distances. 
     */
    public double avgP(){
	int edges = 0; // Number of edges to be added.
	double sum = 0; // Sum of distances.
	for(int i = 0; i < solution.length; i++)
	    for(int j = i; j < solution.length; j++)
		if(Instance.getDistance(solution[i], solution[j]) > 0){
		    edges++;
		    sum += Instance.getDistance(solution[i], solution[j]);
		}
	return sum/edges;
    }

    /** 
     * Returns the distance between two cities if they're connected, and a default value if they're not.
     * @param i Id of the first city.
     * @param j Id of the second city.
     * @return The distance between i and j if they're connected or a default value(max * C) otherwise.
     */
    public static double dPrime(int i, int j){
	if(Instance.getDistance(i, j) > 0)
	    return Instance.getDistance(i, j);
	else
	    return max * C;
    }

    /**
     * Returns this solution's sum of distances.
     * @return The sum of the solution's distances.
     */
    public double getSum(){
	double sum = 0; // Sum of this solution's distances.
	for(int i = 0; i < solution.length - 1; ++i)
	    sum += Instance.getDistance(solution[i], solution[i+1]);
	return sum;
    }
    
    
    /**
     * Returns a String with information about the solution.
     * @return A String with information about the solution.
     */
    @Override
    public String toString(){
	String info = ""; // Information about the solution.
	info += "Sum of distances: " + getSum() + ", Cost: " + getCost() + ", feasible: " + isFeasible() + "\n";
	info += "Cities: \n";
	for(int city : this.solution)
	    info += city + ", ";
	info += "\n";
	return info;	
    }
    
}
