package calculCosts;

import bicycle.*;
/**
 * 
 * @author Zoe
 * the Visitor interface implemented by User to calculate the cost of riding
 *
 */
public interface UserVisitor {
	/**
	 * calculate the cost of riding according to the price per hour and the type of card user holds
	 * @param bicycle
	 * @param minutes
	 * @return value of cost(euro)
	 */
	double visit(Bicycle bicycle, int minutes);
}
