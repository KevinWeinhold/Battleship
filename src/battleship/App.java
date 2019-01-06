package battleship;

import java.awt.BorderLayout;
import java.util.*;

public class App {
	
	private static Gui myBoard;
	private static Game myGame;
	private static Server server;
	private static Client client;
	
	private static boolean serverStatus;
	public static boolean oneReady =false;

	
	public static void main(String[] args) throws InterruptedException {
	 
		myBoard = new Gui();
		/*/Wait for host or server decision
		while(serverStatus == 0)
		{
			//U GOTTA DO WHAT U GOTTA DO
			Thread.sleep(0);
			serverStatus = myBoard.gameGetter();
		}
		
		clientServerSetup(serverStatus);
		
		//Wait for connection to start game
		while(true)
		{
			Thread.sleep(0);
			if(serverStatus == 1 && server.lockBool(false))
			{
				myGame = new Game(true);
				break;
			}
			else if(serverStatus == 2 && client.lockBool(false))
			{
				myGame = new Game(false);
				break;
			}		
		}
		
		//Waiting for ship positions to get setup
		while(true)
		{
			Thread.sleep(0);
			if(serverStatus == 1 && server.lockBool(false) && myGame.startTurns())
			{
				ServerPanel.showMessage("\nSERVER SHIPS SET UP");
				break;
			}
		
			else if(serverStatus == 2 && client.lockBool(false) && myGame.startTurns())
			{
				ServerPanel.showMessage("\nCLIENT SHIPS SET UP");
				break;
			}
		}
		*/
		//Main game loop
		}

	public static void clientServerSetup(boolean isServer)
	{
		serverStatus = isServer;
		if(!serverStatus)
		{
			//client
			client = new Client("127.0.0.1");
			Thread t = new Thread(client);
			t.start();
		}
		//server
		else
		{
			server = new Server();
			Thread t = new Thread(server);
			t.start();
		}
	}
	
	public static void setupGame() {
		myGame = new Game(serverStatus);
		//waitForSetup();
	}
	
	public static void waitForSetup() {
		if(!oneReady) {
			oneReady = true;
		}
		else {
			ServerPanel.showMessage("\nLet the games begin!\nHost's turn!");
			if(!serverStatus) {
				ServerPanel.ableToType(false);
			}
		}
	}
	
}