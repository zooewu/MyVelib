package top;
import bicycle.BicycleFactory;
import station.StationFactory;
import user.*;
/**
 * 
 * @author Zoe
 * the interface is used for produce different concrete factories
 */
public interface FactoryProducer {
	/**
	 * 
	 * @param choice the instance of which class the factory will produce
	 * @return: concrete factory
	 */
	public static AbstractFactory getFactory(String choice) {
		if(choice.equalsIgnoreCase("Station")) {
			return new StationFactory();
		}else if(choice.equalsIgnoreCase("Bicycle")) {
			return new BicycleFactory();
		}else if(choice.equalsIgnoreCase("User")) {
			return new UserFactory();
		}
		return null;
	}
}
