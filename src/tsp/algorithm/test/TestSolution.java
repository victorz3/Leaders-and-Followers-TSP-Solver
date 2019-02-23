package tsp.algorithm.test;

import tsp.algorithm.Solution;
import tsp.model.City;
import tsp.controller.Instance;
import org.junit.Test;
import org.junit.Rule;
import org.junit.Assert;
import org.junit.rules.Timeout;

/**
 * Test class for class {@link Solution}.
 * @author Victor Zamora
 * @version 1.0
 */
public class TestSolution{

    /** 5 second timeout for every test. */
    @Rule public Timeout timeout = Timeout.seconds(5);

    private Solution s1, s2, s3; 

    /**
     * Constructor.
     * Incitializes a new TSP instance.
     */
    public TestSolution(){
	// First, create four cities.
	City c1, c2, c3, c4;
	c1 = new City("Mexico", "Guadalajara", 0, 0, 1, 200);
	c2 = new City("Brazil", "Brasilia", 0, 0, 2, 200);
	c3 = new City("Australia", "Sidney", 0, 0, 3, 200);
	c4 = new City("China", "Beijing", 0, 0, 4, 200);
	City[] cities = {null, c1, c2, c3, c4}; // Store cities inside an array.
	
	double[][] distances = {{}, {0, 0, -1, 15, -1}, {0, -1, 0, 35, 25}, {0, 15, 35, 0, 30}, {0, -1, 25, 30, 0}}; // Distances between cities.
	Instance instance = new Instance(cities, distances); // Instance for testing
	Solution.setInstance(instance);
	Solution.setC(2);
	int[] sol1 = {1, 4, 2, 3};
	this.s1 = new Solution(sol1);
	int[] sol2 = {1, 3, 4, 2};
	this.s2 = new Solution(sol2);
	int[] sol3 = {2, 1, 4};
	this.s3 = new Solution(sol3);
    }

    /**
     * Unit test for {@link Solution#avgP}.
     */
    @Test public void testAvgP(){
	Assert.assertTrue(s1.avgP()==26.25);
	Assert.assertTrue(s2.avgP()==26.25);
	Assert.assertTrue(s3.avgP()==25);
    }

    /**
     * Unit test for {@link Solution#maxP}.
     */
    @Test public void testMaxP(){
	Assert.assertTrue(s1.maxP()==35);
	Assert.assertTrue(s2.maxP()==35);
    }
    
    /**
     * Unit test for {@link Solution#cost}.
     */
    @Test public void testCost(){
	// Cost should always be greater than 0.
	Assert.assertTrue(s1.cost() > 0);
	Assert.assertTrue(s2.cost() > 0);
	// Now check specific cases.
	
	
    }

    // /**
    //  * Prueba unitaria para {@link Solucion#factible}.
    //  */
    // @Test public void testFactible(){
    // 	Assert.assertFalse(s.factible());
    // 	Assert.assertTrue(s2.factible());
    // }

    // /**
    //  * Prueba unitaria para {@link Solucion#maxP}.
    //  */
    // @Test public void testMaxP(){
    // 	Assert.assertTrue(s.maxP()==1778054.73);
    // 	Assert.assertTrue(s2.maxP() == 2723789.75);
    // }
    
}
