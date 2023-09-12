package ridePlanning;

import database.Server;
import station.Slot;
import station.Station;
/**
 *
 * start station and destination station are calculated independently. 
 * The start station is chosen so that the combined walk+ride delay to the end point is minimal, 
 * while the destination station is chosen so that the walking distance to end point is minimal.
 *
 */
public class FastestPathPolicy implements PlanningPolicy {
	Slot slot;
	@Override
	public Solution getStationCouple(double[] clientCoord, double[] endCoord, String bikeKind) {
		CheckStationCondition csc = new CheckStationCondition();
		double dMin = Double.POSITIVE_INFINITY;
		double delayMin = Double.POSITIVE_INFINITY;
		Station[] passedStations = new Station[2];
		double[] stationCoord = new double[2];
		double d1 = 0;double d2 = 0;
		double delay = 0;
		double speedBike = 0;
		double delayHour = 0;
		boolean flag = true;
		for(Station station: Server.stations) {
			for(Slot s: station.getSlots()) {
				if(station.getStatus().equalsIgnoreCase("On-service")&&(s.getBikeType().equalsIgnoreCase(bikeKind))&&(s.getBicycle().isReserved()==false)){
					flag = false;
					stationCoord = station.getCoor();
					speedBike = s.getBicycle().getSpeedKmPerHour(); 
					d1 = csc.clientStationDistance(stationCoord, clientCoord);
					d2 = csc.StationStationDistance(stationCoord, endCoord);
					delay = d1/Server.walkSpdKmPerHour + d2/speedBike;
					if(delayMin>delay) {
						delayMin = delay;
						slot = s;
						passedStations[0] = station;
					}break;
				}
			}
		}if(flag) {
			System.out.println("no suitable start station found");
			return null;
		}
		d1 = 0;
		for(Station station: Server.stations) {
			if((csc.countFreeSlotsInStation(station)!=0)&&station.getStatus().equalsIgnoreCase("On-service")) {
				flag = false;
				stationCoord = station.getCoor();
				d1 = csc.clientStationDistance(stationCoord, endCoord);
				if(dMin>d1) {
					dMin = d1;
					passedStations[1] = station;
				}
			}
		}if(flag) {
			System.out.println("no suitable end station found");
			return null;
		}
		double ssd = csc.StationStationDistance(passedStations[0].getCoor(), passedStations[1].getCoor());
		double csd1 = csc.clientStationDistance(passedStations[0].getCoor(), clientCoord);
		double csd2 = csc.clientStationDistance(passedStations[1].getCoor(), endCoord);
		double pathLen = ssd+csd1+csd2;
		delayHour = ssd/speedBike;
		//System.out.println("from: "+passedStations[0].toString()+"\nto: "+passedStations[1].toString());
		//System.out.println("path length: "+pathLen+"km; delay hours: "+delayHour);
		return new Solution(passedStations[0],passedStations[1],delayHour,pathLen,slot);

	}

}
