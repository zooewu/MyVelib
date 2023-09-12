package bicycle;
import calculCosts.*;

/**
 * abstract class Bicycle is the superclass of different kinds of specify class
 *
 */
public abstract class Bicycle implements BikeVisitable{
	int ID;
	int stationID;
	public boolean reserved;
	String type;
	double speedKmPerHour;
	double pricePerHour;
	
	
	/**
	 * @return bicycle ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * get the ID of the Station which it belongs to, if it is not belong to a station, the return value is 0
	 * @return Station's ID it belongs to, if it's not belongs to a station, the return value is 0
	 */
	public int getStationID() {
		return stationID;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}
	
	public String getType() {
		return type;
	}
	
	/**
	 * 
	 * @return the speed of the bicycle, km/hour
	 */
	public double getSpeedKmPerHour() {
		return speedKmPerHour;
	}


	public double getPricePerHour() {
		return pricePerHour;
	}

	public void setPricePerHour(double pricePerHour) {
		this.pricePerHour = pricePerHour;
	}

	/**
	 * override equals() function specify equal case for bicycles(same id)
	 * @param obj: any object
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Bicycle) {
			Bicycle b = (Bicycle)obj;
			return (b.getID() == this.getID())&&(b.getType().equals(this.getType()));
		}
		return false;
	}

	/**
	 * update hash function as equals() redefined
	 * @return hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Bicycle [ID=" + ID + ", type=" + type + "]";
	}

	@Override
	public abstract double cost(UserVisitor visitor, int minutes);

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}
	
}
