package battleship;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")

//Main Frame that houses all the panels
public class Gui extends JFrame{
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 800;
	private GamePanel myGamePanel;
	private JPanel masterPanel;
	private Server serverPanel;
	private JTextArea textArea;
	public Gui ()
	{
		super("Battleship");
		masterPanel = new JPanel();
		myGamePanel = new GamePanel();
		serverPanel = new Server();
		start();
	}
	
	public void start()
	{
		setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		setLayout(new BorderLayout());
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		buildGUI();
	}
	
	public void buildGUI()
	{
		//Master panel to hold all other JPanels
		//masterPanel.setSize(800,800);
		//masterPanel.add(myGamePanel);
		//add(masterPanel);
		add(myGamePanel, BorderLayout.NORTH);
		//add(serverPanel, BorderLayout.SOUTH);
		//pack(); //pack "resizes" the panel so things fit in it.
	}
	
	public void addChat(boolean isClient)
	{
		if(isClient)
		{
			Client kevin = new Client("127.0.0.1");
			add(kevin);
			pack();
			kevin.startRunning();
		}
		else
		{
			Server evan = new Server();
			add(evan);
			pack();
			evan.startRunning();
		}
	}
	
}
