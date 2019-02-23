package tsp.algorithm;

import tsp.controller.Instance;
import java.util.Random;
import java.util.HashMap;

/**
 * This class represents a solution for the TSP problem. 
 * @author Victor Zamora Gutierrez
 * @version 1.0
 */
public class Solution{
    
    private int[] solution; // City id's ordered in an array.
    private double cost; // Cost function value.
    private static double max; // Maximum distance between two cities.
    private static double avg; // Average distance.
    private static Instance instance; // Instance for the algorithm's execution.
    public static final double C = 2; // Constant value for calculating distance between two non-connected cities.

    
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
	// Update solution's cost.
	cost = (sum / (avg * (solution.length-1)));
	return cost;
    }

    /** 
     * Tells us whether a solution is feasible.
     * @return <code>true</code> if the solution is feasible, <code>false</code> otherwise.
     */
    public boolean isFeasible(){
	for(int i = 0; i < solution.length-1; ++i)
	    if(instance.getDistance(solution[i], solution[i+1]) == instance.getDefaultDistance())
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
		if(instance.getDistance(solution[i], solution[j]) > max)
		    max = instance.getDistance(solution[i], solution[j]);
	return max;	
    }

    /**
     * Average distance in our graph.
     * @return The average of distances. 
     */
    public double avgP(){
	int edges = 0; // Number of edges to be added.
	double sum = 0; // Sum of distances.
	double[][] distances = instance.getDistances(); // Distance matrix.
	for(int i = 0; i < solution.length; i++)
	    for(int j = i+1; j < solution.length; j++){
		int index1, index2; // Indices to look up in distance array.
		index1 = solution[i];
		index2 = solution[j];
		if(distances[index1][index2] > 0){
		    edges++;
		    sum += distances[index1][index2];
		}
	    }
	return sum/edges;
    }

    /**
     * Sets the Instance for the algorithm's execution.
     * @param i - Instance with values for program execution.
     */
    public static void setInstance(Instance i){
	instance = i;
    }

    /** 
     * Returns the distance between two cities if they're connected, and a default value if they're not.
     * @param i Id of the first city.
     * @param j Id of the second city.
     * @return The distance between i and j if they're connected or a default value(max * C) otherwise.
     */
    public static double dPrime(int i, int j){
	if(instance.getDistance(i, j) > 0)
	    return instance.getDistance(i, j);
	else
	    return max * C;
    }

    /**
     * Creates a solution based on its two parents.
     * @param s1 The first parent solution.
     * @param s2 The second parent solution.
     * @param random Random number generator for the algorithm.
     * @return A child solution with information from both parents.
     */
    public static Solution crossover(Solution s1, Solution s2, Random random){
	int[] solution1, solution2; // ID arrays from the solutions.
	solution1 = s1.solution;
	solution2 = s2.solution;
	int[] child = new int[solution1.length]; // The solutions' offspring.

	// First, we're gonna copy a random length interval from s1 into the child.
	int begin = random.nextInt(solution1.length - 1); // Beginning of the interval.
	int end = random.nextInt(solution1.length - begin) + begin + 1; // End of the interval.

	HashMap<Integer, Integer> childMap = new HashMap<>(end - begin); // We have to map the copied interval to check in constant time whether an index has been inserted.
	
	for(int i = begin; i <= end; ++i){
	    child[i] = solution1[i];
	    childMap.put(solution1[i], i);
	}

	int childIndx = 0; // The first index where we have yet to insert an ID.
	int parentIndx = 0; // ID to be inserted from solution2.
	for(int i = childIndx; i < begin; ++i){
	    while(childMap.get(solution2[parentIndx]) != null)
		parentIndx++;
	    child[childIndx] = solution2[parentIndx++];
	}

	childIndx = end + 1;
	for(int i = childIndx; i < child.length; ++i){
	    while(childMap.get(solution2[parentIndx]) != null)
		parentIndx++;
	    child[childIndx] = solution2[parentIndx++];
	}

	// Child should be complete here.
	return new Solution(child);
    }
    
    /**
     * Returns this solution's sum of distances.
     * @return The sum of the solution's distances.
     */
    public double getSum(){
	double sum = 0; // Sum of this solution's distances.
	for(int i = 0; i < solution.length - 1; ++i)
	    sum += instance.getDistance(solution[i], solution[i+1]);
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
