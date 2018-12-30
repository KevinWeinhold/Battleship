package battleship;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.*;

@SuppressWarnings("serial")
public class Client implements Runnable{

	private String serverIP;
	private Socket connection;
	private static ObjectOutputStream output;
	private ObjectInputStream input;
	private boolean game;
	
	public Client(String host)
	{
		serverIP = host;
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		ServerPanel.showMessage("Attempting Connection...\n");
		connection = new Socket(InetAddress.getByName(serverIP),4444);
		ServerPanel.showMessage("Connected to :" + connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		ServerPanel.showMessage("\n Streams are now setup! \n");
		game = true;
	}
	
	private void whileChatting() throws IOException {
		String message = "You are now connected!";
		sendMessage(message);
		ServerPanel.ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				ServerPanel.showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException) {
				ServerPanel.showMessage("\n idk wtf that user sent!");
			}
		}while(!message.equals("SERVER - END"));
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
			output.writeObject("CLIENT -  " + message);
			output.flush();
			ServerPanel.showMessage("\nCLIENT - " + message);
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
		// TODO Auto-generated method stub
		try
		{
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofException) {
			ServerPanel.showMessage("\n Server ended the connection!");
		}catch(IOException ioException) {
				ioException.printStackTrace();
		}finally
		{
			closeCrap();
		}
	}
}