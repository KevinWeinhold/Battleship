package battleship;

import java.awt.BorderLayout;
import java.util.*;

public class App {
	
	private static Gui myBoard;
	private static Game myGame;
	private static Server evan;
	
	private static int serverStatus;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	 
		myBoard = new Gui();
		while(serverStatus == 0)
		{
			serverStatus = myBoard.gameGetter();
		}
		clientServerSetup(serverStatus);
		}

	private static void clientServerSetup(int status)
	{
		if(status == 2)
		{
			//client
			Client client = new Client("127.0.0.1");
			Thread t = new Thread(client);
			t.start();
		}
		//server
		else if(status == 1)
		{
			Server server = new Server();
			Thread t = new Thread(server);
			t.start();
		}
	}
}