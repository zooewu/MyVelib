package user;

import bicycle.Bicycle;
/**
 * concrete user holding vmax card
 * @author Zoe
 *
 */
public class VmaxUser extends User {

	public VmaxUser(String name, double x, double y) {
		super();
		this.userBalance = new UserBalance();
		this.name = name;
		UserNumberGenerator u = UserNumberGenerator.getInstance();
		this.userID = u.getNextUserID();
		this.card = "Vmax";
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
	public double visit(Bicycle bicycle, int minutes) {
		if(minutes<=60)
			return 0;
		else {
			double realMinutes = minutes - 60;
			return realMinutes/60;
		}
	}
}
