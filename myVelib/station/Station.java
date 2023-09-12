package station;

import java.util.ArrayList;
import top.Time;
import database.RentEvent;
import database.Server;
import routineChange.*;
import station.Slot.SlotOperation;

/**
 *  abstract class, define the structure of stations
 *
 */


public abstract class Station implements Observable{
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private int rentNum, returnNum;
	double x;
	double y;
	int ID;
	private int slotNumberGenerator = 1;
	String type;
	String status;//On-service or Offline
	ArrayList<Slot> slots = new ArrayList<Slot>();
	ArrayList<Operation> operations = new ArrayList<Operation>();
	
	public Station() {
		super();
		this.rentNum = 0;
		this.returnNum = 0;
	}
	
	public String toStatistics() {
		String oper = "";
		for(Operation operation: operations) {
			oper+= operation.toString() + "\n";
		}
		return "***************\nStation ID:"+ID+"\nX:"+this.getX()+" Y:"+this.getY()+"\nexisting mechanical bicycle number:"+this.countBike("Mbike")
		+"\nexisting electric bicycle number:"+this.countBike("Ebike")+"\nempty slot number:"+
		this.getEmptySlotNum()+"\ntotal rent: " + rentNum +"\ntotal return:" + returnNum+"\n"+
		"records:\n"+oper; 
	}
	
	
	
	
	
	/**
	 * record rent and return operations
	 * @param rentEvent
	 * @param slot
	 * @param type
	 */
	public void recordOperation(RentEvent rentEvent,Slot slot,String type) {
		if(type.equalsIgnoreCase("rent")) {this.rentNum++;}
		else if(type.equalsIgnoreCase("return")) {this.returnNum++;}
		this.operations.add(new Operation(rentEvent,slot,type,this));
	}

	public String getStatus() {
		return status;
	}
	/**
	 * when the status is changed, observers should be notified, and the operation should be recorded
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
		this.operations.add(new Operation(status,this));
		if(this.status.equalsIgnoreCase("Offline"))
			notifyObserver();
	}

	public int getReturnNum() {
		return returnNum;
	}

	public String getType() {
		return type;
	}
	
	public double getX() {
		return x;
	}
	
	public int getRentNum() {
		return this.rentNum;
	}
	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public int getID() {
		return ID;
	}
	

	public ArrayList<Slot> getSlots() {
		return slots;
	}
	/**
	 * add slots of a given number and of a given status to the station
	 * @param n: number of slots to be added
	 * @param status: free or out-of-service
	 */
	public void addSlot(int n, String status) {
		for(int i=0;i<n;i++) {
			this.slots.add(new Slot(this.ID,status,this.slotNumberGenerator));
			slotNumberGenerator++ ; 
		}
	}
	/**
	 * get coordinates of the station
	 * @return station coordinates{x,y}
	 */
	public abstract double[] getCoor();

	@Override
	public String toString() {
		return "Station [x=" + x + ", y=" + y + ", ID=" + ID + ", type=" + type + ", status=" + status + "\n, slots="
				+ slots + "]";
	}
	/**
	 * count the number of bikes parking in this station
	 * @param bikeType
	 * @return number of bikes
	 */
	public int countBike(String bikeType) {
		int n = 0;
		try{
			for(Slot s: slots) {
				if(s.getBikeType().equalsIgnoreCase(bikeType)) {
				n += 1;
				}
			}return n;
		}catch(NullPointerException e) {
			System.out.println("no bicycle in station");
			return 0;
		}
	}
	
	/**
	 * Return the number of the empty slots within this station
	 * @return
	 */
	public int getEmptySlotNum() {
		int num=0;
		for(Slot slot:slots) {
			if(slot.getStatus().equalsIgnoreCase("free")) {
				num++;
			}
		}return num;
	}

	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);		
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
		
	}

	@Override
	public void notifyObserver() {
		for(Observer ob:observers) {
			ob.update();
		}
		
	}

	public ArrayList<Operation> getOperations() {
		return operations;
	}
	
	/**
	 * Input a time window and output the occupation rate
	 * @param ts1: start moment of observation, {day, hour, minute}
	 * @param te1: end moment of observation, {day, hour, minute}
	 * @return: ratio of occupation
	 */
	public double getRateOccupation(int[]ts1,int[]te1) {
		double total =0.0;
		double busy = 0.0;
		int[] te = te1;
		int[] ts = ts1;
		if(Time.calTimeDifference(Time.getCurrentTime(),te1 )>=0) {
			te = Time.getCurrentTime();
		}
		if(Time.calTimeDifference(Server.globalStartTime, ts1)<= 0) {
			ts = Server.globalStartTime;
		}
		//total = Time.calTimeDifference(te, ts);
		for(Slot slot:this.slots) { 
			int num =0;	
			int cnt =1;
			int cntTotal = slot.getOperations().size();
			
			// search for the one operation just early than ts
			for(SlotOperation operation:slot.getOperations()) {
				if(Time.calTimeDifference(operation.getTime(), ts)>=0) {
					num++;
				}else break;
			}
			String preState = "";
			int[] preTime = new int[3];
			for(SlotOperation operation:slot.getOperations()) {
				if(cnt < num) {
					cnt++;
					continue;	
				}
				if(cnt == num) {	
					preTime = ts;
					preState = operation.getState();
					if(cnt == cntTotal) {
						if(preState.equalsIgnoreCase("occupied")||preState.equalsIgnoreCase("out-of-service")) {
							busy += Time.calTimeDifference(preTime, te);
							total+= Time.calTimeDifference(preTime, te);
							break;
						}else {
							total+= Time.calTimeDifference(preTime, te);
							break;
						}
					}
					cnt++;
					continue;
				}
				if(Time.calTimeDifference(operation.getTime(),te)>0) {
					//free, occupied or out-of-service
					if(preState.equalsIgnoreCase("occupied")||preState.equalsIgnoreCase("out-of-service")) {
						busy += Time.calTimeDifference(preTime, operation.getTime());
						total+= Time.calTimeDifference(preTime, operation.getTime());
					}else {
						total+= Time.calTimeDifference(preTime, operation.getTime());
					}
				}else {
					//free, occupied or out-of-service
					if(preState.equalsIgnoreCase("occupied")||preState.equalsIgnoreCase("out-of-service")) {
						busy += Time.calTimeDifference(preTime, te);
						total+= Time.calTimeDifference(preTime, te);
						break;
					}else {
						total+= Time.calTimeDifference(preTime, te);
						break;
					}	
				}
				preTime = operation.getTime();
				preState = operation.getState();
				// if it is the last operation to be considered
				if(cnt == cntTotal) {
					if(preState.equalsIgnoreCase("occupied")||preState.equalsIgnoreCase("out-of-service")) {
						busy += Time.calTimeDifference(preTime, te);
						total+= Time.calTimeDifference(preTime, te);
						break;
					}else {
						total+= Time.calTimeDifference(preTime, te);
						break;
					}
				}
				cnt++; 
			}
		}return 100*busy/total;
	}

	
	
}
