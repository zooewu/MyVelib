package bicycle;

/**
 * singleton pattern, generate the ID number for bicycle instances
 *
 */
public class BicycleNumberGenerator {
	private int ID = 1;
	private static BicycleNumberGenerator instance = null;
	
	/**
	 * private constructor: to return a unique BicycleNumberGenerator instance
	 */
	private BicycleNumberGenerator() {};
	
	/**
	 * getInstance method
	 * @return an instance of generator itself
	 */
	public static BicycleNumberGenerator getInstance() {
		if(instance == null) {
			instance = new BicycleNumberGenerator();
		}
		return instance;
	}
	/**
	 * get a new ID
	 * @return: id
	 */
	public int getNextBicycleID() {
		return ID++;
	}
}
