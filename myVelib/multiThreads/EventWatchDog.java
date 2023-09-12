package multiThreads;

import java.util.Arrays;
import java.util.Iterator;

import bicycle.Bicycle;
import database.RentEvent;
import database.Server;
import top.Command;
import top.Time;

/**
 * Class EventWatchDog aims to traverse all the existing rentEvent 
 * output some necessary information
 * and check if some of them need to rent a bike, return a bike or already arrive the destination
 *
 */
public class EventWatchDog implements Runnable {
	Command command = new Command();
	@Override
	public void run() {

		// Observing if there is an event
		while(true) {

			// iterate on rent events
			Iterator<RentEvent> iterator = Server.rentEvents.iterator();
			while(iterator.hasNext()) 
			{	
			    RentEvent rentEvent = iterator.next();
			    rentEvent.getInformation();
			    if(rentEvent.getState() == 2 && (Time.calTimeDifference(rentEvent.getEndTime(), Time.getCurrentTime())>=0)) 
			    {
						Bicycle bike = rentEvent.getBike();

						// compute duration
						int duration = Time.calTimeDifference(rentEvent.getStartTime(), Time.getCurrentTime());

						// compute cost if parking bike
						if(command.parkBike(rentEvent)) {
							double cost = bike.cost(rentEvent.getUser(), duration);
							rentEvent.getUser().getUserBalance().update(duration, cost);
							System.out.println("cost of this riding: "+cost+" euros");
							System.out.println("user's time balance = "+rentEvent.getUser().getTimeBalance()+" minutes");
							rentEvent.toDestEvent();
							System.out.println(rentEvent.toString());
							}
				}

				// report when beginning or ending an itinerary
				if(Arrays.equals(rentEvent.getEndTime(),Time.getCurrentTime()))
				{
					if(rentEvent.getState() == 1) {
						command.rentBike(rentEvent);
						System.out.println("User " + rentEvent.getUser().getName() + " ID:" + rentEvent.getUser().getUserID()+" rent a bike and start riding");
						System.out.println(rentEvent.toString());
					}
					else if(rentEvent.getState()==3) {
						System.out.println("User " + rentEvent.getUser().getName()+" ID:" + rentEvent.getUser().getUserID()+" arrive to his/her destination");
						iterator.remove();
					}
				}

			
			}
			try {
				// scan every 30 seconds to avoid overcharged
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
}