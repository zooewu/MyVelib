package user;
/**
 * provide static id for users
 * @author Zoe
 *
 */
public class UserNumberGenerator {
	private int ID = 1;
	private static UserNumberGenerator instance = null;
	
	/**
	 * private constructor: to return a unique UserNumberGenerator instance
	 */
	private UserNumberGenerator() {};
	
	/**
	 * getInstance method
	 * @return instance of generator itself
	 */
	public static UserNumberGenerator getInstance() {
		if(instance == null) {
			instance = new UserNumberGenerator();
		}
		return instance;
	}
	/**
	 * get new id
	 * @return id
	 */
	public int getNextUserID() {
		return ID++;
	}
}
