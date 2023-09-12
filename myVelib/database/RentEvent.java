package database;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

import ridePlanning.*;
import routineChange.*;
import bicycle.Bicycle;
import station.Station;
import top.Time;
import user.User;

/**
 * To save all of the plan information and rent information
 * once a user confirm a route plan a rent event will automatically generated and store all the necessary information for a trip
 * There are 3 different state of a rent event, represent from the start point walks to the start station, from the start station rent a bike and ride to the destination station,
 * parks the bike in the destination station and walks to end point
 *
 */
public class RentEvent implements Observer{
	private User user;
	private Bicycle bike;
	private String bikeType;
	private int[] startTime = new int[3];
	private int[] start_tmp= new int[3];
	private int[] endTime = new int[3];
	private double [] startCoor; 	//start point
	private Station startStation;
	private Station destStation;
	private int state; // 1 represent walking to station, 2 represent riding, 3 represent walking to destination
	private double destX,destY;
	private Solution solution;
	private Scanner sc;

	/**
	 * Create a new rentEvent when the client confirm a route plan
	 * @param user	: The user instance related to this rent event
	 * @param bikeType : "MBike" or "EBike"
	 * @param solution : The route plan to be follow
	 * @param destX	: the  x coordinate of the final destination
	 * @param destY	: the  y coordinate of the final destination
	 */
	public RentEvent(User user,String bikeType,Solution solution,double destX, double destY) {
		super();
		this.state = 1;
		solution.getSlot().getBicycle().setReserved(true);
		this.bikeType = bikeType;
		this.user = user;
		this.destX = destX;
		this.destY = destY;
		this.solution = solution;
		this.startStation = solution.getStartStation();
		this.destStation = solution.getEndStation();
		this.startTime =  Time.getCurrentTime();
		this.start_tmp = startTime;
		this.startCoor = this.user.getCoor();
		this.endTime = calculateEndTime(user.getCoor(),this.startStation.getCoor());
	}
	
	public RentEvent(User user,String bikeType,double destX, double destY) {
		super();
		this.state = 1;
		this.bikeType = bikeType;
		this.user = user;
		this.destX = destX;
		this.destY = destY;
		this.startTime =  Time.getCurrentTime();
		this.start_tmp = startTime;
		this.startCoor = this.user.getCoor();
	}
	
	/**
	 * When the client arrive to the station and rent a bike, we change the rentEvent, state 2
	 * @param bike: The bike instance which has been rent by the user
	 */
	public void rentBikeEvent(Bicycle bike) {
		this.state = 2;
		this.user.updateLocation(solution.getStartStation().getCoor());
		this.startCoor = solution.getStartStation().getCoor();
		this.bike = bike;
		this.destStation = this.solution.getEndStation();
		this.startTime = Time.getCurrentTime();
		this.start_tmp = startTime;
		this.endTime = Time.calEndTime(startTime, (int)(solution.getDelayHour()*60));
	}
	
	public void setBike(Bicycle bike) {
		this.bike = bike;
	}
	
	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}
	
	public void setDestStation(Station destStation) {
		this.destStation = destStation;
	}

	/**
	 * When the client park the bicycle, he starts to walk to the destination
	 */
	public void toDestEvent() {
		this.bike = null;
		this.state = 3;
		this.user.updateLocation(solution.getEndStation().getCoor());
		this.startCoor = this.user.getCoor();
		this.startTime = Time.getCurrentTime();
		double[] coor2 = {this.destX,this.destY};
		this.endTime = Time.calEndTime(startTime, this.calWalkDuration(user.getCoor(), coor2));
	}
	
	
	/**
	 * calculate walking duration according to given coordinators
	 * @param coor1	: first place coordinate
	 * @param coor2	: second place coordinate
	 * @return	walking duration in minutes
	 */
	private int calWalkDuration(double[] coor1,double[] coor2) {
		CheckStationCondition csc = new CheckStationCondition();
		return (int)(csc.clientStationDistance(this.user.getCoor(), coor2)*60/Server.walkSpdKmPerHour);
	}
	
	/**
	 * imagine a man walk from one place to another,return minutes, depends on walking speed
	 * @param coor1: the coordinate where the man starts
	 * @param coor2: the coordinate where the man ends
	 * @return time in minutes
	 */
	private int[] calculateEndTime(double[] coor1, double[] coor2) {
		CheckStationCondition csc = new CheckStationCondition();
		int duration = (int)(csc.clientStationDistance(coor1, coor2)*60/Server.walkSpdKmPerHour);
		return Time.calEndTime(this.startTime, duration);
	}
	
	@Override
	public String toString() {
		if(this.state==1) {
			return "WalkingEvent [user=" + user.getName() +" can take the bike at " + Arrays.toString(this.endTime) +"]";
		}
		if(this.state==2) {
			return "RentEvent [user=" + user.getName() + ", bike=" + bike.getType() +bike.getID() + ", startTime=" + Arrays.toString(startTime)
					+ ", end Time=" + Arrays.toString(endTime) + ", x=" + startCoor[0]
					+ ", y=" + startCoor[1] + ", destStation=" + Arrays.toString(destStation.getCoor()) + "]";
		}
		return null;
	}

	public User getUser() {
		return user;
	}


	public Bicycle getBike() {
		return bike;
	}


	public String getBikeType() {
		return bikeType;
	}

	public int[] getStartTime() {
		return startTime;
	}

	public int[] getStart_tmp() {
		return start_tmp;
	}

	public int[] getEndTime() {
		return endTime;
	}

	public double[] getStartCoor() {
		return startCoor;
	}

	public Station getDestStation() {
		return destStation;
	}

	public int getState() {
		return state;
	}

	public double getDestX() {
		return destX;
	}

	public double getDestY() {
		return destY;
	}

	public Solution getSolution() {
		return solution;
	}
	
	public Station getStartStation() {
		return startStation;
	}
	
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	/*
	 * get the current location of the user
	 */
	public double[] getCurrentLocation() {
		double[] currentCoor = new double[2];
		int pastTime;
		int duration;
		if(state == 2) {
			pastTime = Time.calTimeDifference(this.start_tmp, Time.getCurrentTime());
			duration = Time.calTimeDifference(this.start_tmp, this.endTime);
			currentCoor[0] = (this.destStation.getX()-this.startCoor[0])*pastTime/(double)duration+this.startCoor[0];
			currentCoor[1] = (this.destStation.getY()-this.startCoor[1])*pastTime/(double)duration+this.startCoor[1];
		}else if(state == 1){
			pastTime = Time.calTimeDifference(this.startTime, Time.getCurrentTime());
			duration = Time.calTimeDifference(this.startTime, this.endTime);
			currentCoor[0]= (this.startStation.getX()-this.startCoor[0])*pastTime/(double)duration+this.startCoor[0];
			currentCoor[1]= (this.startStation.getY()-this.startCoor[1])*pastTime/(double)duration+this.startCoor[1];
		}else if(state == 2) {
			pastTime = Time.calTimeDifference(this.startTime, Time.getCurrentTime());
			duration = Time.calTimeDifference(this.startTime, this.endTime);
			currentCoor[0]= (this.destX-this.startCoor[0])*pastTime/(double)duration+this.startCoor[0];
			currentCoor[1]= (this.destY-this.startCoor[1])*pastTime/(double)duration+this.startCoor[1];
		}
		
		return currentCoor;
	}

	/**
	 * if there is change on the route plan, update the rent event
	 */
	@Override
	public void update() {
		sc = new Scanner(System.in);
		System.out.println("destination station is closed, change a route?(y/n)");
		char confirm = sc.next().charAt(0);
		if(confirm=='y'||confirm=='Y') {
			PlanningPolicy policy = new UpdatePolicy();
			double[] toCoord = {this.destX,this.destY};
			Station startStation = this.getStartStation();
			this.setSolution(policy.getStationCouple(this.getCurrentLocation(),toCoord , bikeType));
			this.getSolution().setStartStation(startStation);
			System.out.println("Current location"+Arrays.toString(this.getCurrentLocation()));
			this.start_tmp = Time.getCurrentTime();
			this.startCoor = this.getCurrentLocation();
			this.destStation = this.solution.getEndStation();
			System.out.println("Delay Hour" + solution.getDelayHour());
			this.endTime = Time.calEndTime(start_tmp, (int)(solution.getDelayHour()*60));
		sc.close();
		}
	}
	
	/**
	 * get information of the current state of the user:
	 * what is he/she doing? how many minutes left to reach to the next state
	 * where is him/her?
	 */
	public void getInformation() {
		DecimalFormat df = new DecimalFormat( "0.0000 ");
		if(this.state == 1) {
			int restTime = Time.calTimeDifference(Time.getCurrentTime(), this.endTime);
			if(restTime<0)	restTime = 0;
			this.user.updateLocation(this.getCurrentLocation());
			System.out.println("User "+ this.getUser().getName() +" ID:" + this.getUser().getUserID()+ " walking to the start station --" +restTime + " min(s) left, current position: [" + df.format(this.getCurrentLocation()[0])+", "+ df.format(this.getCurrentLocation()[1])+"]");
		}else if(this.state == 2) {
			int restTime = Time.calTimeDifference(Time.getCurrentTime(), this.endTime);
			if(restTime<0)	restTime = 0;
			this.user.updateLocation(this.getCurrentLocation());
			System.out.println("User "+ this.getUser().getName() +" ID:" + this.getUser().getUserID()+ " riding to the start station --" +restTime + " min(s) left, current position: [" + df.format(this.getCurrentLocation()[0])+", "+ df.format(this.getCurrentLocation()[1])+"]");
		}else if(this.state ==3) {
			int restTime = Time.calTimeDifference(Time.getCurrentTime(), this.endTime);
			if(restTime<0)	restTime = 0;
			this.user.updateLocation(this.getCurrentLocation());
			System.out.println("User "+ this.getUser().getName() +" ID:" + this.getUser().getUserID()+ " walking to the end point --" +restTime + " min(s) left, current position: [" + df.format(this.getCurrentLocation()[0])+", "+ df.format(this.getCurrentLocation()[1])+"]");
		}
	}

	
	
}