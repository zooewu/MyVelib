package user;

import bicycle.Bicycle;
import station.Station;
import top.AbstractFactory;
/**
 * factory for build new user instance
 * @author Zoe
 *
 */
public class UserFactory extends AbstractFactory {

	@Override
	public Station buildStation(String stationType, double x, double y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bicycle buildBicycle(String bicyType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User buildUser(String userType, String name, double x, double y) {
		if(userType.equalsIgnoreCase("NoCard")) {
			return new NoCardUser(name, x, y);
		}
		else if(userType.equalsIgnoreCase("Vlibre")) {
			return new VlibreUser(name,x,y);
		}else if(userType.equalsIgnoreCase("Vmax")) {
			return new VmaxUser(name,x,y);
		}
		return null;
	}

}
