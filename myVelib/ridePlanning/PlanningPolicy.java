package ridePlanning;



/**
 * interface for different ride routine planning policies
 **/
public interface PlanningPolicy {
	/**
	 * this method is used for calculate and choose appropriate start station and destination station
	 * according to user's coordinate, end point coordinate, bike type and policy type
	 * @param clientCoord: a list to represent user's coordinate(x,y)
	 * @param endCoord: a list to represent end point's coordinate(x,y)
	 * @param bikeKind: "Mbike" or "Ebike"
	 * @return instance of class Solution(Station startStation, Station endStation, double delayHour, double pathLenth)
	 */
	public Solution getStationCouple(double[] clientCoord, double[] endCoord, String bikeKind);
}
