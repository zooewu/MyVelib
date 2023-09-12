package test;

import org.junit.Ignore;
import org.junit.Test;
import database.Server;
import multiThreads.EventWatchDog;
import scenarios.ScrambleBikeScenario;
import top.Administrator;
import top.Command;

class CommandTest {

	@Ignore
	void mostUsedSortingTest() throws InterruptedException {
		Administrator admin = new Administrator();
		Command command = new Command();
		admin.initialisation();
        ScrambleBikeScenario.runPolicy(admin, 0);
        EventWatchDog dog = new EventWatchDog();
		Thread t1 = new Thread(dog);
		t1.start();
		Thread.sleep(30000);
		Server.stations.get(1).setStatus("Offline");
		Thread.sleep(30000);
		Server.stations.get(1).setStatus("On-service");
		command.mostUsedStation();
	}
	
	@Test
	void mostOccupiedSortingTest() throws InterruptedException {
		Administrator admin = new Administrator();
		Command command = new Command();
		admin.initialisation();
        ScrambleBikeScenario.runPolicy(admin, 0);
        EventWatchDog dog = new EventWatchDog();
		Thread t1 = new Thread(dog);
		t1.start();
		Thread.sleep(30000);
		Server.stations.get(1).setStatus("Offline");
		Thread.sleep(30000);
		Server.stations.get(1).setStatus("On-service");
		command.mostOccupiedStation(new int[] {23,23,15}, new int[] {23,23,17});
	}

}
