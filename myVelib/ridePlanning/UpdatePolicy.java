package ridePlanning;

import database.Server;
import station.Station;
/**
 *
 * when the original destination station turns offline or fully occupied,
 * this policy is invoked to recalculate and choose another station to head to.
 * algorithm is similar to minimal walking policy
 *
 */
public class UpdatePolicy implements PlanningPolicy {

	@Override
	public Solution getStationCouple(double[] clientCoord, double[] endCoord, String bikeKind) {
		double dMin = Double.POSITIVE_INFINITY;
		CheckStationCondition csc = new CheckStationCondition();
		boolean flag = true;
		double dSq = 0;
		Station s = null;
		for(Station station: Server.stations) {
			if((csc.countFreeSlotsInStation(station)!=0)&&station.getStatus().equalsIgnoreCase("On-service")) {
				flag = false;
				dSq = csc.clientStationDistance(station.getCoor(), endCoord);
				if(dMin>dSq) {
					dMin = dSq;
					s = station;
				}
			}
		}if(flag) {
			System.out.println("no suitable end station found");
			return null;
		}
		double csd = csc.StationStationDistance(clientCoord, s.getCoor());
		double pathLen = csd + dMin;
		double time = csd/csc.getBikeSpeed(bikeKind);
		return new Solution(null, s, time, pathLen);
		
	}

}
