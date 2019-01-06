package battleship;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
@SuppressWarnings("serial")
public class ServerPanel extends JPanel{
	
	private static JTextField userText;
	private static JTextArea chatWindow;
	private static final int DEFAULT_WIDTH = 80;
	private static final int DEFAULT_HEIGHT = 80;
	private static boolean isClient;
	private static boolean readyToSend;
	
	ServerPanel(boolean tof)
	{
		isClient = tof;
		setLayout(new BorderLayout());
		userText = new JTextField();
		userText.setEditable(false);
		userText.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					
					Game.positionInput(event.getActionCommand());
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

	public static void showMessage(final String text) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						chatWindow.append(text);
					}
				}
			);
	}
		
	public static void ableToType(final boolean tof) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						userText.setEditable(tof);
					}
				}
			);
	}
	
	public void ableToSend(final boolean tof) {
		readyToSend = tof;
	}
}
