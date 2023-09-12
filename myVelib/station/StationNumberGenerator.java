package station;

/**
 *  Produce a global unique ID number for
 *
 */
 
public class StationNumberGenerator {
	private static StationNumberGenerator instance= null;
	private int ID=1;
	
	/**
	 * private constructor: returns the unique StationNumberGenerator object
	 */
	private StationNumberGenerator() {};

	/**
	 * public get Instance method
	 * @return: instance of generator itself
	 */
	public static StationNumberGenerator getInstance() {
		if(instance == null) {
			instance = new StationNumberGenerator();
		}
		return instance;
	}
	
	/**
	 * public method to obtain next unique stationNumber
	 * @return id
	 */
	public int getNextStationID() {
		return ID++;
	}
}
