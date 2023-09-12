package station;

import java.util.ArrayList;
import java.util.Arrays;

import bicycle.Bicycle;
import top.Time;

/**
 * 
 * @author Zoe
 * class to represent the slot and relevant methods
 */
public class Slot {
	private int ID;
	private String status;//free, occupied or out-of-service
	private int stationID;//Id of the station which the slot belongs to
	private Bicycle bicycle;// the bicycle which is parking in this slot
	private ArrayList<SlotOperation> operations= new ArrayList<SlotOperation>();
	
	public Slot(int stationID,String state,int ID) {
		super();
		this.ID = ID;
		this.setStatus(state);
		this.stationID = stationID;
	}

	public ArrayList<SlotOperation> getOperations() {
		return operations;
	}

	public String getStatus() {
		return status;
	}
	/**
	 * when the status is changed, an operation is saved in array list
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
		operations.add(new SlotOperation(this.status));
	}

	public int getStationID() {
		return stationID;
	}

	public void setStationID(int stationID) {
		this.stationID = stationID;
	}

	public int getID() {
		return ID;
	}

	public Bicycle getBicycle() {
		return bicycle;
	}
	
	public String getBikeType() {
		try{
			return bicycle.getType();
		}catch(NullPointerException e) {
			return "null";
		}
	}

	/**
	 * park the given bicycle into the slot, if success, return true, otherwise false
	 * @param bicycle
	 * @return: if the parking is done or not
	 */
	public boolean setBicycle(Bicycle bicycle) {
		if(this.status.equalsIgnoreCase("free")) {
			this.bicycle = bicycle;
			this.setStatus("occupied");
			bicycle.setStationID(stationID);
			return true;
		}else {return false;}
	}
	/**
	 * take the bike parked in this slot
	 * @return: the bike taken from this slot
	 */
	public Bicycle takeBicycle() {
		Bicycle bicycle = this.bicycle;
		bicycle.setStationID(0);
		this.bicycle = null;
		this.setStatus("free");
		return bicycle;
	}
	
	@Override
	public String toString() {
		String operationInfo = "\t[Records]\n";
		for(SlotOperation operation:operations) {
			operationInfo += "\t\t"+operation.toString()+"\n";
		}
		if(operationInfo.equalsIgnoreCase("/t[Records]")) {
			return "Slot [ID=" + ID + ", status=" + status + ", stationID=" + stationID + ", bicycle=" + bicycle + "]\n";
		}else {
			return "Slot [ID=" + ID + ", status=" + status + ", stationID=" + stationID + ", bicycle=" + bicycle + "]\n"+operationInfo+"\n";
		}
	}
	
	/**
	 * in-built class operation, record the information of the change of the slot's state
	 * @author Zhuofan Yu
	 *
	 */
	public class SlotOperation {
		private int[] time = new int[3];
		private String state;
		public SlotOperation(String state) {
			this.time = Time.getCurrentTime();
			this.state = state;
		}
				
		public int[] getTime() {
			return time;
		}

		public String getState() {
			return state;
		}

		@Override
		public String toString() {
			return "SlotOperation [time=" + Arrays.toString(time) + ", state=" + state + "]";
		}
	}
	
	
}
