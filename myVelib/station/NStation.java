package station;

import java.util.ArrayList;



/**
 * 
 * @author Zoe
 * Normal station, subclass of class Station
 */


public class NStation extends Station {
	/**
	 * 
	 * @param x: coordinate x
	 * @param y: coordinate y
	 * @param slots: array list of slots
	 */
	public NStation(double x,double y,ArrayList<Slot> slots) {
		super();
		StationNumberGenerator g = StationNumberGenerator.getInstance();
		this.ID = g.getNextStationID();
		this.x  = x;
		this.y  = y;
		this.slots = slots;
		this.type = "Nstation";
		this.status = "On-service";
	}

	@Override
	public double[] getCoor() {
		double coor [] = {this.x, this.y};
		//System.out.println(coor.get(0));
		return coor;
	}

	
}
