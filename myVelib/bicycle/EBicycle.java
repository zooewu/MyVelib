package bicycle;

import calculCosts.UserVisitor;

/**
 * Electric bicycle, subclass of class Bicycle
 *
 */
public class EBicycle extends Bicycle {

	public EBicycle() {
		super();
		BicycleNumberGenerator b = BicycleNumberGenerator.getInstance();
		this.ID = b.getNextBicycleID();
		this.type = "Ebike";
		this.speedKmPerHour = 20;
		this.pricePerHour = 2;
		this.reserved = false;

	}

	@Override
	public double cost(UserVisitor visitor, int minutes) {
		return visitor.visit(this,minutes);
	}
}
