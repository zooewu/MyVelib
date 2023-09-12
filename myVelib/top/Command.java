package top;

import user.*;

import java.util.Random;
import java.util.Scanner;


import bicycle.Bicycle;
import database.RentEvent;
import database.Server;
import ridePlanning.*;
import station.Slot;
import station.Station;


/**
 * this class contains methods for server initialization, facilities initialization, action of rent&returning bike,
 *  choosing policy, basic interaction with user, as well as station sorting methods
 * @author Zoe
 *
 */

public class Command {
	AbstractFactory stationFactory = FactoryProducer.getFactory("Station");	
	AbstractFactory bicycleFactory = FactoryProducer.getFactory("Bicycle");	
	AbstractFactory slotFactory = FactoryProducer.getFactory("Slot");
	AbstractFactory userFactory = FactoryProducer.getFactory("User");
	private Scanner scanner;
	Random rand = new Random();
	
	public void setup(String[] tokens) {
		if(tokens.length == 2) {
			initialiseBuildStationComm(tokens[1]);
			initializeSlots();
			
			int eNum = 23; int mNum = 52;int i=0;
			while(i<eNum) {
				int k = rand.nextInt(10);
				for(Slot slot:Server.stations.get(k).getSlots()) {
					if(slot.setBicycle(bicycleFactory.buildBicycle("ebike"))) {
						i +=1;
						break;
					}
				}
			}i=0;
			while(i<mNum) {
				int k = rand.nextInt(10);
				for(Slot slot:Server.stations.get(k).getSlots()) {
					if(slot.setBicycle(bicycleFactory.buildBicycle("mbike"))) {
						i +=1;
						break;
					}
				}
			}
		}else if(tokens.length == 6) {
			String mapName = tokens[1];
			int stationNum = Integer.parseInt(tokens[2]);
			int slotNum = Integer.parseInt(tokens[3]);
			double s = Double.valueOf(tokens[4]);
			int bikeNum = Integer.parseInt(tokens[5]);
			initialiseBuildStationComm(mapName, stationNum,s);
			if(slotNum>0) {
				this.initializeSlots(slotNum);
			}else System.err.println("please enter a positive number of slots to be built!;)");
			int eNum = (int)0.3*bikeNum;		
			int mNum = bikeNum - eNum;
			int i=0;
			while(i<eNum) {
				int k = rand.nextInt(10);
				for(Slot slot:Server.stations.get(k).getSlots()) {
					if(slot.setBicycle(bicycleFactory.buildBicycle("ebike"))) {
						i +=1;
						break;
					}
				}
			}i=0;
			while(i<mNum) {
				int k = rand.nextInt(10);
				for(Slot slot:Server.stations.get(k).getSlots()) {
					if(slot.setBicycle(bicycleFactory.buildBicycle("bike"))) {
						i +=1;
						break;
					}
				}
			}	
		}else {
			System.err.println("The format of setup command is incorrect");
		}
	}
	
	public void addUser(String[] tokens) {
		if(tokens.length == 4) {
			String userName = tokens[1];
			String cardType = tokens[2];
			if((!(cardType.equalsIgnoreCase("none")))&&(!(cardType.equalsIgnoreCase("vlibre")))&&(!(cardType.equalsIgnoreCase("vmax")))) {
				System.err.println("Wrong card type");
			}
			Server.users.add(userFactory.buildUser(cardType,userName,0,0));
		}else if(tokens.length == 5) {
			String userName = tokens[1];
			String cardType = tokens[2];
			double x = Double.valueOf(tokens[3]);
			double y = Double.valueOf(tokens[4]);
			if((!(cardType.equalsIgnoreCase("none")))&&(!(cardType.equalsIgnoreCase("vlibre")))&&(!(cardType.equalsIgnoreCase("vmax")))) {
				System.err.println("Wrong card type");
			}
			Server.users.add(userFactory.buildUser(cardType,userName,x,y));
		}else System.err.println("The format of addUser command is incorrect");
	}
	
	public void offline(String[] tokens) {
		if(tokens.length==3) {
			int index = Integer.parseInt(tokens[2])-1;
			if((index>Server.stations.size()-1)||index<0) {
				System.err.println("The station ID is not exist");
			}
			Server.stations.get(index).setStatus("Offline");
		}else if(tokens.length==2) {
			int index = Integer.parseInt(tokens[1])-1;
			if((index>Server.stations.size()-1)||index<0) {
				System.err.println("The station ID is not exist");
			}
			Server.stations.get(index).setStatus("Offline");
		}else System.err.println("The format of offline command is incorrect");
	}
	
	public void online(String[] tokens) {
		if(tokens.length==3) {
			int index = Integer.parseInt(tokens[2])-1;
			if((index>Server.stations.size()-1)||index<0) {
				System.err.println("The station ID is not exist");
			}
			Server.stations.get(index).setStatus("On-service");
		}else if(tokens.length==2) {
			int index = Integer.parseInt(tokens[1])-1;
			if((index>Server.stations.size()-1)||index<0) {
				System.err.println("The station ID is not exist");
			}
			Server.stations.get(index).setStatus("On-service");
		}else System.err.println("The format of offline command is incorrect");
	}
	
	public void rentBike(String[] tokens) {
		User user = null;
		Station station = null;
		Bicycle bike = null;
		for(User u:Server.users) {
			if(u.getUserID()== Integer.parseInt(tokens[1])) {
				user = u;
				break;
			}
		}
		for(Station s:Server.stations) {
			if(s.getID()== Integer.parseInt(tokens[2])) {
				station = s;
				break;
			}
		}if(user==null) {
			System.err.println("The format of user id is incorrect");
		}if(station==null) {
			System.err.println("The format of station id is incorrect");
		}
		Slot s = null;
		for(Slot slot:station.getSlots()) {
			if(slot.getBicycle()!=null) {
				bike = slot.takeBicycle();
				s = slot;
				break;
			}
		}if(bike==null) {
			System.err.println("no bike available");
		}
		RentEvent rv = new RentEvent(user,bike.getType(),0,0);
		rv.setBike(bike);rv.setStartStation(station);
		rv.getStartStation().recordOperation(rv, s, "rent");
		Server.rentEvents.add(rv);
		
	}
	
	public void returnBike(String[] tokens) {
		int userID = Integer.parseInt(tokens[1]);
		int stationID = Integer.parseInt(tokens[2]);
		int duration = Integer.parseInt(tokens[3]);
		RentEvent rentEvent = null;
		Station station = null;
		for(RentEvent rv: Server.rentEvents) {
			if(rv.getUser().getUserID()==userID) {
				rentEvent = rv;
				break;
			}
		}
		if(rentEvent == null) {
			System.err.println(" It's impossible to return with wrong information");
		}
		for(Station s:Server.stations) {
			if(s.getID() == stationID) {
				station = s;
				break;
			}
		}
		if(station == null) {
			System.err.println(" The station to return bike does not exist");
		}
		rentEvent.setDestStation(station);
		Bicycle bike = rentEvent.getBike();
		if(parkBike(rentEvent)) { 	
			double cost = bike.cost(rentEvent.getUser(), duration);
			rentEvent.getUser().getUserBalance().update(duration, cost);
			System.out.println("cost of this riding: "+cost+" euros");
			System.out.println("user's time balance = "+rentEvent.getUser().getTimeBalance()+" minutes");
			Server.rentEvents.remove(rentEvent);
			}
	}
	
	public void move(String[] tokens) {
		if(tokens.length == 5) {
			int userID = Integer.parseInt(tokens[1]);
			User user = null;
			double x = Double.parseDouble(tokens[2]);
			double y = Double.parseDouble(tokens[3]);
			String bikeType = tokens[4];
			if(!((bikeType.equalsIgnoreCase("mbike"))||(bikeType.equalsIgnoreCase("ebike")))){
				System.err.println("Wrong input type of bike");
			}
			for(User u:Server.users) {
				if(u.getUserID()==userID) {
					user = u;
					break;
				}
			}
			if(user == null) System.err.println("User not found for move instruction");
			PlanningPolicy policy = new MinWalkingPolicy();
			Solution solution = policy.getStationCouple(user.getCoor(), new double[]{x,y}, bikeType);
			Server.rentEvents.add(new RentEvent(user, "Mbike",solution,x,y));
		}else if(tokens.length == 6) {
			int userID = Integer.parseInt(tokens[1]);
			User user = null;
			double x = Double.parseDouble(tokens[2]);
			double y = Double.parseDouble(tokens[3]);
			String bikeType = tokens[4];
			PlanningPolicy pp = chooseStrategy(Integer.parseInt(tokens[5]));
			if(!((bikeType.equalsIgnoreCase("mbike"))||(bikeType.equalsIgnoreCase("ebike")))){
				System.err.println("Wrong input type of bike");
			}
			for(User u:Server.users) {
				if(u.getUserID()==userID) {
					user = u;
					break;
				}
			}
			if(user == null) System.err.println("User not found for move instruction");
			Solution solution = pp.getStationCouple(user.getCoor(), new double[]{x,y}, bikeType);
			Server.rentEvents.add(new RentEvent(user, "Mbike",solution,x,y));
		}
		else System.err.println("Wrong input format of instruction ��move��");
	}
	
	public String sortStation(String[] tokens) {
		String info="";
		if(tokens.length == 3) {
			if(tokens[2].equalsIgnoreCase("mostOccupiedStation")) {
				info = this.mostOccupiedStation(Server.globalStartTime, Time.getCurrentTime());
			}else if(tokens[2].equalsIgnoreCase("mostUsedStation")) {
				info = this.mostUsedStation();
			}else System.err.println("sorting policy unfounded");
		}else System.err.println("The format of offline command is incorrect");
		
		return info;
	}
	
	public String displayUser(String[] tokens) {
		User user = null;
		for(User u:Server.users) {
			if(u.getUserID()==Integer.parseInt(tokens[2])) {
				user = u;
				break;
			}
		}if(user==null) {
			System.err.println("user is not found");
		}
		return user.toStatistics();
	}
	
	public String displayStation(String[] tokens) {
		Station station = null;
		for(Station s:Server.stations) {
			if(s.getID()==Integer.parseInt(tokens[2])) {
				station = s;
				break;
			}
		}if(station==null) {
			System.err.println("station is not found");
		}
		return station.toStatistics();
	}
	
	/**
	 * CLUI instruction, display the entire status(stations, parking bays) of the whole system
	 * @param
	 * @return all of the information of the system in string
	 */
	public String display() {
		String info = "";
		for(Station station:Server.stations) {
			info += station.toStatistics();
			info += "[Slots]\n";
			for(Slot slot:station.getSlots()) {
				info += "\t" + slot.toString();
			}
		}
		return info;
	}
	
	/**
	 * Build new stations,with the argument of stationType,location(x,y)
	 */
	public void buildStationComm(){
		char msg='Y'; 
		while(msg=='Y' || msg== 'y') {
			scanner = new Scanner(System.in);
			System.out.print("What station would you like?(NStation/PStation):\t");
			String stationType = scanner.nextLine();				
			System.out.print("What the coordinate of this station?(eg:135.2,265,7)");
			System.out.print("x = ");
			double infX = scanner.nextFloat();
			System.out.print("y = ");
			double infY =scanner.nextFloat();
			Server.stations.add(stationFactory.buildStation(stationType,infX,infY));
			System.out.print("Build one more station?(Y/N)");
			msg = scanner.next().charAt(0);
			scanner.close();
		}
	}
	
	/**
	 * method initialiseBuildStationComm in order to initialize the stations automatically
	 * coordinates are chosen in purpose to do different tests
	 */
	public void initialiseBuildStationComm() {
		Server.stations.add(stationFactory.buildStation("pstation",0,0));
		Server.stations.add(stationFactory.buildStation("pstation",12,12.5));
		Server.stations.add(stationFactory.buildStation("pstation",1,0));
		Server.stations.add(stationFactory.buildStation("pstation",20,12.5));
		Server.stations.add(stationFactory.buildStation("pstation",25,25));
		Server.stations.add(stationFactory.buildStation("nstation",4,0));
		Server.stations.add(stationFactory.buildStation("nstation",12.5,12.5));
		Server.stations.add(stationFactory.buildStation("nstation",12.5,13));
		Server.stations.add(stationFactory.buildStation("nstation",6,25));
		Server.stations.add(stationFactory.buildStation("nstation",23,23));	
	}
	
	public void initialiseBuildStationComm(String mapName) {
		if(mapName.equals("map1")) {
			Server.stations.add(stationFactory.buildStation("pstation",0,0));
			Server.stations.add(stationFactory.buildStation("nstation",4,0));
			Server.stations.add(stationFactory.buildStation("pstation",8,0));
			Server.stations.add(stationFactory.buildStation("nstation",0,4));
			Server.stations.add(stationFactory.buildStation("nstation",4,4));
			Server.stations.add(stationFactory.buildStation("nstation",8,4));
			Server.stations.add(stationFactory.buildStation("nstation",0,8));
			Server.stations.add(stationFactory.buildStation("nstation",4,8));
			Server.stations.add(stationFactory.buildStation("pstation",8,8));
			Server.stations.add(stationFactory.buildStation("pstation",0,12));
		}
		else if(mapName.equals("map2")) {
			Server.stations.add(stationFactory.buildStation("pstation",0,0));
			Server.stations.add(stationFactory.buildStation("nstation",4,0));
			Server.stations.add(stationFactory.buildStation("nstation",8,0));
			Server.stations.add(stationFactory.buildStation("pstation",12,0));
			Server.stations.add(stationFactory.buildStation("nstation",0,4));
			Server.stations.add(stationFactory.buildStation("nstation",4,4));
			Server.stations.add(stationFactory.buildStation("nstation",8,4));
			Server.stations.add(stationFactory.buildStation("nstation",0,8));
			Server.stations.add(stationFactory.buildStation("nstation",4,8));
			Server.stations.add(stationFactory.buildStation("pstation",0,12));
		}else if(mapName.equals("mapDemo")) {
			Server.stations.add(stationFactory.buildStation("pstation",0,0));
			Server.stations.add(stationFactory.buildStation("pstation",12,12.5));
			Server.stations.add(stationFactory.buildStation("pstation",1,0));
			Server.stations.add(stationFactory.buildStation("pstation",20,12.5));
			Server.stations.add(stationFactory.buildStation("pstation",25,25));
			Server.stations.add(stationFactory.buildStation("nstation",4,0));
			Server.stations.add(stationFactory.buildStation("nstation",12.5,12.5));
			Server.stations.add(stationFactory.buildStation("nstation",12.5,13));
			Server.stations.add(stationFactory.buildStation("nstation",6,25));
			Server.stations.add(stationFactory.buildStation("nstation",23,23));	
		}else {
			System.err.println("Wrong map type");
		}
	}
	
	public void initialiseBuildStationComm(String mapName, int nStation, double s) {
		if(mapName.equals("circle")) {
			Random rand = new Random();
			for(int i=0;i<nStation;i++) {
				double r = s*rand.nextDouble();
				double seta = 2*Math.PI*rand.nextDouble();
				int isN = rand.nextInt(2);
				if(isN == 1)
					Server.stations.add(stationFactory.buildStation("nstation",r*Math.cos(seta),r*Math.sin(seta)));
			}
		}
	}
	/**
	 * Build new bicycles,with the argument of bikeType, station belonging
	 */
	public void buildBicycleComm() {
		char msg='Y';	
		while(msg=='Y' || msg== 'y') 
		{
			scanner = new Scanner(System.in);
			System.out.print("Which type of bicycle to be produced?(Mbike/Ebike):\t");
			String bicycleType = scanner.nextLine();
			System.out.print("Which station does the bicycle initially belong to?(input a station ID):\t");
			int stationID = scanner.nextInt();
			Bicycle newbi = bicycleFactory.buildBicycle(bicycleType);
			newbi.setStationID(stationID);
						

			Server.bicycles.add(newbi);
			System.out.print("Produce one more bicycle?(Y/N)");
			msg = scanner.next().charAt(0);
		}
	}

	/**
	 * Initialize bicycles
	 * @param s: the station to put the bikes
	 * @param eNum:number of new electrical bikes
	 * @param mNum:number of new mechanical bikes
	 */
	public void initializeBicycle(Station s, int eNum, int mNum) {
		for(int i=0;i<mNum;i++) {
			Bicycle bi = bicycleFactory.buildBicycle("mbike");
			for(Slot slot:s.getSlots()) {
				if(slot.setBicycle(bi))
					break;
			}
			Server.bicycles.add(bi);
		}
		for(int i=0;i<eNum;i++) {
			Bicycle bi = bicycleFactory.buildBicycle("ebike");
			for(Slot slot:s.getSlots()) {
				if(slot.setBicycle(bi))
					break;
			}
			Server.bicycles.add(bi);
		}
	}
	/**
	 * initialize bikes in all stations
	 * number of bikes are chosen in purpose to do different tests
	 */
	public void initAllBikes() {
		initializeBicycle(Server.stations.get(0),3,2);
		initializeBicycle(Server.stations.get(1),2,6);
		initializeBicycle(Server.stations.get(2),5,5);
		initializeBicycle(Server.stations.get(3),2,7);
		initializeBicycle(Server.stations.get(4),1,7);
		initializeBicycle(Server.stations.get(5),0,0);
		initializeBicycle(Server.stations.get(6),4,2);
		initializeBicycle(Server.stations.get(7),3,6);
		initializeBicycle(Server.stations.get(8),0,7);
		initializeBicycle(Server.stations.get(9),1,7);
	}
	
	/**
	 * Ask the administrator which Slot to be built
	 */
	public void buildSlotsComm() {
		char msg='Y'; 
		while(msg=='Y' || msg== 'y') 
		{
			scanner = new Scanner(System.in);
			System.out.print("Which station does the slot belong to?(enter the station ID):\t");
			int stationID = scanner.nextInt();
			System.out.print("What is the inital status of the slot?(free, occupied or out-of-service):\t");
			String iniStatus = scanner.nextLine();
			System.out.print("How many slots you want to build?(give an integer):\t");
			int num = scanner.nextInt();

			for(Station s:Server.stations) {
				if(s.getID()==stationID){
					s.addSlot(num, iniStatus);
					break;
				}
			}
			
			System.out.print("Produce one more slot?(Y/N)");
			msg = scanner.next().charAt(0);
			scanner.close();
		}
	}
	
	/**
	 *  Initialize slots(build in station)
	 */
	public void initializeSlots() {
		for(Station station:Server.stations) {
			station.addSlot(10, "free");
		}
	}
	
	public void initializeSlots(int num) {
		for(Station station:Server.stations) {
			station.addSlot(num, "free");
		}
	}
	

	
	
	
	/**
	 * find a station according to its ID number
	 * @param id
	 * @return: station
	 */
	public static Station searchStation(int id) {
		for(Station s: Server.stations) {
			if(s.getID() == id) {
				return s;
			}
		}System.out.println("station not found");
		return null;
	}
	
	/**
	 *  Build new user,with the argument of userType,user name, location(x,y)
	 */
	public void buildUserComm(){
		char msg='Y';
		while(msg=='Y' || msg== 'y') {
			scanner = new Scanner(System.in);
			System.out.print("What kind of user would you like?(NoCard/Vlibre/Vmax):\t");
			String userType = scanner.nextLine();
			System.out.print("What is user's name?:\t");
			String name = scanner.nextLine();	
			System.out.print("What the coordinate of this user?(eg:135.2,265,7)");
			System.out.print("x = ");
			double infX = scanner.nextFloat();
			System.out.print("y = ");
			double infY =scanner.nextFloat();			
			Server.users.add(userFactory.buildUser(userType,name,infX,infY));
			System.out.print("Build one more user?(Y/N)");
			msg = scanner.next().charAt(0);
			scanner.close();
		}
	}
	
	/**
	 * method initialiseBuildUserComm in order to initialize the users automatically
	 */
	public void initialiseBuildUserComm() {
		Server.users.add(userFactory.buildUser("Vlibre","Luke1",2,0));
		Server.users.add(userFactory.buildUser("Vlibre","Luke2",2,0));
		Server.users.add(userFactory.buildUser("Vlibre","Luke3",2,0));
		Server.users.add(userFactory.buildUser("Vlibre","Luke4",2,0));
		Server.users.add(userFactory.buildUser("Vlibre","Luke5",2,0));
		Server.users.add(userFactory.buildUser("Vlibre","Luke6",2,0));
		
		Server.users.add(userFactory.buildUser("Vmax","Vader",12.6,12.6));
		Server.users.add(userFactory.buildUser("NoCard","Yoda",23,25));
		Server.users.get(0).setTimeBalance(100);
	}
	
	/**
	 *  rent a bike from the given station and create a new rentEvent
	 * @param rentEvent
	 * @return true if the rent succeeded
	 */
	public boolean rentBike(RentEvent rentEvent) {
		Bicycle bicycle=null;
		Slot slot=rentEvent.getSolution().getSlot();
		bicycle = slot.takeBicycle();
		if(bicycle != null) 
		{
			rentEvent.getDestStation().registerObserver(rentEvent);
			rentEvent.rentBikeEvent(bicycle);
			rentEvent.getStartStation().recordOperation(rentEvent, slot, "rent");
			return true;
		}
		return false;	
	}
	
	/**
	 *  park the bike into a free slot
	 * @param rentEvent
	 * @return true if the returning succeeded
	 */
	public boolean parkBike(RentEvent rentEvent) {
		for(Slot slot:rentEvent.getDestStation().getSlots()) {
			while(!slot.setBicycle(rentEvent.getBike())) {
				if(rentEvent.getDestStation().getEmptySlotNum()==0) {
					rentEvent.getDestStation().notifyObserver();
				}
				slot.getBicycle().setReserved(false);
				System.out.println("User:" + rentEvent.getUser().getName()+"\t parked his/her bike in Station: "+rentEvent.getDestStation().getID()+" Slot: " + slot.getID()+" at " +Time.getCurrentTime()[1]+":"+Time.getCurrentTime()[2] +" on " +Time.getCurrentTime()[0]+"th " );
				rentEvent.getDestStation().removeObserver(rentEvent);
				rentEvent.getDestStation().recordOperation(rentEvent, slot, "return");
				if(rentEvent.getDestStation().getType().equalsIgnoreCase("Pstation")&&rentEvent.getUser().getCard().equalsIgnoreCase("Vlibre")) {
					rentEvent.getUser().addTimeBalance();
					rentEvent.getUser().getUserBalance().update(true);
				}
				return true;
			}
		}
		System.out.println("Error while parking");
		System.out.println(this.toString());
		return false;
	}
	

	/**
	 * method for ride planning policy choice
	 * @param choice: the index of policy
	 * @return: a new PlanningPolicy instance
	 */
	public PlanningPolicy chooseStrategy(int choice) {
		PlanningPolicy policy = null;
		switch(choice) {
		case 1:
			policy = new MinWalkingPolicy();break;
		case 2:
			policy = new FastestPathPolicy();break;
		case 3:
			policy = new AvoidPlusPolicy();break;
		case 4:
			policy = new PreferPlusPolicy();break;
		case 5:
			policy = new UniformityPreservePolicy();break;
		default:
			System.err.print("policy not found");
		}
		return policy;
	}
	/**
	 *  simple interaction with user to start a whole riding process
	 */
	public void userInteract() {
		char confirm = 'n';
		while(confirm == 'n'||confirm=='N') {
			scanner = new Scanner(System.in);
			System.out.println("please enter your user ID: ");
			int idInput = scanner.nextInt();
			User user = null;
			for(User u:Server.users) {
				if(u.getUserID()==idInput) {
					user = u; 
				}
			}if(user==null) {
				System.out.println("user not found,try agin");
				continue;
			}
			System.out.println(user.toString());
			System.out.println("please enter your destination coordinate x = ");
			double toX = scanner.nextDouble();
			System.out.println("please enter your destination coordinate y = ");
			double toY = scanner.nextDouble();
			double[] toCoord = {toX, toY};
			System.out.println("would you like a mechanical bike(1) or an electrical bike(2)? ");
			int choiceBike = scanner.nextInt();
			String bikeType = null;
			if(choiceBike==1)
				bikeType = "mbike";
			else
				bikeType = "ebike";
			System.out.println("how would you like to find the routine?(enter a number):\n"
					+ "1. I don't like to walk\n"+"2. I want to arrive as quickly as possible\n"
					+"3. I don't want to arrive at Plus-type station\n"
					+"4. I'd like to arrive at a Plus-type station\n"+
					"5. I'd like more spare bikes and empty slots");
			int choice = scanner.nextInt();
			PlanningPolicy policy = chooseStrategy(choice);
			Solution solution = policy.getStationCouple(user.getCoor(), toCoord, bikeType);
			System.out.println(solution.toString());
			System.out.println("confirm the order?(y/n): ");
			confirm = scanner.next().charAt(0);
			if(confirm=='y'||confirm=='Y')
				Server.rentEvents.add(new RentEvent(user, bikeType,solution,toX,toY));
			scanner.close();
		}
	}
	/**
	 * sorting stations taking in consideration the total rent+returning operation times
	 */
	public String mostUsedStation() {
		String infoStations = "\n***************\nSorted stations(most used station)\n";
		int len = Server.stations.size();
		int[][] toSort = new int[len][2];
		int x=0;
		for(Station s:Server.stations) {
			toSort[x][0] = s.getID();
			toSort[x][1] = s.getRentNum()+s.getReturnNum();
			x += 1;
		}
		
		for(int i=0;i<len;i++) {
			for(int j=1;j<len-i;j++) {
				if(toSort[j-1][1]<=toSort[j][1]) {
					int[] temp = toSort[j-1];
					toSort[j-1] = toSort[j];
					toSort[j] = temp;
				}
			}
		}
		for(int i=0;i<toSort.length;i++) {
				infoStations += "the station "+toSort[i][0]+" has been used "+toSort[i][1]+" times\n";
				//System.out.println("the station "+toSort[i][0]+" has been used "+toSort[i][1]+" times");
		}
		return infoStations;
	}
	/**
	 *  sorting stations taking in consideration the ratio of occupation
	 * @param ts:start moment of time window
	 * @param te: end moment of time window
	 */
	public String mostOccupiedStation(int[] ts, int[] te) {
		String infoStations = "\n***************\nSorted stations(most occupied station)\n";
		int len = Server.stations.size();
		double[][] toSort = new double[len][2];
		int i=0;int j = 0;
		for(Station s:Server.stations) {
			toSort[i][0] = s.getID();
			toSort[i][1] = s.getRateOccupation(ts, te);
			i+=1;
		}
		
		for(i=0;i<len;i++) {
			for(j=1;j<len-i;j++) {
				if(toSort[j-1][1]<toSort[j][1]) {
					double[] temp = toSort[j-1];
					toSort[j-1] = toSort[j];
					toSort[j] = temp;
				}
			}
		}
		for(double x[]:toSort) {
			infoStations += "the station "+x[0]+" has been occupied "+x[1]+" minutes\n";
			//System.out.println("the station "+x[0]+" has been occupied "+x[1]+" minutes");
		}
		return infoStations;
	}
	
}
