package battleship;

import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.*;

@SuppressWarnings("serial")
public class Server implements Runnable{

	private static ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	private static AtomicBoolean atomicBool = new AtomicBoolean(false);
	
	public void waitForConnection() throws IOException {
		ServerPanel.showMessage(" Waiting for someone to connect... \n");
		connection = server.accept();
		ServerPanel.showMessage(" Now connected to "+connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException{
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		ServerPanel.showMessage("\n Streams are now setup! \n");
		App.setupGame();
	}
	
	private void whileChatting() throws IOException {
		ServerPanel.ableToType(true);
		String message = "";
		do {
			try {
				message = (String) input.readObject();
				ServerPanel.showMessage("\n" + message);
				if(message.equals("CLIENT - CLIENT is ready!"))
				{
					//atomicBool.set(true);
					App.waitForSetup();
				}
				else if(message.contains("FIRE ON:")) {
					Game.getMove(message);
				}
			}catch(ClassNotFoundException classNotFoundException) {
				ServerPanel.showMessage("\n idk wtf that user sent!");
			}
		}while(message != "CLIENT - END");
	}
	
	private void closeCrap()
	{
		ServerPanel.showMessage("\n Closing connections...\n");
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException) {
			ioException.printStackTrace(); 
		}
	}
	
	public static void sendMessage(String message){
		try {
			output.writeObject("SERVER - " + message);
			output.flush();
			ServerPanel.showMessage("\nSERVER - " + message);
		}catch(IOException ioException) {
			System.out.println("\n ERROR CANT SEND THAT MESSAGE");
		}
	}
	
	public static boolean lockBool(boolean tof)
	{
		return atomicBool.getAndSet(tof);
	}
	@Override
	
	public void run() {
		try
		{
			server = new ServerSocket(4444,100);
			try
			{
				waitForConnection();
				setupStreams();
				whileChatting();
			}catch(EOFException eofException) {
				ServerPanel.showMessage("\n Server ended the connection!");
			}finally
			{
				closeCrap();
			}
		}
		catch(IOException i)
		{
			i.printStackTrace();
		}
	}
}
