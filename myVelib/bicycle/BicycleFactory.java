package bicycle;

import station.Station;
import top.AbstractFactory;
import user.User;

/**
 * build different kinds of bicycles, subclass of abstract class AbstractFactory
 *
 */
public class BicycleFactory extends AbstractFactory {

	
	@Override
	public Bicycle buildBicycle(String bicyType) {
		if(bicyType == null) {
			return null;
		}
		if(bicyType.equalsIgnoreCase("Ebike")) {
			return new EBicycle();
		}else if(bicyType.equalsIgnoreCase("MBike")) {
			return new MBicycle();
		}
		return null;
	}

	@Override
	public Station buildStation(String stationType, double x, double y) {
		return null;
	}

	@Override
	public User buildUser(String userType, String name, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

}
