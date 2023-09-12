package user;
/**
 * statistics for service condition of each user
 * @author Zoe
 *
 */
public class UserBalance {
	 private int rideNum;
	 private int rideTime;
	 private double totalCharge;
	 private int timeCredit;
	
	public String toString() {
		return "total ride number:" + this.rideNum + "\ntotal ride Time" + this.rideTime +"\ntotal charge" +
	this.totalCharge+" euro\ntotal time credit earned:" + this.timeCredit;
	}
	 
	 
	public int getRideNum() {
		return rideNum;
	}

	public int getRideTime() {
		return rideTime;
	}

	public double getTotalCharge() {
		return totalCharge;
	}

	public int getTimeCredit() {
		return timeCredit;
	}
	
	public UserBalance() {
		this.rideNum = 0;
		this.rideTime = 0;
		this.totalCharge = 0.0;
		this.timeCredit = 0;
	}
	/**
	 *  when user has completed a riding, add the relevant riding duration and charge to the statistics
	 *  and his ride number increases by one
	 * @param duration
	 * @param charge
	 */
	public void update(int duration,double charge) {
		rideNum++;
		this.rideTime += duration;
		this.totalCharge += charge;
		
	}
	
	public void update(boolean credit) {
		if(credit) this.timeCredit+=5;
	}
	
	public UserBalance getUserBalance() {
		return this;
	}
}
