package top;

import database.Server;
import station.Station;

public interface Verification {
	
	/**
	 *  In order to check whether the station exist or not,
	 * @param stationID
	 * @return :  if not, return false
	 */
		public static boolean checkExistanceStationID(int stationID) {
			for(Station station:Server.stations) {
				if(station.getID()==stationID) return true;
			}
			return false;
		}
}
