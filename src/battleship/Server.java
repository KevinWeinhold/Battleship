package battleship;

import java.net.*;
import java.io.*;

@SuppressWarnings("serial")
public class Server implements Runnable{

	private static ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	
	private boolean game;
	
	public void waitForConnection() throws IOException {
		ServerPanel.showMessage(" Waiting for someone to connect... \n");
		connection = server.accept();
		ServerPanel.showMessage(" Now connected to "+connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		ServerPanel.showMessage("\n Streams are now setup! \n");
	}
	
	private void whileChatting() throws IOException {
		String message = "You are now connected!";
		sendMessage(message);
		ServerPanel.ableToType(true);
		do {
			try {
				message = (String)input.readObject();
				ServerPanel.showMessage("\n" + message);
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
			output.writeObject("SERVER -  " + message);
			output.flush();
			ServerPanel.showMessage("\nSERVER - " + message);
		}catch(IOException ioException) {
			System.out.println("\n ERROR CANT SEND THAT MESSAGE");
		}
	}
	
	public boolean getGame()
	{
		return game;
	}

	@Override
	public void run() {
		System.out.print("here");
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
