package tspsolver.algorithms;

import java.util.Properties;

public interface ISolver {
	
	/**
	 * 
	 * @param distances
	 * @return
	 * 
	 * take distances between points return the array of point indexes in optimized order
	 */
	public int[] solve(double[][] distances, Properties properties);

}