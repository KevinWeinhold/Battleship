package battleship;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class Game {
	
	private boolean isTurn;
	private static boolean startTurns;
	private static String playerStatus;
	private static int[][] map;
	private static int[] ships;
	private static int index;
	private static Dictionary table;
	
	//Game class that handles turns and phases of the game
	public Game(boolean tof)
	{
		playerStatus = "CLIENT";
		if(tof) {
			playerStatus = "SERVER";
		}
		map = new int[9][9];
		ships = new int[] {3,3,4,4,5};
		startTurns = false;
		isTurn = tof;
		table = new Hashtable();
	}
	
	
	
	public static void positionInput(String command) {
		if(startTurns) {
			makeMove(command);
		}
		else {
			String[] parts = command.split(":");
			try {
				if(assignShips(returnIndex(parts[0]),returnIndex(parts[1]))) {
					index += 1;
					//SET HOW MANY SHIPS HERE
					if(index == 2) {
						printMap();
						
						startTurns = true;
						sendMessage(playerStatus +" is ready!");
						if(!App.oneReady) {
							ServerPanel.ableToType(false);
						}
						App.waitForSetup();
					}
				}
			}
			catch (IndexOutOfBoundsException e){
				ServerPanel.showMessage("\n Invalid form. Must be like A1:A4");
			}	
		}
	}
	
	private static void makeMove(String command) {
		int[] wrong = {-1,-1};
		if(!returnIndex(command).equals(wrong)) {
			sendMessage("FIRE ON: " + command);
			ServerPanel.ableToType(false);
		}
		else {
			ServerPanel.showMessage("\nIncorrect Format");
		}
	}
	
	public static void getMove(String command) {
			String clean_command = command.split(": ")[1];
			int i = returnIndex(clean_command)[0];
			int j = returnIndex(clean_command)[1];
			if(map[i][j] == 1) {
				sendMessage("DIRECT HIT!");
				map[i][j] = 2;
				isShip("" + i + j);
				printMap();
				printTable();
			}
			else {
				sendMessage("THATS A MISS");
			}
			ServerPanel.ableToType(true);
		}
		
	@SuppressWarnings("unused")
	private static void sendMessage(String message)
	{
		if(playerStatus == "CLIENT")
		{
			Client.sendMessage(message);
		}
		else
		{
			Server.sendMessage(message);
		}
	}
	
	/*Return an array representing the row index and column
	 * index of a given position. Return -1 if incorrect command
	 * or not found. Example:
	 * 
	 *  position = 'A4'
	 *  returnIndex(position) = [0,3]
	 */
	private static int[] returnIndex(String position) {
		String new_pos = position.toLowerCase();
		final String alphabet = "abcdefghi";
		try {
			int num1 = alphabet.indexOf(new_pos.charAt(0));
			int num2 = Character.getNumericValue(new_pos.charAt(1))-1;
			if(num1 == -1 || num2 == -1) {
				return new int[] {-1, -1};
			}
			else {
				return new int[] {num1, num2};
			}
		}
		catch (IndexOutOfBoundsException e){
			ServerPanel.showMessage("\n Invalid form. Must be like A1 ");
			return new int[] {-1, -1};
		}	
	}
	
	/*Assign the ships in order as they appear in the ships array.
	 * Assign a 1 for every place in the map where a ship appears.
	 * Ships may not be placed diagonally. Return true if good.
	 * Return False if not.
	 */
	@SuppressWarnings("unchecked")
	private static boolean assignShips(int[] coord1, int[] coord2) {
		int i1 = coord1[0];
		int j1 = coord1[1];
		int i2 = coord2[0];
		int j2 = coord2[1];
		String result = "";
		
		int shipLength = ships[index]-1;
		
		int[][] newMap = map;
		//Assign on the row
		if(i1 == i2 && j1 < j2 && j2 - j1 == shipLength && j1 + shipLength <= 9){
			for(int i = j1; i < j1+shipLength+1; i ++) {
				if(!fillCheck(newMap, i1, i)) {
					ServerPanel.showMessage("\n Theres already a ship in this area.");
					return false;
				}
			}
			result += "a" + i1 + j1 + j2;
		}
		//Assign on the same column
		else if(j1 == j2 && i1 < i2 && i2 - i1 == shipLength && i1 + shipLength <= 9){
			for(int i = i1; i < i1+shipLength+1; i ++) {
				if(!fillCheck(newMap, i, j1)) {
					ServerPanel.showMessage("\n Theres already a ship in this area.");
					return false;
				}
			}
			result += "d" + j1 + i1 + i2;
		}
		else {
			ServerPanel.showMessage("\n Must assign ship of length " + ships[index] + " on same row or column.");
			return false;
		}
			map = newMap;
			ServerPanel.showMessage("\n Successfully assigned ship " + (index + 1) + "/5");
			table.put(result, shipLength+1);
			return true; 
		}
	
	/*Check to see if the space in the map has a one, if it doesn't
	 * assign it to one
	 */
	private static boolean fillCheck(int[][] map_buff, int i, int j) {
		if(map_buff[i][j] == 1) {
			return false;
		}
		else {
			map_buff[i][j] = 1;
			return true;
		}
	}
	
	private static void isShip(String coord) {
		for(Enumeration i = table.keys(); i.hasMoreElements();) {
			String key = (String)i.nextElement();
			if(isBetween(coord,key)) {
				int  new_val = (int) table.get(key) - 1;
				if(new_val == 0) {
					sendMessage("YOU SUNK MY BATTLESHIP!!!");
					table.remove(key);
				}
				table.put(key, new_val);
				break;
			}
		}
	}
	
	private static boolean isBetween(String coord, String key) {
		int i = Character.getNumericValue(coord.charAt(0));
		int j = Character.getNumericValue(coord.charAt(1));
		int start = Character.getNumericValue(key.charAt(2));
		int end = Character.getNumericValue(key.charAt(3));
		//Check for in between on row
		if(key.charAt(0) == 'a' && coord.charAt(0) == key.charAt(1)) {
			if(start <= j && j <= end) {
				return true;
			}
		}
		//Check for in between on column
		else if(key.charAt(0) == 'd' && coord.charAt(1) == key.charAt(1)) {
			if(start <= i && i <= end) {
				return true;
			}
		}
		
		return false;
	}
	
	private static void printMap() {
		for(int i = 0; i < map.length; i ++) {
			for(int j = 0; j < map[i].length; j ++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.print("\n");
		}
		System.out.print("\n");
	}
	
	private static void printTable() {
		for(Enumeration i = table.keys(); i.hasMoreElements();) {
			String key = (String) i.nextElement();
			System.out.println(key + ": " + table.get(key));
		}
	}
}