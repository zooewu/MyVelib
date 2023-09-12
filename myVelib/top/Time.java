package top;
import java.util.Calendar;
/**
 * 
 * @author Zoe
 * class provides all methods concerning the computation of time
 */
public interface Time {
	/**
	 * get local time,
	 * @return: {day, hour, minute}
	 */
	public static int[] getCurrentTime(){
		int[] currentTime = new int[3];
		Calendar c = Calendar.getInstance();
		currentTime[0] = c.get(Calendar.DAY_OF_MONTH);
		currentTime[1] = c.get(Calendar.HOUR_OF_DAY);
		currentTime[2] = c.get(Calendar.MINUTE);
		return currentTime;
	}
	/**
	 * calculate the end time of riding
	 * @param start: start time of riding
	 * @param duration: riding duration
	 * @return: end time  {day, hour, minute}
	 */
	public static int[] calEndTime(int[] start, int duration) {
		int[] end= start.clone();
		if(end[2] + duration%60 >= 60) {
			end[1] += 1;
			end[2] += duration%60 - 60;
		}else {end[2] += duration%60;}
		
		if(end[1] + duration/60 >= 24) {
			end[0] += 1;
			end[1] += duration/60 - 24;
		}else {end[1] += duration/60;}
		return end;
	}
	
	/**
	 * calculate the difference between two time
	 * @param startTime
	 * @param currentTime
	 * @return: minutes
	 */
	public static int calTimeDifference(int[] startTime, int[] currentTime) {
		if(currentTime[0]>=startTime[0]) {
			return (currentTime[0]-startTime[0])*24*60+(currentTime[1]-startTime[1])*60+currentTime[2]-startTime[2];
		}else if(currentTime[0]<startTime[0]) {
			return 1*24*60+(currentTime[1]-startTime[1])*60+currentTime[2]-startTime[2];
		}
		return 0;
	}
		
	/**
	 * get current month, January = 0
	 * @return month
	 */
		public static int getCurrentMonth() {
			Calendar c = Calendar.getInstance();
			return c.get(Calendar.MONTH);
		}
		
	/**
	 *  input the array form of time and output the string form
	 * @param time
	 * @return
	 */
		public static String toString(int[] time) {
			return time[0]+"th " + time[1]+":"+time[2]+' ';
		}
}