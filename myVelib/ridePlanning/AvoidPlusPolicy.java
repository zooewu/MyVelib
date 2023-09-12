package ridePlanning;

import database.Server;
import station.Slot;
import station.Station;
/**
 *
 * like minimal walking distance but return station cannot be a "plus" station
 *
 */
public class AvoidPlusPolicy implements PlanningPolicy {
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
		dMin = Double.POSITIVE_INFINITY;
		for(Station station: Server.stations) {
			if ((csc.countFreeSlotsInStation(station)!=0)&&!(station.getType().equals("Pstation"))&&station.getStatus().equalsIgnoreCase("On-service")) {
				flag = false;
				stationCoord = station.getCoor();
				dSq = csc.clientStationDistance(stationCoord, endCoord);
				if(dMin>dSq) {
					dMin = dSq;
					passedStations[1] = station;
				}
			}
		}if(flag) {
			System.out.println("no suitable end station found");
			return null;
		}
		double ssd = csc.StationStationDistance(passedStations[0].getCoor(), passedStations[1].getCoor());
		pathLen += dMin+ssd;
		delayHour += ssd/speedBike;
		return new Solution(passedStations[0],passedStations[1],delayHour,pathLen,slot);
	}

}
