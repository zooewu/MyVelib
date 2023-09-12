package station;

import java.util.ArrayList;



/**
 * Plus station, subclass of class Station
 * station allowing to earn 5 minutes credits for card-holder users
 */


public class PStation extends Station {


	public PStation(double x,double y,ArrayList<Slot> slots) {
		super();
		StationNumberGenerator g = StationNumberGenerator.getInstance();
		this.ID = g.getNextStationID();
		this.x  = x;
		this.y  = y;
		this.slots = slots;
		this.type = "Pstation";
		this.status = "On-service";
	}

	@Override
	public double[] getCoor() {
		double coor [] = {this.x, this.y};
		//System.out.println(coor.get(0));
		return coor;
	}


	
}
