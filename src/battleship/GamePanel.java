package battleship;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class GamePanel extends JPanel{
	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 600;
	
	public GamePanel()
	{
		super();
		this.setPreferredSize(new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT));
		this.setVisible(true);
		this.setBackground(Color.black);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawString("This is my custom panel", 10, 20);
	}
}
