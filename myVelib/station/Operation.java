package station;

import top.Time;
import bicycle.Bicycle;
import database.RentEvent;
import user.User;
/**
 * 
 * @author Zoe
 * a class used for memorizing the operations happened on a station,
 * including status changes, rent and returning of bikes
 */
public class Operation {
	String type; // rent return On-service or Offline
	User user;
	Bicycle bike;
	Slot slot;
	String bikeType;
	Station station;
	int[] time;
	
	/**
	 * constructor For recording renting and returning events
	 * @param rentEvent
	 * @param slot
	 * @param type
	 * @param station
	 */
	public Operation(RentEvent rentEvent,Slot slot,String type,Station station) {
		super();
		this.type = type;
		this.station = station;
		this.user = rentEvent.getUser();
		this.bike = rentEvent.getBike();
		this.time = Time.getCurrentTime();
		this.slot = slot;
	}
	
	/**
	 * constructor For recording the offline and reopen issue of a station
	 * @param type
	 * @param station
	 */
	public Operation(String type,Station station) {
		this.type = type;
		this.station = station;
		this.time = Time.getCurrentTime();
	}
	
	public String toString() {
		if(this.type.equalsIgnoreCase("rent")||this.type.equalsIgnoreCase("return")) {
			return  "[Operation]" +Time.toString(this.time)+ " Station "+ this.station.getID() + ' ' +  "Event: "+this.type;
		}else if(this.type.equalsIgnoreCase("Offline")||this.type.equalsIgnoreCase("On-service")) {
			return  "[Operation]" +Time.toString(this.time)+ " Station "+ this.station.getID() + ' ' + "Event: "+this.type;
		}return "Wrong information collected";
	}
	
}
