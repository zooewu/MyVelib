package user;

import bicycle.Bicycle;
/**
 * concrete user holding vlibre card
 * @author Zoe
 *
 */
public class VlibreUser extends User {
	public VlibreUser(String name, double x, double y) {
		super();
		this.userBalance = new UserBalance();
		this.name = name;
		UserNumberGenerator u = UserNumberGenerator.getInstance();
		this.userID = u.getNextUserID();
		this.card = "Vlibre";
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
		if(minutes<=60) {
			System.out.println("time balance = "+this.getTimeBalance()+" minutes");
			return (bicycle.getPricePerHour()-1)*minutes/60;
		}else if((minutes>60)&&(minutes<this.getTimeBalance()+60)){
			this.setTimeBalance(this.getTimeBalance()+60-minutes);
			System.out.println("time balance = "+this.getTimeBalance()+" minutes");
			return bicycle.getPricePerHour()-1;
		}else {
			int balance = this.getTimeBalance();
			this.setTimeBalance(0);
			System.out.println("time balance = "+this.getTimeBalance()+" minutes");
			return bicycle.getPricePerHour()-1+bicycle.getPricePerHour()*(minutes-60-balance)/60;
		}
	}
}
