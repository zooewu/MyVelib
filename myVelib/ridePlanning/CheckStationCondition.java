package ridePlanning;

import bicycle.*;
import station.Slot;
import station.Station;

/**
 *
 * this class provides methods which help to do necessary calculations:
 * (1)get bike speed of given type;
 * (2)check if end station has at least one free parking slot;
 * (3)calculate euclidean distance and hamilton distance;
 *
 */
public class CheckStationCondition {
	/**
	 * given a certain station, return the number of free slot within this station
	 * @param station: instance of Station class
	 * @return: the number of free slot
	 */
	public int countFreeSlotsInStation(Station station) {
		
		int sum = 0;
		for(Slot s: station.getSlots()) {
			if((s.getStationID()==station.getID())&&(s.getStatus()=="free")){
				sum = sum + 1;
			}
		}
		return sum;
	}
	/**
	 * calculate euclidean distance between two objects, mainly user and a station
	 * @param stationCoord: a list to represent station's coordinate(x,y)
	 * @param clientCoord: 	a list to represent user's coordinate(x,y)
	 * @return: double type distance value
	 */
	public double clientStationDistance(double[] stationCoord, double[] clientCoord) {
		 return Math.sqrt(Math.pow((stationCoord[0]-clientCoord[0]),2)+
				Math.pow((stationCoord[1]-clientCoord[1]),2));
	}
	/**
	 * calculate hamilton distance between two objects, mainly between two stations
	 * @param stationStart: a list to represent start station's coordinate(x,y)
	 * @param stationEnd: a list to represent destination station's coordinate(x,y)
	 * @return double type distance value
	 */
	public double StationStationDistance(double[] stationStart, double[] stationEnd) {
		return Math.abs(stationStart[0]-stationEnd[0])+Math.abs(stationStart[1]-stationEnd[1]);
	}
	/**
	 * given a type of bike, show its speed
	 * @param bikeType
	 * @return value of speed (km/h)
	 */
	public double getBikeSpeed(String bikeType) {
		BicycleFactory bf = new BicycleFactory();
		Bicycle bike = bf.buildBicycle(bikeType);
		return bike.getSpeedKmPerHour();
	}
	
	
}
