package top;
import bicycle.Bicycle;
import station.Station;
import user.*;
/**
 *
 * abstract class realized by concrete factories
 */
public abstract class AbstractFactory {
	public abstract Station buildStation(String stationType, double x, double y) ;
	public abstract Bicycle buildBicycle(String bicyType);
	public abstract User buildUser(String userType, String name, double x, double y);
	
}
