package user;

import bicycle.*;
/**
 * 
 * @author Zoe
 * class for user holding no card
 */
public class NoCardUser extends User {
	public NoCardUser(String name, double x, double y) {
		super();
		this.userBalance = new UserBalance();
		this.name = name;
		UserNumberGenerator u = UserNumberGenerator.getInstance();
		this.userID = u.getNextUserID();
		this.card = "NoCard";
		this.x = x;
		this.y = y;
		this.timeBalance = 0;
	}

	@Override
	public double[] getCoor() {
		double coor [] = {this.x, this.y};
		return coor;
	}

	@Override
	public int getTimeBalance() {
		System.out.println("user without card have no time balance");
		return 0;
	}

	@Override
	public void setTimeBalance(int timeBalance) {
		System.out.println("user without card have no time balance");
	}

	@Override
	public double visit(Bicycle bicycle, int minutes) {
		return bicycle.getPricePerHour()*minutes/60;
	}

	
}
