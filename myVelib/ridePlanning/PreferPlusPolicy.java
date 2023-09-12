package ridePlanning;

import database.Server;
import station.Slot;
import station.Station;
/**
 *
 *  a "plus" station will be chosen as the destination station instead of the minimal-distance one,
 *   if its distance to the end point is shorter than 110% of the minimal distance could be got between a station and the end point.
 *
 */
public class PreferPlusPolicy implements PlanningPolicy {
	Slot slot;
	@Override
	public Solution getStationCouple(double[] clientCoord, double[] endCoord, String bikeKind) {
		CheckStationCondition csc = new CheckStationCondition();
		double dMin = Double.POSITIVE_INFINITY;
		double pathLen = 0;
		Station[] passedStations = new Station[2];
		double[] stationCoord = new double[2];
		double dSq = 0;
		double speedBike = 0;
		double delayHour = 0;
		boolean flag = true;
		for(Station station: Server.stations) {
			for(Slot s: station.getSlots()) {
				if(s.getBikeType().equalsIgnoreCase(bikeKind)&&station.getStatus().equalsIgnoreCase("On-service")&&(s.getBicycle().isReserved()==false)){
					flag = false;
					stationCoord = station.getCoor();
					speedBike = s.getBicycle().getSpeedKmPerHour(); 
					dSq = csc.clientStationDistance(stationCoord, clientCoord);
					if(dMin>dSq) {
						dMin = dSq;
						slot = s;
						passedStations[0] = station;
					}break;
				}
			}
		}if(flag) {
			System.out.println("no suitable start station found");
			return null;
		}
		pathLen += dMin;
		//delayHour += dMin/Server.walkSpdKmPerHour;
		dMin = Double.POSITIVE_INFINITY;
		double dMinP = Double.POSITIVE_INFINITY;
		double dsqP = 0;
		Station candidatP = null;
		for(Station station: Server.stations) {
			if ((csc.countFreeSlotsInStation(station)!=0)&&station.getStatus().equalsIgnoreCase("On-service")) {
				flag = false;
				if(station.getType().equals("Pstation")){
					dsqP = csc.clientStationDistance(station.getCoor(), endCoord);
					if(dMinP>dsqP) {
						dMinP = dsqP;
						candidatP = station;
					}
				}else {
					dSq = csc.clientStationDistance(station.getCoor(), endCoord);
					if(dMin>dSq) {
						dMin = dSq;
						passedStations[1] = station;
					}
				}
			}
		}if(flag) {
			System.out.println("no suitable end station found");
			return null;
		}
		if(dMinP<1.1*dMin) {
			dMin = dMinP;
			passedStations[1] = candidatP;
		}
		double ssd = csc.StationStationDistance(passedStations[0].getCoor(), passedStations[1].getCoor());
		pathLen += dMin+ssd;
		delayHour += ssd/speedBike;
		return new Solution(passedStations[0],passedStations[1],delayHour,pathLen,slot);
	}

}
