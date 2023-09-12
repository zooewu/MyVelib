package bicycle;

import calculCosts.UserVisitor;

/**
 * Mechanical bicycle, subclass of class Bicycle
 *
 */
public class MBicycle extends Bicycle {

	public MBicycle() {
		super();
		BicycleNumberGenerator b = BicycleNumberGenerator.getInstance();
		this.ID = b.getNextBicycleID();
		this.type = "Mbike";
		this.speedKmPerHour = 15;
		this.pricePerHour = 1;
		this.reserved = false;
	}

	@Override
	public double cost(UserVisitor visitor, int minutes) {

		return visitor.visit(this, minutes);
	}


}
