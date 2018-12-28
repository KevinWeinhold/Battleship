package battleship;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.*;

@SuppressWarnings("serial")
public class Server extends JPanel{

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket server;
	private Socket connection;
	private JTextField userText;
	private JTextArea chatWindow;
	
	private static final int DEFAULT_WIDTH = 80;
	private static final int DEFAULT_HEIGHT = 80;
	
	public Server()
	{
		setLayout(new BorderLayout());
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					sendMessage(event.getActionCommand());
					userText.setText("");
				}
			}
		);
		this.add(userText,BorderLayout.SOUTH);
		chatWindow = new JTextArea();
		this.add(chatWindow,BorderLayout.NORTH);
		chatWindow.setEditable(false);
		this.add(new JScrollPane(chatWindow));
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void startRunning()
	{
		try
		{
			server = new ServerSocket(4444,100);
			while(true)
			{
				try
				{
					waitForConnection();
					setupStreams();
					whileChatting();
				}catch(EOFException eofException) {
					showMessage("\n Server ended the connection!");
				}finally
				{
					closeCrap();
				}
			}
		}
		catch(IOException i)
		{
			i.printStackTrace();
		}
	}
	
	private void waitForConnection() throws IOException {
		showMessage(" Waiting for someone to connect... \n");
		connection = server.accept();
		showMessage(" Now connected to "+connection.getInetAddress().getHostName());
	}
	
	private void setupStreams() throws IOException {
		output = new ObjectOutputStream(connection.getOutputStream());
		output.flush();
		input = new ObjectInputStream(connection.getInputStream());
		showMessage("\n Streams are now setup! \n");
	}
	
	private void whileChatting() throws IOException {
		String message = "You are now connected!";
		sendMessage(message);
		ableToType(true);
		do {
			try {
				message = (String) input.readObject();
				showMessage("\n" + message);
			}catch(ClassNotFoundException classNotFoundException) {
				showMessage("\n idk wtf that user sent!");
			}
		}while(!message.equals("CLIENT - END"));
	}
	
	private void closeCrap()
	{
		showMessage("\n Closing connections...\n");
		try {
			output.close();
			input.close();
			connection.close();
		}catch(IOException ioException) {
			ioException.printStackTrace(); 
		}
	}
	
	private void sendMessage(String message){
		try {
			output.writeObject("SERVER -  " + message);
			output.flush();
			showMessage("\nSERVER - " + message);
		}catch(IOException ioException) {
			System.out.println("\n ERROR CANT SEND THAT MESSAGE");
		}
	}
	
	private void showMessage(final String text) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						chatWindow.append(text);
					}
				}
			);
	}
	
	private void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						userText.setEditable(tof);
					}
				}
			);
	}
}
