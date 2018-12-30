package battleship;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")

//Main Frame that houses all the panels
public class Gui extends JFrame implements ActionListener{
	private static final int DEFAULT_WIDTH = 500;
	private static final int DEFAULT_HEIGHT = 500;
	private GamePanel myGamePanel;
	private JPanel masterPanel;
	private Server serverPanel;
	private JTextArea textArea;
	private JButton button1;
	private JButton button2;
	
	private static boolean startGame; 
	private int isClient;
	
	public Gui ()
	{
		super("Battleship");
		
		masterPanel = new JPanel();
		myGamePanel = new GamePanel();
		
		button1 = new JButton("Server");
		button1.setActionCommand("host");
		
		button2 = new JButton("Client");
		button2.setActionCommand("client");
		
		button1.addActionListener(this);
		button2.addActionListener(this);
		
		startGame = false;
		isClient = 0;
		
		start();
	}
	
	public void start()
	{
		//Setup Main Frame
		setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setLayout(new BorderLayout());
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		//Add on other panels
		buildGUI();
	}
	
	public void buildGUI()
	{
		add(button1, BorderLayout.PAGE_START);
		add(button2, BorderLayout.PAGE_END);
		add(myGamePanel, BorderLayout.CENTER);
		pack();//pack "resizes" the panel so things fit in it.
	}
	
	private void addChat(boolean isClient)
	{
		remove(button1);
		remove(button2);
		revalidate();
		//Client
		if(isClient)
		{
			ServerPanel kevin = new ServerPanel(true);
			add(kevin, BorderLayout.SOUTH);
			pack();
		}
		//Server
		else
		{
			ServerPanel evan = new ServerPanel(false);
			add(evan, BorderLayout.SOUTH);
			pack();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
	    if ("host".equals(e.getActionCommand())) {
	    	//Server
	        addChat(false);
	        isClient = 1;
	    } else {
	    	//Client
	        addChat(true);
	        isClient = 2;
	    }
	}
	
	public int gameGetter()
	{
		return isClient;
	}

	
}
