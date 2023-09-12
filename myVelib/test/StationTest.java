package test;

import static org.junit.Assert.*;

import org.junit.Test;


import org.junit.Ignore;

import top.Administrator;
import top.Time;
import station.Station;

import database.Server;

class StationTest {
	
	@Ignore
	void testGetRateOccupation1() throws InterruptedException {
		Administrator adm = new Administrator();
		adm.initialisation();
		Thread.sleep(1000);
		int[] ts1 = Time.getCurrentTime();
		Thread.sleep(120000);
		int[] te1= Time.getCurrentTime();
		for(Station station:Server.stations) {
			System.out.println(station.getRateOccupation(ts1, te1));
		}
	}
	
	
	@Ignore
	void testGetRateOccupation2() throws InterruptedException {
		Administrator adm = new Administrator();
		adm.initialisation();
		Thread.sleep(1000);
		int[] ts1 = Time.getCurrentTime();
		Thread.sleep(100000);
		Server.stations.get(0).getSlots().get(0).takeBicycle();
		Thread.sleep(120000);
		int[] te1= Time.getCurrentTime();
		for(Station station:Server.stations) {
			System.out.println(station.getRateOccupation(ts1, te1));
		}
		//System.out.println(Server.stations.get(0).getRateOccupation(ts1, te1));
		//for(SlotOperation operation:Server.stations.get(0).getSlots().get(0).getOperations()) {
		//	System.out.println(operation.toString());
		// }
	}
	
	@Test
	void testGetRateOccupation3() throws InterruptedException {
		Administrator adm = new Administrator();
		adm.initialisation();
		Thread.sleep(1000);
		int[] ts1 = Time.getCurrentTime();
		Thread.sleep(150000);
		Server.stations.get(0).getSlots().get(0).takeBicycle();
		Server.stations.get(2).getSlots().get(7).takeBicycle();
		Server.stations.get(3).getSlots().get(3).takeBicycle();
		Thread.sleep(300000);
		Server.stations.get(0).getSlots().get(4).takeBicycle();
		Server.stations.get(7).getSlots().get(2).takeBicycle();
		Server.stations.get(4).getSlots().get(0).takeBicycle();
		int[] te1= Time.getCurrentTime();
		Thread.sleep(60000);
		Server.stations.get(0).getSlots().get(3).takeBicycle();
		Server.stations.get(7).getSlots().get(8).takeBicycle();
		Server.stations.get(4).getSlots().get(1).takeBicycle();
		for(Station station:Server.stations) {
			System.out.println(station.getRateOccupation(ts1, te1));
		}
	}
	

}
