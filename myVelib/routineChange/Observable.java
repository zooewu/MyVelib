package routineChange;
/**
 *
 * interface implemented by stations, which launch notifications to their observer if certain condition is meeted.
 */
public interface Observable {
	/**
	 * once the rent event (an observer) is built, register it into array list of the destination station 
	 * @param observer: interface implemented by rent event
	 */
	public void registerObserver(Observer observer);
	/**
	 * once the riding is over, remove the observer from array list of the destination station  
	 * @param observer
	 */
	public void removeObserver(Observer observer);
	/**
	 * once the station is offline or fully occupied, notify the observer
	 */
	public void notifyObserver();
}
