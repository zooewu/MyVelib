package station;

import java.util.ArrayList;

import bicycle.Bicycle;
import database.Server;
import top.AbstractFactory;
import user.User;

/**Build Stations
 *
 * Class Command invokes the methods in this class to create real stations
 * return a real station
 */
 


public class StationFactory extends AbstractFactory{
	// use buildStation to get object of station

	
	@Override
	public Station buildStation(String stationType,double x,double y) {
		if(!verifyLocation(x,y)) {
			return null;
		}
		else if(stationType.equalsIgnoreCase("NStation")) {
			return new NStation(x,y,new ArrayList<Slot>());
		}else if(stationType.equalsIgnoreCase("PStation")) {
			return new PStation(x,y,new ArrayList<Slot>());
		}
		return null;
	}
	
	@Override
	public Bicycle buildBicycle(String bicyType) {
		return null;
	}

	/**
	 * In order to verify the location of the new station is not occupied
	 * @param x
	 * @param y
	 * @return: true if the location is  not occupied
	 */
	private boolean verifyLocation(double x,double y) {
		for(Station station:Server.stations) {
			if(station.getX()==x&&station.getY()==y) {
				System.out.println("This location is already occupied! Please build on another location");
				return false;
			}	
		}
		return true;
	}

	@Override
	public User buildUser(String userType, String name, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}
}
