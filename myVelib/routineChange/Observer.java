package routineChange;
/**
 *
 * interface implemented by RentEvents which receive notifications from stations and update its route planning
 */
public interface Observer {
	/**
	 * update attributes, especially the solution of ride planning
	 */
	public void update();
}
