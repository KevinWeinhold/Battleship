package battleship;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")

//Main Frame that houses all the panels
public class Gui extends JFrame{
	private GamePanel myGamePanel;
	public Gui ()
	{
		super("Battleship");
		myGamePanel = new GamePanel();
		start();
	}
	
	public void start()
	{
		setVisible(true);
		buildGUI();
	}
	
	public void buildGUI()
	{
		//Master panel to hold all other JPanels
		JPanel masterPanel = new JPanel();
		masterPanel.add(myGamePanel);
		//add(masterPanel);
		add(myGamePanel);
		pack(); //pack "resizes" the panel so things fit in it.
	}
}
