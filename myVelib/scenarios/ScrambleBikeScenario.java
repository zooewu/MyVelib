package scenarios;
import database.RentEvent;
import database.Server;
import multiThreads.EventWatchDog;
import ridePlanning.MinWalkingPolicy;
import ridePlanning.PlanningPolicy;
import ridePlanning.Solution;
import top.Administrator;

public class ScrambleBikeScenario {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		var admin = new Administrator();
		admin.initialisation();
		runPolicy(admin, 0);
		runPolicy(admin, 1);
		runPolicy(admin, 2);
		runPolicy(admin, 3);
		runPolicy(admin, 4);
		runPolicy(admin, 5);
		
		EventWatchDog dog = new EventWatchDog();

		// when the thread has started, it will call the run function in EventWatchDog
		Thread t1 = new Thread(dog);
		t1.start();
	}

	public static void runPolicy(Administrator admin, int userId) {
		PlanningPolicy policy1 = new MinWalkingPolicy();
		Solution solution1 = policy1.getStationCouple(Server.users.get(userId).getCoor(), new double[]{11.5,12.5}, "Mbike");
		Server.rentEvents.add(new RentEvent(Server.users.get(userId), "Mbike",solution1,11.5,12.5));
	}

}
