package top;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import bicycle.Bicycle;
import database.Server;
import multiThreads.EventWatchDog;
import station.Station;
import user.User;

/**
 *  Main function,class Administrator is the application for administrators*/

public class Administrator {


	public void initialisation(){
		Command command = new Command();
		command.initialiseBuildStationComm();
		command.initializeSlots();
		command.initAllBikes();
		command.initialiseBuildUserComm();
		
		
		for(Station station:Server.stations) 
		{
			System.out.println(station.toString());
		}

		for(Bicycle bicycle:Server.bicycles) {
			System.out.println(bicycle.getType()+" ID="+bicycle.getID()+" belongs to station"+bicycle.getStationID());
		}
		
		for(User user:Server.users) 
		{
			double[] coor = user.getCoor();
			System.out.println(user.getCard()+" User "+user.getName()+", ID "+user.getUserID());
			System.out.println("X is :"+coor[0]);
			System.out.println("Y is :"+coor[1]);
		}
		
		//PlanningPolicy policy = new UniformityPreservePolicy();
		//policy.getStationCouple(new double[]{12,15}, new double[] {100,900}, "mbike");
		
		//System.out.println(Server.bicycles.get(0).cost(Server.users.get(0), time));
		/*EventWatchDog dog = new EventWatchDog();
		Thread t1 = new Thread(dog);
		t1.start();
		command.userInteract();
		Thread.sleep(60000);
		Server.stations.get(0).setStatus("Offline");*/
	}
	
	public static ArrayList<String> fileReader(String fileName) {
		ArrayList<String> returnValue = new ArrayList<String>();
		  FileReader file = null;
		  BufferedReader reader = null;
		  
		  try {
			  // open input stream pointing at fileName
			  file = new FileReader(fileName);
			  
			  // open input buffered reader to read file line by line
			  reader = new BufferedReader(file);
			  String line = "";
			  
			  // reading input file line by line
			  while ((line = reader.readLine()) != null) {
				  returnValue.add(line);
			  }
		  } catch (Exception e) {
		      throw new RuntimeException(e);
		  } finally {
		    if (file != null) {
		      try {
		        file.close();
		        reader.close();
		       
		      } catch (IOException e) {
		    	  System.out.println("File not found: " + fileName);
		        // Ignore issues during closing 
		      }
		    }
		  }
		  return returnValue;		
	}

		 public static void writeTextFile(String fileName, String s) {
		    FileWriter output = null;
		    try {
		      	output = new FileWriter(fileName,true);
				output.write(s);
				output.close();
				if (output != null) {
					try {
						output.close();
						System.out.println("writing file " + fileName +" closing" );
					} catch (IOException e) {
						e.printStackTrace();
						System.out.println("writing file " + fileName +" IOException" );
					}
				}
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
	}

	public static void writeTextFile(String fileName, boolean a) {
		    FileWriter output = null;
		    try {
		      output = new FileWriter(fileName);
		    } catch (IOException e) {
		      e.printStackTrace();
		    } finally {
		      if (output != null) {
		        try {
		          output.close();
		        } catch (IOException e) {
		          e.printStackTrace();
		        }
		      }
		    }
	}
	public static void runTest(String fileName) throws NumberFormatException, InterruptedException {
		ArrayList<String> commandList = Administrator.fileReader("eval/"+fileName+".txt");
		var outputFile = "eval/"+fileName+"_output.txt";
		writeTextFile(outputFile,true);
		Command cmd = new Command();
		Server.globalStartTime = Time.getCurrentTime();
		for(String sentence:commandList) {
			String[] tokens= sentence.trim().replace(",","%").replace(" ","%").split("%");
			switch(tokens[0]) {
			case "setup":	cmd.setup(tokens);
							break;
			case "addUser":	cmd.addUser(tokens);
							break;
			case "offline": cmd.offline(tokens);
							break;
			case "online":	cmd.online(tokens);
							break;
			case "rentBike":cmd.rentBike(tokens);
							break;
			case "returnBike":cmd.returnBike(tokens);
							break;
			case "displayStation": writeTextFile(outputFile,cmd.displayStation(tokens));
									break;
			case "displayUser": writeTextFile(outputFile,cmd.displayUser(tokens));
								break;
			case "sortStation":writeTextFile(outputFile,cmd.sortStation(tokens));
								break;
			case "display"	:writeTextFile(outputFile,cmd.display());
							break;
			case "pause"	:Thread.sleep(1000 * Integer.parseInt(tokens[1]));
							break;
			case "move"		:cmd.move(tokens);
							break;
			case "msg"		:System.out.println(sentence.substring(4));
							break;
			default: System.err.println("command " + tokens[0] +" not found");
			
			}
			
		}
	}
	public static void main(String[] args) throws FileNotFoundException, NumberFormatException, InterruptedException{

		Command cmd = new Command();
		EventWatchDog dog = new EventWatchDog();
		Thread t1 = new Thread(dog);
		t1.start();
		Server.globalStartTime = Time.getCurrentTime();
		Scanner sc = new Scanner(System.in);
		while(true) {
			//ArrayList<String> commandList = new ArrayList<String>();
			System.out.println("input command line>>>>>>>>>");
			String commandLine = sc.nextLine();
			String[] tokens = commandLine.trim().replace(",","%").replace(" ","%").split("%");
			switch(tokens[0]) {
				case "setup":	cmd.setup(tokens);
								break;
				case "addUser":	cmd.addUser(tokens);
								break;
				case "offline": cmd.offline(tokens);
								break;
				case "online":	cmd.online(tokens);
								break;
				case "rentBike":cmd.rentBike(tokens);
								break;
				case "returnBike":cmd.returnBike(tokens);
								break;
				case "displayStation": writeTextFile("eval/output"+Time.getCurrentTime()+".txt",cmd.displayStation(tokens));
										break;
				case "displayUser": writeTextFile("eval/output"+Time.getCurrentTime()+".txt",cmd.displayUser(tokens));
									break;
				case "sortStation":writeTextFile("eval/output"+Time.getCurrentTime()+".txt",cmd.sortStation(tokens));
									break;
				case "display"	:writeTextFile("eval/output"+Time.getCurrentTime()+".txt",cmd.display());
								break;
				case "pause"	:Thread.sleep(1000*Integer.parseInt(tokens[1]));
								break;
				case "move"		:cmd.move(tokens);
								break;
				case "msg"		:System.out.println(commandLine.substring(4));
								break;
				case "runtest"  :runTest(tokens[1]);
								break;
				
				default: System.err.println("command " + tokens[0] +" not found");
				
				
			}
		}
	}
}
