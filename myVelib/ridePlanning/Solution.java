package ridePlanning;

import station.*;
/**
 *
 * instance of this class contains result of ride planning computation as attributes 
 *
 */
public class Solution {
	private Station startStation, endStation;
	private double delayHour;
	private double pathLenth;
	Slot slot;
	
	public Solution() {
		super();
		this.delayHour = 0;
		this.pathLenth = 0;
	}
	/**
	 * constructor of Soluiton
	 * @param startStation
	 * @param endStation
	 * @param delayHour: required duration for the riding, duration of walking not included
	 * @param pathLenth: total kilometers of the travel
	 */
	public Solution(Station startStation, Station endStation, double delayHour, double pathLenth,Slot slot) {
		super();
		this.startStation = startStation;
		this.endStation = endStation;
		this.delayHour = delayHour;
		this.pathLenth = pathLenth;
		this.slot = slot;
	}
	
	public Solution(Station startStation, Station endStation, double delayHour, double pathLenth) {
		super();
		this.startStation = startStation;
		this.endStation = endStation;
		this.delayHour = delayHour;
		this.pathLenth = pathLenth;
	}
	
	public Slot getSlot() {
		return this.slot;
	}

	public Station getStartStation() {
		return startStation;
	}

	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}

	public Station getEndStation() {
		return endStation;
	}

	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}

	public double getDelayHour() {
		return delayHour;
	}

	public void setDelayHour(double delayHour) {
		this.delayHour = delayHour;
	}

	public double getPathLenth() {
		return pathLenth;
	}

	public void setPathLenth(double pathLenth) {
		this.pathLenth = pathLenth;
	}

	@Override
	public String toString() {
		return "Solution [startStation=" + startStation + ", endStation=" + endStation + ", Ride hour=" + delayHour
				+ ", pathLenth=" + pathLenth + "]";
	}
	
	
}
