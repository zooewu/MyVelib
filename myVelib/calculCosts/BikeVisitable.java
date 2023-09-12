package calculCosts;
/**
 * 
 * @author Zoe
 *  the Visitable interface implemented by Bicycle to calculate the cost of riding
 *
 */
public interface BikeVisitable {
	/**
	 * invoke visitor.visit() which returns the value of cost
	 * @param visitor: user
	 * @param minutes
	 * @return the value of cost
	 */
	public double cost(UserVisitor visitor, int minutes);
}
