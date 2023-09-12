package user;
import bicycle.Bicycle;
import calculCosts.*;
import station.Operation;

/**
 * class for All of the users information
 * @author Zoe
 *
 */

public abstract class User implements UserVisitor{
	int userID;
	String name;
	String card;
	double x,y;
	int timeBalance;//minute
	UserBalance userBalance;
	//RentEvent rentevent = null;
	
	public String toStatistics() {
		return "***************\nUser ID:"+this.userID +"\nName:" + this.name+"\n[User Balance]\n"+this.userBalance.toString(); 
	}
	
	public int getUserID() {
		return userID;
	}

	@Override
	public double visit(Bicycle bicycle, int minutes) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCard() {
		return card;
	}


	public void setCard(String card) {
		this.card = card;
	}


	public double getX() {
		return x;
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


	public int getTimeBalance() {
		return timeBalance;
	}
	/**
	 * if user holds Vlibre card, every time he parks bike in a plus station, his time balance is increased by 5 minutes
	 */
	public void addTimeBalance() {
		this.timeBalance += 5;
	}

	public void setTimeBalance(int timeBalance) {
		this.timeBalance = timeBalance;
	}
	
	public void updateLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateLocation(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void updateLocation(double x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public UserBalance getUserBalance() {
		return userBalance;
	}

	public void updateLocation(double []coor) {
		this.x = coor[0];
		this.y = coor[1];
	}
	
	public abstract double[] getCoor();

	@Override
	public String toString() {
		return "User [userID=" + userID + ", name=" + name + ", card=" + card + ", x=" + x + ", y=" + y
				+ ", timeBalance=" + timeBalance + "]";
	}
	
}
