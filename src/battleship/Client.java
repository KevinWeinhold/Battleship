package battleship;

import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.io.*;

@SuppressWarnings("serial")
public class Client extends JPanel{

	private String serverIP;
	private Socket connection;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private JTextField userText;
	private JTextArea chatWindow;
		
	private static final int DEFAULT_WIDTH = 80;
	private static final int DEFAULT_HEIGHT = 80;
	
	public Client(String host)
	{
		serverIP = host;
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
	
	public void startRunning(){
		try
		{
			connectToServer();
			setupStreams();
			whileChatting();
		}catch(EOFException eofException) {
			showMessage("\n Server ended the connection!");
		}catch(IOException ioException) {
				ioException.printStackTrace();
		}finally
		{
			closeCrap();
		}
	}
	
	//connect to server
	private void connectToServer() throws IOException{
		showMessage("Attempting Connection...\n");
		connection = new Socket(InetAddress.getByName(serverIP),4444);
		showMessage("Connected to :" + connection.getInetAddress().getHostName());
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
		}while(!message.equals("SERVER - END"));
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
			output.writeObject("CLIENT -  " + message);
			output.flush();
			showMessage("\nCLIENT - " + message);
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