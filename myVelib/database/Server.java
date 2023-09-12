package database;

import java.util.ArrayList;

import bicycle.Bicycle;
import station.Station;
import user.*;

/**
 *  static class Server store all the data(Stations, Bicycles, Users, RentEvent) and constants
 *
 */

public class Server {
	public static ArrayList<Station> stations = new ArrayList<Station>();
	public static ArrayList<Bicycle> bicycles = new ArrayList<Bicycle>();
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<RentEvent> rentEvents = new ArrayList<RentEvent>();
	
	public final static double walkSpdKmPerHour = 4;
	public static int[] globalStartTime = new int[3];
}
