package scenarios;

import database.Server;
import multiThreads.EventWatchDog;
import top.*;
/**
 *
 * vlibre card user Luke goes from (2,0) to (11.5,12.5), riding mechanical bike, choosing minimal walking policy
 * one minute after he started the navigation, the  destination station goes offline
 * the user receive a notification about the destination station to be unavailable
 */
public class StationCloseScenario {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Administrator admin = new Administrator();
		admin.initialisation();
        ScrambleBikeScenario.runPolicy(admin, 0);

        EventWatchDog dog = new EventWatchDog();
		Thread t1 = new Thread(dog);
		t1.start();
		Thread.sleep(30000);
		Server.stations.get(1).setStatus("Offline");
		Thread.sleep(30000);
		Server.stations.get(1).setStatus("On-service");
	}

}
